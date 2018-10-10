package com.hht.weather.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.hht.weather.R;
import com.hht.weather.data.DataDao;
import com.hht.weather.data.Weather;
import com.hht.weather.interface_.RecyclerViewClickInterface;
import com.hht.weather.utils.HttpUtil;

import java.util.ArrayList;

/*
WeatherSettingActivity item :  city list item
display and remove selected city
 */
public class CitySettingAdapter extends RecyclerView.Adapter<CitySettingAdapter.CitySettingHolder>
        implements View.OnLongClickListener, View.OnClickListener {

    private final String TAG = "CitySettingAdapter";

    private Context mContext = null;
    private ArrayList<String> mDatas = null;
    private DataDao mDataDao = null;

    private RecyclerViewClickInterface mClickListener = null;
    private boolean mDeleteMode = false;

    public CitySettingAdapter(Context context, ArrayList data) {
        super();
        mContext = context;
        mDatas = data;
        mDataDao = new DataDao(mContext);
    }

    public void setClickListener(RecyclerViewClickInterface clickListener){
        mClickListener = clickListener;
    }

    public void setDeleteDisplay(boolean deleteMode){
        mDeleteMode = deleteMode;
        notifyDataSetChanged();
    }

    public void setDatas(ArrayList<String> datas){
        mDatas = datas;
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
        String cityName = mDatas.get(position);
        String cityCode = mDataDao.qureyCityCodeByName(cityName);
        Weather weather = mDataDao.qureyWeatherByCityName(cityName);
        holder.cityName.setText(cityName);
        if(cityName.equals(HttpUtil.getLocationCity())){
            holder.cityLocationFlag.setVisibility(View.VISIBLE);
        }else {
            holder.cityLocationFlag.setVisibility(View.INVISIBLE);
        }
        if(weather != null){
            holder.cityWeather.setImageResource(getImageResource("he" + weather.getCond_code()));
            String tempMin = HttpUtil.getTemperature(weather.getTemp_min());
            String tempMax = HttpUtil.getTemperature(weather.getTemp_max());
            holder.cityTemperature.setText(tempMin + "/" + tempMax);
        }

        holder.cityDelete.setVisibility(mDeleteMode ? View.VISIBLE : View.GONE);
        holder.cityDelete.setOnClickListener(this);
        holder.cityDelete.setTag(position);
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
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

    private int getImageResource(String name){
        int id = mContext.getResources().getIdentifier(name, "drawable", mContext.getPackageName());
        if (id <= 0){
            Log.e(TAG, name + " not found drawalbe");
        }
        return id;
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
