package com.FF.first.customWidget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.util.Log;
import android.view.View;

/**
 * Created by song on 14-2-4.
 */
public class GraphView extends View {
    Line lineList[]=new Line[10];
    public GraphView(Context context) {
        super(context);
        for(int i=0;i<lineList.length;i++){
            lineList[i]=null;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(int i=0;i<lineList.length;i++){
            Line l=lineList[i];
            if(l!=null){
                l.draw(canvas);
            }
        }
    }
    public Line getLine(){
        Line line=new Line();
        for(int i=0;i<lineList.length;i++){
            if(lineList[i]==null){
                lineList[i]=line;
                break;
            }
        }
        return line;
    };

    public void reDraw(){
        //this.invalidate();
        postInvalidate();
    }

    public static class Line{
        private int pointList[]=new int[500];
        Color _color;
        Paint paint;
        Line(){
            paint=new Paint();
        }
        public void setPoint(int x){
            pointList[pointList.length-1]=x;
            for(int i=0;i<pointList.length-1;i++){
                pointList[i]=pointList[i+1];
            }
        }
        public void draw(Canvas canvas){
            for(int i=0;i<pointList.length-1;i++){
                canvas.drawLine(i*2,pointList[i],(i+1)*2,pointList[i+1],paint);
            }
        }
        public void setColor(int a,int r,int g,int b){
            paint.setColor(Color.argb(a,r,g,b));
        }
    }
}
