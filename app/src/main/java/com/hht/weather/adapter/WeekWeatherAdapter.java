package com.hht.weather.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hht.weather.R;
import com.hht.weather.data.WeekWeather;
import com.hht.weather.utils.HttpUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public  class WeekWeatherAdapter extends RecyclerView.Adapter<WeekWeatherAdapter.WeekWeatherViewHolder> {

    private static final String TAG = "WeekWeatherAdapter";

    private Context mContext;
    private ArrayList mDatas = new ArrayList<WeekWeather>();

    public WeekWeatherAdapter(Context context, ArrayList data) {
        super();
        mContext = context;
        mDatas = data;
    }

    @Override
    public void onBindViewHolder(WeekWeatherViewHolder holder, int position) {
        //TODO set item data,time must server`s time
        WeekWeather weekWeather = (WeekWeather) mDatas.get(position);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
        holder.date.setText(sdf.format(weekWeather.getDate()));
        holder.weekWeather.setImageResource(getImageResource("he" + weekWeather.getCond_code()));
        String tempMin = HttpUtil.getTemperature(weekWeather.getTemp_min());
        String tempMax = HttpUtil.getTemperature(weekWeather.getTemp_max());
        holder.weekTemperature.setText(tempMin + "/" + tempMax);
    }

    @Override
    public WeekWeatherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        WeekWeatherViewHolder viewHolder;
        viewHolder = new WeekWeatherViewHolder((LayoutInflater.from(mContext).inflate(
                R.layout.item_week_weather_recycler, parent, false)));
        return viewHolder;
    }


    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    private int getImageResource(String name){
        int id = mContext.getResources().getIdentifier(name, "drawable", mContext.getPackageName());
        if (id <= 0){
            Log.e(TAG, name + " not found drawalbe");
        }
        return id;
    }


    class WeekWeatherViewHolder extends RecyclerView.ViewHolder {

        private TextView date;
        private ImageView weekWeather;
        private TextView weekTemperature;

        public WeekWeatherViewHolder(View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.textView_item_date);
            weekWeather = itemView.findViewById(R.id.imageView_item_weather);
            weekTemperature = itemView.findViewById(R.id.textView_item_temperature);
        }
    }
}
