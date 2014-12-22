package com.example.song.mycontroller;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

/**
 * Created by song on 14/12/21.
 */
public class MyBluetooth {
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    String address="00:11:06:03:02:27";
    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket btSocket=null;
    OutputStream ops=null;
    InputStream ips=null;
    public MyBluetooth(){
        mBluetoothAdapter= BluetoothAdapter.getDefaultAdapter();
    }

    public void conectToRemoteDevice(String _address){
        BluetoothDevice bluetoothDevice=mBluetoothAdapter.getRemoteDevice(address);
        //disconnect();
        try {
            btSocket=bluetoothDevice.createRfcommSocketToServiceRecord(MY_UUID);
            btSocket.connect();
            Log.e("blueTooth", ""+btSocket.isConnected());
            if(btSocket.isConnected()){
                ops=btSocket.getOutputStream();
                listenData();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendData(){
        byte[] buf=new byte[6];
        buf[0]=0x24;
        buf[1]=0x4D;
        buf[2]=0x3C;
        buf[3]=0x00;
        buf[4]=0x64;
        buf[5]=0x64;
        try {
            //checkSum(buf);
            if(ops!=null){
                ops.write(buf);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*
    int read32() {
        return (inBuf[p++] & 0xff) + ((inBuf[p++] & 0xff) << 8)
                + ((inBuf[p++] & 0xff) << 16) + ((inBuf[p++] & 0xff) << 24);
    }

    int read16() {
        return (inBuf[p++] & 0xff) + ((inBuf[p++]) << 8);
    }

    int read8() {
        return inBuf[p++] & 0xff;
    }
    */
    public void checkSum(byte[] buf){
        byte z=buf[0];
        for(int i=1;i<buf.length;i++){
          z=(byte)(z^(buf[i]&0xff));
        }
        Log.e("sum",z+"");
    }
    private void listenData(){
        final int receiveLength=500;
        final byte[] receiveByte=new byte[receiveLength];
        try {
            ips=btSocket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()){
                    try {
                        int result=ips.available();
                        if (result>0) {
                            ips.read(receiveByte);
                            Log.e("receive",""+receiveByte[0]);
                        }
                        for(int i=0;i<receiveLength;i++){
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    };
                }
            }
        }).start();
    }



    public void disconnect(){
        try {
            if(btSocket!=null){
                btSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initBluetooth(){
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
        }
        if (!mBluetoothAdapter.isEnabled()) {
            return;
        }
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
// If there are paired devices
        if (pairedDevices.size() > 0) {
            // Loop through paired devices
            for (BluetoothDevice device : pairedDevices) {
                // Add the name and address to an array adapter to show in a ListView
                Log.e("device",device.getName()+device.getAddress());
                // mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
            }
        }

    }

}
