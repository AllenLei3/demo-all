package org.xl.redis;

import org.xl.redis.config.JedisConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 键过期相关操作
 * 
 * @author xulei
 */
public class JedisKeyExpireExample {

    public static void main(String[] args) {
        JedisPool jedisPool = new JedisPool(JedisConfig.IP, JedisConfig.PORT);
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.auth(JedisConfig.AUTH);

            // 存储键值对（默认情况下永不过期）
            jedis.set("k", "v");
            // 查询 TTL（过期时间）
            Long ttl = jedis.ttl("k");
            System.out.println("过期时间：" + ttl);

            // 设置 100s 后过期
            jedis.expire("k", 100L);
            System.out.println("过期时间：" + jedis.ttl("k"));

            // 等待 1s 后执行
            Thread.sleep(1000);
            // 打印过期日志
            System.out.println("执行 expire 后的 TTL=" + jedis.ttl("k"));
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
    }
}
