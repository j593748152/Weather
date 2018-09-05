package com.hht.weather;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.hht.weather.adapter.WeekWeatherAdapter;
import com.hht.weather.data.CityDao;
import com.hht.weather.utils.HttpUtil;

import java.util.ArrayList;

public class WeatherActivity extends Activity {
    private final static String TAG = "WeatherActivity";

    private Context mContext = null;
    private RecyclerView mWeekWeatherRecyclerView = null;
    private WeekWeatherAdapter mWeekWeatherAdapter = null;
    private CityDao mCityDao = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_weather_activity);
        mContext = this;
        mWeekWeatherRecyclerView = findViewById(R.id.recyclerView_week_weather);
        mWeekWeatherAdapter = new WeekWeatherAdapter(this, new ArrayList());
        mWeekWeatherRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mWeekWeatherRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mWeekWeatherRecyclerView.setAdapter(mWeekWeatherAdapter);


        mCityDao = new CityDao(this);
        mCityDao.insertCity(300100,"beijing", "beijing");
        mCityDao.qureyCityCode("beijing");

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (HttpUtil.isNetworkAvalible(mContext)){
                    Log.d(TAG, HttpUtil.getWebContent(HttpUtil.s.replace("xxxxxxxxx",  "" + mCityDao.qureyCityCode("beijing"))));
                }
            }
        });
    }
}
