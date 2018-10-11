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
import com.hht.weather.data.TimeWeather;
import com.hht.weather.utils.HttpUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class TimeWeatherAdapter extends RecyclerView.Adapter<TimeWeatherAdapter.TimeWeatherViewHolder>{
    private static final String TAG = "TimeWeatherAdapter";

    private Context mContext;
    private ArrayList mDatas = new ArrayList<TimeWeather>();

    public TimeWeatherAdapter(Context context, ArrayList data) {
        super();
        mContext = context;
        mDatas = data;
    }

    @Override
    public TimeWeatherAdapter.TimeWeatherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TimeWeatherViewHolder viewHolder;
        viewHolder = new TimeWeatherViewHolder((LayoutInflater.from(mContext).inflate(
                R.layout.item_time_weather_recycler, parent, false)));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TimeWeatherAdapter.TimeWeatherViewHolder holder, int position) {
        //TODO set item data,time must server`s time

        TimeWeather timeWeather =  (TimeWeather) mDatas.get(position);
        long time = timeWeather.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        holder.time.setText(sdf.format(time) + "点");
        holder.timeWeather.setImageResource(getImageResource("he" + timeWeather.getCond_code()));
        holder.timeTemperature.setText(HttpUtil.getTemperature(timeWeather.getTemperature()));
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


    class TimeWeatherViewHolder extends RecyclerView.ViewHolder {

        private TextView time;
        private ImageView timeWeather;
        private TextView timeTemperature;

        public TimeWeatherViewHolder(View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.textView_time);
            timeWeather = itemView.findViewById(R.id.imageView_time_weather);
            timeTemperature = itemView.findViewById(R.id.textView_time_temperature);
        }
    }
}
