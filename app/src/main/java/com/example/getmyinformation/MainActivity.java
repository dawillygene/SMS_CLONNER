package com.example.getmyinformation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startService(View view) {
        // Manually start the service if needed (e.g., when a button is clicked)
        Intent serviceIntent = new Intent(this, SmsService.class);
        startService(serviceIntent);
    }
}
