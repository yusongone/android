package com.example.song.mycontroller.multiwii;

/**
 * Created by song on 14/12/21.
 */
public class Protocol{
    private int Range_min;
    private int Range_max;
    private int Range;
    private int PITCH;
    private int ROLL;
    private int YAW;
    private int THR;
    private int AUX1;
    private int AUX2;
    public Protocol(int min,int max){
        Range_min=min;
        Range_max=max;
        Range=max-min;
    }
    public void setRawData(float _PITCH,float _ROLL,float _YAW,float _THR,float _AUX1,float _AUX2){
       PITCH=_PITCH!=-1?computer(_PITCH):PITCH;
       ROLL=_ROLL!=-1?computer(_ROLL):ROLL;
       YAW=_YAW!=-1?computer(_YAW):YAW;
       THR=_THR!=-1?computer(_THR):THR;
       AUX1=_AUX1!=-1?computer(_AUX1):AUX1;
       AUX2=_AUX2!=-1?computer(_AUX2):AUX2;
    }

    private int computer(float bl){
     return (int)(Range_min+Range*bl);
    }




    class Remote{
        private byte[] sendHead=new byte[3];
        public Remote(){
            sendHead[0]=0x24;
            sendHead[1]=0x4D;
            sendHead[2]=0x3C;
        }
        public void writeUInt8(String s){

        }
        public void write16(){

        }
        public void write32(){

        }
        public void checkSum(byte[] buf){
            byte z=buf[0];
            for(int i=1;i<buf.length;i++){
                z=(byte)(z^(buf[i]&0xff));
            }
        }
    }
}

