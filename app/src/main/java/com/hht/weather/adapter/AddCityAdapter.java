package com.hht.weather.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hht.weather.R;
import com.hht.weather.data.City;
import com.hht.weather.interface_.RecyclerViewClickInterface;

import java.util.ArrayList;

/*
AddCityActivity  item : search and add to selected city
 */

public class AddCityAdapter extends RecyclerView.Adapter<AddCityAdapter.AddCityViewHolder> implements View.OnClickListener{
    private final static String TAG = "AddCityAdapter";

    private Context mContext = null;
    private ArrayList<City> mData = null;

    private RecyclerViewClickInterface mClickListener = null;

    public AddCityAdapter (Context context, ArrayList data){
        super();
        mContext = context;
        mData = data;
    }

    @Override
    public AddCityAdapter.AddCityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        AddCityViewHolder addCityViewHolder;
        View itemView = LayoutInflater.from(mContext).inflate(
                R.layout.item_add_city_recycler, parent, false);
        addCityViewHolder = new AddCityViewHolder(itemView);
        return addCityViewHolder;
    }

    @Override
    public void onBindViewHolder(AddCityAdapter.AddCityViewHolder holder, int position) {
        City city = mData.get(position);
        holder.cityName.setText(city.getCity_name() + ",中国," + city.getProvince_name());
        holder.cityName.setOnClickListener(this);
        holder.cityName.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onClick(View view) {
        Log.i(TAG, " click " + view.getId());
        mClickListener.onClickRecyclerView(view);
    }

    public void setClickListener(RecyclerViewClickInterface clickListener){
        mClickListener = clickListener;
    }

    public void setData(ArrayList<City> data){
        mData = data;
        notifyDataSetChanged();
    }



    class AddCityViewHolder extends RecyclerView.ViewHolder {
        private TextView cityName;

        public AddCityViewHolder(View itemView) {
            super(itemView);
            cityName = itemView.findViewById(R.id.textView_add_city);
        }
    }
}
