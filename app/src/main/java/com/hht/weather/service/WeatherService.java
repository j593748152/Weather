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
    public static final String CITY_NAME = "cityName";

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
            String city = intent.getStringExtra("cityName");
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

    /*
      get city all weather data
     */
    private void getWeatherInfo(final String city){
        if (city == null) {
            Log.e(TAG, "city name is null");
            return;
        }
        if (!HttpUtil.isNetworkAvalible(this)) {
            return;
        }


        new Thread(new Runnable() {
            @Override
            public void run() {
                //TIP: https://free-api.heweather.com/s6/weather?key=4051ea2ba37948af8b6995fbc3e9cb01&location=shenzhen     get all data
                String weatherUrl = HttpUtil.HE_WEATHER_URL + HttpUtil.HE_WEATHER_LOCATION + city;
                String airQualityUrl = HttpUtil.HE_AIR_QUALITY_URL + HttpUtil.HE_WEATHER_LOCATION + city;

                String weatherResult = HttpUtil.getWebContent(weatherUrl);
                String airQualityResult = HttpUtil.getWebContent(airQualityUrl);

                JSONObject weatherJSON = null;
                JSONObject airQualityJSON = null;
                try {
                    weatherJSON = new JSONObject(weatherResult);
                    airQualityJSON = new JSONObject(airQualityResult);

                    saveWeatherData(weatherJSON, airQualityJSON);

                } catch (JSONException e) {
                    //TODO server weather data exception
                    Log.e(TAG, "server weather data exception");
                    Log.e(TAG, "weatherResult : " + weatherResult);
                    Log.e(TAG, "airQualityResult : " + airQualityResult);
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /*
    save weather data to data base
     */
    private boolean saveWeatherData(JSONObject weatherJSON, JSONObject airQualityJSON) throws JSONException{
        Weather cityWeather = new Weather();
        JSONArray HeWeather6 = weatherJSON.getJSONArray("HeWeather6");
        JSONObject array = HeWeather6.getJSONObject(0);
        JSONObject basic = array.getJSONObject("basic");
        cityWeather.setCity_code( basic.getString("cid"));

        JSONObject update =  array.getJSONObject("update");
        String updateTime =  update.getString("loc");
        cityWeather.setUpdate_time(HttpUtil.parseHeTime(updateTime));
        JSONObject now =  array.getJSONObject("now");
        cityWeather.setCond_txt(now.getString("cond_txt"));
        cityWeather.setCond_code(now.getInt("cond_code"));
        cityWeather.setTemperature(now.getInt("tmp"));

        JSONArray HeWeather = airQualityJSON.getJSONArray("HeWeather6");
        JSONObject index =  HeWeather.getJSONObject(0);
        JSONObject air_now_city = index.getJSONObject("air_now_city");
        cityWeather.setAir_quality(air_now_city.getString("qlty"));

        JSONArray hourly = array.getJSONArray("hourly");
        ArrayList<TimeWeather> timeWeathers = new ArrayList<>();
        for (int i = 0; i < hourly.length(); i++){
            JSONObject hourlyWeather =  hourly.getJSONObject(i);
            TimeWeather timeWeather = new TimeWeather();
            timeWeather.setCity_code(basic.getString("cid"));
            timeWeather.setCond_code(hourlyWeather.getInt("cond_code"));
            timeWeather.setCond_txt(hourlyWeather.getString("cond_txt"));
            int temp = hourlyWeather.getInt("tmp");
            timeWeather.setTemperature(temp);

            String timeStr = hourlyWeather.getString("time");
            Long time = HttpUtil.parseHeTime(timeStr);
            if (time > 0) {
                timeWeather.setTime(time);
            } else {
                Log.e(TAG, "time parse error");
            }
            timeWeathers.add(timeWeather);
        }


        // week weather data
        JSONArray daily_forecast = array.getJSONArray("daily_forecast");
        ArrayList<WeekWeather> weekWeathers = new ArrayList<>();
        for (int i = 0; i < daily_forecast.length(); i++){

            JSONObject dailyWeather =  daily_forecast.getJSONObject(i);
            if(i == 0){
                //for today weather temprature data
                cityWeather.setTemp_min(dailyWeather.getInt("tmp_min"));
                cityWeather.setTemp_max(dailyWeather.getInt("tmp_max"));
                continue;
            }

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

        mDataDao.updateWeather(cityWeather);
        mDataDao.insertTimeWeather(timeWeathers);
        mDataDao.insertWeekWeather(weekWeathers);
        return true;
    }
}
