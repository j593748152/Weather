package com.hht.weather;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.hht.weather.adapter.TimeWeatherAdapter;
import com.hht.weather.adapter.WeekWeatherAdapter;
import com.hht.weather.data.DataDao;
import com.hht.weather.data.Weather;
import com.hht.weather.service.WeatherService;
import com.hht.weather.utils.HttpUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class WeatherActivity extends Activity implements View.OnClickListener{
    private final static String TAG = "WeatherActivity";

    private Context mContext = null;

    private String mCurrentCity = null;

    private ImageView mImageViewWeather = null;
    private TextView mTextViewTemperature = null;
    private TextView mTextViewLocation = null;
    private ImageView mImageViewLocation = null;
    private TextView mTextViewWeather = null;
    private TextView mTextViewTempRank = null;
    private TextView mTextViewAirQuality = null;
    private TextView mTextViewUpdateTime = null;

    private ImageButton mWeatherSetting = null;
    private RecyclerView mWeekWeatherRecyclerView = null;
    private WeekWeatherAdapter mWeekWeatherAdapter = null;
    private RecyclerView mTimeWeatherRecyclerView = null;
    private TimeWeatherAdapter mTimeWeatherAdapter = null;
    private DataDao mDataDao = null;

    private String mTemperatureUnit = "℃";
    private String mLocation = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_weather_activity);
        mContext = this;
        mDataDao = new DataDao(this);
        mLocation = HttpUtil.getLocationCity();

        initView();
        initOnClickListener();
        initData();

        Intent startWeatherService = new Intent(this, WeatherService.class);
        startWeatherService.putExtra("city", "all");
        startService(startWeatherService);

    }

    private void initView(){
        mImageViewWeather = findViewById(R.id.imageView_weather);
        mTextViewTemperature = findViewById(R.id.textView_temprature);
        mTextViewLocation = findViewById(R.id.textView_location);
        mImageViewLocation = findViewById(R.id.imageView_location);
        mTextViewWeather = findViewById(R.id.textView_weather);
        mTextViewTempRank = findViewById(R.id.textView_tempRank);
        mTextViewAirQuality = findViewById(R.id.textView_airQuality);
        mTextViewUpdateTime = findViewById(R.id.textView_UpdateTtime);


        mTimeWeatherRecyclerView = findViewById(R.id.recyclerView_time_weather);
        mTimeWeatherRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mWeekWeatherRecyclerView = findViewById(R.id.recyclerView_week_weather);
        mWeekWeatherRecyclerView.setItemAnimator(new DefaultItemAnimator());


    }

    private void initOnClickListener(){
        mWeatherSetting = findViewById(R.id.imageButton_setting);
        mWeatherSetting.setOnClickListener(this);
    }

    private void initData(){
        mCurrentCity = HttpUtil.getLocationCity();
        String cityCode = mDataDao.qureyCityCodeByName(mCurrentCity);
        Weather currentWeather = mDataDao.qureyWeatherByCityName(mCurrentCity);
        //TODO weather big icon
        //mImageViewWeather = findViewById(R.id.imageView_weather);
        mTextViewTemperature.setText(HttpUtil.getTemperature(currentWeather.getTemperature()));
        mTextViewLocation.setText(mCurrentCity);
        if(mCurrentCity.equals(HttpUtil.getLocationCity())){
            mImageViewLocation.setVisibility(View.VISIBLE);
        } else {
            mImageViewLocation.setVisibility(View.INVISIBLE);
        }
        mTextViewWeather.setText(currentWeather.getCond_txt());
        mTextViewTempRank.setText(HttpUtil.getTemperature(currentWeather.getTemp_min()) + "/" + HttpUtil.getTemperature(currentWeather.getTemp_max()) );
        mTextViewAirQuality.setText("空气" + currentWeather.getAir_quality());
        long updateTime = currentWeather.getUpdate_time();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
        mTextViewUpdateTime.setText(sdf.format(updateTime) + "发布");


        ArrayList timeWeahterList = new ArrayList();
        timeWeahterList = mDataDao.qureyWeatherByCityCode(cityCode);
        mTimeWeatherAdapter = new TimeWeatherAdapter(this, timeWeahterList);
        mTimeWeatherRecyclerView.setAdapter(mTimeWeatherAdapter);


        ArrayList weekWeahterList = new ArrayList();
        weekWeahterList = mDataDao.qureyWeekWeatherByCityCode(cityCode);
        mWeekWeatherAdapter = new WeekWeatherAdapter(this, weekWeahterList);
        mWeekWeatherRecyclerView.setAdapter(mWeekWeatherAdapter);
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
