package com.hht.weather.data;

public class City {
    private int _id;
    private String city_code;
    private String city_name;
    private String province_name;
    private String city_name_pinyin;
    //city name abbreviation, example: bj
    private String city_name_ab;

    public City(){

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

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getProvince_name() {
        return province_name;
    }

    public void setProvince_name(String province_name) {
        this.province_name = province_name;
    }

    public String getCity_name_ab() {
        return city_name_ab;
    }

    public void setCity_name_ab(String city_name_ab) {
        this.city_name_ab = city_name_ab;
    }

    public String getCity_name_pinyin() {
        return city_name_pinyin;
    }

    public void setCity_name_pinyin(String city_name_pinyin) {
        this.city_name_pinyin = city_name_pinyin;
    }
}
