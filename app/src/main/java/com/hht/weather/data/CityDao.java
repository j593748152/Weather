package com.hht.weather.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CityDao {

    private Context mContext = null;
    private SQLiteDatabase db = null;
    private WeatherSQLiteOpenHelper dbHelper = null;
    private SQLiteDatabase.CursorFactory cf = null;

    public CityDao(Context context) {
        mContext = context;
        dbHelper = new WeatherSQLiteOpenHelper(mContext, "weather.db", null, 1);
        db = dbHelper.getWritableDatabase();
    }

    public boolean insertCity(int city_id, String city_name, String province_name) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("city_id", city_id);
        contentValues.put("city_name", city_name);
        contentValues.put("province_name", province_name);
        db.insert("city", null, contentValues);
        return true;
    }

    public int qureyCityCode(String city_name){
        Cursor cursor = db.query("city", new String[]{"city_id"},"city_name", new String[]{"city_name"},null,null,null);
        int city_code = cursor.getInt(0);
        return city_code;
    }

}
