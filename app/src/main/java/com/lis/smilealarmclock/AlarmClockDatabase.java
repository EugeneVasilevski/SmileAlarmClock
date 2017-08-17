package com.lis.smilealarmclock;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
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
        database.insert(tableName, null, getAlarmClockContentValues(alarmClock));
    }

    public void update(AlarmClock alarmClock) {
        database.update(tableName, getAlarmClockContentValues(alarmClock),
                "id = " + alarmClock.getId(), null);
    }

    public void updateParameter(int id, String parameter, int value) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(parameter, value);
        database.update(tableName, contentValues, "id = " + id, null);
    }

    public void delete(int id) {
        database.delete(tableName, "id = " + id, null);
    }

    public void cleare() {
        database.delete(tableName, null, null);
    }

    public AlarmClock getById(int id) {
        ArrayList<AlarmClock> alarmClockList = getAlarmClockList(database.query(tableName,
                null, "id = " + id, null, null, null, null));

        if (alarmClockList == null) {
            return null;
        }

        return alarmClockList.get(0);
    }

    public ArrayList<AlarmClock> getAll() {
        return getAlarmClockList(database.query(tableName, null, null,
                null, null, null, null));
    }

    public ArrayList<AlarmClock> getAllActive() {
        return getAlarmClockList(database.query(tableName, null, "active = 1",
                null, null, null, null));
    }

    public void close() {
        databaseHelper.close();
    }

    private ContentValues getAlarmClockContentValues(AlarmClock alarmClock) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("hour", alarmClock.getHour());
        contentValues.put("minute", alarmClock.getMinute());
        contentValues.put("active", alarmClock.isActive());
        contentValues.put("camera", alarmClock.isCamera());

        if (alarmClock.isRepeat()) {
            contentValues.put("activeDays", convertToString(alarmClock.getActiveDays()));
        }

        return contentValues;
    }

    @Nullable
    private ArrayList<AlarmClock> getAlarmClockList(Cursor cursor) {
        if (!cursor.moveToFirst()) {
            return null;
        }

        ArrayList<AlarmClock> alarmClockList  = new ArrayList<>(cursor.getCount());

        /*int idIndex = cursor.getColumnIndex("id");
        int hourIndex = cursor.getColumnIndex("hour");
        int minuteIndex = cursor.getColumnIndex("minute");
        int activeIndex = cursor.getColumnIndex("active");
        int repeatIndex = cursor.getColumnIndex("repeat");
        int cameraIndex = cursor.getColumnIndex("camera");*/

        do {
            alarmClockList.add(new AlarmClock(
                    cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getInt(cursor.getColumnIndex("hour")),
                    cursor.getInt(cursor.getColumnIndex("minute")),
                    (cursor.getInt(cursor.getColumnIndex("active")) == 1),
                    (cursor.getInt(cursor.getColumnIndex("camera")) == 1),
                    convertToBooleanArray(cursor.getString(cursor.getColumnIndex("activeDays")))));
        } while (cursor.moveToNext());

        return alarmClockList;
    }

    private String convertToString(boolean[] activeDays) {
        if (activeDays == null) {
            return null;
        }

        String activeDaysIndices = "";

        for (int i = 0; i < activeDays.length; i++) {
            if (activeDays[i]) {
                activeDaysIndices += i;
            }
        }

        return activeDaysIndices;
    }

    private boolean[] convertToBooleanArray(String activeDaysIndices) {
        if (activeDaysIndices == null) {
            return null;
        }

        char[] activeDaysIndicesArray = activeDaysIndices.toCharArray();
        boolean[] activeDays = new boolean[7];

        for (int i = 0; i < activeDays.length; i++) {
            activeDays[i] = false;
        }

        for (char index : activeDaysIndicesArray) {
            activeDays[Character.getNumericValue(index)] = true;
        }

        return activeDays;
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
                + "activeDays text,"
                + "camera integer" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {

    }
}
