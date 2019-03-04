package com.rgrg.dailydevotion;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import java.io.InputStream;

public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        loadImg();
        loadSys();
    }

    private void loadImg(){
        try {
            ConstraintLayout cl = (ConstraintLayout) findViewById(R.id.app_loading_screen);
            InputStream is = getAssets().open("bg/ddev_loading.jpg");
            Drawable d = Drawable.createFromStream(is, null);
            cl.setBackground(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadSys() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run(){

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    Intent i = new Intent(getApplicationContext(), com.rgrg.dailydevotion.notification_latest.DailyDevotionService.class);
                    startForegroundService(i);
                } else if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.N){
                    Intent i = new Intent(getApplicationContext(), com.rgrg.dailydevotion.notification_legacy.DailyDevotionService.class);
                    startService(i);
                }
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);
    }
}
