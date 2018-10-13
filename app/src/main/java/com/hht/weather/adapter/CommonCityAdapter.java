package com.hht.weather.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hht.weather.R;
import com.hht.weather.WeatherActivity;
import com.hht.weather.data.DataDao;
import com.hht.weather.service.WeatherService;
import com.hht.weather.utils.HttpUtil;

import java.util.ArrayList;

/*
AddCityActivity  item : common city item  and click listener
 */
public class CommonCityAdapter extends RecyclerView.Adapter<CommonCityAdapter.CommonCityViewHolder> implements View.OnClickListener{

    private static final String TAG = "CommonCityAdapter";

    private Context mContext = null;
    private DataDao mDataDao = null;
    private ArrayList<String> mCommonCityList = null;

    public CommonCityAdapter(Context context, ArrayList<String> data){
        mContext = context;
        mCommonCityList = data;
        mDataDao = new DataDao(mContext);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public CommonCityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        TextView cityTextView = new TextView(mContext);
        cityTextView.setHeight(60);
        cityTextView.setGravity(Gravity.CENTER);
        cityTextView.setTextColor(R.color.text_color_black);
        CommonCityViewHolder viewHolder = new CommonCityViewHolder(cityTextView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CommonCityViewHolder holder, int position) {
        holder.cityTextView.setText(mCommonCityList.get(position));
        holder.cityTextView.setTag(position);
        holder.cityTextView.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return mCommonCityList.size();
    }

    @Override
    public void onClick(View view) {
        int commonCityPosition = (int)view.getTag();
        String commonCityName = mCommonCityList.get(commonCityPosition);

        //start weather service to get weather data
        Intent weatherServiceIntent2 = new Intent(mContext, WeatherService.class);
        weatherServiceIntent2.putExtra("cityName", commonCityName);
        mContext.startService(weatherServiceIntent2);

        //add selected city
        if(!commonCityName.equals(HttpUtil.getLocationCity())){
            mDataDao.insertSelectedCity(commonCityName);
        }

        //start weather activity
        Intent weatherActivityIntent2 = new Intent(mContext, WeatherActivity.class);
        weatherActivityIntent2.putExtra("city", commonCityName);
        mContext.startActivity(weatherActivityIntent2);

        Log.d(TAG,  "common city name = " + commonCityName);
    }


    class CommonCityViewHolder extends RecyclerView.ViewHolder {
        private TextView cityTextView = null;
        public CommonCityViewHolder(View itemView) {
            super(itemView);
            cityTextView = (TextView) itemView;
        }
    }
}
