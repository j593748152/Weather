package com.hht.weather.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hht.weather.R;

public class TimeWeatherAdapter extends RecyclerView.Adapter<TimeWeatherAdapter.TimeWeatherViewHolder>{

    @Override
    public TimeWeatherAdapter.TimeWeatherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(TimeWeatherAdapter.TimeWeatherViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 12;
    }

    class TimeWeatherViewHolder extends RecyclerView.ViewHolder {

        private TextView date;
        private ImageView weather;
        private TextView temperature;

        public TimeWeatherViewHolder(View itemView) {
            super(itemView);
            //TODO define view in item
            date = itemView.findViewById(R.id.textView_item_date);
            weather = itemView.findViewById(R.id.imageView_item_weather);
            temperature = itemView.findViewById(R.id.textView_item_temperature);
        }
    }
}
