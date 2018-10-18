package com.hht.weather;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.hht.weather.utils.ImageUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class WeatherActivity extends Activity implements View.OnClickListener{
    private final static String TAG = "WeatherActivity";

    private Context mContext = null;

    private RecyclerView mRecyclerViewPagePoint = null;
    private PagePointAdapter mPagePointAdapter = null;

    private ViewPager mViewPagerWeather = null;
    private WeatherViewPagerAdapter mWeatherViewPagerAdapter = null;

    private ConstraintLayout mConstraintLayoutNoNet = null;
    private Button mButtonBack = null;
    private Button mButtonStartNet = null;

    private DataDao mDataDao = null;
    private String mLocation = null;
    private String intentCity = null;

    //mIndex and mCurrentCity  must match in mSelectedcityList :  mSelectedcityList.get(mIndex) = mCurrentCity
    private ArrayList<String> mSelectedcityList = null;
    private int mIndex = 0;
    private String mCurrentCity = null;

    private boolean isFirstStart = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_weather_activity);
        mContext = this;
        mDataDao = new DataDao(this);
        mLocation = HttpUtil.getLocationCity();
        mCurrentCity = mLocation;

        /* 模糊背景，图片处理耗时长
        WallpaperManager manager = WallpaperManager.getInstance(this);
        Drawable drawable = manager.getDrawable();
        BitmapDrawable bd = (BitmapDrawable) drawable;
        drawable = ImageUtil.BoxBlurFilter(bd.getBitmap());
        this.getWindow().setBackgroundDrawable(drawable);
        */

        initView();
        initData();
        initListener();

        if(HttpUtil.isNetworkAvalible(mContext)){
            Intent startWeatherService = new Intent(this, WeatherService.class);
            startWeatherService.putExtra(WeatherService.CITY_NAME, mLocation);
            startService(startWeatherService);
        }else {
            mRecyclerViewPagePoint.setVisibility(View.GONE);
            mViewPagerWeather.setVisibility(View.GONE);
            mConstraintLayoutNoNet.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.i(TAG, "onNewIntent");
        super.onNewIntent(intent);
        intentCity = intent.getStringExtra("city");
        mSelectedcityList = mDataDao.getAllSelectedCity();
        mSelectedcityList.add(0, mLocation);
        mWeatherViewPagerAdapter.setDatas(mSelectedcityList);
        mPagePointAdapter.setSize(mSelectedcityList.size());
        if(mSelectedcityList.contains(intentCity)){
            mCurrentCity = intentCity;
            mIndex = mSelectedcityList.indexOf(intentCity);
            mViewPagerWeather.setCurrentItem(mIndex);
            mPagePointAdapter.setIndex(mIndex);
        }
    }

    private void initView(){
        mRecyclerViewPagePoint = findViewById(R.id.recyclerView_page_point);
        mViewPagerWeather = findViewById(R.id.viewPager_weather);
        mConstraintLayoutNoNet = findViewById(R.id.constraintLayout_no_net);
        mButtonBack = findViewById(R.id.button_back);
        mButtonStartNet = findViewById(R.id.button_start_net);
    }

    private void initData(){
        mSelectedcityList = mDataDao.getAllSelectedCity();
        mSelectedcityList.add(0, mLocation);
        mWeatherViewPagerAdapter = new WeatherViewPagerAdapter(mContext, mSelectedcityList);
        mViewPagerWeather.setAdapter(mWeatherViewPagerAdapter);

        mPagePointAdapter = new PagePointAdapter(mContext, mSelectedcityList.size());
        mRecyclerViewPagePoint.setAdapter(mPagePointAdapter);
    }

    private void initListener(){
        mViewPagerWeather.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mIndex = position;
                mCurrentCity = mSelectedcityList.get(position);
                mPagePointAdapter.setIndex(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mButtonBack.setOnClickListener(this);
        mButtonStartNet.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "onResume");
        super.onResume();
        if (isFirstStart){
            isFirstStart = false;
        } else {
            refreshView();
        }

    }

    private void refreshView(){
        //TODO refresh main activity
        //selected city change
        //temperature change
        //location change
        //net status change
        mSelectedcityList = mDataDao.getAllSelectedCity();
        mSelectedcityList.add(0, mLocation);
        if (mSelectedcityList.contains(mCurrentCity)){
            mIndex = mSelectedcityList.indexOf(mCurrentCity);
        }else {
            mIndex = 0;
            mCurrentCity = mLocation;
        }
        mViewPagerWeather.removeAllViews();
        //refresh view pager data
        mWeatherViewPagerAdapter.setDatas(mSelectedcityList);
        //set view pager display index
        mViewPagerWeather.setCurrentItem(mIndex);
        //set page point size
        mPagePointAdapter.setSize(mSelectedcityList.size());
        //set page point index
        mPagePointAdapter.setIndex(mIndex);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        switch (viewId){
            case R.id.button_back:
                finish();
                break;
            case R.id.button_start_net:
                //TODO start net setting
                break;
        }
    }
}
