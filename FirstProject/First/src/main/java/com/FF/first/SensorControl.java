package com.FF.first;

import android.hardware.SensorManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorEventListener2;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by song on 14-2-3.
 */
public class SensorControl {
    Sensor sensorADX;
    Sensor sensorGYRO;
    SensorManager _sm;
    SensorEventListener sel;
    private Callback _cb=null;
    SensorControl(SensorManager sm){
        _sm=sm;
        sensorADX=sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorGYRO=sm.getDefaultSensor(Sensor.TYPE_GYROSCOPE_UNCALIBRATED);
        this.init();
    }
    public void pause(){
        _sm.unregisterListener(sel);
        _cb.OnPause();
    }
    public void resume(){
        //this.init();
        _sm.registerListener(sel,sensorADX,SensorManager.SENSOR_DELAY_GAME);
        //_sm.registerListener(sel,sensorGYRO,SensorManager.SENSOR_DELAY_GAME);
        //_cb.OnResume();
    }
    public void setOnChangeListener(Callback cb){
        _cb=cb;
    }
    public void init(){
        sel=new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float x= sensorEvent.values[0];
                float y=sensorEvent.values[1];
                float z=sensorEvent.values[2];
                //Log.e("ttdd","x:"+x+" y:"+y+" z:"+z);
                if(null!=_cb){
                    _cb.OnChange(sensorEvent);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };

    }

    public static class Callback{
        Callback(){
        }
        public void OnChange(SensorEvent se){
        }
        public void OnResume(){
        }
        public void OnPause(){
        }
    }
}
