package com.example.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created by Administrator on 2016/11/3.
 */
public class Server {

    ServerSocket servSock;
    ServerThread mServerThread;

    public Server(int port) {
        try {
            this.servSock = new ServerSocket(port);
            mServerThread = new ServerThread();
            mServerThread.setName("socket_server_thread");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setupServer() throws IOException{
        Socket connectSocket = null;
        InputStream in=null;
        OutputStream out=null;
        BufferedReader bufferedReader=null;
            while (true) {
                // 2.调用accept方法，建立和客户端的连接
//                    if(connectSocket==null||connectSocket.isClosed()){
                        connectSocket = servSock.accept();
//                    }
                    SocketAddress clientAddress =
                            connectSocket.getRemoteSocketAddress();
                    System.out.println("Handling client at " + clientAddress);
                    in = connectSocket.getInputStream();
                    if(in.markSupported()){
                        bufferedReader = new BufferedReader(new InputStreamReader(in));
                        String s = bufferedReader.readLine();
                        System.out.println(mServerThread.getName() + ":" + s);
                    }
                    out = connectSocket.getOutputStream();
                    out.write("ok".getBytes("utf-8"));
                    out.flush();
                    connectSocket.close();
                    System.out.println("wait...");
            }


    }

    class ServerThread extends Thread {
        @Override
        public void run() {
            super.run();
            try {
                setupServer();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
