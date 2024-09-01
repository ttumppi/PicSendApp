package com.example.PicSend;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ClientSocket {
    private Socket _clientSocket;
    String _endMessage;

    public ClientSocket(String endMessage){
        _endMessage = endMessage;
    }

    public void StartConnection(String ip, int port) throws IOException {
        _clientSocket = new Socket(ip, port);


    }





    public synchronized boolean SendMessage(byte[] data) throws IOException{

        byte[] answer = new byte[_endMessage.getBytes(StandardCharsets.UTF_8).length];
        StringBuilder chars = new StringBuilder();
        if (data.length != 0){
            _clientSocket.getOutputStream().write(data);
        }

        _clientSocket.getOutputStream().write(_endMessage.getBytes(StandardCharsets.UTF_8));
        _clientSocket.getOutputStream().flush();
        long startTime = System.currentTimeMillis();
        while (_clientSocket.getInputStream().read(answer) != -1){
            chars.append( new String(answer, StandardCharsets.UTF_8));
            if (chars.toString().equals(_endMessage)){
                return true;
            }
            answer = new byte[_endMessage.getBytes(StandardCharsets.UTF_8).length];
            if (System.currentTimeMillis() - startTime > 5000L){
                return false;
            }
        }



        return false;
    }



    public void Close() throws IOException {

        _clientSocket.close();
    }
}
