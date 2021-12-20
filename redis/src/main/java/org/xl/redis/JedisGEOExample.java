package org.xl.redis;

import org.xl.redis.config.JedisConfig;
import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.GeoRadiusResponse;
import redis.clients.jedis.GeoUnit;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * GEO相关操作
 * 
 * @author xulei
 */
public class JedisGEOExample {

    public static void main(String[] args) {
        JedisPool jedisPool = new JedisPool(JedisConfig.IP, JedisConfig.PORT);
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.auth(JedisConfig.AUTH);

            Map<String, GeoCoordinate> map = new HashMap<>();
            // 添加小明的位置
            map.put("小明", new GeoCoordinate(116.404269, 39.913164));
            // 添加小红的位置
            map.put("小红", new GeoCoordinate(116.36, 39.922461));
            // 添加小美的位置
            map.put("小美", new GeoCoordinate(116.499705, 39.874635));
            // 添加小二的位置
            map.put("小二", new GeoCoordinate(116.193275, 39.996348));
            jedis.geoadd("person", map);

            // 查询小明和小红的直线距离
            Double dist = jedis.geodist("person", "小明", "小红", GeoUnit.KM);
            System.out.println("小明和小红相距：" + dist + " KM");

            // 查询小明附近5公里的人
            List<GeoRadiusResponse> res = jedis.georadiusByMemberReadonly("person", "小明",
                    5, GeoUnit.KM);
            if (res != null) {
                res.forEach(r -> System.out.println("小明附近的人：" + r.getMemberByString()));
            }
        }
    }
}
