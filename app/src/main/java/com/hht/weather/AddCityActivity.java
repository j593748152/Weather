package com.hht.weather;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
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
import com.hht.weather.service.WeatherService;
import com.hht.weather.utils.FileParser;
import com.hht.weather.utils.HttpUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/*
search city and add to selected city
 */
public class AddCityActivity extends Activity implements View.OnClickListener, RecyclerViewClickInterface{
    private final String TAG = "AddCityActivity";

    private final String COMMON_CITY_FILE = "common-city-list.txt";

    private Context mContext = null;
    private DataDao mDataDao = null;

    private ImageButton mImageButtonBack = null;
    private EditText mSearchCityEditText = null;
    private ImageButton mSearchClearImageButton = null;
    private RecyclerView mAddCityRecyclerView = null;
    private AddCityAdapter mAddCityAdapter = null;
    private TextView mTextViewTittle = null;
    private RecyclerView mCommonCityRecyclerView = null;
    private CommonCityAdapter mCommonCityAdapter = null;
    private ArrayList<String> mCommonCityList = null;

    private ImageView mImageViewNoResult = null;
    private TextView mTextViewNoResult = null;

    private ArrayList<City> mCitySearchResult = new ArrayList<City>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_city_activity);
        mContext = this;
        mDataDao = new DataDao(this);

        mImageButtonBack = findViewById(R.id.imageButton_add_city_back);
        mSearchCityEditText = findViewById(R.id.editText_search_city);
        mSearchClearImageButton = findViewById(R.id.imageButton_search_clear);
        mTextViewTittle = findViewById(R.id.textView_tittle);
        mAddCityRecyclerView = findViewById(R.id.recyclerView_add_city);
        mCommonCityRecyclerView = findViewById(R.id.recyclerView_common_city);
        mImageViewNoResult = findViewById(R.id.imageView_no_result);
        mTextViewNoResult = findViewById(R.id.textView_no_result);

        initData();
        initListener();
    }

    private void initData(){
        mAddCityAdapter = new AddCityAdapter(mContext, mCitySearchResult);
        mAddCityRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAddCityRecyclerView.setAdapter(mAddCityAdapter);

        mCommonCityRecyclerView.setLayoutManager(new GridLayoutManager(this,5));
        AssetManager assetManager = mContext.getAssets() ;
        try {
            InputStream commonCityInput = assetManager.open(COMMON_CITY_FILE) ;
            mCommonCityList = FileParser.getCommonCityList(commonCityInput);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mCommonCityAdapter = new CommonCityAdapter(this, mCommonCityList);
        mCommonCityRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mCommonCityRecyclerView.setAdapter(mCommonCityAdapter);

    }

    private void initListener(){
        mImageButtonBack.setOnClickListener(this);
        mSearchClearImageButton.setOnClickListener(this);
        mAddCityAdapter.setClickListener(this);

        mSearchCityEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mCitySearchResult.clear();
                ArrayList<City> cityArrayList = mDataDao.qureyCityByName(charSequence.toString());
                if (cityArrayList != null && cityArrayList.size() > 0){
                    mCitySearchResult = cityArrayList;
                    mImageViewNoResult.setVisibility(View.GONE);
                    mTextViewNoResult.setVisibility(View.GONE);
                } else {
                    mImageViewNoResult.setVisibility(View.VISIBLE);
                    mTextViewNoResult.setVisibility(View.VISIBLE);
                }
                mAddCityAdapter.setData(mCitySearchResult);

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length() > 0){
                    mTextViewTittle.setText("搜索结果");
                    mCommonCityRecyclerView.setVisibility(View.GONE);
                    mSearchClearImageButton.setVisibility(View.VISIBLE);
                }else {
                    mTextViewTittle.setText("常用城市");
                    mCommonCityRecyclerView.setVisibility(View.VISIBLE);
                    mSearchClearImageButton.setVisibility(View.INVISIBLE);
                    mImageViewNoResult.setVisibility(View.GONE);
                    mTextViewNoResult.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onClickRecyclerView(View view) {
        int viewID = view.getId();
        Log.i(TAG, "click " + viewID);
        switch (viewID){
            case R.id.textView_add_city:
                int position = (int)view.getTag();
                String city_name = mCitySearchResult.get(position).getCity_name();

                //start weather service to get weather data
                Intent weatherServiceIntent = new Intent(mContext, WeatherService.class);
                weatherServiceIntent.putExtra("cityName", city_name);
                mContext.startService(weatherServiceIntent);

                //add selected city
                if(!city_name.equals(HttpUtil.getLocationCity())){
                    mDataDao.insertSelectedCity(city_name);
                }

                //start weather activity
                Intent weatherActivityIntent = new Intent(mContext, WeatherActivity.class);
                weatherActivityIntent.putExtra("city", city_name);
                mContext.startActivity(weatherActivityIntent);

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
            case R.id.imageButton_add_city_back:
                finish();
                break;
            case R.id.imageButton_search_clear:
                mSearchCityEditText.setText("");
                mSearchCityEditText.requestFocusFromTouch();
                break;
        }

    }
}
