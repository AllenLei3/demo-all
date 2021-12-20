package org.xl.redis;

import org.xl.redis.config.JedisConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

/**
 * 延迟队列相关操作
 * 
 * @author xulei
 */
public class JedisDelayQueueExample {

    // zset key
    private static final String _KEY = "myDelayQueue";

    public static void main(String[] args) throws InterruptedException {
        JedisPool jedisPool = new JedisPool(JedisConfig.IP, JedisConfig.PORT);
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.auth(JedisConfig.AUTH);

            // 延迟 30s 执行（30s 后的时间）
            long delayTime = Instant.now().plusSeconds(30).getEpochSecond();
            jedis.zadd(_KEY, delayTime, "order_1");
            // 继续添加测试数据
            jedis.zadd(_KEY, Instant.now().plusSeconds(2).getEpochSecond(), "order_2");
            jedis.zadd(_KEY, Instant.now().plusSeconds(2).getEpochSecond(), "order_3");
            jedis.zadd(_KEY, Instant.now().plusSeconds(7).getEpochSecond(), "order_4");
            jedis.zadd(_KEY, Instant.now().plusSeconds(10).getEpochSecond(), "order_5");
            // 开启延迟队列
            doDelayQueue(jedis);
        }
    }

    /**
     * 延迟队列消费
     * @param jedis Redis 客户端
     */
    public static void doDelayQueue(Jedis jedis) throws InterruptedException {
        while (true) {
            // 当前时间
            Instant nowInstant = Instant.now();
            long lastSecond = nowInstant.plusSeconds(-1).getEpochSecond(); // 上一秒时间
            long nowSecond = nowInstant.getEpochSecond();
            // 查询当前时间的所有任务
            Set<String> data = jedis.zrangeByScore(_KEY, lastSecond, nowSecond);
            for (String item : data) {
                // 消费任务
                System.out.println("消费：" + item);
            }
            // 删除已经执行的任务
            jedis.zremrangeByScore(_KEY, lastSecond, nowSecond);
            Thread.sleep(1000); // 每秒轮询一次
        }
    }

    /**
     * 延迟队列消费（方式2）
     * @param jedis Redis 客户端
     */
    public static void doDelayQueue2(Jedis jedis) throws InterruptedException {
        while (true) {
            // 当前时间
            long nowSecond = Instant.now().getEpochSecond();
            // 每次查询一条消息，判断此消息的执行时间
            Set<String> data = jedis.zrange(_KEY, 0, 0);
            if (data.size() == 1) {
                String firstValue = data.iterator().next();
                // 消息执行时间
                Double score = jedis.zscore(_KEY, firstValue);
                if (nowSecond >= score) {
                    // 消费消息（业务功能处理）
                    System.out.println("消费消息：" + firstValue);
                    // 删除已经执行的任务
                    jedis.zrem(_KEY, firstValue);
                }
            }
            Thread.sleep(100); // 执行间隔
        }
    }
}
