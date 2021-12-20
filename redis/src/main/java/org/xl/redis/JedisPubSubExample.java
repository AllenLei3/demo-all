package org.xl.redis;

import org.xl.redis.config.JedisConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

/**
 * HyperLogLog相关操作
 * 
 * @author xulei
 */
public class JedisPubSubExample {

    public static void main(String[] args) {
        JedisPool jedisPool = new JedisPool(JedisConfig.IP, JedisConfig.PORT);
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.auth(JedisConfig.AUTH);

            // 创建一个新线程作为消费者
            new Thread(JedisPubSubExample::consumer).start();
            Thread.sleep(500);

            // 生产者发送消息
            jedis.publish("my_channel", "Hello, channel.");
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * 消费者
     */
    public static void consumer() {
        Jedis jedis = new Jedis(JedisConfig.IP, JedisConfig.PORT);
        // 接收并处理消息
        jedis.subscribe(new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                // 接收消息，业务处理
                System.out.println("频道 " + channel + " 收到消息：" + message);
            }
        }, "my_channel");
    }

    /**
     * 主题订阅
     */
    public static void pConsumer() {
        Jedis jedis = new Jedis(JedisConfig.IP, JedisConfig.PORT);
        // 主题订阅
        jedis.psubscribe(new JedisPubSub() {
            @Override
            public void onPMessage(String pattern, String channel, String message) {
                // 接收消息，业务处理
                System.out.println(pattern + " 主题 | 频道 " + channel + " 收到消息：" + message);
            }
        }, "my_channel*");
    }
}
