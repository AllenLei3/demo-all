package org.xl.java.net.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author xulei
 */
public class NIOEchoServer {

    private static final int PORT = 8888;

    public static void main(String[] args) throws Exception {
        Selector selector;
        try (ServerSocketChannel serverChannel = ServerSocketChannel.open()) {
            // 创建服务端通道
            serverChannel.bind(new InetSocketAddress(PORT), 1024);
            serverChannel.configureBlocking(false);
            selector = Selector.open();
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
            while (true) {
                selector.select();
                Set<SelectionKey> readyKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = readyKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    try {
                        // 如果是一个客户端连接就绪事件
                        if (key.isAcceptable()) {
                            ServerSocketChannel server = (ServerSocketChannel) key.channel();
                            SocketChannel client = server.accept();
                            System.out.println("接收到客户端连接请求: " + client);

                            client.configureBlocking(false);
                            // 只注册读事件就可以，因为我们是被动等待客户端发送消息
                            client.register(selector, SelectionKey.OP_READ);
                        }

                        // 读取客户端发送来的消息，并返回响应
                        else if (key.isReadable()) {
                            SocketChannel client = (SocketChannel) key.channel();
                            ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                            // 要考虑半读、半写的情况
                            // 因为我们事先并不知道要读多少数据，根据read的返回值是否为0判断读取是否结束
                            int readBytes = client.read(readBuffer);
                            if (readBytes > 0) {
                                readBuffer.flip();
                                byte[] bytes = new byte[readBuffer.remaining()];
                                readBuffer.get(bytes);
                                System.out.println("Receive Client Request: " + new String(bytes));

                                // 写数据时，由于发送缓冲区大小可能不够用，所以不会一次性发送所有数据
                                // 可通过hasRemaining()方法来判断缓冲区是否还有剩余
                                ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
                                writeBuffer.put(bytes);
                                writeBuffer.flip();
                                client.write(writeBuffer);
                            } else if (readBytes == 0) {
                                break;
                            } else {
                                key.cancel();
                                client.close();
                            }
                        }
                    } catch (IOException e) {
                        key.cancel();
                        try {
                            key.channel().close();
                        } catch (IOException ie) {
                            //
                        }
                    }
                }
            }
        }
    }
}
