package com.lis.smilealarmclock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    final String TAG = this.getClass().getSimpleName();

    private TextView textView;
    private TimePicker timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.text);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
    }

    public void clickOnStart(View view) {
        /*startService(new Intent(this, AlarmClockService.class).putExtra("time", 4));
        startService(new Intent(this, AlarmClockService.class).putExtra("time", 7));
        startService(new Intent(this, AlarmClockService.class).putExtra("time", 5));
        textView.setText("Service started");*/

        AlarmClockDatabase alarmClockDatabase = new AlarmClockDatabase(this);
        alarmClockDatabase.connection();
        ArrayList<AlarmClock> alarmClockList = alarmClockDatabase.getAll();

        if (alarmClockList != null) {
            AlarmClock alarmClock = alarmClockList.get(alarmClockList.size() - 1);
            alarmClock.setHour(timePicker.getCurrentHour());
            alarmClock.setMinute(timePicker.getCurrentMinute());

            alarmClockDatabase.update(alarmClock);
            alarmClockList = alarmClockDatabase.getAll();

            textView.setText(Integer.toString(alarmClockList.get(alarmClockList.size() - 1).getMinute()));
        } else {
            textView.setText("Database is empty");
        }

        alarmClockDatabase.close();
    }

    public void clickOnStop(View view) {
        /*stopService(new Intent(this, AlarmClockService.class));
        textView.setText("Service stopped");*/

        AlarmClockDatabase alarmClockDatabase = new AlarmClockDatabase(this);
        alarmClockDatabase.connection();
        ArrayList<AlarmClock> alarmClockList = alarmClockDatabase.getAll();

        if (alarmClockList != null) {
            AlarmClock alarmClock = alarmClockList.get(alarmClockList.size() - 1);
            alarmClockDatabase.delete(alarmClock.getId());
        } else {
            textView.setText("Database is empty");
        }

        alarmClockDatabase.getById(1);
        alarmClockList = alarmClockDatabase.getAll();

        if (alarmClockList != null) {
            textView.setText(Integer.toString(alarmClockList.get(alarmClockList.size() - 1).getMinute()));
        } else {
            textView.setText("Database is empty");
        }
    }

    public void click(View view) {
       AlarmClock alarmClock = new AlarmClock(timePicker.getCurrentHour(),
                timePicker.getCurrentMinute(), true, false, false);
        AlarmClockTask alarmClockTask = new AlarmClockTask(this);
        alarmClockTask.start(alarmClock);

        Log.d(TAG, "click");

        /*AlarmClockDatabase alarmClockDatabase = new AlarmClockDatabase(this);
        alarmClockDatabase.connection();
        alarmClockDatabase.add(new AlarmClock(timePicker.getCurrentHour(),
                timePicker.getCurrentMinute(), true, false, false));

        ArrayList<AlarmClock> alarmClockList = alarmClockDatabase.getAllActive();
        //textView.setText(Integer.toString(alarmClockList.get(0).getMinute()));
        textView.setText(Integer.toString(alarmClockList.get(alarmClockList.size() - 1).getMinute()));
        alarmClockDatabase.close();*/
    }
}
