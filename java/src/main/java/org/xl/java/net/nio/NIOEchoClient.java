package org.xl.java.net.nio;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;

/**
 * @author xulei
 */
public class NIOEchoClient {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8888;

    public static void main(String[] args) throws Exception {
        try (SocketChannel client = SocketChannel.open(new InetSocketAddress(HOST, PORT))) {
            client.configureBlocking(false);
            // 写入数据
            byte[] data = "Hello NIO".getBytes();
            ByteBuffer sendBuffer = ByteBuffer.allocate(data.length);
            sendBuffer.put(data);
            sendBuffer.flip();
            client.write(sendBuffer);

            // 读取数据，在非阻塞模式下，没有字节可用时会立即返回0
            ByteBuffer receiveBuffer = ByteBuffer.allocate(1024);
            while (true) {
                int n = client.read(receiveBuffer);
                if (n > 0) {
                    // 将读取到的数据写入到输出流中，打印在控制台
                    receiveBuffer.flip();
                    byte[] bytes = new byte[receiveBuffer.remaining()];
                    receiveBuffer.get(bytes);
                    System.out.println("Receive Server Response: " + new String(bytes));
                } else if (n == -1) {
                    // 如果读到-1表示读通道关闭
                    break;
                }
            }
        }
    }
}
