package com.newbiechen.usenightmode;

import android.app.Application;
import android.content.SharedPreferences;

import com.newbiechen.usenightmode.Utils.SharedPreUtils;

/**
 * Created by PC on 2016/11/4.
 */

public class MyApplication extends Application {
    public static boolean isNightMode = false;
    @Override
    public void onCreate() {
        super.onCreate();
        //从Shared中获取
        getNightMode();
    }

    private void getNightMode(){
        SharedPreferences sharedPreferences = getSharedPreferences(SharedPreUtils.FILE_NAME,MODE_PRIVATE);
        isNightMode = sharedPreferences.getBoolean(SharedPreUtils.KEY_NIGHT_MODE,false);
    }
}
