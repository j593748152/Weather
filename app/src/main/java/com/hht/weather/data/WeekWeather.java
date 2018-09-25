package com.hht.weather.data;

import java.sql.Date;

public class WeekWeather {
    private int _id;
    private String city_code;
    private  int cond_code;
    private String cond_txt;
    private String temperature_rand;
    private long date;

    public void setCity_code(String city_code) {
        this.city_code = city_code;
    }

    public void setCond_code(int cond_code) {
        this.cond_code = cond_code;
    }

    public void setCond_txt(String cond_txt) {
        this.cond_txt = cond_txt;
    }

    public void setTemperature_rand(String temperature_rand) {
        this.temperature_rand = temperature_rand;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int get_id() {
        return _id;
    }

    public String getCity_code() {
        return city_code;
    }

    public int getCond_code() {
        return cond_code;
    }

    public String getCond_txt() {
        return cond_txt;
    }

    public String getTemperature_rand() {
        return temperature_rand;
    }

    public long getDate() {
        return date;
    }
}
