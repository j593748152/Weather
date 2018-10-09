package com.hht.weather.data;

import java.sql.Date;

public class WeekWeather {
    private int _id;
    private String city_code;
    private  int cond_code;
    private String cond_txt;
    private int temp_max;
    private int temp_min;
    private long date;

    public WeekWeather() {
    }

    public int get_id() {
        return _id;
    }

    public String getCity_code() {
        return city_code;
    }

    public void setCity_code(String city_code) {
        this.city_code = city_code;
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

    public int getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(int temp_max) {
        this.temp_max = temp_max;
    }

    public int getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(int temp_min) {
        this.temp_min = temp_min;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
