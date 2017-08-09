package com.lis.smilealarmclock;

public class AlarmClock {

    private int hour;
    private int minute;
    private boolean repeat;
    private boolean camera;

    public AlarmClock(int hour, int minute, boolean repeat, boolean camera) {
        this.hour = hour;
        this.minute = minute;
        this.repeat = repeat;
        this.camera = camera;
    }

    public int getHour() {
        return this.hour;
    }

    public int getMinute() {
        return this.minute;
    }

    public boolean isRepeat() {
        return this.repeat;
    }

    public boolean isCamera() {
        return this.camera;
    }
}
