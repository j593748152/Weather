package com.hht.weather;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.hht.weather.adapter.PagePointAdapter;
import com.hht.weather.adapter.TimeWeatherAdapter;
import com.hht.weather.adapter.WeatherViewPagerAdapter;
import com.hht.weather.adapter.WeekWeatherAdapter;
import com.hht.weather.data.DataDao;
import com.hht.weather.data.Weather;
import com.hht.weather.service.WeatherService;
import com.hht.weather.utils.HttpUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class WeatherActivity extends Activity{
    private final static String TAG = "WeatherActivity";

    private Context mContext = null;

    private RecyclerView mRecyclerViewPagePoint = null;
    private PagePointAdapter mPagePointAdapter = null;

    private ViewPager mViewPagerWeather = null;
    private WeatherViewPagerAdapter mWeatherViewPagerAdapter = null;
    private DataDao mDataDao = null;
    private String mLocation = null;
    private String intentCity = null;
    private ArrayList<String> mSelectedcityList = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_weather_activity);
        mContext = this;
        mDataDao = new DataDao(this);
        mLocation = HttpUtil.getLocationCity();

        initView();
        initData();

        Intent startWeatherService = new Intent(this, WeatherService.class);
        startWeatherService.putExtra(WeatherService.CITY_NAME, mLocation);
        startService(startWeatherService);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        intentCity = intent.getStringExtra("city");
        mSelectedcityList = mDataDao.getAllSelectedCity();
        mSelectedcityList.add(0, mLocation);
        mWeatherViewPagerAdapter.setDatas(mSelectedcityList);
        mPagePointAdapter.setSize(mSelectedcityList.size());
        if(mSelectedcityList.contains(intentCity)){
            int index = mSelectedcityList.indexOf(intentCity);
            mViewPagerWeather.setCurrentItem(index);
            mPagePointAdapter.setIndex(index);
        }
    }

    private void initView(){
        mRecyclerViewPagePoint = findViewById(R.id.recyclerView_page_point);
        mViewPagerWeather = findViewById(R.id.viewPager_weather);
    }

    private void initData(){
        mSelectedcityList = mDataDao.getAllSelectedCity();
        mSelectedcityList.add(0, mLocation);
        mWeatherViewPagerAdapter = new WeatherViewPagerAdapter(mContext, mSelectedcityList);
        mViewPagerWeather.setAdapter(mWeatherViewPagerAdapter);

        mPagePointAdapter = new PagePointAdapter(mContext, mSelectedcityList.size());
        mRecyclerViewPagePoint.setAdapter(mPagePointAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void refreshView(){
        //TODO refresh main activity
    }
}
