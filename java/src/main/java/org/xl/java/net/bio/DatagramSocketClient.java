package org.xl.java.net.bio;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * @author xulei
 */
public class DatagramSocketClient {

    private static final String IP_ADDRESS = "127.0.0.1";
    private static final int PORT = 7777;
    private static final int MAX_TRIES = 3;

    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        socket.setSoTimeout(1000);

        InetAddress address = InetAddress.getByName(IP_ADDRESS);
        byte[] data = "Hello Datagram".getBytes();
        DatagramPacket sendPacket = new DatagramPacket(data, data.length, address, PORT);

        byte[] bytes = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(bytes, bytes.length);

        // 数据报文可能丢失，必须有重传的逻辑
        int tries = 0;
        boolean receiveResponse = false;
        do {
            socket.send(sendPacket);
            try {
                // receive()方法将阻塞等待，直到收到一个数据报文或等待超时
                socket.receive(receivePacket);
                if (!receivePacket.getAddress().equals(address)) {
                    throw new IOException("Receive packet form unknown resource");
                }
                receiveResponse = true;
            } catch (IOException e) {
                tries += 1;
                System.out.println("Timeout, rites count:" + tries);
            }
        } while (!receiveResponse && (MAX_TRIES >= tries));
        if (receiveResponse) {
            System.out.println("receive :" + new String(receivePacket.getData()));
        }
        socket.close();
    }
}
