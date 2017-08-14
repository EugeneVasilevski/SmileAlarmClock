package com.lis.smilealarmclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

public class AlarmClockTaskBootReceiver extends BroadcastReceiver {

    final String TAG = AlarmClockTask.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            /*AlarmClock alarmClock = new AlarmClock(17, 19, true, false, false);
            AlarmClockTask alarmClockTask = new AlarmClockTask(alarmClock, context);
            alarmClockTask.start();*/

            AlarmClockDatabase alarmClockDatabase = new AlarmClockDatabase(context);

            if (alarmClockDatabase.connection()) {
                ArrayList<AlarmClock> alarmClockList = alarmClockDatabase.getAllActive();
                if (alarmClockList != null) {
                    new AlarmClockTask(context).startAll(alarmClockList);
                }
            }
        }
    }
}