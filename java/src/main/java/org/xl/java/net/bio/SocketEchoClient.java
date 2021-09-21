package org.xl.java.net.bio;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author xulei
 */
public class SocketEchoClient {

    private static final String IP_ADDRESS = "127.0.0.1";
    private static final int PORT = 7777;

    public static void main(String[] args) {
        try (Socket socket = new Socket(IP_ADDRESS, PORT)) {
            socket.setSoTimeout(15000);

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            writer.println("Hello Socket");

            // 阻塞等待服务器端发送过来数据进行读取
            String message = reader.readLine();
            if (message != null) {
                System.out.println("Receive Server Response:" + message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
