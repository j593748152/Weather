package com.hht.weather.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.hht.weather.utils.HttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class WeatherService extends Service {
    private static final String TAG = "WeatherService";

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new Runnable() {
            @Override
            public void run() {

                String weatherUrl = HttpUtil.HE_WEATHER_URL + HttpUtil.HE_WEATHER_LOCATION + "北京";
                String hourlyWeatherUrl = HttpUtil.HE_WEATHER_HOURLY_URL + HttpUtil.HE_WEATHER_LOCATION + "北京";
                String weekWeatherUrl = HttpUtil.HE_WEATHER_WEEK_URL + HttpUtil.HE_WEATHER_LOCATION + "北京";


                String weatherResult = HttpUtil.getWebContent(weatherUrl);
                String hourlyWweatherResult = HttpUtil.getWebContent(hourlyWeatherUrl);
                String weekWeatherResult = HttpUtil.getWebContent(weekWeatherUrl);

                JSONObject weatherJSON = null;
                JSONObject hourlyWeatherJSON = null;
                JSONObject weekWeatherJSON = null;
                try {
                    weatherJSON = new JSONObject(weatherResult);
                    hourlyWeatherJSON = new JSONObject(hourlyWweatherResult);
                    weekWeatherJSON = new JSONObject(weekWeatherResult);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private boolean saveWeatherData(JSONObject weatherJSON) throws JSONException {
        JSONObject basic = (JSONObject) weatherJSON.get("");

        return true;
    }

    private boolean  saveHourlyWeatherData(){
        return true;
    }

    private boolean saveWeekWeatherData(){
        return true;
    }

}
