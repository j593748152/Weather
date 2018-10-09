package com.hht.weather.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.hht.weather.data.DataDao;
import com.hht.weather.data.TimeWeather;
import com.hht.weather.data.Weather;
import com.hht.weather.data.WeekWeather;
import com.hht.weather.interface_.GetWeatherInfoInterface;
import com.hht.weather.utils.HttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class WeatherService extends Service {
    private static final String TAG = "WeatherService";

    private DataDao mDataDao = null;
    private ArrayList<String> mCitySelectedList = new ArrayList<String>();

    @Override
    public void onCreate() {
        super.onCreate();

        getWeatherInfo(HttpUtil.getLocationCity());

        mDataDao = new DataDao(this);
        mCitySelectedList = mDataDao.getAllSelectedCity();
        for(int i = 0; i< mCitySelectedList.size(); i++){
            String city = mCitySelectedList.get(i);
            Log.i(TAG, "city = " + city);
            getWeatherInfo(city);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent != null){
            String city = intent.getStringExtra("city");
            getWeatherInfo(city);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return new GetWatherInfo();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private class GetWatherInfo extends Binder implements GetWeatherInfoInterface {

        @Override
        public boolean getWeatherInfo_(String city) {
            getWeatherInfo(city);
            return false;
        }

    }

    private void getWeatherInfo(final String city){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String weatherUrl = HttpUtil.HE_WEATHER_URL + HttpUtil.HE_WEATHER_LOCATION + city;
                String airQualityUrl = HttpUtil.HE_AIR_QUALITY_URL + HttpUtil.HE_WEATHER_LOCATION + city;
                String tempRandUrl = HttpUtil.HE_TEMP_RAND_URL + HttpUtil.HE_WEATHER_LOCATION + city;

                String weatherResult = HttpUtil.getWebContent(weatherUrl);
                String airQualityResult = HttpUtil.getWebContent(airQualityUrl);
                String tempRandResult = HttpUtil.getWebContent(tempRandUrl);

                JSONObject weatherJSON = null;
                JSONObject airQualityJSON = null;
                JSONObject tempRandJSON = null;
                try {
                    weatherJSON = new JSONObject(weatherResult);
                    airQualityJSON = new JSONObject(airQualityResult);
                    tempRandJSON = new JSONObject(tempRandResult);

                    saveWeatherData(weatherJSON, airQualityJSON, tempRandJSON);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private boolean saveWeatherData(JSONObject weatherJSON, JSONObject airQualityJSON, JSONObject tempRandJSON) throws JSONException{
        Weather cityWeather = new Weather();
        JSONArray HeWeather6 = weatherJSON.getJSONArray("HeWeather6");
        Log.e(TAG, "1 " + HeWeather6.toString());
        JSONObject array = HeWeather6.getJSONObject(0);
        Log.e(TAG, "2 " + array.toString());
        JSONObject basic = array.getJSONObject("basic");
        Log.e(TAG, "3" + basic.toString());
        cityWeather.setCity_code( basic.getString("cid"));

        JSONObject update =  array.getJSONObject("update");
        String updateTime =  update.getString("loc");
        cityWeather.setUpdate_time(HttpUtil.parseHeTime(updateTime));
        JSONObject now =  array.getJSONObject("now");
        cityWeather.setCond_txt(now.getString("cond_txt"));
        cityWeather.setCond_code(now.getInt("cond_code"));
        cityWeather.setTemperature(now.getInt("tmp"));

        JSONArray HeWeather = airQualityJSON.getJSONArray("HeWeather6");
        Log.e(TAG, "4" + HeWeather.toString());
        JSONObject index =  HeWeather.getJSONObject(0);
        Log.e(TAG, "5" + index.toString());
        JSONObject air_now_city = index.getJSONObject("air_now_city");
        Log.e(TAG, "6" + air_now_city.toString());
        cityWeather.setAir_quality(air_now_city.getString("qlty"));

        //TIP: https://free-api.heweather.com/s6/weather?key=4051ea2ba37948af8b6995fbc3e9cb01&location=shenzhen     get all data
        JSONArray hourly = array.getJSONArray("hourly");
        ArrayList<TimeWeather> timeWeathers = new ArrayList<>();
        int temp_min = hourly.getJSONObject(0).getInt("tmp");
        int temp_max = hourly.getJSONObject(0).getInt("tmp");
        for (int i = 0; i < hourly.length(); i++){
            JSONObject hourlyWeather =  hourly.getJSONObject(i);
            TimeWeather timeWeather = new TimeWeather();
            timeWeather.setCity_code(basic.getString("cid"));
            timeWeather.setCond_code(hourlyWeather.getInt("cond_code"));
            timeWeather.setCond_txt(hourlyWeather.getString("cond_txt"));
            int temp = hourlyWeather.getInt("tmp");
            timeWeather.setTemperature(temp);

            //get weather temperature range
            if(temp > temp_max){
                temp_max = temp;
            }else if (temp < temp_min){
                temp_min = temp;
            }

            String timeStr = hourlyWeather.getString("time");
            Long time = HttpUtil.parseHeTime(timeStr);
            if (time > 0) {
                timeWeather.setTime(time);
            } else {
                Log.e(TAG, "time parse error");
            }
            timeWeathers.add(timeWeather);
        }
        cityWeather.setTemp_min(temp_min);
        cityWeather.setTemp_max(temp_max);
        mDataDao.updateWeather(cityWeather);
        mDataDao.insertTimeWeather(timeWeathers);

        // week weather data
        JSONArray daily_forecast = array.getJSONArray("daily_forecast");
        ArrayList<WeekWeather> weekWeathers = new ArrayList<>();
        for (int i = 0; i < daily_forecast.length(); i++){
            JSONObject dailyWeather =  daily_forecast.getJSONObject(i);
            WeekWeather weekWeather = new WeekWeather();
            weekWeather.setCity_code(basic.getString("cid"));
            weekWeather.setCond_code(dailyWeather.getInt("cond_code_d"));
            weekWeather.setCond_txt(dailyWeather.getString("cond_txt_d"));
            weekWeather.setTemp_max(dailyWeather.getInt("tmp_max"));
            weekWeather.setTemp_min(dailyWeather.getInt("tmp_min"));

            String timeStr = dailyWeather.getString("date");
            Long date = HttpUtil.parseHeDate(timeStr);
            if (date > 0 ) {
                weekWeather.setDate(date);
            } else {
                Log.e(TAG, "date parse error");
            }
            weekWeathers.add(weekWeather);
        }
        mDataDao.insertWeekWeather(weekWeathers);
        return true;
    }
}
