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

public  class WeekWeatherAdapter extends RecyclerView.Adapter<WeekWeatherAdapter.WeekWeatherViewHolder> {

    private Context mContext;
    private ArrayList mDatas = new ArrayList<String>();

    public WeekWeatherAdapter(Context context, ArrayList data) {
        super();
        mContext = context;
        mDatas = data;
    }

    @Override
    public void onBindViewHolder(WeekWeatherViewHolder holder, int position) {
        //TODO set item data
        holder.date.setText("8/22");
        holder.weekWeather.setImageResource(R.drawable.weather);
        holder.weekTemperature.setText("28/32`C");
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
        return 7;
    }


    class WeekWeatherViewHolder extends RecyclerView.ViewHolder {

        private TextView date;
        private ImageView weekWeather;
        private TextView weekTemperature;

        public WeekWeatherViewHolder(View itemView) {
            super(itemView);
            //TODO define view in item
            date = itemView.findViewById(R.id.textView_item_date);
            weekWeather = itemView.findViewById(R.id.imageView_item_weather);
            weekTemperature = itemView.findViewById(R.id.textView_item_temperature);
        }
    }
}
