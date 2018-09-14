package com.hht.weather;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.hht.weather.adapter.AddCityAdapter;
import com.hht.weather.listener.RecyclerViewClickInterface;

import java.util.ArrayList;

public class AddCityActivity extends Activity implements RecyclerViewClickInterface{
    private final String TAG = "AddCityActivity";

    private Context mContext = null;
    private RecyclerView mAddCityRecyclerView = null;
    private AddCityAdapter mAddCityAdapter = null;
    private EditText mSereachCityEditText = null;

    private ArrayList<String> mCitySereachResult = new ArrayList<String>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_city_activity);
        mContext = this;

        mSereachCityEditText = findViewById(R.id.editText_sereach_city);


        mAddCityRecyclerView = findViewById(R.id.recyclerView_add_city);
        mAddCityAdapter = new AddCityAdapter(mContext, mCitySereachResult);
        mAddCityRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAddCityRecyclerView.setAdapter(mAddCityAdapter);
        initListener();
    }

    private void initListener(){
        mSereachCityEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //TODO sereach database, get result, update view
                mCitySereachResult.add("深圳，中国，广东");
                mCitySereachResult.add("深圳，中国，广东");
                mCitySereachResult.add("深圳，中国，广东");
                mAddCityAdapter.setData(mCitySereachResult);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onClickRecyclerView(View view) {
        int viewID = view.getId();
        switch (viewID){
            case R.id.textView_add_city:
                int position = (int)view.getTag();
                Log.e(TAG, "positon = " + position);
                //TODO add city in database
                break;
            default:
        }

    }

    @Override
    public boolean onLongClickRecyclerView(View view) {
        return false;
    }
}
