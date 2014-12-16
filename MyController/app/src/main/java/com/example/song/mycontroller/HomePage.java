package com.example.song.mycontroller;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;




public class HomePage extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final HomePage t=this;
        setContentView(R.layout.activity_internet);

        final GetDataFromInternet gdfi=new GetDataFromInternet();

        final ImageView iv=(ImageView)findViewById(R.id.logo);
        final ListView lv=(ListView)findViewById(R.id.article_list);
        final ViewFlipper vf=(ViewFlipper)findViewById(R.id.viewFlipper);

        final Animation l= AnimationUtils.loadAnimation(t,R.anim.ani_right);
        l.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        vf.setAnimation(l);
        vf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vf.showNext();
            }
        });


        new Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject pageData=gdfi.getPageData();
                JSONArray slideshowData=null;
                try {
                    slideshowData=pageData.getJSONArray("slideshowData");
                    for(int i=0;i<slideshowData.length();i++){
                        String url=slideshowData.getJSONObject(i).optString("url");
                        final Bitmap bitmap=gdfi.getImageBitmap(url);
                        final ImageView iv2=new ImageView(t);
                        vf.post(new Runnable() {
                            @Override
                            public void run() {
                                iv2.setImageBitmap(bitmap);
                                iv2.setScaleType(ImageView.ScaleType.FIT_XY);
                                vf.addView(iv2);
                                iv2.destroyDrawingCache();
                                System.gc();
                            }
                        });
                    }
                    vf.setAutoStart(false);         // 设置自动播放功能（点击事件，前自动播放）
                    vf.setFlipInterval(500);
                } catch (JSONException e) {
                    Log.e("fefe","get a bug ");
                    e.printStackTrace();
                }
            }
        }).start();

    }

    final Thread getSlideshowData=new Thread(new Runnable() {
        @Override
        public void run() {

        }
    });

}
