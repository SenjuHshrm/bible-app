package com.rgrg.dailydevotion.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;
import android.support.v4.app.NotificationCompat;

import com.rgrg.dailydevotion.MainActivity;

import java.util.Calendar;

public class DailyDevotionServicev2 extends JobIntentService {

    public static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, DailyDevotionServicev2.class, 1000, work);
    }
    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Calendar calM = Calendar.getInstance(),
                 calE = Calendar.getInstance();
        calM.set(Calendar.HOUR_OF_DAY, 6);
        calM.set(Calendar.MINUTE, 0);
        calM.set(Calendar.SECOND, 0);
        calM.set(Calendar.MILLISECOND, 0);
        calE.set(Calendar.HOUR_OF_DAY, 18);
        calE.set(Calendar.MINUTE, 0);
        calE.set(Calendar.SECOND, 0);
        calE.set(Calendar.MILLISECOND, 0);
        long milliM = calM.getTimeInMillis(),
             milliE = calE.getTimeInMillis();
        if(Calendar.getInstance().getTimeInMillis() == milliM){
            notifyUser("morning");
        } else if(Calendar.getInstance().getTimeInMillis() == milliE) {
            notifyUser("evening");
        }
    }

    private void notifyUser(String str) {
        try {
            String msg = "Time for your " + str + " journal";
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 1000, new Intent(getApplicationContext(), MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationManager notifManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(this, "session")
                    .setContentTitle("Daily Devotion Journal")
                    .setContentText(msg)
                    .setSmallIcon(android.R.mipmap.sym_def_app_icon)
                    .setPriority(Notification.PRIORITY_MAX)
                    .setContentIntent(pendingIntent);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                NotificationChannel channel = new NotificationChannel("session", "daily_dev_notif", NotificationManager.IMPORTANCE_HIGH);
                notifManager.createNotificationChannel(channel);
            }
            notifManager.notify(1000, notifBuilder.build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
