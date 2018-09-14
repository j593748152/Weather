package com.hht.weather.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hht.weather.R;
import com.hht.weather.listener.RecyclerViewClickInterface;

import java.util.ArrayList;

public class AddCityAdapter extends RecyclerView.Adapter<AddCityAdapter.AddCityViewHolder> implements View.OnClickListener{

    private Context mContext = null;
    private ArrayList<String> mData = null;

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
        itemView.setOnClickListener(this);
        return addCityViewHolder;
    }

    @Override
    public void onBindViewHolder(AddCityAdapter.AddCityViewHolder holder, int position) {
        holder.cityName.setText(mData.get(position));
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onClick(View view) {
        mClickListener.onClickRecyclerView(view);
    }

    public void setClickListener(RecyclerViewClickInterface clickListener){
        mClickListener = clickListener;
    }

    public void setData(ArrayList<String> data){
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
