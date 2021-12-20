package org.xl.redis;

import org.xl.redis.config.JedisConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

/**
 * 事务相关操作
 * 
 * @author xulei
 */
public class JedisTransactionExample {

    public static void main(String[] args) {
        JedisPool jedisPool = new JedisPool(JedisConfig.IP, JedisConfig.PORT);
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.auth(JedisConfig.AUTH);

            // 设置键值
            jedis.set("k", "v");
            // 开启监视 watch
            jedis.watch("k");
            // 开始事务
            Transaction tx = jedis.multi();
            // 命令入列
            tx.set("k", "v2");
            // 执行事务
            tx.exec();
            System.out.println(jedis.get("k"));
        }
    }
}
