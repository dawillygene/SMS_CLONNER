package com.example.getmyinformation;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

public class AppRestartManager {

    // Method to schedule app restart
    @SuppressLint("MissingPermission")
    public static void scheduleAppRestart(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // Create an Intent to trigger a broadcast, which will restart the app
        Intent restartIntent = new Intent(context, BootReceiver.class);  // You can use any activity or service here
        PendingIntent restartPendingIntent = PendingIntent.getBroadcast(context, 0, restartIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Set the alarm to trigger the restart after 5 minutes (300000 milliseconds)
        long triggerAtMillis = SystemClock.elapsedRealtime() + 12;  // 5 minutes
        alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtMillis, restartPendingIntent);
    }
}
