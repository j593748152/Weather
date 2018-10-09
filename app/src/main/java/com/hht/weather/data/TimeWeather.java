package com.hht.weather.data;

import java.sql.Time;

public class TimeWeather {
    private int _id;
    private String city_code;
    private long time;
    private int cond_code;
    private String cond_txt;
    private int temperature;

    public int get_id() {
        return _id;
    }

    public String getCity_code() {
        return city_code;
    }

    public void setCity_code(String city_code) {
        this.city_code = city_code;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getCond_code() {
        return cond_code;
    }

    public void setCond_code(int cond_code) {
        this.cond_code = cond_code;
    }

    public String getCond_txt() {
        return cond_txt;
    }

    public void setCond_txt(String cond_txt) {
        this.cond_txt = cond_txt;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }
}
