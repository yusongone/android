package com.FF.first.customWidget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import com.FF.first.MyBluetooth;
import com.FF.first.SensorControl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by songyu on 14-2-20.
 */
public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder sfh;
    float mScreenWidth;
    float mScreenHeight;
    GetAndDrawVideo gadV=null;
    private boolean _run=false;
    private Canvas canvas;
    TextView tv4;


    private void initialize(){

    }

    public MySurfaceView(Context context) {
        super(context);
        sfh = this.getHolder();
        sfh.addCallback(this);
        this.setKeepScreenOn(true);// 保持屏幕常亮
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
    }
    private void unbindEvent(){
        this.setOnTouchListener(null);

    }

    public void start(){
        _run=true;
        if(gadV==null){
            gadV=new GetAndDrawVideo();
            gadV.start();
        }
    }
    public void stop(){
        unbindEvent();
        if(gadV!=null){
            _run=false;
            gadV.interrupt();
            gadV.clear();
            gadV=null;
        }
    }

    class GetAndDrawVideo extends Thread{
        HttpURLConnection htpUrl;
        private float imgWidth=0;
        private float imgHeight=0;
        Paint pt = new Paint();

        public  GetAndDrawVideo(){
            DisplayMetrics dm = getResources().getDisplayMetrics();
            mScreenWidth = dm.widthPixels;
            mScreenHeight = dm.heightPixels;
            pt.setAntiAlias(true);
            pt.setColor(Color.RED);
            pt.setTextSize(20);
            pt.setStrokeWidth(1);

        };
        public void clear(){
            canvas = sfh.lockCanvas();
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            canvas.drawText("stop",600,50,pt);
            //canvas.drawColor(Color.BLACK);
            sfh.unlockCanvasAndPost(canvas);
        }
        public void run(){

            int readStream=0;

            Bitmap bmp;
            Bitmap mBitmap;
            long time0=0;
            long time1=0;


            int bufSize = 512 * 1024;	            //视频图片缓冲
            byte[] jpg_buf = new byte[bufSize];	        // buffer to read jpg

            int readSize = 4096;		            //每次最大获取的流
            byte[] buffer = new byte[readSize];	        // buffer to read stream
            int status=0;
            int jpg_count=0;
            URL url= null;
            try {
                url = new URL("http://192.168.1.1:8080/?action=stream");
                htpUrl=(HttpURLConnection)url.openConnection();
                InputStream is=htpUrl.getInputStream();
                //htpUrl.getInputStream();
                while (_run){
                    //time0=System.currentTimeMillis();
                    readStream=is.read(buffer,0,readSize);

                    for(int i=0;i<readStream;i++){
                        switch (status){
                            //Content-Length:
                            case 0: if (buffer[i] == (byte)'C') {status++;} else status = 0; break;
                            case 1: if (buffer[i] == (byte)'o') status++; else status = 0; break;
                            case 2: if (buffer[i] == (byte)'n') status++; else status = 0; break;
                            case 3: if (buffer[i] == (byte)'t') status++; else status = 0; break;
                            case 4: if (buffer[i] == (byte)'e') status++; else status = 0; break;
                            case 5: if (buffer[i] == (byte)'n') status++; else status = 0; break;
                            case 6: if (buffer[i] == (byte)'t') status++; else status = 0; break;
                            case 7: if (buffer[i] == (byte)'-') status++; else status = 0; break;
                            case 8: if (buffer[i] == (byte)'L') status++; else status = 0; break;
                            case 9: if (buffer[i] == (byte)'e') status++; else status = 0; break;
                            case 10: if (buffer[i] == (byte)'n') status++; else status = 0; break;
                            case 11: if (buffer[i] == (byte)'g') status++; else status = 0; break;
                            case 12: if (buffer[i] == (byte)'t') status++; else status = 0; break;
                            case 13: if (buffer[i] == (byte)'h') status++; else status = 0; break;
                            case 14: if (buffer[i] == (byte)':') status++; else status = 0; break;
                            case 15: if (buffer[i] == (byte)0xFF) status++;
                                jpg_count = 0;
                                jpg_buf[jpg_count++] =buffer[i];
                                break;
                            case 16:
                                if (buffer[i] == (byte)0xD8){
                                    status++;
                                    jpg_buf[jpg_count++] = buffer[i];
                                }
                                else{
                                    if (buffer[i] != (byte)0xFF) status = 15;
                                }
                                break;
                            case 17:
                                jpg_buf[jpg_count++] = buffer[i];
                                if (buffer[i] == (byte)0xFF) status++;
                                if (jpg_count >= bufSize) status = 0;
                                break;
                            case 18:
                                jpg_buf[jpg_count++] = buffer[i];
                                if (buffer[i] == (byte)0xD8){

                                    status = 0;
                                    //time0=System.currentTimeMillis();

                                    bmp = BitmapFactory.decodeStream(new ByteArrayInputStream(jpg_buf));

                                    if(imgWidth==0){imgWidth=bmp.getWidth();};
                                    if(imgHeight==0){imgHeight=bmp.getHeight();};
                                    float bl=mScreenWidth/imgWidth;
                                    int finalH=(int)(imgHeight*bl);


                                    mBitmap = Bitmap.createScaledBitmap(bmp,(int)mScreenWidth ,finalH, false);
                                    //mBitmap = Bitmap.createScaledBitmap(bmp,(int)imgWidth ,(int)imgHeight, false);
                                    //mBitmap=bmp;
                                    time1=System.currentTimeMillis();
                                    canvas = sfh.lockCanvas(new Rect(0,0,(int)mScreenWidth,(int)mScreenHeight));
                                    canvas.drawColor(Color.BLACK);
                                    canvas.drawBitmap(mBitmap,0,-(finalH-mScreenHeight)/2, null);
                                    canvas.drawText((time1-time0)+"",600,50,pt);
                                    sfh.unlockCanvasAndPost(canvas);//画完一副图像，解锁画布
                                    time0=time1;


                                }else{
                                    if (buffer[i] != (byte)0xFF) status = 17;
                                }
                                break;
                            default:
                                status = 0;
                                break;
                        }
                    }
                }
            } catch (IOException e) {
                htpUrl.disconnect();
                e.printStackTrace();
            }
        }
    }
}
