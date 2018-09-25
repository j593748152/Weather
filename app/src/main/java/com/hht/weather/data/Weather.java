package com.hht.weather.data;

import java.sql.Time;

public class Weather {
    private int _id;
    private String city_code;
    private int cond_code;
    private String cond_txt;
    private String temperature;
    private String temperature_rand;
    private String air_quality;
    private long update_time;

    public Weather(){

    }

    public int get_id() {
        return _id;
    }

    public void setCity_code(String city_code) {
        this.city_code = city_code;
    }

    public void setAir_quality(String air_quality) {
        this.air_quality = air_quality;
    }

    public void setCond_code(int cond_code) {
        this.cond_code = cond_code;
    }

    public void setCond_txt(String cond_txt) {
        this.cond_txt = cond_txt;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public void setTemperature_rand(String temperature_rand) {
        this.temperature_rand = temperature_rand;
    }

    public void setUpdate_time(long update_time) {
        this.update_time = update_time;
    }

    public String getCity_code() {
        return city_code;
    }

    public int getCond_code() {
        return cond_code;
    }

    public String getAir_quality() {
        return air_quality;
    }

    public String getCond_txt() {
        return cond_txt;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getTemperature_rand() {
        return temperature_rand;
    }

    public long getUpdate_time() {
        return update_time;
    }
}
