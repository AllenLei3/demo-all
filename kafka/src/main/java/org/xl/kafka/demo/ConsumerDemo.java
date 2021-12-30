package org.xl.kafka.demo;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.xl.kafka.config.KafkaConfig;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Kafka消费者示例
 *
 * @author xulei
 */
public class ConsumerDemo {

    private static final String GROUP_NAME = "TestGroup";
    private static final AtomicBoolean RUNNING = new AtomicBoolean(true);

    public static void main(String[] args) {
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(initConsumerConfig());
        consumer.subscribe(Collections.singletonList(KafkaConfig.TOPIC));
        try {
            while (RUNNING.get()) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
                for (ConsumerRecord<String, String> record : records) {
                    System.out.println("topic is:" + record.topic() + ", partition is:" + record.partition() + ", offset is:" + record.offset());
                    System.out.println("key is:" + record.key() + ", value is:" + record.value());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            consumer.close();
        }
    }

    private static Properties initConsumerConfig() {
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", KafkaConfig.BROKER_LIST);
        properties.setProperty("key.deserializer", StringDeserializer.class.getName());
        properties.setProperty("value.deserializer", StringDeserializer.class.getName());
        properties.setProperty("group.id", GROUP_NAME);
        return properties;
    }
}
