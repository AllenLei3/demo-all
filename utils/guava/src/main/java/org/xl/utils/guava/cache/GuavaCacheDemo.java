package org.xl.utils.guava.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author xulei
 */
public class GuavaCacheDemo {

    public static void main(String[] args) throws ExecutionException {
        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                // 设置并发级别为8，并发级别是指可以同时写缓存的线程数
                .concurrencyLevel(8)
                // 设置缓存容器的初始容量为10
                .initialCapacity(10)
                // 设置缓存最大容量为100，超过100之后就会按照LRU最近最少使用算法来移除缓存项
                .maximumSize(100)
                // 是否需要统计缓存情况，该操作消耗一定的性能，生产环境应该去除
                .recordStats()
                // 设置写缓存后n秒钟过期
                .expireAfterWrite(60, TimeUnit.SECONDS)
                // 设置读写缓存后n秒钟过期，实际很少用到，一般只使用expireAfterWrite
                .expireAfterAccess(60, TimeUnit.SECONDS)
                // 设置写缓存后n秒钟刷新对应键值对
                .refreshAfterWrite(60, TimeUnit.SECONDS)
                // 设置缓存的移除通知
                .removalListener(notification -> {
                    System.out.println("key is:" + notification.getKey() + ", value is:" + notification.getValue()
                            + " 被移除! 原因:" + notification.getCause());
                })
                // build方法中可以指定CacheLoader，在缓存键不存在时通过CacheLoader的实现自动加载键对应的缓存值
                .build(new DemoCacheLoader());

        // 添加元素
        cache.put("a", "1");
        cache.put("a", "2");
        cache.put("b", "2");
        cache.put("c", "3");

        // 获取元素
        System.out.println(cache.get("a"));
        System.out.println(cache.get("d"));

        // 缓存失效
        cache.invalidate("c");
        System.out.println(cache.get("c"));

        // 刷新缓存
        cache.refresh("a");
        System.out.println(cache.get("a"));

        // 缓存命中状态查看
        System.out.println(cache.stats());
    }

    /**
     * 随机缓存加载
     */
    public static class DemoCacheLoader extends CacheLoader<String, String> {

        @Override
        public String load(String key) throws Exception {
            System.out.println(Thread.currentThread().getName() + " 加载" + key + "开始");
            TimeUnit.SECONDS.sleep(1);
            String value = String.valueOf(new Random().nextInt(100));
            System.out.println(Thread.currentThread().getName() + " 加载" + key + "结束");
            return value;
        }
    }
}
