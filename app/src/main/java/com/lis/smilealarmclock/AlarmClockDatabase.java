package com.lis.smilealarmclock;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class AlarmClockDatabase {

    public static final String databaseName = "AlarmClockDatabase";
    public static final String tableName = "AlarmClock";

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

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
        database.insert(tableName, null, fillContentValues(alarmClock));
    }

    public void update(AlarmClock alarmClock) {
        database.update(tableName, fillContentValues(alarmClock),
                "id = " + alarmClock.getId(), null);
    }

    private ContentValues fillContentValues(AlarmClock alarmClock) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("hour", alarmClock.getHour());
        contentValues.put("minute", alarmClock.getMinute());
        contentValues.put("active", alarmClock.isActive());
        contentValues.put("repeat", alarmClock.isRepeat());
        contentValues.put("camera", alarmClock.isCamera());

        return contentValues;
    }

    public void delete(AlarmClock alarmClock) {
        String id = Integer.toString(alarmClock.getId());
        database.delete(tableName, "id = " + id, null);
    }

    public void cleare() {
        database.delete(tableName, null, null);
    }

    public AlarmClock get() {
        return new AlarmClock(17, 30, true, false, false);
    }

    public ArrayList<AlarmClock> getAll() {
        return result(database.query(tableName, null, null, null, null, null, null));
    }

    public ArrayList<AlarmClock> getAllActive() {
        return result(database.query(tableName, null, "active = 1", null, null, null, null));
    }

    private ArrayList<AlarmClock> result(Cursor cursor) {
        if (!cursor.moveToFirst()) {
            return null;
        }

        ArrayList<AlarmClock> alarmClockList  = new ArrayList<>(cursor.getCount());

        int idIndex = cursor.getColumnIndex("id");
        int hourIndex = cursor.getColumnIndex("hour");
        int minuteIndex = cursor.getColumnIndex("minute");
        int activeIndex = cursor.getColumnIndex("active");
        int repeatIndex = cursor.getColumnIndex("repeat");
        int cameraIndex = cursor.getColumnIndex("camera");

        do {
            alarmClockList.add(new AlarmClock()
                    .setId(cursor.getInt(idIndex))
                    .setHour(cursor.getInt(hourIndex))
                    .setMinute(cursor.getInt(minuteIndex))
                    .setActive(cursor.getInt(activeIndex) == 1)
                    .setRepeat(cursor.getInt(repeatIndex) == 1)
                    .setCamera(cursor.getInt(cameraIndex) == 1));
        } while (cursor.moveToNext());

        return alarmClockList;
    }

    public void close() {
        databaseHelper.close();
    }
}

class DatabaseHelper extends SQLiteOpenHelper {

    final String TAG = this.getClass().getSimpleName();

    public DatabaseHelper(Context context) {
        super(context, AlarmClockDatabase.databaseName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        Log.d(TAG, "onCreate");

        database.execSQL("create table " + AlarmClockDatabase.tableName + " ("
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
