package com.hht.weather.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.hht.weather.R;
import com.hht.weather.listener.RecyclerViewClickInterface;

import java.util.ArrayList;

public class CitySettingAdapter extends RecyclerView.Adapter<CitySettingAdapter.CitySettingHolder>
        implements View.OnLongClickListener, View.OnClickListener {

    private final String TAG = "CitySettingAdapter";

    private Context mContext = null;
    private ArrayList datas = null;

    private RecyclerViewClickInterface mClickListener = null;
    private boolean mDeleteMode = false;

    public CitySettingAdapter(Context context, ArrayList data) {
        super();
        mContext = context;
        datas = data;
    }

    public void setClickListener(RecyclerViewClickInterface clickListener){
        mClickListener = clickListener;
    }

    public void setDeleteDisplay(boolean deleteMode){
        mDeleteMode = deleteMode;
        notifyDataSetChanged();
    }

    @Override
    public CitySettingAdapter.CitySettingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CitySettingHolder citySettingHolder;
        View itemView = LayoutInflater.from(mContext).inflate(
                R.layout.item_city_setting_recycler, parent, false);
        citySettingHolder = new CitySettingHolder(itemView);
        itemView.setOnLongClickListener(this);
        itemView.setOnClickListener(this);
        return citySettingHolder;
    }

    @Override
    public void onBindViewHolder(CitySettingAdapter.CitySettingHolder holder, int position) {
        holder.cityName.setText("深圳");
        holder.cityLocationFlag.setImageResource(R.drawable.location);
        holder.cityWeather.setImageResource(R.drawable.weather_icon_rain2);
        holder.cityTemperature.setText("28℃");
        holder.cityDelete.setVisibility(mDeleteMode ? View.VISIBLE : View.GONE);
        holder.cityDelete.setOnClickListener(this);
        holder.cityDelete.setTag(position);
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    @Override
    public boolean onLongClick(View view) {
        mClickListener.onLongClickRecyclerView(view);
        return false;
    }

    @Override
    public void onClick(View view) {
        mClickListener.onClickRecyclerView(view);
    }

    class CitySettingHolder extends RecyclerView.ViewHolder {
        private TextView cityName = null;
        private ImageView cityLocationFlag = null;
        private ImageView cityWeather = null;
        private TextView cityTemperature = null;
        private ImageButton cityDelete = null;

        public CitySettingHolder(View itemView) {
            super(itemView);
            cityName = itemView.findViewById(R.id.textView_city_name);
            cityLocationFlag = itemView.findViewById(R.id.imageView_location_flag);
            cityWeather = itemView.findViewById(R.id.imageView_city_weather);
            cityTemperature = itemView.findViewById(R.id.textView_city_temperature);
            cityDelete = itemView.findViewById(R.id.imageButton_delete_city);
        }

    }


}
