package com.modeset.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.modeset.service.NormalSetService;

/**
 * Created by JackShen on 2016/5/8.
 */
public class NormalReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, NormalSetService.class);
        context.startService(i);
    }
}
