package org.xl.redis;

import org.xl.redis.config.JedisConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

/**
 * 字符串相关操作
 *
 * @author xulei
 */
public class JedisStringExample {

    public static void main(String[] args) {
        JedisPool jedisPool = new JedisPool(JedisConfig.IP, JedisConfig.PORT);
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.auth(JedisConfig.AUTH);

            // 添加一个元素
            jedis.set("StringKey", "redis");
            // 获取元素
            System.out.println(jedis.get("StringKey"));

            // 添加多个元素(key1-value1, key2-value2)
            jedis.mset("db", "redis", "type", "string");
            // 获取多个元素
            List<String> mList = jedis.mget("db", "type");
            System.out.println(mList);

            // 给元素追加字符串
            jedis.append("db", ",mysql");
            // 打印追加的字符串
            System.out.println(jedis.get("db"));

            // 当 key 不存在时，赋值键值
            Long setNX = jedis.setnx("db", "db2");
            // 因为 db 元素已经存在，所以会返回 0 条修改
            System.out.println(setNX);

            // 字符串截取
            String range = jedis.getrange("db", 0, 2);
            System.out.println(range);

            // 添加键值并设置过期时间(单位：毫秒)
            String setEX = jedis.setex("db", 1000L, "redis");
            System.out.println(setEX);

            // 查询键值的过期时间
            Long ttl = jedis.ttl("db");
            System.out.println(ttl);
        }
    }
}
