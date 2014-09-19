package com.FF.first;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Created by song on 14-1-26.
 */
public class MyBluetooth {
    private OnDataListener _odl;
    String address="00:11:06:03:02:27";
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private BluetoothSocket btSocket = null;
    private InputStream inStream = null;
    private OutputStream outStream = null;
    byte delimiter = 10;
    int readBufferPosition = 0;
    byte[] readBuffer = new byte[1024];
    byte[] encodedBytes;
    String readString="";
    String finalString="";
    MyBluetooth(){

    }
    public void ss(String str){

    }
    public void sendData(String str){
        str="{{"+str+"}}";
        try {
            Log.e("ee",str);
            outStream=btSocket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] messStr=str.getBytes();
        try {
            outStream.write(messStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void initBluetooth(){
        BluetoothAdapter mBluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
        /*
        TelephonyManager tm=(TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        String UUID=tm.getDeviceId();*/
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        Log.d("test", "Connecting to ... " + device);
        // mBluetoothAdapter.cancelDiscovery();
        try{
            btSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
        }catch (IOException ie){

        }
    }
    public void connect(){
        try {
            btSocket.connect();
            //listenData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void disconnect(){
        try {
            btSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setOnDataListener(OnDataListener odl){
        _odl=odl;
    }

    public void listenData(){
        try {
            inStream = btSocket.getInputStream();
        } catch (IOException e) {
        }

        Thread thread=new Thread(new Runnable() {

            private void parseData(){

            }
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()){
                    try {
                        int bytesAvailable = inStream.available();
                        if(bytesAvailable>0){
                            byte[] paketBytes=new byte[bytesAvailable];
                            inStream.read(paketBytes);

                            for(int i=0;i<bytesAvailable;i++){
                                byte b=paketBytes[i];
                                readBuffer[readBufferPosition++]=b;
                            }
                            encodedBytes = new byte[readBufferPosition];
                            System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
                            final String data = new String(encodedBytes, "US-ASCII");
                            readBufferPosition=0;
                            readString+=data;
                            boolean begin=readString.contains("{{");
                            boolean end=readString.contains("}}");
                            if(begin&&end){
                                int beginIndex=readString.indexOf("{{");
                                int endIndex=readString.indexOf("}}");
                                finalString=readString.substring(beginIndex+2,endIndex);
                                readString=readString.substring(endIndex+2);
                               // Log.e("rrrrrrrrrrrrrrrrrrr",readString);
                                if(_odl!=null){
                                    _odl.onData(finalString);
                                }
                            };
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    public static class OnDataListener{
        public OnDataListener(){

        }
        public void onData(String s){

        }
    }
}
