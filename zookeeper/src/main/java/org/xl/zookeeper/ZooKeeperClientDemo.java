package org.xl.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.xl.zookeeper.config.ZkConfig;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * ZooKeeper原生客户端使用
 *
 * @author xulei
 */
public class ZooKeeperClientDemo implements Watcher {

    private static final CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        ZooKeeper zooKeeper = new ZooKeeper(ZkConfig.ADDRESS, 5000, new ZooKeeperClientDemo());
        // 此处为CONNECTING
        System.out.println(zooKeeper.getState());
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {

        }
        // 此处为CONNECTED
        System.out.println(zooKeeper.getState());

        // 删除节点
//        zooKeeper.delete("/test", -1);
//        zooKeeper.delete("/test/second", -1);

        // 创建节点
        String path = zooKeeper.create("/test",
                "Hello ZooKeeper".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(path);
        String innerPath = zooKeeper.create("/test/second",
                "Hello ZooKeeper".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(innerPath);

        // 获取子列表
        List<String> childrenList = zooKeeper.getChildren("/test", false);
        System.out.println(childrenList);

        // 获取ZNode数据内容
        byte[] dataInfo = zooKeeper.getData("/test", true, null);
        System.out.println(new String(dataInfo));
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        if (Event.KeeperState.SyncConnected == watchedEvent.getState()) {
            countDownLatch.countDown();
        }
    }
}
