package com.rgrg.dailydevotion.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class StartAppOnBoot extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                Intent i = new Intent(context, com.rgrg.dailydevotion.notification_latest.DailyDevotionService.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startForegroundService(i);
            } else if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.N){
                Intent i = new Intent(context, com.rgrg.dailydevotion.notification_legacy.DailyDevotionService.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startService(i);
            }
        }
    }
}
