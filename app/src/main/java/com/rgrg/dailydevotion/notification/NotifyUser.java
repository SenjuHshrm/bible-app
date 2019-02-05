package com.rgrg.dailydevotion.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.rgrg.dailydevotion.MainActivity;

public class NotifyUser extends JobService {

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        try{
            PendingIntent contIntent = PendingIntent.getActivity
                    (this, 0 , new Intent(this, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationManager man = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1010");
            builder
                    .setContentTitle("Daily Devotion")
                    .setContentText("Time for your journal")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setDefaults(NotificationCompat.DEFAULT_ALL);

            man.notify(0, builder.build());
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return true;
    }
}
