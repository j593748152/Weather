package com.hht.weather.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.hht.weather.service.WeatherService;

public class BootReceiver extends BroadcastReceiver {
    private static final String TAG = "BootReceiver";
    private static final String ACTION_BOOT = "android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.d(TAG, "jws action = " + action);
        if(ACTION_BOOT.equals(action)){
            Intent startWeatherService = new Intent(context, WeatherService.class);
            context.startService(startWeatherService);
        }
    }
}
