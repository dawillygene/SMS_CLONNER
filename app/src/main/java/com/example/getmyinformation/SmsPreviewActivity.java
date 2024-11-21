package com.example.getmyinformation;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SmsPreviewActivity extends AppCompatActivity {
    private static final String TAG = "SmsPreviewActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // Fixed missing parenthesis

        // Get sender and message from intent
        String sender = getIntent().getStringExtra("sender");
        String message = getIntent().getStringExtra("message");

        // Display the SMS details in a dialog
        if (sender != null && message != null) {
            showSmsDialog(sender, message);
        } else {
            Log.e(TAG, "No SMS data received.");
            finish(); // Close the activity if no data
        }
    }

    private void showSmsDialog(String sender, String message) {
        new AlertDialog.Builder(this)
                .setTitle("SMS Received")
                .setMessage("Sender: " + sender + "\nMessage: " + message)
                .setPositiveButton("Send to Server", (dialog, which) -> {
                    sendSmsToServer(sender, message);
                    finish(); // Close the activity after sending
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.dismiss();
                    finish(); // Close the activity
                })
                .setCancelable(false)
                .show();
    }

    private void sendSmsToServer(String sender, String message) {
        new Thread(() -> {
            try {
                URL url = new URL("https://backuptrack.salumtransports.co.tz/sms-handler.php");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                String jsonInput = "{\"sender\":\"" + sender + "\", \"message\":\"" + message + "\"}";

                OutputStream os = connection.getOutputStream();
                os.write(jsonInput.getBytes());
                os.flush();
                os.close();

                int responseCode = connection.getResponseCode();
                Log.d(TAG, "Server Response Code: " + responseCode);

            } catch (Exception e) {
                Log.e(TAG, "Error sending SMS to server: ", e);
            }
        }).start();
    }
}
