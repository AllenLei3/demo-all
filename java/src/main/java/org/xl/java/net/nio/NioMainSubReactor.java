package org.xl.java.net.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Java NIO for Reactor
 */
public class NioMainSubReactor {

    /**
     * Main Reactor
     */
    private final ReactorThread[] mainReactorThreads = new ReactorThread[1];

    /**
     * Sub Reactor
     */
    private final ReactorThread[] subReactorThreads = new ReactorThread[8];

    /**
     * Worker ThreadPool
     */
    private static final ExecutorService workPool = Executors.newCachedThreadPool();

    private abstract static class ReactorThread extends Thread {

        private final Selector selector;
        private final LinkedBlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<>();
        private volatile boolean running = false;

        private ReactorThread() throws IOException {
            selector = Selector.open();
        }

        /**
         * Selector监听到有事件后,调用这个方法
         */
        public abstract void handler(SelectableChannel channel) throws Exception;

        @Override
        public void run() {
            while (running) {
                try {
                    // 从队列中获取要执行的任务
                    Runnable task;
                    while ((task = taskQueue.poll()) != null) {
                        task.run();
                    }
                    selector.select(1000);
                    Set<SelectionKey> selected = selector.selectedKeys();
                    Iterator<SelectionKey> iter = selected.iterator();
                    while (iter.hasNext()) {
                        SelectionKey key = iter.next();
                        iter.remove();
                        // 关注 Read 和 Accept两个事件
                        if (key.isReadable() || key.isAcceptable()) {
                            try {
                                SelectableChannel channel = (SelectableChannel) key.attachment();
                                channel.configureBlocking(false);
                                handler(channel);
                            } catch (Exception ex) {
                                // 如果有异常,就取消这个KEY的订阅
                                key.cancel();
                            }
                        }
                    }
                    selector.selectNow();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private SelectionKey register(SelectableChannel channel) throws Exception {
            // 为什么register要以任务提交的形式，让reactor线程去处理？
            // 因为线程在执行channel注册到selector的过程中，会和调用selector.select()方法的线程争用同一把锁
            // 而select()方法是在eventLoop中通过while循环调用的，争抢的可能性很高，为了让register能更快的执行，就放到同一个线程来处理
            FutureTask<SelectionKey> futureTask = new FutureTask<>(() -> channel.register(selector, 0, channel));
            taskQueue.add(futureTask);
            return futureTask.get();
        }

        private void doStart() {
            if (!running) {
                running = true;
                start();
            }
        }
    }

    /**
     * 初始化Main Reactor线程组, 只做请求分发，不做具体的数据读取
     */
    private void initMainReactorGroup() throws IOException {
        for (int i = 0; i < mainReactorThreads.length; i++) {
            mainReactorThreads[i] = new ReactorThread() {

                private final AtomicInteger incr = new AtomicInteger(0);

                @Override
                public void handler(SelectableChannel channel) throws Exception {
                    ServerSocketChannel ch = (ServerSocketChannel) channel;
                    SocketChannel socketChannel = ch.accept();
                    socketChannel.configureBlocking(false);

                    // 收到连接建立的通知之后，分发给I/O线程继续去读取数据
                    int index = incr.getAndIncrement() % subReactorThreads.length;
                    ReactorThread workEventLoop = subReactorThreads[index];
                    workEventLoop.doStart();
                    SelectionKey selectionKey = workEventLoop.register(socketChannel);
                    selectionKey.interestOps(SelectionKey.OP_READ);
                    System.out.println(Thread.currentThread().getName() + "收到新连接 : " + socketChannel.getRemoteAddress());
                }
            };
        }
    }

    /**
     * 初始化Sub Reactor线程组, 负责处理客户端连接以后socketChannel的IO读写
     */
    private void initSubReactorGroup() throws IOException {
        for (int i = 0; i < subReactorThreads.length; i++) {
            subReactorThreads[i] = new ReactorThread() {
                @Override
                public void handler(SelectableChannel channel) throws IOException {
                    SocketChannel ch = (SocketChannel) channel;
                    ByteBuffer requestBuffer = ByteBuffer.allocate(1024);
                    ch.read(requestBuffer);
                    requestBuffer.flip();

                    byte[] content = new byte[requestBuffer.limit()];
                    requestBuffer.get(content);
                    System.out.println(new String(content));
                    System.out.println(Thread.currentThread().getName() + "收到数据, 来自：" + ch.getRemoteAddress());
                    workPool.submit(() -> {
                        // 拿到数据后，可以执行一些业务操作 eg:数据库、RPC
                    });
                    ByteBuffer buffer = ByteBuffer.wrap("OK".getBytes());
                    while (buffer.hasRemaining()) {
                        ch.write(buffer);
                    }
                }
            };
        }
    }

    /**
     * 初始化channel,并且绑定一个eventLoop线程
     */
    private void startMainReactor() throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);

        int index = new Random().nextInt(mainReactorThreads.length);
        mainReactorThreads[index].doStart();
        SelectionKey selectionKey = mainReactorThreads[index].register(serverSocketChannel);
        selectionKey.interestOps(SelectionKey.OP_ACCEPT);

        serverSocketChannel.bind(new InetSocketAddress(8080));
        System.out.println("启动完成，端口8080");
    }

    public static void main(String[] args) throws Exception {
        NioMainSubReactor reactor = new NioMainSubReactor();
        // 初始化
        reactor.initMainReactorGroup();
        reactor.initSubReactorGroup();
        // 启动MainReactor
        reactor.startMainReactor();
    }
}