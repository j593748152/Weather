package com.hht.weather.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class CityDao {
    private static final String TAG = "CityDao";
    public static final String DB_NAME  = "weather.db";

    private Context mContext = null;
    private SQLiteDatabase db = null;
    private WeatherSQLiteOpenHelper dbHelper = null;
    private SQLiteDatabase.CursorFactory cf = null;

    public CityDao(Context context) {
        mContext = context;
        dbHelper = new WeatherSQLiteOpenHelper(mContext, DB_NAME, null, 1);
        db = dbHelper.getWritableDatabase();
    }

    public boolean insertCity(int city_id, String city_name, String province_name, String city_name_pinyin, String city_name_ab) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("city_id", city_id);
        contentValues.put("city_name", city_name);
        contentValues.put("province_name", province_name);
        contentValues.put("city_name_pinyin", city_name_pinyin);
        contentValues.put("city_name_ab", city_name_ab);
        long l = db.insert("city", null, contentValues);
        Log.d(TAG , "insert city " + l);
        return true;
    }

    public int qureyCityCode(String city_name){
        Cursor cursor = db.query("city", new String[]{"city_id"},"city_name=?", new String[]{"北京"},null,null,null);
        cursor.moveToFirst();
        int city_code = cursor.getInt(0);
        return city_code;
    }

}
