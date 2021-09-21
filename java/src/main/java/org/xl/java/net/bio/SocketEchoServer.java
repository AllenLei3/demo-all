package org.xl.java.net.bio;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author xulei
 */
public class SocketEchoServer {

    private static final int PORT = 7777;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                // 阻塞等待客户端的连接请求
                Socket socket = serverSocket.accept();
                System.out.println("接收到客户端连接请求 " + socket.getInetAddress() + ":" + socket.getPort());

                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                String message = reader.readLine();
                if (message != null) {
                    System.out.println("Receive Client Request:" + message);
                }
                writer.println(message);
                socket.close();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
