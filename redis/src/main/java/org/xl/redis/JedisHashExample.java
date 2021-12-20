package org.xl.redis;

import org.xl.redis.config.JedisConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * 哈希表相关操作
 * 
 * @author xulei
 */
public class JedisHashExample {

    private static final String HASH_KEY = "hash_key";

    public static void main(String[] args) {
        JedisPool jedisPool = new JedisPool(JedisConfig.IP, JedisConfig.PORT);
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.auth(JedisConfig.AUTH);

            // 插入单个元素
            jedis.hset(HASH_KEY, "key1", "value1");
            // 查询单个元素
            String singleKey = jedis.hget(HASH_KEY, "key1");
            System.out.println(singleKey);

            // 查询所有元素
            jedis.hset(HASH_KEY, "key2", "value2");
            Map<String, String> allMap = jedis.hgetAll(HASH_KEY);
            System.out.println(allMap);

            // 删除单个元素
            Long delResult = jedis.hdel(HASH_KEY, "key1");
            System.out.println("删除结果：" + delResult);    // 输出：删除结果：1
        }
    }
}
