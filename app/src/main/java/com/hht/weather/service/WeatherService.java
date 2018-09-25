package com.hht.weather.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.hht.weather.interface_.GetWeatherInfoInterface;
import com.hht.weather.utils.HttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class WeatherService extends Service {
    private static final String TAG = "WeatherService";

    @Override
    public void onCreate() {
        super.onCreate();


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
                String hourlyWeatherUrl = HttpUtil.HE_WEATHER_HOURLY_URL + HttpUtil.HE_WEATHER_LOCATION + city;
                String weekWeatherUrl = HttpUtil.HE_WEATHER_WEEK_URL + HttpUtil.HE_WEATHER_LOCATION + city;
                String airQualityUrl = HttpUtil.HE_AIR_QUALITY_URL + HttpUtil.HE_WEATHER_LOCATION + city;

                String weatherResult = HttpUtil.getWebContent(weatherUrl);
                String hourlyWweatherResult = HttpUtil.getWebContent(hourlyWeatherUrl);
                String weekWeatherResult = HttpUtil.getWebContent(weekWeatherUrl);
                String airQualityResult = HttpUtil.getWebContent(airQualityUrl);

                JSONObject weatherJSON = null;
                JSONObject hourlyWeatherJSON = null;
                JSONObject weekWeatherJSON = null;
                JSONObject airQualityJSON = null;
                try {
                    weatherJSON = new JSONObject(weatherResult);
                    hourlyWeatherJSON = new JSONObject(hourlyWweatherResult);
                    weekWeatherJSON = new JSONObject(weekWeatherResult);
                    airQualityJSON = new JSONObject(airQualityResult);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
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
