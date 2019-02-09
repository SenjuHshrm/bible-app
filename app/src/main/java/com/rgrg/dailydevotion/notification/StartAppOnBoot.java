package com.rgrg.dailydevotion.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.rgrg.dailydevotion.MainActivity;

public class StartAppOnBoot extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())){
            Intent i = new Intent(context, AppService.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }
}
