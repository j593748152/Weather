package com.hht.weather;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.hht.weather.adapter.WeekWeatherAdapter;
import com.hht.weather.utils.HttpUtil;

import java.util.ArrayList;

public class WeatherActivity extends Activity {
    private final static String TAG = "WeatherActivity";

    private RecyclerView mRecyclerView = null;
    private WeekWeatherAdapter mWeekWeatherAdapter = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_weather_activity);

        mRecyclerView = findViewById(R.id.recyclerView_week_weather);
        mWeekWeatherAdapter = new WeekWeatherAdapter(this, new ArrayList());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mWeekWeatherAdapter);

        //Log.d(TAG, HttpUtil.getWebContent(HttpUtil.s));
    }
}
