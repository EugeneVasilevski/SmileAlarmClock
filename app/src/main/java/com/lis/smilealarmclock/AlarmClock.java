package com.lis.smilealarmclock;

public final class AlarmClock {

    private int id;
    private int hour;
    private int minute;
    private boolean active;
    private boolean camera;
    private boolean[] activeDays;

    public AlarmClock(int id, int hour, int minute, boolean active,
                      boolean camera, boolean[] activeDays) {
        this.id = id;
        this.hour = hour;
        this.minute = minute;
        this.active = active;
        this.activeDays = activeDays;
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

    public final AlarmClock setCamera(boolean camera) {
        this.camera = camera;
        return this;
    }

    public final AlarmClock setActiveDays(boolean[] activeDays) {
        this.activeDays = activeDays;
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

    public final boolean[] getActiveDays() {
        return this.activeDays;
    }

    public final boolean isActive() {
        return this.active;
    }

    public final boolean isCamera() {
        return this.camera;
    }

    public final boolean isRepeat() {
        return activeDays != null;
    }
}
