package com.hht.weather.data;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.hht.weather.utils.FileParser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class WeatherSQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = "WeatherSQLiteOpenHelper";
    private static String cityCsv = "china-city-list.csv";
    private Context mContext;


    public WeatherSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    public WeatherSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //sqLiteDatabase.execSQL(" DROP TABLE if exists city");
        sqLiteDatabase.execSQL("create table if not exists " + DataDao.TABLE_CITY +
                "(_id integer  primary key AUTOINCREMENT, city_code varchar(20), city_name varchar(20), province_name varchar(20), city_name_pinyin varchar(20), city_name_ab varchar(20) )");
        sqLiteDatabase.execSQL("create table if not exists "+ DataDao.TABLE_SELECTED_CITY +"(_id integer  primary key AUTOINCREMENT,city_name varchar(20))");
        sqLiteDatabase.execSQL("create table if not exists "+ DataDao.TABLE_WEATHER +"(_id integer  primary key AUTOINCREMENT, city_code varchar(20),  cond_code integer, cond_txt varchar(10), temperature INT," +
                " temp_max INT, temp_min INT, air_quality varchar(10), update_time INT64)");
        sqLiteDatabase.execSQL("create table if not exists "+ DataDao.TABLE_TIME_WEATHER +"(_id integer  primary key AUTOINCREMENT, city_code varchar(20), cond_code integer, cond_txt varchar(10), temperature INT,  time INT64)");
        sqLiteDatabase.execSQL("create table if not exists "+ DataDao.TABLE_WEEK_WEATHER +"(_id integer  primary key AUTOINCREMENT, city_code varchar(20), cond_code integer, cond_txt varchar(10), temp_max INT, temp_min INT, date INT64)");

        //init city data
        AssetManager assetManager = mContext.getAssets() ;
        try {
            InputStream cityCsvInput = assetManager.open(cityCsv) ;
            ArrayList<City> cityList = FileParser.parseCityCsv(cityCsvInput);
            for (City city : cityList){
                String sql = "'" + city.getCity_code() + "','" + city.getCity_name() + "','" + city.getProvince_name() + "','" + city.getCity_name_pinyin() + "','" + city.getCity_name_ab() + "'";
                sqLiteDatabase.execSQL("insert into " + DataDao.TABLE_CITY + " (city_code,city_name,province_name,city_name_pinyin,city_name_ab) values (" + sql + ")");
                //sqLiteDatabase.execSQL("insert into " + DataDao.TABLE_CITY + " values (" + sql + ")");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
