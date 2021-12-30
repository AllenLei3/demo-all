package org.xl.kafka;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.Config;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.DescribeConfigsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.ConfigResource;
import org.xl.kafka.config.KafkaConfig;

import java.util.Collections;
import java.util.Properties;

/**
 * @author xulei
 */
public class KafkaAdminClientDemo {

    /**
     * 创建主题
     */
    public static void createTopics() {
        Properties properties = new Properties();
        properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfig.BROKER_LIST);
        properties.put(AdminClientConfig.REQUEST_TIMEOUT_MS_CONFIG, 30000);
        AdminClient adminClient = AdminClient.create(properties);

        NewTopic newTopic = new NewTopic(KafkaConfig.TOPIC, 3, (short) 1);
        CreateTopicsResult result = adminClient.createTopics(Collections.singletonList(newTopic));
        try {
            result.all().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        adminClient.close();
    }

    /**
     * 查看主题配置
     */
    public static void describeConfigs() {
        Properties properties = new Properties();
        properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfig.BROKER_LIST);
        properties.put(AdminClientConfig.REQUEST_TIMEOUT_MS_CONFIG, 30000);
        AdminClient adminClient = AdminClient.create(properties);

        ConfigResource configResource = new ConfigResource(ConfigResource.Type.TOPIC, KafkaConfig.TOPIC);
        DescribeConfigsResult result = adminClient.describeConfigs(Collections.singletonList(configResource));
        try {
            Config config = result.all().get().get(configResource);
            System.out.println(config);
        } catch (Exception e) {
            e.printStackTrace();
        }
        adminClient.close();
    }

    public static void main(String[] args) {
        describeConfigs();
    }
}
