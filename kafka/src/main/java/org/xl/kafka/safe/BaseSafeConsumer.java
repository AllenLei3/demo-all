package org.xl.kafka.safe;

import org.apache.kafka.clients.consumer.CommitFailedException;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.clients.consumer.OffsetCommitCallback;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.InterruptException;
import org.apache.kafka.common.errors.WakeupException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalLong;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author xulei
 */
public abstract class BaseSafeConsumer<K, V> {

    protected static final Logger LOG = LoggerFactory.getLogger(BaseSafeConsumer.class);
    private long lastPollTime;
    private long lastCommitTime;
    private int maxPollIntervalMillis = 180 * 1000 * 1000;
    private KafkaConsumer<K, V> kafkaConsumer;
    private final OffsetTracker offsetTracker;
    private final BlockingQueue<PartitionOffset> unAppliedAckList;
    private final List<TopicPartition> assignedPartitions;
    private final ConsumerRebalanceListener internalReBalanceListener;
    private final AtomicBoolean stop = new AtomicBoolean(false);
    private final AtomicBoolean init = new AtomicBoolean(false);

    public BaseSafeConsumer(int offsetTrackerPageSize, int offsetTrackerMaxOpenPagesPerPartition) {
        checkArgument(offsetTrackerPageSize < 0, "offsetTrackerPageSize不能小于0");
        checkArgument(offsetTrackerMaxOpenPagesPerPartition < 0, "offsetTrackerMaxOpenPagesPerPartition不能小于0");
        this.offsetTracker = new OffsetTracker(offsetTrackerPageSize, offsetTrackerMaxOpenPagesPerPartition);
        this.unAppliedAckList = new LinkedBlockingQueue<>();
        this.assignedPartitions = new ArrayList<>();
        ConsumerRebalanceListener listener = getReBalanceListener();
        this.internalReBalanceListener = new ConsumerRebalanceListener() {
            @Override
            public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
                handlerAck(true);
                if (listener != null) {
                    listener.onPartitionsRevoked(partitions);
                }
            }
            @Override
            public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
                // 更新缓存的分区
                assignedPartitions.clear();
                assignedPartitions.addAll(partitions);
                LOG.info("Kafka consumer partitions assigned: {}", partitions);
                offsetTracker.reset();
                // 调用用户自定义监听器
                if (listener != null) {
                    listener.onPartitionsAssigned(partitions);
                }
            }
        };
    }

    public void start() {
        if (!init.compareAndSet(false, true)) {
            return;
        }
        String topic = getTopic();
        checkArgument(topic == null || topic.isEmpty(), "topic不能为空");
        Thread consumerThread = new Thread(() -> {
            // 更新消费者配置
            Properties properties = getConsumerProperties();
            if (properties.containsKey(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG)) {
                maxPollIntervalMillis = Integer.parseInt(properties.getProperty(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG));
            } else {
                properties.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, maxPollIntervalMillis);
            }
            properties.put(ConsumerConfig.GROUP_ID_CONFIG, getGroupId());
            properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
            try {
                this.kafkaConsumer = new KafkaConsumer<>(properties);
                this.kafkaConsumer.subscribe(Collections.singleton(topic), this.internalReBalanceListener);
                while (!stop.get()) {
                    try {
                        // 判断是否可以进行位移提交
                        handlerAck(false);
                        this.lastPollTime = System.currentTimeMillis();
                        ConsumerRecords<K, V> records = kafkaConsumer.poll(getPollTimeout());
                        // 跟踪已拉取到的消息
                        trackRecords(records);
                        // 处理消息
                        processRecords(records);
                    } catch (WakeupException | InterruptException | InterruptedException e) {
                        if (!stop.get()) {
                            throw new IllegalStateException("consumer消息处理异常中断！", e);
                        }
                        return;
                    }
                }
            } catch (WakeupException | InterruptException e) {
                if (!stop.get()) {
                    throw new IllegalStateException("consumer异常中断！", e);
                }
            } catch (Throwable t) {
                LOG.error("consumer异常中止！", t);
            } finally {
                this.kafkaConsumer.close();
                LOG.info("consumer subscribed topic:{} with groupId:{} closed.", getTopic(), getGroupId());
            }
        });
        consumerThread.setName("Kafka-consumer[" + topic + "]-" + this.getClass().getSimpleName());
        consumerThread.start();
    }

    public void ack(PartitionOffset partitionOffset) {
        unAppliedAckList.add(partitionOffset);
    }

    /**
     * 通知偏移跟踪器新的ack，并在有安全偏移可以提交时进行提交。
     */
    private void handlerAck(boolean sync) {
        int size = unAppliedAckList.size();
        if (size == 0) {
            Map<TopicPartition, OffsetAndMetadata> waitCommit = offsetTracker.drainNotFullOffset(getTopic());
            // todo 这里记录一下上次提交的位移偏移量，小于等于则不再重复提交
            if (!waitCommit.isEmpty() && System.currentTimeMillis() - lastCommitTime > 1000) {
                kafkaConsumer.commitAsync(waitCommit, getOffsetCommitCallback());
                lastCommitTime = System.currentTimeMillis();
            }
            return;
        }
        List<PartitionOffset> offsets = new ArrayList<>(size);
        // 从队列中检索并删除最多给定数量的可用元素
        unAppliedAckList.drainTo(offsets, size);
        // 提交可用位移
        Map<TopicPartition, OffsetAndMetadata> offsetsToCommit = new HashMap<>();
        for (PartitionOffset offset : offsets) {
            OptionalLong offsetToCommit = offsetTracker.ack(offset.getPartition(), offset.getOffset());
            if (offsetToCommit.isPresent()) {
                offsetsToCommit.put(new TopicPartition(getTopic(), offset.getPartition()), new OffsetAndMetadata(offsetToCommit.getAsLong()));
            }
        }
        if (offsetsToCommit.isEmpty()) {
            return;
        }
        if (sync) {
            try {
                kafkaConsumer.commitSync(offsetsToCommit);
                lastCommitTime = System.currentTimeMillis();
            } catch (CommitFailedException e) {
                //
            }
        } else {
            kafkaConsumer.commitAsync(offsetsToCommit, getOffsetCommitCallback());
            lastCommitTime = System.currentTimeMillis();
        }
    }

    private void trackRecords(ConsumerRecords<K, V> records) throws InterruptedException {
        for (ConsumerRecord<K, V> record : records) {
            while (!offsetTracker.track(record.partition(), record.offset())) {
                LOG.error("分区{}的偏移量跟踪器已满!等待中......", record.partition());
                handlerAck(false);
                Thread.sleep(10);
                keepConnectionAlive();
            }
        }
    }

    /**
     * b尝试通过调用poll()使与Kafka的连接保持活动状态，但它首先调用pause()以避免接收poll()上的记录。
     */
    private void keepConnectionAlive() {
        if (System.currentTimeMillis() - lastPollTime < (int) (0.7 * maxPollIntervalMillis)) {
            return;
        }
        boolean reBalanceHappened = true;
        while (reBalanceHappened) {
            List<TopicPartition> copyOfAssignedPartitions = new ArrayList<>(assignedPartitions);
            try {
                // 暂停接收消息
                kafkaConsumer.pause(copyOfAssignedPartitions);
                reBalanceHappened = false;
                lastPollTime = System.currentTimeMillis();
                kafkaConsumer.poll(0);
                // 也许分区已重新平衡，所以使用强制恢复以确保使用者可以从分配给它的所有分区中轮询记录。
                forceResume(copyOfAssignedPartitions);
            } catch (IllegalStateException e) {
                // 如果未将提供的分区之一分配给此使用者，则会引发异常，因此重新平衡在此过程中发生。
                forceResume(copyOfAssignedPartitions);
            }
        }
    }

    /**
     * 从提供的分区强制恢复轮询
     */
    private void forceResume(List<TopicPartition> topicPartitions) {
        for (TopicPartition topicPartition : topicPartitions) {
            try {
                kafkaConsumer.resume(Collections.singletonList(topicPartition));
            } catch (IllegalStateException e) {
                LOG.warn("无法恢复消费者从主题[{}],分区[{}]进行轮询。", topicPartition.topic(), topicPartition.partition());
            }
        }
    }

    private static void checkArgument(boolean result, String msg) {
        if (result) {
            throw new IllegalArgumentException(msg);
        }
    }

    public void close() {
        this.stop.set(true);
        if (this.kafkaConsumer != null) {
            this.kafkaConsumer.wakeup();
            this.kafkaConsumer.close();
        }
    }

    //-------------------------------------- Abstract Method --------------------------------------

    public abstract Properties getConsumerProperties();

    public abstract String getTopic();

    public abstract String getGroupId();

    public abstract OffsetCommitCallback getOffsetCommitCallback();

    public abstract ConsumerRebalanceListener getReBalanceListener();

    public abstract Long getPollTimeout();

    public abstract void processRecords(ConsumerRecords<K, V> records);
}
