package com.lis.smilealarmclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

public class AlarmClockTask {

    final String TAG = AlarmClockTask.class.getSimpleName();

    private AlarmClock alarmClock;
    private AlarmManager alarmManager;
    private Intent intent;
    private PendingIntent pendingIntent;
    private Context context;

    public AlarmClockTask(AlarmClock alarmClock, Context context) {
        this.alarmClock = alarmClock;
        this.context = context;
    }

    public void start() {
        alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, alarmClock.getHour());
        calendar.set(Calendar.MINUTE, alarmClock.getMinute());
        calendar.set(Calendar.SECOND, 0);

        intent = createIntent("action", "extra");
        pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        Log.d(TAG, "start");

        if (alarmClock.isRepeat()) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
    }

    private Intent createIntent(String action, String extra) {
        Intent intent = new Intent(context, AlarmClockReceiver.class);
        intent.setAction(action);
        intent.putExtra("extra", extra);
        return intent;
    }

    public void stop() {
        if (alarmManager!= null) {
            alarmManager.cancel(pendingIntent);
        }
    }

    public void update() {}
}
