package com.hht.weather;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hht.weather.adapter.CitySettingAdapter;

import java.util.ArrayList;

public class WeatherSettingActivity extends Activity implements View.OnClickListener{
    private final String TAG = "WeatherSettingActivity";

    private Context mContext = null;
    private RecyclerView mCitySettingRecyclerView = null;
    private CitySettingAdapter mCitySettingAdapter = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_setting_activity);
        mContext = this;

        mCitySettingRecyclerView = findViewById(R.id.recyclerView_city_collection);
        mCitySettingAdapter = new CitySettingAdapter(mContext, new ArrayList());
        mCitySettingRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mCitySettingRecyclerView.setAdapter(mCitySettingAdapter);


    }


    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        switch (viewId) {
            case R.id.imageView_setting_back:
                finish();
                break;
            case R.id.radioButton_Celsius:
                break;
            case R.id.radioButton_Fahrenheit:
                break;
            case R.id.imageButton_add_city:
                startActivity(new Intent());
                break;
        }
    }
}
