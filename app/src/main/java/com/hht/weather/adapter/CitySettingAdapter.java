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

public class CitySettingAdapter extends RecyclerView.Adapter<CitySettingAdapter.CitySettingHolder> {

    private Context mContext = null;
    private ArrayList datas = null;
    public CitySettingAdapter(Context context, ArrayList data) {
        super();
        mContext = context;
        datas = data;
    }



    @Override
    public CitySettingAdapter.CitySettingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CitySettingHolder citySettingHolder;
        citySettingHolder = new CitySettingHolder((LayoutInflater.from(mContext).inflate(
                R.layout.item_city_setting_recycler, parent, false)));

        return citySettingHolder;
    }

    @Override
    public void onBindViewHolder(CitySettingAdapter.CitySettingHolder holder, int position) {
        holder.cityName.setText("深圳");
        holder.cityWeather.setImageResource(R.drawable.weather);
        holder.cityTemperature.setText("28`C");

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class CitySettingHolder extends RecyclerView.ViewHolder {
        private TextView cityName = null;
        private ImageView cityLocationFlag = null;
        private ImageView cityWeather = null;
        private TextView cityTemperature = null;

        public CitySettingHolder(View itemView) {
            super(itemView);
            cityName = itemView.findViewById(R.id.textView_city_name);
            cityLocationFlag = itemView.findViewById(R.id.imageView_location_flag);
            cityWeather = itemView.findViewById(R.id.imageView_city_weather);
            cityTemperature = itemView.findViewById(R.id.textView_city_temperature);
        }

    }
}
