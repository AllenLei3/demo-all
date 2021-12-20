package org.xl.redis;

import org.xl.redis.config.JedisConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

/**
 * HyperLogLog相关操作
 * 
 * @author xulei
 */
public class JedisHyperLogLogExample {

    public static void main(String[] args) {
        JedisPool jedisPool = new JedisPool(JedisConfig.IP, JedisConfig.PORT);
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.auth(JedisConfig.AUTH);

            // 添加元素
            jedis.pfadd("k", "redis", "sql");
            jedis.pfadd("k", "redis");
            // 统计元素
            long count = jedis.pfcount("k");
            // 打印统计元素
            System.out.println("k：" + count);
            // 合并 HLL
            jedis.pfmerge("k2", "k");
            // 打印新 HLL
            System.out.println("k2：" + jedis.pfcount("k2"));
        }
    }
}
