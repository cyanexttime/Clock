package com.example.clock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // This is where you could launch a notification or a sound
        Toast.makeText(context, "Alarm is ringing!", Toast.LENGTH_SHORT).show();
    }
}