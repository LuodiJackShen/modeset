package com.modeset.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.modeset.util.ShutdownUtil;

/**
 * Created by JackShen on 2016/6/2.
 */
public class Shutdown extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        /***
         * 关机逻辑代码
         */
        ShutdownUtil.shutdown();
    }
}
