package com.hht.weather.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.hht.weather.data.DataDao;
import com.hht.weather.interface_.GetWeatherInfoInterface;
import com.hht.weather.utils.HttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class WeatherService extends Service {
    private static final String TAG = "WeatherService";

    private DataDao mDataDao = null;
    private ArrayList<String> mCityList = new ArrayList<String>();

    @Override
    public void onCreate() {
        super.onCreate();
        mDataDao = new DataDao(this);
        mCityList = mDataDao.getAllSelectedCity();
        for(int i = 0; i<mCityList.size(); i++){
            String city = mCityList.get(i);
            Log.i(TAG, "city = " + city);
            getWeatherInfo(city);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String city = intent.getStringExtra("city");
        getWeatherInfo(city);
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
                String hourlyWeatherUrl = HttpUtil.HE_WEATHER_HOURLY_URL + HttpUtil.HE_WEATHER_LOCATION + city;
                String weekWeatherUrl = HttpUtil.HE_WEATHER_WEEK_URL + HttpUtil.HE_WEATHER_LOCATION + city;
                String airQualityUrl = HttpUtil.HE_AIR_QUALITY_URL + HttpUtil.HE_WEATHER_LOCATION + city;
                String tempRandUrl = HttpUtil.HE_TEMP_RAND_URL + HttpUtil.HE_WEATHER_LOCATION + city;

                String weatherResult = HttpUtil.getWebContent(weatherUrl);
                String hourlyWweatherResult = HttpUtil.getWebContent(hourlyWeatherUrl);
                String weekWeatherResult = HttpUtil.getWebContent(weekWeatherUrl);
                String airQualityResult = HttpUtil.getWebContent(airQualityUrl);
                String tempRandResult = HttpUtil.getWebContent(tempRandUrl);

                JSONObject weatherJSON = null;
                JSONObject hourlyWeatherJSON = null;
                JSONObject weekWeatherJSON = null;
                JSONObject airQualityJSON = null;
                JSONObject tempRandJSON = null;
                try {
                    weatherJSON = new JSONObject(weatherResult);
                    hourlyWeatherJSON = new JSONObject(hourlyWweatherResult);
                    weekWeatherJSON = new JSONObject(weekWeatherResult);
                    airQualityJSON = new JSONObject(airQualityResult);
                    tempRandJSON = new JSONObject(tempRandResult);

                    saveWeatherData(weatherJSON);
                    saveHourlyWeatherData(hourlyWeatherJSON);
                    saveWeekWeatherData(weekWeatherJSON);
                    saveAirQualityData(airQualityJSON);
                    saveTempRandData(tempRandJSON);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private boolean saveWeatherData(JSONObject weatherJSON) {
        try {
            JSONObject basic = (JSONObject) weatherJSON.get("basic");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return true;
    }

    private boolean  saveHourlyWeatherData(JSONObject hourlyWeatherJSON){
        return true;
    }

    private boolean saveWeekWeatherData(JSONObject weekWeatherJSON){
        return true;
    }

    private boolean saveAirQualityData(JSONObject airQualityJSON){
        return true;
    }
    private boolean saveTempRandData(JSONObject tempRandJSON){
        return true;
    }

}
