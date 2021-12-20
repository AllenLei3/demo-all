package org.xl.redis;

import org.xl.redis.config.JedisConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Arrays;
import java.util.Collections;

/**
 * 布隆过滤器相关操作
 * 
 * @author xulei
 */
public class JedisBloomFilterExample {

    private static final String _KEY = "user";

    public static void main(String[] args) {
        JedisPool jedisPool = new JedisPool(JedisConfig.IP, JedisConfig.PORT);
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.auth(JedisConfig.AUTH);

            for (int i = 1; i < 100001; i++) {
                bfAdd(jedis, _KEY, "user_" + i);
                boolean exists = bfExists(jedis, _KEY, "user_" + (i + 1));
                if (exists) {
                    System.out.println("找到了" + i);
                    break;
                }
            }
            System.out.println("执行完成");
        }
    }

    /**
     * 添加元素
     * @param jedis Redis 客户端
     * @param key   key
     * @param value value
     * @return boolean
     */
    public static boolean bfAdd(Jedis jedis, String key, String value) {
        String luaStr = "return redis.call('bf.add', KEYS[1], KEYS[2])";
        Object result = jedis.eval(luaStr, Arrays.asList(key, value),
                Collections.emptyList());
        return result.equals(1L);
    }

    /**
     * 查询元素是否存在
     * @param jedis Redis 客户端
     * @param key   key
     * @param value value
     * @return boolean
     */
    public static boolean bfExists(Jedis jedis, String key, String value) {
        String luaStr = "return redis.call('bf.exists', KEYS[1], KEYS[2])";
        Object result = jedis.eval(luaStr, Arrays.asList(key, value),
                Collections.emptyList());
        return result.equals(1L);
    }
}
