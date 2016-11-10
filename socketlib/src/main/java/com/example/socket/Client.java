package com.example.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by Administrator on 2016/11/3.
 */
public class Client {
    private String serverAddress;
    private int serverPort;
    private ClientThread clientThread;

    public Client(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        clientThread = new ClientThread();
        clientThread.setName("client_thread");
    }

    public void setupClient(int code) throws IOException {
        Socket socket  = new Socket(serverAddress, serverPort);
        InputStream in = socket.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        String s= bufferedReader.readLine();
        System.out.print(clientThread.getName() + ":" + s);
        OutputStream out = socket.getOutputStream();
        out.write(code);
        socket.close();
    }

    public void sendMessage(int code) {
        clientThread.setCode(code);
        clientThread.start();
    }

    class ClientThread extends Thread {
        int code;
        @Override
        public void run() {
            super.run();
            try {
                setupClient(code);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void setCode(int code) {
            this.code = code;
        }
    }
}
