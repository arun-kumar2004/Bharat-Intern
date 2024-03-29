package com.example.weatherapp;

import org.json.JSONException;
import org.json.JSONObject;

public class HourlyWeather {
    private int animation;
    private String time;
    public HourlyWeather(int animation, String time) {
        this.animation = animation;
        this.time = time;
    }

    public void setAnimation(int animation) {
        this.animation = animation;
    }

    public int getAnimation() {
        return animation;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
