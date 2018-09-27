package com.hht.weather.utils;

import android.content.res.AssetManager;
import android.support.annotation.NonNull;
import android.util.Log;

import com.hht.weather.data.City;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class FileParser {
    private static String TAG = "FileParser";


    public static ArrayList<City> parseCityCsv(InputStream cityCsvInput)  {
        ArrayList<City> cityList = new ArrayList<City>();
        cityList.clear();
        BufferedReader br = null;
        int i = 0;
        try {
            br = new BufferedReader(new InputStreamReader(cityCsvInput));
            String line = "";
            while ((line = br.readLine()) != null){
                i++;
                if(i < 3) continue;
                Log.d(TAG, "line = " + line);
                String buffer[] = line.split(",");
                //only city, not include district
                if(buffer[2].equals(buffer[9])){
                    City city = new City();
                    city.setCity_code(buffer[0]);
                    city.setCity_name(buffer[2]);
                    city.setProvince_name(buffer[7]);
                    city.setCity_name_pinyin(buffer[8]);
                    cityList.add(city);
                }
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "city list size " + cityList.size());
        return cityList;
    }
}
