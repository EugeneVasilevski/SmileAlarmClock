package com.lis.smilealarmclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.Calendar;

public class AlarmClockTask {

    final String TAG = AlarmClockTask.class.getSimpleName();

    private AlarmClock alarmClock;
    private Context context;
    private AlarmManager alarmManager;

    public AlarmClockTask(AlarmClock alarmClock, Context context) {
        this.alarmClock = alarmClock;
        this.context = context;
    }

    public void create() {}

    public void start() {
        alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, alarmClock.getHour());
        calendar.set(Calendar.MINUTE, alarmClock.getMinute());
        calendar.set(Calendar.SECOND, 0);

        Intent intent = new Intent(context, AlarmClockTaskStarterReceiver.class);
        intent.putExtra("camera", alarmClock.isCamera());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        if (alarmClock.isRepeat()) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }

        //something();
    }

    public void something() {
        ComponentName receiver = new ComponentName(context, AlarmClockTaskBootReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);

        Log.d(TAG, "start");
    }

    private Intent createIntent(String action, String extra) {
        Intent intent = new Intent(context, AlarmClockTaskStarterReceiver.class);
        intent.setAction(action);
        intent.putExtra("extra", extra);
        return intent;
    }

    /*public static void stop() {
        if (alarmManager!= null) {
            alarmManager.cancel(pendingIntent);
        }
    }*/

    public void update() {}
}
