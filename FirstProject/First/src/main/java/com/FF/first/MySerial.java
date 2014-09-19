package com.FF.first;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by songyu on 14-3-4.
 */
public class MySerial {
    MySocket mySocket;
    public  MySerial(){
        mySocket=new MySocket();
       // mySocket.start();
    }
    public void sendData(String t){
        mySocket.sendData(t);
    }

    public class MySocket extends Thread{
        byte[] readBuffer=new byte[1024];
        int position=0;
        Socket socket;
        OutputStream op;
        InputStream is;
        String readString;
        public void run(){
            try {
                socket=new Socket("192.168.1.1",2001);
                op=socket.getOutputStream();
                is=socket.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (true){
                int bytesAvailable = 0;
                try {
                    bytesAvailable = is.available();
                    if(bytesAvailable>0){
                        byte[] paketBytes=new byte[bytesAvailable];
                        is.read(paketBytes);

                        for(int i=0;i<bytesAvailable;i++){
                            byte b=paketBytes[i];
                            readBuffer[position++]=b;
                        }
                        byte[] encodedBytes = new byte[position];
                        System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
                        final String data = new String(encodedBytes, "US-ASCII");
                        position=0;
                        readString+=data;
                        readString+="\n";
                        Log.e("fffffff", readString);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

        public void sendData(String str){
            str="{{"+str+"}}";
            byte[] a=str.getBytes();
            try {
                op.write(a);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
