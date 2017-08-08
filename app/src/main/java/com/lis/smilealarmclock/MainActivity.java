package com.lis.smilealarmclock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.text);
    }

    public void clickOnStart(View view) {
        startService(new Intent(this, AlarmClockService.class).putExtra("time", 4));
        startService(new Intent(this, AlarmClockService.class).putExtra("time", 7));
        startService(new Intent(this, AlarmClockService.class).putExtra("time", 5));
        textView.setText("Service started");
    }

    public void clickOnStop(View view) {
        stopService(new Intent(this, AlarmClockService.class));
        textView.setText("Service stopped");
    }
}
