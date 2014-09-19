package com.FF.first.customWidget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.FF.first.MyBluetooth;
import com.FF.first.MySerial;

/**
 * Created by song on 14-1-26.
 */
public class Rocker_UI extends View {
    TextView _tv;
    private Coord coord=new Coord(this);
    private MyBluetooth _myBluetooth;
    private MySerial myserial;
    private String _name;
    private boolean _touch;
    private int final_x;
    private int final_y;
    int size=300;
    public Rocker_UI(Context context, MyBluetooth myBluetooth, String name, TextView tv){
        super(context);
        _myBluetooth=myBluetooth;
        _name=name;
        this.bindEvent();
        _tv=tv;
    }
    public Rocker_UI(Context context,AttributeSet attrs){
        super(context,attrs);

    }

    protected void onDraw(Canvas canvas){
        Paint p=new Paint();
        p.setAntiAlias(true);
        p.setStyle(Paint.Style.STROKE);
        p.setColor(Color.argb(250,65,250,20));
        canvas.drawCircle(150, 150, 150, p);
        p.setStyle(Paint.Style.FILL);
        canvas.drawCircle(coord.getPointX(),coord.getPointY(), 50, p);
    }
    //重写控件尺寸
    protected void onMeasure(int width,int height){
        setMeasuredDimension(300, 300);
    }

    private void bindEvent(){
        this.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if(_touch){return false;}
                int x = (int) event.getX();
                int y = (int) event.getY();
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_MOVE:
                        coord.setPoint(x,y);
                        break;
                    case MotionEvent.ACTION_UP:
                        coord.setPoint(150,150);
                        break;
                };
                return true;
            }
        });
    }
    public void createSendData(int x,int y){
        x=x-150;
        y=y-150;

            int temp1=100*x/150;
            int temp2=100*y/150;
            if(Math.abs(temp1-final_x)>1||Math.abs(temp2-final_y)>1||Math.abs(temp1)==100||temp1==0||Math.abs(temp2)==100||temp2==0){
                final_x=temp1;
                final_y=temp2;
                String t="";
                t="Rocker:["+this._name+","+final_x+","+final_y+",]";
                _tv.setText(t);
                _myBluetooth.sendData(t);
               // myserial.sendData(t);
            }

    }
    public void stopTouch(){
        _touch=true;
    }
    public void beginTouch(){
        _touch=false;
    }
    public Coord getCoord(){
        return coord;
    }

    public class Coord{
        private int pointX=150;
        private int pointY=150;
        private View _view;
        Coord(View view){
            _view=view;
        }
        public void setPoint(int x,int y){

            if(y>300){
                y=300;
            }else if(y<0){
                y=0;
            }
            if(x<0){
                x=0;
            }else if(x>300){
                x=300;
            }
            pointX = x;
            pointY = y;
            _view.invalidate();
            createSendData(x,y);
        }

        public int getPointX(){
            return pointX;
        }
        public int getPointY(){
            return pointY;
        }
    }
}
