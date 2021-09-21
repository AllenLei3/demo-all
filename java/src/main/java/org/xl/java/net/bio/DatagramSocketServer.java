package org.xl.java.net.bio;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * @author xulei
 */
public class DatagramSocketServer {

    private static final int PORT = 7777;

    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket(PORT);

        while (true) {
            // 阻塞获取从客户端发来的数据报文，每个数据报文都可能发送自不同的客户端
            DatagramPacket receive = new DatagramPacket(new byte[1024], 1024);
            socket.receive(receive);
            System.out.println("Handling client at " + receive.getAddress().getHostAddress() + ", on port " + receive.getPort());

            // 响应
            byte[] resBytes = "ok".getBytes();
            DatagramPacket response = new DatagramPacket(resBytes, resBytes.length, receive.getSocketAddress());
            socket.send(response);
        }
    }
}
