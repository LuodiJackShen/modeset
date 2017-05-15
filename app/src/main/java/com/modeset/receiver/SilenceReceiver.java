package com.modeset.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.modeset.service.SilenceSetService;

/**
 * Created by JackShen on 2016/5/8.
 */
public class SilenceReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, SilenceSetService.class);
        context.startService(i);
    }
}
