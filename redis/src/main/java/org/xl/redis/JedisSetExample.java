package org.xl.redis;

import org.xl.redis.config.JedisConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/**
 * 集合相关操作
 * 
 * @author xulei
 */
public class JedisSetExample {

    private static final String SET_KEY = "set_key";
    private static final String SET2_KEY = "set2_key";

    public static void main(String[] args) {
        JedisPool jedisPool = new JedisPool(JedisConfig.IP, JedisConfig.PORT);
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.auth(JedisConfig.AUTH);

            // 创建集合并添加元素
            jedis.sadd(SET_KEY, "java", "golang");
            // 查询集合中的所有元素
            Set<String> members = jedis.smembers(SET_KEY);
            System.out.println(members); // 输出：[java, golang]

            // 查询集合中的元素数量
            System.out.println(jedis.scard(SET_KEY));

            // 移除集合中的一个元素
            jedis.srem(SET_KEY, "golang");
            System.out.println(jedis.smembers(SET_KEY)); // 输出：[java]

            // 创建集合 set2 并添加元素
            jedis.sadd(SET2_KEY, "java", "python");
            // 查询两个集合中交集
            Set<String> inters = jedis.sinter(SET_KEY, SET2_KEY);
            System.out.println(inters); // 输出：[java]

            // 查询两个集合中并集
            Set<String> unions = jedis.sunion(SET_KEY, SET2_KEY);
            System.out.println(unions); // 输出：[java,golang,python]

            // 查询两个集合的错集
            Set<String> diffs = jedis.sdiff(SET_KEY, SET2_KEY);
            System.out.println(diffs);
        }
    }
}
