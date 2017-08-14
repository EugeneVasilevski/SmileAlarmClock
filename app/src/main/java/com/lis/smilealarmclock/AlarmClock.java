package com.lis.smilealarmclock;

public final class AlarmClock {

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

    public final AlarmClock setId(int id) {
        this.id = id;
        return this;
    }

    public final AlarmClock setHour(int hour) {
        this.hour = hour;
        return this;
    }

    public final AlarmClock setMinute(int minute) {
        this.minute = minute;
        return this;
    }

    public final AlarmClock setActive(boolean active) {
        this.active = active;
        return this;
    }

    public final AlarmClock setRepeat(boolean repeat) {
        this.repeat = repeat;
        return this;
    }

    public final AlarmClock setCamera(boolean camera) {
        this.camera = camera;
        return this;
    }

    public final int getId() {
        return this.id;
    }

    public final int getHour() {
        return this.hour;
    }

    public final int getMinute() {
        return this.minute;
    }

    public final boolean isActive() {
        return this.active;
    }

    public final boolean isRepeat() {
        return this.repeat;
    }

    public final boolean isCamera() {
        return this.camera;
    }
}
