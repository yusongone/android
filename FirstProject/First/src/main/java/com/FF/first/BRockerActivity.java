package com.FF.first;

import com.FF.first.customWidget.MySurfaceView;
import com.FF.first.customWidget.SingleRocker;
import com.FF.first.util.SystemUiHider;


import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.MediaController;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class BRockerActivity extends Activity {
    SingleRocker singleRocker_left;
    SingleRocker singleRocker_right;
    MyBluetooth myBluetooth;
    LinearLayout colA;
    int heightL;
    int heightR;
    int midX;
    int midY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
        setContentView(R.layout.b_rocker_layout);
        singleRocker_left=(SingleRocker)findViewById(R.id.singleL);
        singleRocker_right=(SingleRocker)findViewById(R.id.singleR);
        myBluetooth=new MyBluetooth();
        myBluetooth.initBluetooth();
        MediaController mediaController = new MediaController(this);

        bindUIEvent();

    }
    public void onResume(){
        super.onResume();
        myBluetooth.connect();
    }
    public void onPause(){
        super.onPause();
        myBluetooth.disconnect();
    }
    public void onWindowFocusChanged(boolean b){
        super.onWindowFocusChanged(b);
        heightL=singleRocker_left.getHeight();
        heightR=singleRocker_right.getHeight();
    }
    private void bindUIEvent(){

        singleRocker_left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int y=(int)motionEvent.getY();
                int finalY=0;
                switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_MOVE:
                        singleRocker_left.setPoint(y);
                        finalY=(int)((y-(float)heightL/2)/((float)heightL/2)*100);
                        break;
                    case MotionEvent.ACTION_UP:
                        singleRocker_left.setPoint(heightL/2);
                        finalY=0;
                        break;
                };
                String t="Rocker:[1,0,"+finalY+",]";
                myBluetooth.sendData(t);
                return true;
            }
        });
        singleRocker_right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int y=(int)motionEvent.getY();
                int finalY=0;
                switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_MOVE:
                        singleRocker_right.setPoint(y);
                        finalY=(int)((y-(float)heightL/2)/((float)heightL/2)*100);
                        break;
                    case MotionEvent.ACTION_UP:
                        singleRocker_right.setPoint(heightL/2);
                        finalY=0;
                        break;
                };
                String t="Rocker:[2,0,"+finalY+",]";
                myBluetooth.sendData(t);
                return true;
            }
        });
    }

}
