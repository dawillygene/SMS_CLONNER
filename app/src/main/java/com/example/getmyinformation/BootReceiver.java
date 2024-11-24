package com.example.getmyinformation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Intent serviceIntent = new Intent(context, BackgroundService.class);
            context.startForegroundService(serviceIntent);
            scheduleAppRestart(context);
            AppRestartManager.scheduleAppRestart(context);
        }
        scheduleAppRestart(context);
        AppRestartManager.scheduleAppRestart(context);
    }

    private void scheduleAppRestart(Context context) {
        Intent restartIntent = new Intent(context, MainActivity.class);
        restartIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(restartIntent);
    }
}

