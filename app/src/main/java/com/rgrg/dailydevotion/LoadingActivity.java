package com.rgrg.dailydevotion;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.rgrg.dailydevotion.notification.DailyDevotionService;

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
                Intent i = new Intent(getApplicationContext(), DailyDevotionService.class);
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    startForegroundService(i);
                } else if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.N){
                    startService(i);
                }
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);
    }
}
