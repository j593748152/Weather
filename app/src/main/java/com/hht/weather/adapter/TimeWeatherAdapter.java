package com.hht.weather.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hht.weather.R;

import java.util.ArrayList;

public class TimeWeatherAdapter extends RecyclerView.Adapter<TimeWeatherAdapter.TimeWeatherViewHolder>{
    private Context mContext;
    private ArrayList mDatas = new ArrayList<String>();

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
        holder.time.setText("17点");
        holder.timeWeather.setImageResource(R.drawable.weather);
        holder.timeTemperature.setText("31`C");
    }

    @Override
    public int getItemCount() {
        return 12;
    }

    class TimeWeatherViewHolder extends RecyclerView.ViewHolder {

        private TextView time;
        private ImageView timeWeather;
        private TextView timeTemperature;

        public TimeWeatherViewHolder(View itemView) {
            super(itemView);
            //TODO define view in item
            time = itemView.findViewById(R.id.textView_time);
            timeWeather = itemView.findViewById(R.id.imageView_time_weather);
            timeTemperature = itemView.findViewById(R.id.textView_time_temperature);
        }
    }
}
