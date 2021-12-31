package org.xl.kafka.safe;

import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.OptionalLong;

/**
 * @author xulei
 */
public class OffsetTracker {

    private static final Logger LOG = LoggerFactory.getLogger(OffsetTracker.class);

    private final int pageSize;
    private final int maxOpenPagesPerPartition;
    private volatile Map<Integer /*partition*/, PartitionTracker> partitionTrackers;

    /**
     * @param pageSize 每个页的大小，每个分区的偏移量会在多个页中进行跟踪，每个页面负责特定范围的偏移量。
     * 确认页面的所有偏移量后，将获得页面尾部偏移量，作为 {@link #ack（int，long）}}返回值，这是提交的安全偏移量。
     *
     * @param maxOpenPagesPerPartition 已打开页面的最大数量（已跟踪但未确认偏移量的页面）。
     * 在分区上达到此限制后，对该分区上的{@link #ack（int，long）}的下一次调用失败，并返回false。
     */
    public OffsetTracker(int pageSize, int maxOpenPagesPerPartition) {
        this.pageSize = pageSize;
        this.maxOpenPagesPerPartition = maxOpenPagesPerPartition;
        this.partitionTrackers = new HashMap<>();
    }

    /**
     * 清除所有先前的跟踪记录。适用于KafkaConsumer关闭或重新平衡时的情况。
     * 从一个单独的线程中调用它是安全的，然后从中调用{@link #ack（int，long）} 和{@link #track（int，long）}。
     */
    public void reset() {
        partitionTrackers = new HashMap<>();
    }

    /**
     * 追踪给定分区的指定偏移量。如果成功则返回true；如果跟踪器已满则返回false
     * （即在此分区上已达到打开页面的最大数量）。
     *
     * @param partition 分区
     * @param offset 追踪的偏移量
     */
    public boolean track(int partition, long offset) {
        PartitionTracker partitionTracker = partitionTrackers.get(partition);
        if (partitionTracker == null) {
            partitionTracker = new PartitionTracker(offset, this.pageSize, this.maxOpenPagesPerPartition);
            partitionTrackers.put(partition, partitionTracker);
        }
        return partitionTracker.track(offset);
    }

    /**
     * 告诉跟踪器已将给定分区的指定偏移量传递到其目标,如果没有可以在此分区上安全提交的新偏移量，则为空；如果存在，则为提交的安全偏移量。
     *
     * @param partition 分区
     * @param offset 确认的偏移量
     */
    public OptionalLong ack(int partition, long offset) {
        PartitionTracker partitionTracker = partitionTrackers.get(partition);
        if (partitionTracker == null) {
            LOG.warn("接收到未跟踪区域的位移提交！partition:{}, offset:{}", partition, offset);
            return OptionalLong.empty();
        }
        return partitionTracker.ack(offset);
    }

    public Map<TopicPartition, OffsetAndMetadata> drainNotFullOffset(String topic) {
        Map<TopicPartition, OffsetAndMetadata> result = new HashMap<>();
        for (Map.Entry<Integer, PartitionTracker> entry : this.partitionTrackers.entrySet()) {
            PartitionTracker partitionTracker = entry.getValue();
            long offset = partitionTracker.drainNotFullOffset();
            if (offset != -1) {
                result.put(new TopicPartition(topic, entry.getKey()), new OffsetAndMetadata(offset));
            }
        }
        return result;
    }
}
