package org.xl.redis;

import org.xl.redis.config.JedisConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

/**
 * 管道相关操作
 * 
 * @author xulei
 */
public class JedisPipelineExample {

    public static void main(String[] args) {
        JedisPool jedisPool = new JedisPool(JedisConfig.IP, JedisConfig.PORT);
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.auth(JedisConfig.AUTH);

            // 获取 Pipeline 对象
            Pipeline pipe = jedis.pipelined();
            // 设置多个 Redis 命令
            for (int i = 0; i < 100; i++) {
                pipe.set("key" + i, "val" + i);
                pipe.del("key" + i);
            }
            // 执行命令
            pipe.sync();
            // 如果要接收管道所有命令的执行结果，可使用 syncAndReturnAll() 方法
            // List<Object> res = pipe.syncAndReturnAll();
        }
    }
}
