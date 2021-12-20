package org.xl.redis;

import org.xl.redis.config.JedisConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

/**
 * SCAN相关操作
 * 
 * @author xulei
 */
public class JedisScanExample {

    public static void main(String[] args) {
        JedisPool jedisPool = new JedisPool(JedisConfig.IP, JedisConfig.PORT);
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.auth(JedisConfig.AUTH);

            // 定义match和count参数
            ScanParams params = new ScanParams();
            params.count(10000);
            params.match("user_token_9999*");
            // 游标
            String cursor = "0";
            while (true) {
                ScanResult<String> res = jedis.scan(cursor, params);
                if (res.getCursor().equals("0")) {
                    // 表示最后一条
                    break;
                }
                cursor = res.getCursor(); // 设置游标
                for (String item : res.getResult()) {
                    // 打印查询结果
                    System.out.println("查询结果：" + item);
                }
            }
        }
    }
}
