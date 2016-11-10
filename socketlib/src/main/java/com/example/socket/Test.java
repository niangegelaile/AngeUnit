package com.example.socket;

/**
 * Created by Administrator on 2016/11/3.
 */
public class Test {
    public static void main(String[] arg){
        Server server=new Server(5);
        server.mServerThread.start();
//        Client client=new Client("127.0.0.1",59009);
//        client.sendMessage(12);
    }
}
