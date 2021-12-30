package org.xl.kafka.demo;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.xl.kafka.config.KafkaConfig;

import java.util.Properties;
import java.util.concurrent.Future;

/**
 * Kafka生产者示例
 *
 * @author xulei
 */
public class ProducerDemo {

    public static void main(String[] args) {
        KafkaProducer<String, String> producer = new KafkaProducer<>(initProducerConfig());
        ProducerRecord<String, String> record = new ProducerRecord<>(KafkaConfig.TOPIC, "Hello Kafka");
        try {
            Future<RecordMetadata> future = producer.send(record);
            RecordMetadata metadata = future.get();
            System.out.println(metadata.offset());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.close();
        }
    }

    private static Properties initProducerConfig() {
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", KafkaConfig.BROKER_LIST);
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", StringSerializer.class.getName());
        return properties;
    }
}
