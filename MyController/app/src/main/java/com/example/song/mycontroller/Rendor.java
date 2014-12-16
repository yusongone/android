package com.example.song.mycontroller;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.lang.reflect.Array;

/**
 * Created by song on 14/12/15.
 */
public class Rendor {
    public void rendorSlideshow(final ViewFlipper vf,JSONObject json){
        JSONObject obj= null;
        JSONArray jar=null;
        String str="";
        try {
            str= json.getJSONArray("slideshowData").getJSONObject(0).optString("slug"); jar= json.getJSONArray("slideshowData"); for(int i=0;i<jar.length();i++) { String d = jar.getJSONObject(i).optString("url");
                Log.e("dddddddddddddd", d);
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("test",str);

    }
}
