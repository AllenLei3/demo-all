package org.xl.utils.guava.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author xulei
 */
public class GuavaCacheRefreshDemo {

    public static void main(String[] args) {
        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                // 设置1s刷新
                .refreshAfterWrite(1, TimeUnit.SECONDS)
                .removalListener(notification -> {
                    System.out.println("key is:" + notification.getKey() + ", value is:" + notification.getValue()
                            + " 被移除! 原因:" + notification.getCause());
                })
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        System.out.println(Thread.currentThread().getName() + " 加载" + key + "开始");
                        String value = String.valueOf(new Random().nextInt(100));
                        System.out.println(Thread.currentThread().getName() + " 加载" + key + "结束");
                        return value;
                    }
                });

        // 添加元素
        cache.put("a", "1");

        // 等待5s后获取元素
        new Thread(() -> {
            try {
                Thread.sleep(1000 * 5);
                System.out.println(cache.get("a"));
            } catch (Exception e) {
                //
            }
        }).start();
    }

    /**
     * 随机缓存加载
     */
    public static class DemoCacheLoader extends CacheLoader<String, Object> {

        @Override
        public Object load(String key) throws Exception {
            System.out.println(Thread.currentThread().getName() + " 加载" + key + "开始");
            TimeUnit.SECONDS.sleep(10);
            String value = String.valueOf(new Random().nextInt(100));
            System.out.println(Thread.currentThread().getName() + " 加载" + key + "结束");
            return value;
        }

        @Override
        public ListenableFuture<Object> reload(String key, Object oldValue) throws Exception {
            ListenableFutureTask<Object> task = ListenableFutureTask.create(() -> load(key));
            Executors.newCachedThreadPool().execute(task);
            return task;
        }
    }
}
