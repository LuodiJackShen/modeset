package com.modeset.util;

import android.app.Application;
import android.content.Context;

/**
 * Created by JackShen on 2016/5/21.
 */
public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
