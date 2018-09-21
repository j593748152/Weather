package com.hht.weather.data;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WeatherSQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = "WeatherSQLiteOpenHelper";



    public WeatherSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public WeatherSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table if not exists city(" +
                "city_id integer  primary key, city_name varchar(20), province_name varchar(20), city_name_pinyin varchar(20), city_name_ab varchar(20) )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    private boolean initCityData(){
        //TODO parse xml,insert data
        return true;
    }
}
