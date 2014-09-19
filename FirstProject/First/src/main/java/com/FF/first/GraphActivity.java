package com.FF.first;

import com.FF.first.customWidget.GraphView;
import com.FF.first.customWidget.Rocker_UI;
import com.FF.first.util.SystemUiHider;

import android.app.Activity;
import android.content.Context;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class GraphActivity extends Activity {
    SensorManager sm;
    SensorControl sc;
    GraphView g;
    GraphView.Line line1;
    GraphView.Line line2;
    GraphView.Line line3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
        setContentView(R.layout.activity_graph);
        g=new GraphView(this);
        LinearLayout ll=(LinearLayout)findViewById(R.id.lbox);
        ll.addView(g);
        sm=(SensorManager)getSystemService(SENSOR_SERVICE);
        sc=new SensorControl(sm);
        line1=g.getLine();
        line1.setColor(250,250,0,0);
        line2=g.getLine();
        line2.setColor(250,0,250,0);
        line3=g.getLine();
        line3.setColor(250,0,0,250);

        sc.setOnChangeListener(new SensorControl.Callback(){
            @Override
            public void OnChange(SensorEvent se) {
                super.OnChange(se);
                line1.setPoint((int)(se.values[0]*10)+400);
                line2.setPoint((int)(se.values[1]*10)+400);
                line3.setPoint((int)(se.values[2]*10)+400);
                g.reDraw();
            }
        });

    }
    public void onResume(){
        sc.resume();
        super.onResume();
    }
}
