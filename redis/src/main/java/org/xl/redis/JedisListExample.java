package org.xl.redis;

import org.xl.redis.config.JedisConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ListPosition;

import java.util.List;

/**
 * 列表相关操作
 * 
 * @author xulei
 */
public class JedisListExample {

    private static final String LIST_KEY = "list_key";

    public static void main(String[] args) {
        JedisPool jedisPool = new JedisPool(JedisConfig.IP, JedisConfig.PORT);
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.auth(JedisConfig.AUTH);

            // 在头部插入一个或多个元素
            Long lpushResult = jedis.lpush(LIST_KEY, "redis", "mysql");
            System.out.println(lpushResult);

            // 获取第 0 个元素的值
            String idValue = jedis.lindex(LIST_KEY, 0);
            System.out.println(idValue);

            // 查询指定区间的元素, -1为全部
            List<String> list = jedis.lrange(LIST_KEY, 0, -1);
            System.out.println(list);

            // 在元素redis前添加elasticsearch元素
            jedis.linsert(LIST_KEY, ListPosition.BEFORE, "redis", "elasticsearch");
            System.out.println(jedis.lrange(LIST_KEY, 0, -1));
        }
    }
}
