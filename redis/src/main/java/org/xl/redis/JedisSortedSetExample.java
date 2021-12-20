package org.xl.redis;

import org.xl.redis.config.JedisConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 有序集合相关操作
 * 
 * @author xulei
 */
public class JedisSortedSetExample {

    private static final String SORTED_SET_KEY = "grade";

    public static void main(String[] args) {
        JedisPool jedisPool = new JedisPool(JedisConfig.IP, JedisConfig.PORT);
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.auth(JedisConfig.AUTH);

            Map<String, Double> map = new HashMap<>();
            map.put("小明", 80.5d);
            map.put("小红", 75d);
            map.put("老王", 85d);

            // 为有序集合(ZSet)添加元素
            jedis.zadd(SORTED_SET_KEY, map);

            // 查询分数在 80~100 分之间的人(包含 80 分和 100 分)
            Set<String> gradeSet = jedis.zrangeByScore(SORTED_SET_KEY, 80, 100);
            System.out.println(gradeSet); // 输出：[小明, 老王]

            // 查询小红的排名(排名从 0 开始)
            System.out.println(jedis.zrank(SORTED_SET_KEY, "小明")); // 输出：1

            // 从集合中移除老王
            jedis.zrem(SORTED_SET_KEY, "老王");

            // 查询有序集合中的所有元素(根据排名从小到大)
            Set<String> range = jedis.zrange(SORTED_SET_KEY, 0, -1);
            System.out.println(range); // 输出：[小红, 小明]

            // 查询有序集合中的所有元素(根据 score 从小到大)
            Set<String> rangeByScore = jedis.zrangeByScore(SORTED_SET_KEY, 0, 100);
            System.out.println(rangeByScore);
        }
    }
}
