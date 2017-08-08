package com.lis.smilealarmclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public class AlarmClockTask {

    private int hour;
    private int minute;
    private boolean repeat;
    private boolean camera;
    private Context context;

    public AlarmClockTask(int hour, int minute, boolean repeat, boolean camera, Context context) {
        this.hour = hour;
        this.minute = minute;
        this.repeat = repeat;
        this.camera = camera;
        this.context = context;
    }

    public void start() {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    public void stop() {}
}
