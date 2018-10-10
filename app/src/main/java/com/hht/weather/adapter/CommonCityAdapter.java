package com.hht.weather.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hht.weather.R;

import java.util.ArrayList;

/*

 */
public class CommonCityAdapter extends RecyclerView.Adapter<CommonCityAdapter.CommonCityViewHolder>{

    private Context mContext = null;
    private ArrayList<String> CommonCityList = null;

    public CommonCityAdapter(Context context, ArrayList<String> data){
        mContext = context;
        CommonCityList = data;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public CommonCityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView cityTextView = new TextView(mContext);
        cityTextView.setHeight(20);
        cityTextView.setWidth(50);
        cityTextView.setGravity(Gravity.CENTER);
        cityTextView.setTextColor(R.color.text_color_black);
        CommonCityViewHolder viewHolder = new CommonCityViewHolder(cityTextView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CommonCityViewHolder holder, int position) {
        holder.cityTextView.setText(CommonCityList.get(position));
    }

    @Override
    public int getItemCount() {
        return CommonCityList.size();
    }

    class CommonCityViewHolder extends RecyclerView.ViewHolder {
        private TextView cityTextView = null;
        public CommonCityViewHolder(View itemView) {
            super(itemView);
            cityTextView = (TextView) itemView;
        }
    }
}
