package com.lis.smilealarmclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmClockReceiver extends BroadcastReceiver {

    final String TAG = AlarmClockReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context ctx, Intent intent) {
        Log.d(TAG, "onReceive");
        Log.d(TAG, "action = " + intent.getAction());
        Log.d(TAG, "extra = " + intent.getStringExtra("extra"));

        Intent newIntent= new Intent(ctx, CameraSmileActivity.class);
        newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(newIntent);
    }
}
