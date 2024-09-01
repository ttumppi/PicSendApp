package com.example.PicSend;

import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ListenerSocket {

    private ServerSocket _socket;

    OnImageCommandReceived _onCommand;

    private boolean _running;

    private Thread _thread;

    private final String _endMessage = "ACK";

    private final byte[] _endMessageInBytes = _endMessage.getBytes(StandardCharsets.UTF_8);

    public ListenerSocket(OnImageCommandReceived callBack)
    {
        _onCommand = callBack;
        _running = false;
        _thread = CreateThread();
        try{
            _socket = new ServerSocket(33666);

        }
        catch(Exception e){}
    }

    public void Start(){
        _running = true;
        _thread.start();
    }

    private Thread CreateThread(){
        return new Thread(() -> {
            Socket comm = null;
           while (_running){

               if (comm == null){

                   try{
                       comm = _socket.accept();
                   }
                   catch(Exception e){}
               }

               if (!CheckConnection(comm)){
                   comm = null;
                   continue;
               }

               if (DataAvailable(comm)){
                   ReadInputStream(comm);
                   SendAcknowledge(comm);
                   _onCommand.OnCommandReceived();
               }

           }
        });
    }

    private boolean DataAvailable(Socket socket){
        try {
            return socket.getInputStream().available() > 0;
        }
        catch(Exception e){}
        return false;
    }

    private void ReadInputStream(Socket socket){
        try{
            byte[] messages = new byte[0];
            byte[] message = new byte[socket.getInputStream().available()];
            while (socket.getInputStream().read(message) != -1){
               messages = AppendArrays(messages, message);

                if (CheckEndOfMessage(messages)){
                    break;
                }

            }
        }
        catch (Exception e){
            String ex = e.getMessage();
        }
    }

    private void SendAcknowledge(Socket socket){
        try{

            socket.getOutputStream().write(_endMessageInBytes);
            socket.getOutputStream().flush();
        }
        catch(Exception e){}
    }

    private boolean CheckConnection(Socket socket){
        try{
            socket.getOutputStream().write(new byte[0]);
        }
        catch(Exception e){
            return false;
        }
        return true;
    }

    public void Shutdown(){
        _running = false;
        try{
            _socket.close();
        }
        catch(Exception e){}
    }

    private boolean CheckEndOfMessage(byte[] message){
        int startIndex = _endMessageInBytes.length -1;
        for (int i = message.length -1; i > message.length - _endMessageInBytes.length; i--){
            if (_endMessageInBytes[startIndex] != message[i]){
                return false;
            }
            startIndex--;
        }
        return true;
    }

    private byte[] AppendArrays(byte[] appendTo, byte[] appendFrom){
        byte[] combined = new byte[appendTo.length + appendFrom.length];
        int startIndex = 0;

        System.arraycopy(appendTo, 0, combined, startIndex, appendTo.length);
        startIndex += appendTo.length;

        System.arraycopy(appendFrom, 0, combined, startIndex, appendFrom.length);
        return combined;
    }
}
