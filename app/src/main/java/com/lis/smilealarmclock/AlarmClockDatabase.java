package com.lis.smilealarmclock;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class AlarmClockDatabase {

    private final String databaseName = "AlarmClockDatabase";
    private final String tableName = "AlarmClock";

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private ContentValues contentValues = new ContentValues();

    public AlarmClockDatabase(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public boolean connection() {
        if (databaseHelper == null) {
            return false;
        }
        database = databaseHelper.getWritableDatabase();
        return true;
    }

    public void add(AlarmClock alarmClock) {
        contentValues.put("hour", alarmClock.getHour());
        contentValues.put("minute", alarmClock.getMinute());
        contentValues.put("active", alarmClock.isActive());
        contentValues.put("repeat", alarmClock.isRepeat());
        contentValues.put("camera", alarmClock.isCamera());
        database.insert("AlarmClock", null, contentValues);
    }

    public void update() {}

    public void remove() {}

    public AlarmClock get() {
        return new AlarmClock(17, 30, true, false, false);
    }

    public ArrayList getAll() {
        Cursor cursor = database.query(tableName, null, null, null, null, null, null);
        ArrayList<AlarmClock> alarmClockList  = new ArrayList<>(1);

        //database.getPageSize();

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex("id");
            int hourIndex = cursor.getColumnIndex("hour");
            int minuteIndex = cursor.getColumnIndex("minute");
            int activeIndex = cursor.getColumnIndex("active");
            int repeatIndex = cursor.getColumnIndex("repeat");
            int cameraIndex = cursor.getColumnIndex("camera");

            do {
                AlarmClock alarmClock = new AlarmClock();
                alarmClock.setId(cursor.getInt(idIndex));
                alarmClock.setHour(cursor.getInt(hourIndex));
                alarmClock.setMinute(cursor.getInt(minuteIndex));
                alarmClock.setActive(cursor.getInt(activeIndex) == 1);
                alarmClock.setRepeat(cursor.getInt(repeatIndex) == 1);
                alarmClock.setCamera(cursor.getInt(cameraIndex) == 1);
                alarmClockList.add(alarmClock);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return alarmClockList;
    }

    public void getAllActive() {}

    private void close() {
        database.close();
    }
}

class DatabaseHelper extends SQLiteOpenHelper {

    final String TAG = this.getClass().getSimpleName();

    public DatabaseHelper(Context context) {
        super(context, "AlarmClockDatabase", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        Log.d(TAG, "onCreate");

        database.execSQL("create table AlarmClock ("
            + "id integer primary key autoincrement,"
            + "hour integer,"
            + "minute integer,"
            + "active integer,"
            + "repeat integer,"
            + "camera integer" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {

    }
}
