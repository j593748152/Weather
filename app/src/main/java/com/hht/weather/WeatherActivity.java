package com.hht.weather;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import com.hht.weather.adapter.TimeWeatherAdapter;
import com.hht.weather.adapter.WeekWeatherAdapter;
import com.hht.weather.data.CityDao;

import java.util.ArrayList;

public class WeatherActivity extends Activity implements View.OnClickListener{
    private final static String TAG = "WeatherActivity";

    private Context mContext = null;

    private ImageButton mWeatherSetting = null;
    private RecyclerView mWeekWeatherRecyclerView = null;
    private WeekWeatherAdapter mWeekWeatherAdapter = null;
    private RecyclerView mTimeWeatherRecyclerView = null;
    private TimeWeatherAdapter mTimeWeatherAdapter = null;
    private CityDao mCityDao = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_weather_activity);
        mContext = this;

        mWeekWeatherRecyclerView = findViewById(R.id.recyclerView_week_weather);
        mWeekWeatherAdapter = new WeekWeatherAdapter(this, new ArrayList());
        mWeekWeatherRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mWeekWeatherRecyclerView.setAdapter(mWeekWeatherAdapter);

        mTimeWeatherRecyclerView = findViewById(R.id.recyclerView_time_weather);
        mTimeWeatherAdapter = new TimeWeatherAdapter(this, new ArrayList());
        mTimeWeatherRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mTimeWeatherRecyclerView.setAdapter(mTimeWeatherAdapter);

        mWeatherSetting = findViewById(R.id.imageButton_setting);
        mWeatherSetting.setOnClickListener(this);


/*      not work
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
        });*/
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        switch (viewId) {
            case R.id.imageButton_setting:
                Intent intent = new Intent();
                intent.setClass(mContext, WeatherSettingActivity.class);
                startActivity(intent);
        }

    }
}
