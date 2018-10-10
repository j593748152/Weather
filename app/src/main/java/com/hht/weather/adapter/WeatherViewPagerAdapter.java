package com.hht.weather.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.hht.weather.R;
import com.hht.weather.WeatherSettingActivity;
import com.hht.weather.data.DataDao;
import com.hht.weather.data.Weather;
import com.hht.weather.utils.HttpUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/*
main activity page item adapter
 */
public class WeatherViewPagerAdapter extends PagerAdapter implements View.OnClickListener{

    private static final String TAG = "WeatherViewPagerAdapter";

    private Context mContext = null;
    private ArrayList<String> datas = null;
    private DataDao mDataDao = null;


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


    public WeatherViewPagerAdapter(Context context, ArrayList data){
        mContext = context;
        datas = data;
        mDataDao = new DataDao(mContext);
    }

    public void setDatas(ArrayList<String> data){
        datas = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = View.inflate(mContext,R.layout.item_weather_view_pager,null);


        mImageViewWeather = view.findViewById(R.id.imageView_weather);
        mTextViewTemperature = view.findViewById(R.id.textView_temprature);
        mTextViewLocation = view.findViewById(R.id.textView_location);
        mImageViewLocation = view.findViewById(R.id.imageView_location);
        mTextViewWeather = view.findViewById(R.id.textView_weather);
        mTextViewTempRank = view.findViewById(R.id.textView_tempRank);
        mTextViewAirQuality = view.findViewById(R.id.textView_airQuality);
        mTextViewUpdateTime = view.findViewById(R.id.textView_UpdateTtime);
        mWeatherSetting = view.findViewById(R.id.imageButton_setting);

        mWeatherSetting.setOnClickListener(this);

        mTimeWeatherRecyclerView = view.findViewById(R.id.recyclerView_time_weather);
        mTimeWeatherRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mWeekWeatherRecyclerView = view.findViewById(R.id.recyclerView_week_weather);
        mWeekWeatherRecyclerView.setItemAnimator(new DefaultItemAnimator());


        String mCurrentCity = datas.get(position);
        String cityCode = mDataDao.qureyCityCodeByName(mCurrentCity);
        Weather currentWeather = mDataDao.qureyWeatherByCityName(mCurrentCity);
        //TODO net exception warn
        for(int i = 0 ;i < 3 && currentWeather == null; i++){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            currentWeather = mDataDao.qureyWeatherByCityName(mCurrentCity);
        }

        //TODO weather big icon
        //mImageViewWeather = findViewById(R.id.imageView_weather);
        if(currentWeather != null){
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
        } else {
            Log.e(TAG, "net exception: can not get weather data");
        }



        ArrayList timeWeahterList = new ArrayList();
        timeWeahterList = mDataDao.qureyWeatherByCityCode(cityCode);
        mTimeWeatherAdapter = new TimeWeatherAdapter(mContext, timeWeahterList);
        mTimeWeatherRecyclerView.setAdapter(mTimeWeatherAdapter);


        ArrayList weekWeahterList = new ArrayList();
        weekWeahterList = mDataDao.qureyWeekWeatherByCityCode(cityCode);
        mWeekWeatherAdapter = new WeekWeatherAdapter(mContext, weekWeahterList);
        mWeekWeatherRecyclerView.setAdapter(mWeekWeatherAdapter);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }


    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        switch (viewId) {
            case R.id.imageButton_setting:
                Intent intent = new Intent();
                intent.setClass(mContext, WeatherSettingActivity.class);
                mContext.startActivity(intent);
        }
    }
}
