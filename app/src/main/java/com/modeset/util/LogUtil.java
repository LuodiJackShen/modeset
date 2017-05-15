package com.modeset.util;

import android.util.Log;

/**
 * Created by JackShen on 2016/5/9.
 */
public class LogUtil {
    private static final int VERBOSE = 0;
    private static final int DEBUG = 1;
    private static final int INFO = 2;
    private static final int WARN = 3;
    private static final int ERROR = 4;
    private static final int NOTHING = 6;

    /***
     * LEVEL = VERBOSE, logcat can do it.
     * LEVEL = NOTHING, logcat can't do it.
     */
//    private static final int LEVEL = VERBOSE;
    private static final int LEVEL = NOTHING;

    public static void v(String a, String b) {
        if (LEVEL <= VERBOSE) {
            Log.v(a, b);
        }
    }

    public static void d(String a, String b) {
        if (LEVEL <= DEBUG) {
            Log.d(a, b);
        }
    }

    public static void i(String a, String b) {
        if (LEVEL <= INFO) {
            Log.i(a, b);
        }
    }

    public static void w(String a, String b) {
        if (LEVEL <= WARN) {
            Log.w(a, b);
        }
    }

    public static void e(String a, String b) {
        if (LEVEL <= ERROR) {
            Log.e(a, b);
        }
    }
}
