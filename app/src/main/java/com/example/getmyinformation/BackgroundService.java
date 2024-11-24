package com.example.getmyinformation;

import android.app.NotificationManager;
import android.app.Service;
import android.app.Notification;
import android.app.NotificationChannel;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import androidx.core.app.NotificationCompat;

public class BackgroundService extends Service {

    private static final String TAG = "BackgroundService";
    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "BACKGROUND_CHANNEL";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Service created");

        // Create the notification channel (for Android O and above)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Background Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            getSystemService(NotificationManager.class).createNotificationChannel(channel);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Service started");

        // Create a notification to run this as a foreground service
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Background Service")
                .setContentText("Monitoring in the background")
                .setSmallIcon(R.drawable.ic_launcher_foreground) // Make sure to have a valid icon
                .build();

        //
        startForeground(NOTIFICATION_ID, notification);

        // Run your background task here (e.g., SMS capturing, sending data to server)
        startBackgroundTask();

        return START_STICKY; // Ensures the service restarts if it gets killed
    }

    private void startBackgroundTask() {
        // Simulate your background task (e.g., log data, monitor SMS)
        Log.d(TAG, "Background task running...");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Service destroyed");

        AppRestartManager.scheduleAppRestart(getApplicationContext());
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null; // We don’t use binding here
    }
}
