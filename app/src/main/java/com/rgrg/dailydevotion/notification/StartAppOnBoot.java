package com.rgrg.dailydevotion.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class StartAppOnBoot extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())){
            Intent i = new Intent(context, DailyDevotionServicev1.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                context.startForegroundService(i);
            } else if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.N){
                context.startService(i);
            }
        }
    }
}
