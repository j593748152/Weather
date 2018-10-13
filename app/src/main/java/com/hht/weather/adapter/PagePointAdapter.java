package com.hht.weather.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hht.weather.R;

public class PagePointAdapter extends RecyclerView.Adapter<PagePointAdapter.PagePointViewHolder> {
    private static String TAG = "PagePointAdapter";

    private Context mContext;
    private int mSize;
    private int mIndex;

    public PagePointAdapter(Context context, int size){
        mContext =context;
        mSize = size;
        mIndex = 0;
    }

    public void setIndex(int index){
        mIndex = index;
        notifyDataSetChanged();
    }

    public void setSize(int size){
        mSize = size;
        notifyDataSetChanged();
    }


    @Override
    public PagePointViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PagePointViewHolder viewHolder ;
        ImageView imageView = new ImageView(mContext);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(40,30);
        imageView.setLayoutParams(params);
        viewHolder = new PagePointViewHolder(imageView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PagePointViewHolder holder, int position) {
        if (position == mIndex){
            holder.pointImageView.setImageResource(R.drawable.point_white);
        }else {
            holder.pointImageView.setImageResource(R.drawable.point_gray);
        }
    }

    @Override
    public int getItemCount() {
        return mSize;
    }

     class PagePointViewHolder extends RecyclerView.ViewHolder {
        private ImageView pointImageView;
        public PagePointViewHolder(View itemView) {
            super(itemView);
            pointImageView = (ImageView) itemView;
        }


    }
}
