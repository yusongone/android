package com.example.song.mycontroller;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

/**
 * Created by song on 14/12/13.
 */
public class Rocker extends View{
    private int pointX;
    private int pointY;
    private int radius;
    private int DOM_width;
    private int DOM_height;
    public Rocker(Context context) {
        super(context);
    }
    public Rocker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public Rocker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void onMeasure(int width,int height){
        int _width=MeasureSpec.getSize(width);
        int _height=MeasureSpec.getSize(height);

        if(_width>600){ _width=600; }
        if(_height>600){ _height=600; }
        setMeasuredDimension(_width,_height);
        DOM_width=getMeasuredWidth();
        DOM_height=getMeasuredHeight();
        pointX=DOM_height/2;
        pointY=DOM_width/2;
        radius=DOM_width<DOM_height?DOM_width/2:DOM_height/2;
        Log.e("radius",":"+this.radius);
        super.onMeasure(width, height);

    }

    protected void onDraw(Canvas canvas){
        Paint p=new Paint();
        Resources r=this.getResources();
        p.setColor(r.getColor(R.color.rocker_background));
        canvas.drawCircle(this.DOM_width/2,this.DOM_height/2,this.radius,p);

        super.onDraw(canvas);
    }
    public void test(){
        this.radius--;
        this.invalidate();
    }
}
