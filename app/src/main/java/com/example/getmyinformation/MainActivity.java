package com.example.getmyinformation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;



public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent serviceIntent = new Intent(this, BackgroundService.class);
        startService(serviceIntent);

        requestBatteryOptimizationExclusion(this);

    }

    public void startService(View view) {
        Intent serviceIntent = new Intent(this, SmsService.class);
        startService(serviceIntent);
    }


    public void requestBatteryOptimizationExclusion(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
            intent.setData(Uri.parse("package:" + context.getPackageName()));
            context.startActivity(intent);
        }
    }

}
