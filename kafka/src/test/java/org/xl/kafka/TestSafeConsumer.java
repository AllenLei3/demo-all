package org.xl.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetCommitCallback;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.xl.kafka.config.KafkaConfig;
import org.xl.kafka.safe.BaseSafeConsumer;
import org.xl.kafka.safe.PartitionOffset;

import java.util.Properties;

/**
 * @author xulei
 * @date 2020/11/11 11:47 上午
 */
public class TestSafeConsumer extends BaseSafeConsumer<String, String> {

    public static void main(String[] args) {
        TestSafeConsumer consumer = new TestSafeConsumer(100, 100);
        consumer.start();
    }

    public TestSafeConsumer(int offsetTrackerPageSize, int offsetTrackerMaxOpenPagesPerPartition) {
        super(offsetTrackerPageSize, offsetTrackerMaxOpenPagesPerPartition);
    }

    @Override
    public Properties getConsumerProperties() {
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfig.BROKER_LIST);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        return properties;
    }

    @Override
    public String getTopic() {
        return KafkaConfig.TOPIC;
    }

    @Override
    public String getGroupId() {
        return KafkaConfig.GROUP_NAME;
    }

    @Override
    public OffsetCommitCallback getOffsetCommitCallback() {
        return (offsets, exception) -> {
            if (exception != null) {
                LOG.error("commit failed!", exception);
            } else {
                LOG.debug("commit offset {} success!", offsets);
            }
        };
    }

    @Override
    public ConsumerRebalanceListener getReBalanceListener() {
        return null;
    }

    @Override
    public Long getPollTimeout() {
        return 500L;
    }

    @Override
    public void processRecords(ConsumerRecords<String, String> records) {
        for (ConsumerRecord<String, String> record : records) {
            LOG.debug("接收到消息：partition=" + record.partition() + ", offset=" + record.offset());
            try {
                Thread.sleep(100);
                LOG.info("topic is:{}, partition is:{}, offset is:{}", record.topic(), record.partition(), record.offset());
                LOG.info("key is:{}, value is:{}", record.key(), record.value());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ack(new PartitionOffset(record.partition(), record.offset()));
        }
    }
}
