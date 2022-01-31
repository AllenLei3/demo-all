package org.xl.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.xl.zookeeper.config.ZkConfig;

/**
 * Curator客户端使用
 *
 * @author xulei
 */
public class CuratorClientDemo {

    public static void main(String[] args) throws Exception {
        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 5, 30 * 1000);
        CuratorFramework curator = CuratorFrameworkFactory.builder()
                // 会话超时时间
                .sessionTimeoutMs(10 * 1000)
                // 连接创建超时时间
                .connectionTimeoutMs(5 * 1000)
                // 重试策略
                .retryPolicy(retryPolicy)
                // zookeeper服务器列表
                .connectString(ZkConfig.ADDRESS)
                .build();
        // 完成会话创建
        curator.getUnhandledErrorListenable().addListener((message, e) -> System.out.println("ZK客户端初始化异常! 原因:" + message));
        curator.start();

        // 监听节点
        PathChildrenCache childrenCache = new PathChildrenCache(curator, "/", true);
        childrenCache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
        childrenCache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                if (event == null || event.getData() == null || event.getData().getData() == null) {
                    return;
                }
                switch (event.getType()) {
                    case CHILD_ADDED:
                        System.out.println("CHILD_ADDED," + event.getData().getPath());
                        break;
                    case CHILD_UPDATED:
                        System.out.println("CHILD_UPDATED," + event.getData().getPath());
                        break;
                    case CHILD_REMOVED:
                        System.out.println("CHILD_REMOVED," + event.getData().getPath());
                        break;
                    default:
                }
            }
        });

        // 创建节点
        curator.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath("/curator", "Hello Curator".getBytes());

        // 更新节点数据
        curator.setData().forPath("/curator", "new value".getBytes());

        // 获取节点数据
        Stat stat = new Stat();
        byte[] data = curator.getData().storingStatIn(stat).forPath("/curator");
        System.out.println(new String(data));
        System.out.println(stat);

        // 删除节点
        curator.delete().deletingChildrenIfNeeded().forPath("/curator");

        // 用以获取回调通知
        Thread.sleep(5 * 1000);
    }
}