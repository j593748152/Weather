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
import android.widget.RadioButton;

import com.hht.weather.adapter.CitySettingAdapter;
import com.hht.weather.interface_.RecyclerViewClickInterface;

import java.util.ArrayList;

public class WeatherSettingActivity extends Activity implements View.OnClickListener, RecyclerViewClickInterface{
    private final String TAG = "WeatherSettingActivity";

    private Context mContext = null;
    private RecyclerView mCitySettingRecyclerView = null;
    private CitySettingAdapter mCitySettingAdapter = null;
    private ImageButton mAddCtiyImageButton = null;
    private RadioButton mRadioButtonCelsius = null;
    private RadioButton mRadioButtonFahrenheit = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_setting_activity);
        mContext = this;

        mAddCtiyImageButton = findViewById(R.id.imageButton_add_city);

        mRadioButtonCelsius = findViewById(R.id.radioButton_Celsius);
        mRadioButtonFahrenheit = findViewById(R.id.radioButton_Fahrenheit);

        mCitySettingRecyclerView = findViewById(R.id.recyclerView_city_collection);
        mCitySettingAdapter = new CitySettingAdapter(mContext, new ArrayList());
        mCitySettingRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mCitySettingRecyclerView.setAdapter(mCitySettingAdapter);

        initClickListener();


    }

    private void initClickListener(){

        mAddCtiyImageButton.setOnClickListener(this);
        mRadioButtonCelsius.setOnClickListener(this);
        mRadioButtonFahrenheit.setOnClickListener(this);

        mCitySettingAdapter.setClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        switch (viewId) {
            case R.id.imageView_setting_back:
                finish();
                break;
            case R.id.radioButton_Celsius:
                //TODO set Temperature unit
                break;
            case R.id.radioButton_Fahrenheit:
                //TODO set Temperature unit
                break;
            case R.id.imageButton_add_city:
                Intent intent = new Intent(mContext, AddCityActivity.class);
                startActivity(intent);
                break;
        }
    }

    //city RecyclerView  click event
    //if recycler`s item click, please set onClickLisner in adapter`s onBindViewHolder()
    @Override
    public void onClickRecyclerView(View view) {
        int viewID = view.getId();
        switch(viewID) {
            case R.id.imageButton_delete_city:
                int position = (int)view.getTag();
                Log.e(TAG,"position = " + position);
                //TODO delete city data
                break;
            default:
                mCitySettingAdapter.setDeleteDisplay(false);
        }
    }

    //city RecyclerView  LongClick event
    @Override
    public boolean onLongClickRecyclerView(View view) {
        mCitySettingAdapter.setDeleteDisplay(true);
        return false;
    }
}
