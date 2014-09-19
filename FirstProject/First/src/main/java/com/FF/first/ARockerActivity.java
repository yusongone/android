package com.FF.first;

import com.FF.first.customWidget.MySurfaceView;
import com.FF.first.customWidget.Rocker_UI;
import com.FF.first.customWidget.SingleRocker;
import com.FF.first.util.SystemUiHider;

import android.app.Activity;
import android.content.Intent;

import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;





/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class ARockerActivity extends Activity {

    MyBluetooth myBluetooth;
    MySurfaceView mfv;
    TextView tv1;
    TextView tv2;
    TextView tv3;
    TextView tv4;
    SensorManager sm;
    SensorControl sc;
    Switch sbtn;
    Rocker_UI rocker_ui;
    Rocker_UI rocker_ui2;
    SingleRocker singleRocker_left;
    SingleRocker singleRocker_right;
    Button toGraph;
    Button bt_a;
    Button bt_b;
    String _s;
    LinearLayout colA;
    LinearLayout colB;
    LinearLayout colC;
    LinearLayout sufarceA;
    //57600;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
        setContentView(R.layout.activity_fullscreen);
        tv1=(TextView)findViewById(R.id.testView1);
        tv2=(TextView)findViewById(R.id.testView2);
        tv3=(TextView)findViewById(R.id.testView3);
        tv4=(TextView)findViewById(R.id.testView4);

        colA = (LinearLayout)findViewById(R.id.colA);
        colB =(LinearLayout)findViewById(R.id.colB);
        colC = (LinearLayout)findViewById(R.id.colC);
        sufarceA = (LinearLayout)findViewById(R.id.sufarceA);

        toGraph=(Button)findViewById(R.id.toGraph);
        sbtn=(Switch)findViewById(R.id.SensorBtn);
        bt_a=(Button)findViewById(R.id.buttonA);
        bt_b=(Button)findViewById(R.id.buttonB);


        myBluetooth=new MyBluetooth();
        myBluetooth.initBluetooth();
        mfv=new MySurfaceView(this);
        sufarceA.addView(mfv);

        //添加蓝牙监听事件；
        bindBluetoothEvent();
        //初始化 rocker
        initRocker();

        sm=(SensorManager)getSystemService(SENSOR_SERVICE);
        sc=new SensorControl(sm);

        bindADXEvent();
        bindUIEvent();

}

    public void onPause(){
        myBluetooth.disconnect();
        sc.pause();
        super.onPause();
        sbtn.setChecked(false);
        mfv.stop();
    }
    public void onResume(){
        //sc.resume();
        myBluetooth.connect();
        sc.pause();
        super.onResume();
    }
    private void bindUIEvent(){
        sbtn.setChecked(false);
        sbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    rocker_ui.stopTouch();
                    rocker_ui2.stopTouch();
                    sc.resume();
                }else{
                    rocker_ui.beginTouch();
                    rocker_ui2.beginTouch();
                    sc.pause();
                }
            }

        });

        toGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(ARockerActivity.this,GraphActivity.class);
                startActivity(intent);

                //finish();
            }
        });
        bt_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // myBluetooth.sendData("Button:[1,]");
            mfv.start();

            }
        });
        bt_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // myBluetooth.sendData("Button:[1,]");
                mfv.stop();
            }
        });

    }
    private void bindADXEvent(){
        SensorControl.Callback callback = new SensorControl.Callback(){
            @Override
            public void OnChange(SensorEvent se) {
                super.OnChange(se);
                double x=se.values[0]*16+150;
                double y=se.values[1]*16+150;
                String str="x;"+(x-150)+"\ny:"+(y-150)+"\n";
                tv3.setText(str);
                rocker_ui.getCoord().setPoint(150,(int)x);
                rocker_ui2.getCoord().setPoint((int)y,150);
            }
            public void OnPause(){
                rocker_ui.getCoord().setPoint(150,150);
                rocker_ui2.getCoord().setPoint(150,150);
            }
        };
        sc.setOnChangeListener(callback);
    }

    private void bindBluetoothEvent(){
        myBluetooth.setOnDataListener(new MyBluetooth.OnDataListener(){
            @Override
            public void onData(String s) {
                super.onData(s);
                _s=s;
                tv4.post(new Runnable() {
                    @Override
                    public void run() {
                        tv4.setText(_s);
                    }
                });
            }
        });
    }

    private void initRocker(){
        //MySerial ms=new MySerial();
        rocker_ui=new Rocker_UI(this,myBluetooth,"1",tv1);
        rocker_ui2=new Rocker_UI(this,myBluetooth,"2",tv2);
        colA.addView(rocker_ui);
        colC.addView(rocker_ui2);
    }


}

