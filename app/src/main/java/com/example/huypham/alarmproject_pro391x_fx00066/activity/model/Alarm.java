package com.example.huypham.alarmproject_pro391x_fx00066.activity.model;

import java.io.Serializable;

public class Alarm implements Serializable {
    private int hour;
    private int minutes;
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }
}