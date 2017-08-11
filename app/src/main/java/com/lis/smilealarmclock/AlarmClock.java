package com.lis.smilealarmclock;

public class AlarmClock {

    private int id;
    private int hour;
    private int minute;
    private boolean active;
    private boolean repeat;
    private boolean camera;

    public AlarmClock() {
        this.hour = 0;
        this.minute = 0;
        this.active = false;
        this.repeat = false;
        this.camera = false;
    }

    public AlarmClock(int hour, int minute, boolean active, boolean repeat, boolean camera) {
        this.hour = hour;
        this.minute = minute;
        this.active = active;
        this.repeat = repeat;
        this.camera = camera;
    }

    public int getId() {
        return this.id;
    }

    public int getHour() {
        return this.hour;
    }

    public int getMinute() {
        return this.minute;
    }

    public boolean isActive() {
        return this.active;
    }

    public boolean isRepeat() {
        return this.repeat;
    }

    public boolean isCamera() {
        return this.camera;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    public void setCamera(boolean camera) {
        this.camera = camera;
    }
}
