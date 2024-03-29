package com.example.weatherapp;

public class WeatherCurrentData {

    private String icon,time, pod;
    private double speed, temperature;


    public WeatherCurrentData(double temperature, String icon, double speed, String time, String pod) {
        this.icon = icon;
        this.time = time;
        this.pod = pod;
        this.speed = speed;
        this.temperature = temperature;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPod() {
        return pod;
    }

    public void setPod(String pod) {
        this.pod = pod;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
}
