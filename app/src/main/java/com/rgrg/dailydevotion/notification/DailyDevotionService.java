package com.rgrg.dailydevotion.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.rgrg.dailydevotion.MainActivity;
import com.rgrg.dailydevotion.R;

import java.util.Calendar;

import static com.rgrg.dailydevotion.notification.DailyDevotion.CHANNEL_ID;

public class DailyDevotionService extends Service {

    public DailyDevotionService() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Intent i = intent;
        onTaskRemoved(i);
        Calendar calM = Calendar.getInstance();
        Calendar calE = Calendar.getInstance();
        calM.set(Calendar.HOUR_OF_DAY, 6);
        calM.set(Calendar.MINUTE, 0);
        calM.set(Calendar.SECOND, 0);
        calM.set(Calendar.MILLISECOND, 0);
        calE.set(Calendar.HOUR_OF_DAY, 18);
        calE.set(Calendar.MINUTE, 0);
        calE.set(Calendar.SECOND, 0);
        calE.set(Calendar.MILLISECOND, 0);
        long milliM = calM.getTimeInMillis();
        long milliE = calE.getTimeInMillis();
        if(Calendar.getInstance().getTimeInMillis() == milliM) {
            notifyUser("morning");
        } else if(Calendar.getInstance().getTimeInMillis() == milliE) {
            notifyUser("evening");
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            return START_REDELIVER_INTENT;
        }
        return START_STICKY;

    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onTaskRemoved(Intent intent){
        Intent restartService = new Intent(getApplicationContext(), this.getClass());
        restartService.setPackage(getPackageName());
        startService(restartService);
        super.onTaskRemoved(intent);
    }

    private void notifyUser(String str) {
        try {
            String msg = "Time for your " + str + " journal";
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 1020, new Intent(getApplicationContext(), MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
            NotificationCompat.Builder notif = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                    .setContentIntent(pendingIntent)
                    .setContentTitle("Daily Devotion")
                    .setContentText(msg)
                    .setSmallIcon(R.mipmap.ic_app_logo)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setAutoCancel(true);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                startForeground(1020, notif.build());
                stopForeground(false);
            } else {
                notificationManager.notify(1020, notif.build());
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
