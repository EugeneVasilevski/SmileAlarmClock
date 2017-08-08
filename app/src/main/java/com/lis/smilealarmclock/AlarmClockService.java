package com.lis.smilealarmclock;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.concurrent.TimeUnit;

public class AlarmClockService extends Service {

    final String TAG = AlarmClockService.class.getSimpleName();

    Object someObject;

    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        someObject = new Object();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        new Run(intent.getIntExtra("time", 1), startId);
        return START_REDELIVER_INTENT;
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        someObject = null;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return null;
    }

    private void someTask() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 60; i++) {
                    Log.d(TAG, "i = " + Integer.toString(i));
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                stopSelf();
            }
        }).start();
    }

    class Run implements Runnable {

        int time;
        int startId;

        public Run(int time, int startId) {
            this.time = time;
            this.startId = startId;
        }

        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(time);
                Log.d(TAG, "RUN#" + " startId:"+ startId +" time:" + time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                Log.d(TAG, "RUN#" + " startId:"+ startId +" object state:" + someObject.getClass());
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            stop();
        }

        public void stop() {
            stopSelf(startId);
        }
    }
}
