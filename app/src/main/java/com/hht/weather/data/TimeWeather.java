package com.hht.weather.data;

import java.sql.Time;

public class TimeWeather {
    private int _id;
    private String city_code;
    private long time;
    private int cond_code;
    private String cond_txt;
    private String time_temperature;

    public void setCond_txt(String cond_txt) {
        this.cond_txt = cond_txt;
    }

    public void setCond_code(int cond_code) {
        this.cond_code = cond_code;
    }

    public void setCity_code(String city_code) {
        this.city_code = city_code;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setTime_temperature(String time_temperature) {
        this.time_temperature = time_temperature;
    }

    public int get_id() {
        return _id;
    }

    public String getCond_txt() {
        return cond_txt;
    }

    public int getCond_code() {
        return cond_code;
    }

    public String getCity_code() {
        return city_code;
    }

    public long getTime() {
        return time;
    }

    public String getTime_temperature() {
        return time_temperature;
    }
}
