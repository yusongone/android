package com.FF.first;

import com.FF.first.util.SystemUiHider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class MenuActivity extends Activity {
    Button Arocker;
    Button Brocker;
    Button graph_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
        setContentView(R.layout.menu_activity);

        Arocker=(Button)findViewById(R.id.ARocker);
        Brocker=(Button)findViewById(R.id.BRocker);
        graph_view=(Button)findViewById(R.id.graph_view);

        Arocker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(MenuActivity.this,ARockerActivity.class);
                startActivity(intent);
            }
        });
        Brocker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(MenuActivity.this,BRockerActivity.class);
                startActivity(intent);
            }
        });

        graph_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(MenuActivity.this,GraphActivity.class);
                startActivity(intent);
            }
        });
    }
}
