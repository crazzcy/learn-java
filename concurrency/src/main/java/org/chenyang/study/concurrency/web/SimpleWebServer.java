package org.chenyang.study.concurrency.web;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author ChenYang
 * @date 2020-03-26 16:18
 **/

public class SimpleWebServer {

    private static final int MAX_THREAD = 100;
    private static final Executor exec = Executors.newFixedThreadPool(MAX_THREAD);


    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(80);
        while(true) {
            System.out.println("阻塞方法开始..");
            final Socket socketConnection = serverSocket.accept();
            System.out.println("获取到客户端连接..");
            Runnable task = () -> handleRequest(socketConnection);
            exec.execute(task);
        }
    }

    private static void handleRequest(Socket socketConnection) {
        System.out.println("获取到socket连接，即将处理请求...." + socketConnection.toString());
    }
}
