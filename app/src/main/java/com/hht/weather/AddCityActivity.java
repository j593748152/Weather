package com.hht.weather;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.hht.weather.adapter.AddCityAdapter;
import com.hht.weather.adapter.CommonCityAdapter;
import com.hht.weather.data.City;
import com.hht.weather.data.DataDao;
import com.hht.weather.interface_.RecyclerViewClickInterface;

import java.util.ArrayList;

public class AddCityActivity extends Activity implements View.OnClickListener, RecyclerViewClickInterface{
    private final String TAG = "AddCityActivity";

    private Context mContext = null;
    private DataDao mDataDao = null;
    private EditText mSearchCityEditText = null;
    private ImageButton mSearchClearImageButton = null;
    private RecyclerView mAddCityRecyclerView = null;
    private AddCityAdapter mAddCityAdapter = null;
    private RecyclerView mCommonCityRecyclerView = null;
    private CommonCityAdapter mCommonCityAdapter = null;

    private ImageView mImageViewNoResult = null;
    private TextView mTextViewNoResult = null;

    private ArrayList<String> mCitySearchResult = new ArrayList<String>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_city_activity);
        mContext = this;
        mDataDao = new DataDao(this);
        mSearchCityEditText = findViewById(R.id.editText_search_city);
        mSearchClearImageButton = findViewById(R.id.imageButton_search_clear);


        mAddCityRecyclerView = findViewById(R.id.recyclerView_add_city);
        mAddCityAdapter = new AddCityAdapter(mContext, mCitySearchResult);
        mAddCityRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAddCityRecyclerView.setAdapter(mAddCityAdapter);

        //TODO get http common city data
        mCommonCityRecyclerView = findViewById(R.id.recyclerView_common_city);
        mCommonCityRecyclerView.setLayoutManager(new GridLayoutManager(this,5));
        mCommonCityAdapter = new CommonCityAdapter(this, new ArrayList<String>());
        mCommonCityRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mCommonCityRecyclerView.setAdapter(mCommonCityAdapter);

        mImageViewNoResult = findViewById(R.id.imageView_no_result);
        mTextViewNoResult = findViewById(R.id.textView_no_result);

        initListener();
    }

    private void initListener(){
        mSearchClearImageButton.setOnClickListener(this);

        mSearchCityEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                City city = mDataDao.qureyCityByName(charSequence.toString());
                if (city != null){
                    mCitySearchResult.add(city.getCity_name() + "，中国，" + city.getProvince_name());
                    mAddCityAdapter.setData(mCitySearchResult);

                    mImageViewNoResult.setVisibility(View.GONE);
                    mTextViewNoResult.setVisibility(View.GONE);
                } else {
                    mImageViewNoResult.setVisibility(View.VISIBLE);
                    mTextViewNoResult.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length() > 0){
                    mSearchClearImageButton.setVisibility(View.VISIBLE);
                }else {
                    mSearchClearImageButton.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    @Override
    public void onClickRecyclerView(View view) {
        int viewID = view.getId();
        switch (viewID){
            case R.id.textView_add_city:
                int position = (int)view.getTag();
                int index = mCitySearchResult.indexOf(",");
                String city_name = mCitySearchResult.get(position).substring(0,index);
                mDataDao.insertSelectedCity(city_name);
                Log.e(TAG, "positon = " + position + " city name = " + city_name);
                break;
            default:
        }

    }

    @Override
    public boolean onLongClickRecyclerView(View view) {
        return false;
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        switch (viewId) {
            case R.id.imageButton_search_clear:
                mSearchCityEditText.setText("");
                mSearchCityEditText.requestFocusFromTouch();
        }

    }
}
