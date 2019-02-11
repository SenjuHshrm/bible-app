package com.rgrg.dailydevotion.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.rgrg.dailydevotion.MainActivity;

import java.util.Calendar;

public class DailyDevotionNotificationService extends Service {

    public DailyDevotionNotificationService() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        onTaskRemoved(intent);
        Calendar calM = Calendar.getInstance();
        Calendar calE = Calendar.getInstance();
        calM.set(Calendar.HOUR_OF_DAY, 6);
        calM.set(Calendar.MINUTE, 0);
        calM.set(Calendar.SECOND, 0);
        calE.set(Calendar.HOUR_OF_DAY, 18);
        calE.set(Calendar.MINUTE, 0);
        calE.set(Calendar.SECOND, 0);
        long milliM = calM.getTimeInMillis();
        long milliE = calE.getTimeInMillis();
        if(Calendar.getInstance().getTimeInMillis() == milliM || Calendar.getInstance().getTimeInMillis() == milliE) {
            notifyUser();
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

    private void notifyUser() {
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 1020, new Intent(getApplicationContext(), MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder notif = new NotificationCompat.Builder(getApplicationContext(), "1020")
                .setContentIntent(pendingIntent)
                .setContentTitle("Daily Devotion")
                .setContentText("Time for your journal")
                .setSmallIcon(android.R.mipmap.sym_def_app_icon)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL);
        notificationManager.notify(1020, notif.build());
    }
}
