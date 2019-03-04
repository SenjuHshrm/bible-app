package com.rgrg.dailydevotion.app;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class DailyDevotion extends Application {
    public static final String CHANNEL_ID = "DailyDevotionChannelId";

    @Override
    public void onCreate(){
        super.onCreate();
        createNotifChannel();
    }

    private void createNotifChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            try {
                NotificationChannel serviceChannel = new NotificationChannel(
                        CHANNEL_ID,
                        "Daily Devotion Notification Channel",
                        NotificationManager.IMPORTANCE_HIGH
                );
                NotificationManager nMan = getApplicationContext().getSystemService(NotificationManager.class);
                nMan.createNotificationChannel(serviceChannel);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
