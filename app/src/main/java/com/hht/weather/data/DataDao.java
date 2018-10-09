package com.hht.weather.data;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.hht.weather.utils.FileParser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class DataDao {
    private static final String TAG = "DataDao";
    public static final String DB_NAME  = "weather.db";
    public static final String TABLE_CITY = "city";
    public static final String TABLE_WEATHER = "weather";
    public static final String TABLE_SELECTED_CITY = "selected_city";
    public static final String TABLE_TIME_WEATHER = "time_weather";
    public static final String TABLE_WEEK_WEATHER = "week_weather";

    private static String cityCsv = "china-city-list.csv";

    private Context mContext = null;
    private SQLiteDatabase db = null;
    private WeatherSQLiteOpenHelper dbHelper = null;
    private SQLiteDatabase.CursorFactory cf = null;

    public DataDao(Context context) {
        mContext = context;
        dbHelper = new WeatherSQLiteOpenHelper(mContext, DB_NAME, null, 1);
        db = dbHelper.getWritableDatabase();

        //init city data
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (qureyAllCity() < 1) {
                    AssetManager assetManager = mContext.getAssets() ;
                    try {
                        InputStream cityCsvInput = assetManager.open(cityCsv) ;
                        ArrayList<City> cityList = FileParser.parseCityCsv(cityCsvInput);
                        for (City city : cityList){
                            insertCity(city);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    public boolean insertCity(City city) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("city_code", city.getCity_code());
        contentValues.put("city_name", city.getCity_name());
        contentValues.put("province_name", city.getProvince_name());
        contentValues.put("city_name_pinyin", city.getCity_name_pinyin());
        contentValues.put("city_name_ab", city.getCity_name_ab());
        long l = db.insert(TABLE_CITY, null, contentValues);
        Log.d(TAG , "insert city " + l);
        return true;
    }

    public ArrayList<City>  qureyCityByName(String city_name){
        if(city_name == null || city_name.equals("")){
            return null;
        }
        ArrayList<City> cityArrayList = new ArrayList<>();
        Cursor cursor = db.query(TABLE_CITY, new String[]{"city_name,province_name,city_code"},"city_name like ?", new String[]{"%"+city_name+"%"},null,null,null);
        if(cursor.getCount() < 1){
            return null;
        }
        for (cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()) {
            City city = new City();
            city.setCity_name(cursor.getString(0));
            city.setProvince_name(cursor.getString(1));
            city.setCity_code(cursor.getString(2));
            cityArrayList.add(city);
        }

        return cityArrayList;
    }

    public String qureyCityCodeByName(String cityName){
        Cursor cursor = db.query(TABLE_CITY, new String[]{"city_code"},"city_name=?", new String[]{cityName},null,null,null);
        cursor.moveToFirst();
        String cityCode = cursor.getString(0);
        return cityCode;
    }

    public int qureyAllCity(){
        Cursor cursor = db.query(TABLE_CITY, new String[]{"_id"},null, null,null,null,null);
        Log.d(TAG, "city count " + cursor.getCount());
        return cursor.getCount();
    }


    public boolean insertSelectedCity(String cityName) {
        if(hasSelectedCity(cityName)){
            Log.i(TAG, cityName + " has been added");
            return false;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("city_name", cityName);
        long l = db.insert(TABLE_SELECTED_CITY, null, contentValues);
        Log.d(TAG , "insert selected city " + l);
        return true;
    }

    public boolean deleteSelectedCity(String cityName) {
        int i = db.delete(TABLE_SELECTED_CITY,"city_name=?",new String[]{cityName});
        if(i>0){
            return true;
        }
        return false;
    }

    public boolean hasSelectedCity(String cityName) {
        Cursor cursor = db.query(TABLE_SELECTED_CITY, new String[]{"_id"},"city_name=?", new String[]{cityName},null,null,null);
        if(cursor.getCount() > 0){
            return true;
        }
        return false;
    }

    public ArrayList<String> getAllSelectedCity(){
        ArrayList<String> selectedCityList = new ArrayList<String>();
        Cursor cursor = db.query(TABLE_SELECTED_CITY, new String[]{"city_name"},null, null,null,null,null);
        for (cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()) {
            selectedCityList.add(cursor.getString(0));
        }
        return selectedCityList;
    }

    public void insertWeather(Weather weather){
        ContentValues contentValues = new ContentValues();
        contentValues.put("city_code", weather.getCity_code());
        contentValues.put("cond_code", weather.getCond_code());
        contentValues.put("cond_txt", weather.getCond_txt());
        contentValues.put("temperature", weather.getTemperature());
        contentValues.put("temp_max", weather.getTemp_max());
        contentValues.put("temp_min", weather.getTemp_min());
        contentValues.put("air_quality", weather.getAir_quality());
        contentValues.put("update_time", weather.getUpdate_time());
        long l = db.insert(TABLE_WEATHER, null, contentValues);
        Log.d(TAG , "insert weather " + l);
    }

    public void updateWeather(Weather weather){
        String cityCode = weather.getCity_code();
        int id = qureyWeatherID(cityCode);
        if (id < 0) {
            insertWeather(weather);
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("city_code", weather.getCity_code());
        contentValues.put("cond_code", weather.getCond_code());
        contentValues.put("cond_txt", weather.getCond_txt());
        contentValues.put("temperature", weather.getTemperature());
        contentValues.put("temp_max", weather.getTemp_max());
        contentValues.put("temp_min", weather.getTemp_min());
        contentValues.put("air_quality", weather.getAir_quality());
        contentValues.put("update_time", weather.getUpdate_time());
        db.update(TABLE_WEATHER,contentValues, "city_code=?", new String[]{cityCode});
    }

    public int qureyWeatherID(String cityCode){
        Cursor cursor = db.query(TABLE_WEATHER, new String[]{"_id"},"city_code=?", new String[]{cityCode},null,null,null);
        if(cursor.getCount() == 1){
            cursor.moveToFirst();
            return cursor.getInt(0);
        }else {
            Log.e(TAG, cityCode + " has " + cursor.getCount());
            return -1;
        }
    }

    public Weather qureyWeatherByCityName(String cityName){
        String cityCode = qureyCityCodeByName(cityName);
        Weather weather = new Weather();
        Cursor cursor = db.query(TABLE_WEATHER, new String[]{"_id, city_code, cond_code,cond_txt,temperature,temp_max,temp_min,air_quality,update_time"},"city_Code=?", new String[]{cityCode},null,null,null);
        if(cursor.getCount() == 1){
            cursor.moveToFirst();
            weather.setCity_code(cursor.getString(1));
            weather.setCond_code(cursor.getInt(2));
            weather.setCond_txt(cursor.getString(3));
            weather.setTemperature(cursor.getInt(4));
            weather.setTemp_max(cursor.getInt(5));
            weather.setTemp_min(cursor.getInt(6));
            weather.setAir_quality(cursor.getString(7));
            weather.setUpdate_time(cursor.getLong(8));
        }else {
            Log.e(TAG, cityCode + " has weather " + cursor.getCount());
            return null;
        }

        return weather;
    }

    public void insertTimeWeather(ArrayList<TimeWeather> timeWeathers){
        deleteTimeWeather(timeWeathers.get(0).getCity_code());

        for (TimeWeather timeWeather : timeWeathers){
            ContentValues contentValues = new ContentValues();
            contentValues.put("city_code", timeWeather.getCity_code());
            contentValues.put("cond_code", timeWeather.getCond_code());
            contentValues.put("cond_txt", timeWeather.getCond_txt());
            contentValues.put("temperature", timeWeather.getTemperature());
            contentValues.put("time", timeWeather.getTime());
            db.insert(TABLE_TIME_WEATHER,null, contentValues);
        }
    }

    public void deleteTimeWeather(String cityCode){
        db.delete(TABLE_TIME_WEATHER, "city_code=?" , new String[]{cityCode});
    }

    public ArrayList<TimeWeather> qureyWeatherByCityCode(String cityCode){
        ArrayList timeWeatherList = new ArrayList();
        Cursor cursor = db.query(TABLE_TIME_WEATHER, new String[]{"_id, city_code, cond_code,cond_txt,temperature,time"},"city_Code=?", new String[]{cityCode},null,null,"time");
        for (cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()) {
            TimeWeather timeWeather = new TimeWeather();
            timeWeather.setCity_code(cursor.getString(1));
            timeWeather.setCond_code(cursor.getInt(2));
            timeWeather.setCond_txt(cursor.getString(3));
            timeWeather.setTemperature(cursor.getInt(4));
            timeWeather.setTime(cursor.getLong(5));
            timeWeatherList.add(timeWeather);
        }
        return timeWeatherList;
    }



    public void insertWeekWeather(ArrayList<WeekWeather> weekWeathers){
        deleteWeekWeather(weekWeathers.get(0).getCity_code());

        for(WeekWeather weekWeather : weekWeathers){
            ContentValues contentValues = new ContentValues();
            contentValues.put("city_code", weekWeather.getCity_code());
            contentValues.put("cond_code", weekWeather.getCond_code());
            contentValues.put("cond_txt", weekWeather.getCond_txt());
            contentValues.put("temp_max", weekWeather.getTemp_max());
            contentValues.put("temp_min", weekWeather.getTemp_min());
            contentValues.put("date", weekWeather.getDate());
            db.insert(TABLE_WEEK_WEATHER,null, contentValues);
        }
    }
    public void deleteWeekWeather(String cityCode){
        db.delete(TABLE_WEEK_WEATHER, "city_code=?" , new String[]{cityCode});
    }

    public ArrayList<WeekWeather> qureyWeekWeatherByCityCode(String cityCode){
        ArrayList weekWeatherList = new ArrayList();
        Cursor cursor = db.query(TABLE_WEEK_WEATHER, new String[]{"_id, city_code, cond_code,cond_txt,temp_max,temp_min,date"},"city_Code=?", new String[]{cityCode},null,null,"date");
        for (cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()) {
            WeekWeather weekWeather = new WeekWeather();
            weekWeather.setCity_code(cursor.getString(1));
            weekWeather.setCond_code(cursor.getInt(2));
            weekWeather.setCond_txt(cursor.getString(3));
            weekWeather.setTemp_max(cursor.getInt(4));
            weekWeather.setTemp_min(cursor.getInt(5));
            weekWeather.setDate(cursor.getLong(6));
            weekWeatherList.add(weekWeather);
        }
        return weekWeatherList;
    }
}
