package com.lis.smilealarmclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.Calendar;

public class AlarmClockTask {

    final String TAG = AlarmClockTask.class.getSimpleName();

    private AlarmManager alarmManager;
    private Context context;

    public AlarmClockTask(Context context) {
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        this.context = context;
    }

    public void start(AlarmClock alarmClock) {
        if (alarmClock.isRepeat()) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                    createCalendar(alarmClock).getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY,
                    createPendingIntent(alarmClock));
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP,
                    createCalendar(alarmClock).getTimeInMillis(),
                    createPendingIntent(alarmClock));
        }

        //startTaskAfterReboot();
    }

    public void startAll(ArrayList<AlarmClock> alarmClockList) {
        for (AlarmClock alarmClock : alarmClockList) {
            start(alarmClock);
        }
    }

    public void stop(AlarmClock alarmClock) {
        if (alarmManager!= null) {
            alarmManager.cancel(createPendingIntent(alarmClock));
        }
    }

    private Calendar createCalendar(AlarmClock alarmClock) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, alarmClock.getHour());
        calendar.set(Calendar.MINUTE, alarmClock.getMinute());
        calendar.set(Calendar.SECOND, 0);
        return calendar;
    }

    private PendingIntent createPendingIntent(AlarmClock alarmClock) {
        return PendingIntent.getActivity(context,
                alarmClock.getId(),
                new Intent(context, CameraSmileActivity.class)
                        .putExtra("repeat", alarmClock.isRepeat())
                        .putExtra("camera", alarmClock.isCamera()),
                PendingIntent.FLAG_CANCEL_CURRENT);
    }

    public void startTaskAfterReboot() {
        ComponentName receiver = new ComponentName(context, AlarmClockTaskBootReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }

    private Intent createIntent(String action, String extra) {
        Intent intent = new Intent(context, CameraSmileActivity.class);
        intent.setAction(action);
        intent.putExtra("extra", extra);
        return intent;
    }
}
