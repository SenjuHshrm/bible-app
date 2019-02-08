package com.rgrg.dailydevotion.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.rgrg.dailydevotion.MainActivity;

import java.util.Calendar;

public class NotifyUser extends JobService {

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        try{
//            Calendar now = Calendar.getInstance();
//            Calendar SixMo = Calendar.getInstance();
//            SixMo.set(Calendar.HOUR_OF_DAY, 5);
//            SixMo.set(Calendar.MINUTE, 22);
//            SixMo.set(Calendar.SECOND, 0);
//            Calendar SixEv = Calendar.getInstance();
//            SixEv.set(Calendar.HOUR_OF_DAY, 23);
//            SixEv.set(Calendar.MINUTE, 51);
//            SixEv.set(Calendar.SECOND, 0);
            PendingIntent contIntent = PendingIntent.getActivity
                    (this, 1010 , new Intent(this, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationManager man = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            NotificationCompat.Builder notif = new NotificationCompat.Builder(this, "1010")
                    .setContentIntent(contIntent)
                    .setContentTitle("Daily Devotion")
                    .setContentText("Time for your journal")
                    .setSmallIcon(android.R.mipmap.sym_def_app_icon)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setDefaults(NotificationCompat.DEFAULT_ALL);
            //if(now.getTimeInMillis() == SixEv.getTimeInMillis() || now.getTimeInMillis() == SixMo.getTimeInMillis()) {
            man.notify(1010, notif.build());
            //}
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            onStopJob(jobParameters);
        }
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return true;
    }
}
