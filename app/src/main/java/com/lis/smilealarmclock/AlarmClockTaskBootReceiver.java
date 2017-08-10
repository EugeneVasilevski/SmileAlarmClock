package com.lis.smilealarmclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmClockTaskBootReceiver extends BroadcastReceiver {

    final String TAG = AlarmClockTask.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            AlarmClock alarmClock = new AlarmClock(17, 30, false, false);
            AlarmClockTask alarmClockTask = new AlarmClockTask(alarmClock, context);
            alarmClockTask.start();
        }
    }
}
