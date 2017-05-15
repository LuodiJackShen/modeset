package com.modeset.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.modeset.service.VibrateSetService;

/**
 * Created by JackShen on 2016/5/8.
 */
public class VibrateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, VibrateSetService.class);
        context.startService(i);
    }
}
