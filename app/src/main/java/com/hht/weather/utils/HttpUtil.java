package com.hht.weather.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/*
util abount weahter
 */
public class HttpUtil {

    private static final String TAG = "HttpUtil";
    public static final String MY_KEY = "4051ea2ba37948af8b6995fbc3e9cb01";
    public static final String HE_WEATHER_URL = "https://free-api.heweather.com/s6/weather?key=" + MY_KEY + "&";
    public static final String HE_AIR_QUALITY_URL = "https://free-api.heweather.com/s6/air/now?key=" + MY_KEY + "&";
    public static final String HE_TEMP_RAND_URL = "https://free-api.heweather.com/s6/weather?key=" + MY_KEY + "&";
    public static final String HE_COMMON_CITY_URL = "https://search.heweather.com/top?group=cn&number=10&key=" + MY_KEY;
    public static final String HE_WEATHER_LOCATION = "location=";
    public static final String HE_TIME_FORMAT = "yyyy-MM-dd HH:mm";
    public static final String HE_DATE_FORMAT = "yyyy-MM-dd";

    public static String mTemperatureUnit = "℃";


    public static String getWebContent(String strUrl) {
        Log.d(TAG, "getWebContent " + strUrl);
        HttpURLConnection conn = null;
        try {
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setReadTimeout(10000);

            InputStream in = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuffer.append(line);
            }
            Log.d(TAG, stringBuffer.toString());
            return stringBuffer.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return null;
    }

    public static boolean isNetworkAvalible(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return false;
        } else {
            NetworkInfo[] net_info = connectivityManager.getAllNetworkInfo();

            if (net_info != null) {
                for (int i = 0; i < net_info.length; i++) {
                    if (net_info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static long parseHeTime(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat(HE_TIME_FORMAT);
        try {
           return sdf.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static long parseHeDate(String date){
        SimpleDateFormat sdf = new SimpleDateFormat(HE_DATE_FORMAT);
        try {
            return sdf.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public static String getTemperature(int celsius){
        if (mTemperatureUnit.equals("℃")){
            return celsius + mTemperatureUnit;
        } else if (mTemperatureUnit.equals("℉")) {
            long fahrenheit = Math.round(1.8 * celsius + 32);
            return fahrenheit + mTemperatureUnit;
        }
        return null;
    }

    public static String getLocationCity(){
        return "深圳";
    }
}
