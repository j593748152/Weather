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
import java.util.Calendar;

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
        //TODO set item data,time must server`s time
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        if (position == 0){
            holder.time.setText("现在");
            holder.timeWeather.setImageResource(R.drawable.weather_icon_sunny);
            holder.timeTemperature.setText("31℃");
        }else if (position == 1){
            int h = (hour + 2*position)%24;
            holder.time.setText(h + "点");
            holder.timeWeather.setImageResource(R.drawable.weather_icon_cloudy1);
            holder.timeTemperature.setText((31-position)+"℃");
        }else if (position == 2){
            int h = (hour + 2*position)%24;
            holder.time.setText(h + "点");
            holder.timeWeather.setImageResource(R.drawable.weather_icon_cloudy2);
            holder.timeTemperature.setText((31-position)+"℃");
        }else if (position == 3){
            int h = (hour + 2*position)%24;
            holder.time.setText(h + "点");
            holder.timeWeather.setImageResource(R.drawable.weather_icon_fog);
            holder.timeTemperature.setText((31-position)+"℃");
        }else if (position == 4){
            int h = (hour + 2*position)%24;
            holder.time.setText(h + "点");
            holder.timeWeather.setImageResource(R.drawable.weather_icon_rain1);
            holder.timeTemperature.setText((31-position)+"℃");
        }else if (position == 5){
            int h = (hour + 2*position)%24;
            holder.time.setText(h + "点");
            holder.timeWeather.setImageResource(R.drawable.weather_icon_rain3);
            holder.timeTemperature.setText((31-position)+"℃");
        }else if (position == 6){
            int h = (hour + 2*position)%24;
            holder.time.setText(h + "点");
            holder.timeWeather.setImageResource(R.drawable.weather_icon_cloudy1);
            holder.timeTemperature.setText((31-position)+"℃");
        }else if (position == 7){
            int h = (hour + 2*position)%24;
            holder.time.setText(h + "点");
            holder.timeWeather.setImageResource(R.drawable.weather_icon_rain4);
            holder.timeTemperature.setText((31-position)+"℃");
        }else if (position == 8){
            int h = (hour + 2*position)%24;
            holder.time.setText(h + "点");
            holder.timeWeather.setImageResource(R.drawable.weather_icon_snow);
            holder.timeTemperature.setText((31-position)+"℃");
        }else {
            int h = (hour + 2*position)%24;
            holder.time.setText(h + "点");
            holder.timeWeather.setImageResource(R.drawable.weather_icon_thunder);
            holder.timeTemperature.setText((31-position)+"℃");
        }
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
            time = itemView.findViewById(R.id.textView_time);
            timeWeather = itemView.findViewById(R.id.imageView_time_weather);
            timeTemperature = itemView.findViewById(R.id.textView_time_temperature);
        }
    }
}
