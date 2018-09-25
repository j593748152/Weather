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
        //sqLiteDatabase.execSQL(" DROP TABLE if exists city");
        sqLiteDatabase.execSQL("create table if not exists " + DataDao.TABLE_CITY +
                "(_id integer  primary key AUTOINCREMENT, city_code varchar(20), city_name varchar(20), province_name varchar(20), city_name_pinyin varchar(20), city_name_ab varchar(20) )");
        sqLiteDatabase.execSQL("create table if not exists "+ DataDao.TABLE_SELECTED_CITY +"(_id integer  primary key AUTOINCREMENT,city_name varchar(20))");
        sqLiteDatabase.execSQL("create table if not exists "+ DataDao.TABLE_WEATHER +"(_id integer  primary key AUTOINCREMENT, city_code varchar(20),  cond_code integer, cond_txt varchar(10), temperature varchar(10)," +
                "temperature_rand varchar(20), air_quality varchar(10), update_time integer)");
        sqLiteDatabase.execSQL("create table if not exists "+ DataDao.TABLE_TIME_WEATHER +"(_id integer  primary key AUTOINCREMENT, city_code varchar(20), cond_code integer, cond_txt varchar(10), temperature varchar(10),  time integer)");
        sqLiteDatabase.execSQL("create table if not exists "+ DataDao.TABLE_WEEK_WEATHER +"(_id integer  primary key AUTOINCREMENT, city_code varchar(20), cond_code integer, cond_txt varchar(10), temperature_rand varchar(20), date integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
