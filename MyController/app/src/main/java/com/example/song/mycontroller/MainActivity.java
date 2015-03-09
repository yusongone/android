package com.example.song.mycontroller;import android.app.Activity;import android.os.Bundle;import android.view.MotionEvent;import android.view.View;import android.widget.ImageButton;import android.widget.ListView;import android.widget.ToggleButton;import com.example.song.mycontroller.multiwii.Protocol;public class MainActivity extends Activity {    Protocol p;    Rocker rc_l;    Rocker rc_r;    ToggleButton aux1;    MyBluetooth mbt;    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        setContentView(R.layout.activity_main);        rc_l=(Rocker)findViewById(R.id.left_rocker);        rc_r=(Rocker)findViewById(R.id.right_rocker);        rc_r.initValue((float)0.5,0);        aux1=(ToggleButton)findViewById(R.id.AUX1);        rc_r.starAnimate();        rc_l.starAnimate();        mbt=new MyBluetooth();        p=new Protocol(1000,2000,mbt);        ImageButton ib=(ImageButton)findViewById(R.id.connect);        aux1.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                boolean on = ((ToggleButton) v).isChecked();                if(on){                    p.setRawData(-1,-1,-1,-1,0,-1,-1,-1); // ROLL/PITCH/YAW/THROTTLE/AUX1/AUX2/AUX3AUX4                }else{                    p.setRawData(-1,-1,-1,-1,1,-1,-1,-1); // ROLL/PITCH/YAW/THROTTLE/AUX1/AUX2/AUX3AUX4                }            }        });        rc_l.setOnTouchListener(new View.OnTouchListener() {            @Override            public boolean onTouch(View v, MotionEvent event) {                switch (event.getAction() & MotionEvent.ACTION_MASK) {                    case MotionEvent.ACTION_MOVE:                        return false;                    case MotionEvent.ACTION_UP:                        rc_l.reCenter(true, true);                        return false;                };                return true;            }        });        rc_r.setOnTouchListener(new View.OnTouchListener() {            @Override            public boolean onTouch(View v, MotionEvent event) {                switch (event.getAction() & MotionEvent.ACTION_MASK) {                    case MotionEvent.ACTION_MOVE:                        return false;                    case MotionEvent.ACTION_UP:                        rc_r.reCenter(true,false);                        return false;                };                return true;            }        });        rc_l.setRawDataChange(new Rocker.OnDataChange() {            float tempx=0.5f;            float tempy=0.5f;            public void onChange(float rawx, float rawy) {                if(Math.abs(rawx-tempx)<0.05){                    rawx=tempx;                }else{                   tempx=rawx;                }                if(Math.abs(rawy-tempy)<0.05){                    rawy=tempy;                }else{                   tempy=rawy;                }                p.setRawData(-1, rawy, rawx, -1, -1, -1, -1, -1); // ROLL/PITCH/YAW/THROTTLE/AUX1/AUX2/AUX3AUX4            };        });        rc_r.setRawDataChange(new Rocker.OnDataChange(){            float tempx=0.5f;            public void onChange(float rawx,float rawy){                if(Math.abs(rawx-tempx)<0.08){                    rawx=tempx;                }else{                    tempx=rawx;                }                p.setRawData(rawx,-1,-1,rawy,-1,-1,-1,-1); // ROLL/PITCH/YAW/THROTTLE/AUX1/AUX2/AUX3AUX4            };        });        ListView lv=(ListView)findViewById(R.id.listView);        ib.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                mbt.conectToRemoteDevice("00:11:06:03:02:27");                p.keepSendDataToMultiwii();            }        });    };    public void onPause(){        super.onPause();    }    public void onResume(){        super.onResume();    }}