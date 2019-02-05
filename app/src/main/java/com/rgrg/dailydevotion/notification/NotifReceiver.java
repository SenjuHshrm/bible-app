package com.rgrg.dailydevotion.notification;

import android.content.BroadcastReceiver;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.rgrg.dailydevotion.MainActivity;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NotifReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        PendingIntent contIntent = PendingIntent.getActivity(context, 1010 , new Intent(context, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager man = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder notif = new NotificationCompat.Builder(context, "1010")
                .setContentIntent(contIntent)
                .setContentTitle("Daily Devotion")
                .setContentText("Time for your journal")
                .setSmallIcon(android.R.mipmap.sym_def_app_icon)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL);
        man.notify(1010, notif.build());
    }
}
