package com.rgrg.dailydevotion.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class StartAppOnBoot extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                Intent i = new Intent(context, DailyDevotionServicev2.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                DailyDevotionServicev2.enqueueWork(context, i);
            } else if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.N){
                Intent i = new Intent(context, DailyDevotionServicev1.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startService(i);
            }
        }
    }
}
