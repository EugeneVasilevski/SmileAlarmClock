package com.lis.smilealarmclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmClockTaskStarterReceiver extends BroadcastReceiver {

    final String TAG = AlarmClockTaskStarterReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent newIntent = new Intent(context, CameraSmileActivity.class);
        newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(newIntent);
    }
}