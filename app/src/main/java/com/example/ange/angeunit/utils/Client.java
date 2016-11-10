package com.example.ange.angeunit.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;

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
    Socket socket;
    String code = "null";

    public Client(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        clientThread = new ClientThread();
        clientThread.setName("client_thread");
        clientThread.start();
    }



    public void sendMessage(String code) {
        this.code = code;
       Message ms= Message.obtain();
        clientThread.handler.sendMessage(ms);
    }

    class ClientThread extends Thread {
        Handler handler;
        @Override
        public void run() {
            super.run();
            Looper.prepare();
             handler=new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    try {
                        runMessage();
                    } catch (IOException e) {
                    }
                }
            };
            Looper.loop();

        }
    }


    private void runMessage() throws IOException {
//        while (true) {
            if( !code.equals("null")){
//                if(socket==null||socket.isClosed()){
                    socket = new Socket(serverAddress, serverPort);
//                }
                OutputStream out = socket.getOutputStream();
                out.write(code.getBytes("utf-8"));
                code="null";
            }
            if(socket!=null&&!socket.isClosed()){
                InputStream in = socket.getInputStream();
                if (in.markSupported()) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                    String s = bufferedReader.readLine();
                    Log.d("ange", s);
                }
             socket.close();
            }
//        }
    }
}
