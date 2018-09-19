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

public class HttpUtil {

    private static final String TAG = "HttpUtil";
    public static final String MY_KEY = "4051ea2ba37948af8b6995fbc3e9cb01";
    public static final String HE_WEATHER_URL = "https://free-api.heweather.com/s6/weather/now?key=" + MY_KEY + "&";
    public static final String HE_WEATHER_HOURLY_URL = "https://free-api.heweather.com/s6/weather/hourly?key=" + MY_KEY + "&";
    public static final String HE_WEATHER_WEEK_URL = "https://free-api.heweather.com/s6/weather/forecast?key=" + MY_KEY + "&";
    public static final String HE_WEATHER_LOCATION = "location=";
    public static final String city = "http://guolin.tech/api/china/";

    public static String getWebContent(String strUrl) {
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

    public static JSONObject getProvince(){
        HttpURLConnection conn = null;
        try {
            URL url = new URL(city);
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
            Log.d("TAG", stringBuffer.toString());
            return new JSONObject(stringBuffer.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return null;
    }

    public static JSONArray getCity(String provinceCode){
        HttpURLConnection conn = null;
        try {
            URL url = new URL(city+ "/" + provinceCode);
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
            Log.d("TAG", stringBuffer.toString());
            return new JSONArray(stringBuffer.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return null;
    }
/*
    public static String getWebContent2(String strUrl) {
        HttpURLConnection conn = null;
        try {
            HttpClient httpClient=new DefaultHttpClient();
            HttpGet httpGet=new HttpGet(strUrl);
            HttpResponse httpResponse=httpClient.execute(httpGet);
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
    }*/

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
}
