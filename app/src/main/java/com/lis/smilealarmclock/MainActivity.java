package com.lis.smilealarmclock;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.List;

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
        List<AlarmClock> alarmClockList = alarmClockDatabase.getAll();

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

        /*AlarmClockDatabase alarmClockDatabase = new AlarmClockDatabase(this);
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
        }*/

        AlarmClock alarmClock = new AlarmClock(0, timePicker.getCurrentHour(),
                timePicker.getCurrentMinute(), true, false, null);
        alarmClock.setId(0);
        AlarmClockTask alarmClockTask = new AlarmClockTask(this);
        alarmClockTask.start(alarmClock);
    }

    int id = 0;

    public void click(View view) {
       AlarmClock alarmClock = new AlarmClock(0, timePicker.getCurrentHour(),
                timePicker.getCurrentMinute(), true, false, null);
        alarmClock.setId(id);
        id++;
        AlarmClockTask alarmClockTask = new AlarmClockTask(this);
        alarmClockTask.start(alarmClock);

        Log.d(TAG, "click");
/*
        boolean[] activeDays = {false, true, false, false, false, false, false};

        AlarmClockDatabase alarmClockDatabase = new AlarmClockDatabase(this);
        alarmClockDatabase.connection();
        alarmClockDatabase.add(new AlarmClock(0, timePicker.getCurrentHour(),
                timePicker.getCurrentMinute(), true, false, activeDays));

        List<AlarmClock> alarmClockList = alarmClockDatabase.getAll();

        //textView.setText(Integer.toString(alarmClockList.get(0).getMinute()));
        //textView.setText(Integer.toString(alarmClockList.get(alarmClockList.size() - 1).getId()));

        textView.setText(Boolean.toString(alarmClockList.get(alarmClockList.size() - 1).getActiveDays()[1])); // может вернуть нулевой массив
        alarmClockDatabase.close();*/
    }
}
