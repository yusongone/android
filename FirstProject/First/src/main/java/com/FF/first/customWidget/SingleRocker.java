package com.FF.first.customWidget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;

import java.util.jar.Attributes;

/**
 * Created by songyu on 14-2-27.
 */
public class SingleRocker extends View {
    int screenHeight;
    int screenWidth;
    int y;
    public SingleRocker(Context context) {
        super(context);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenHeight=dm.heightPixels;
        screenWidth=dm.widthPixels;
    }
    public SingleRocker(Context context,AttributeSet attrs){
        super(context,attrs);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenHeight=dm.heightPixels;
        screenWidth=dm.widthPixels;
        this.setMeasuredDimension(200,screenHeight);

    }

    public void onDraw(Canvas canvas){
        int mid=this.getHeight()/2;
        Paint p=new Paint();
        p.setAntiAlias(true);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(5);
        p.setColor(Color.argb(250, 65, 250, 20));
        //canvas.drawCircle(150, 150, 150, p);



        Rect r3=new Rect();
        p.setColor(Color.RED);
        p.setStyle(Paint.Style.FILL);
        if(y>mid){
        r3.set(0,mid,200,y);
        }else{
        r3.set(0,y+50,200,mid);
        }
        p.setColor(Color.argb(100,255,0,0));
        canvas.drawRect(r3,p);
        Rect r2=new Rect();
        r2.set(0,y,200,y+50);
        p.setColor(Color.argb(180,255,0,0));
        canvas.drawRect(r2,p);
        canvas.drawText(y+"",0,y,p);
        Rect r=new Rect();
        p.setColor(Color.argb(255,0,255,0));
        r.set(0, 0, 200, screenHeight);
        p.setStyle(Paint.Style.STROKE);
        canvas.drawRect(r,p);
        //canvas.drawCircle(coord.getPointX(),coord.getPointY(), 50, p);
    }
    public void setPoint(int _y){
        y=_y;
        this.invalidate();
    }
    //重写控件尺寸
    protected void onMeasure(int width,int height){
        setMeasuredDimension(200 ,screenHeight);
    }
}
