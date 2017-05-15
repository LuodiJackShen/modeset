package com.modeset.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.modeset.R;
import com.modeset.util.FileUtil;
import com.modeset.util.MyApplication;

import java.util.Date;

/**
 * Created by JackShen on 2016/5/25.
 */
public class CancelService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                FileUtil.clearAll();
                FileUtil.addToFile("晚安 at " + new Date(), "mslog.txt");
                stopSelf();
            }
        }).start();
        Toast.makeText(MyApplication.getContext(),
                getResources().getString(R.string.label)
                        + "祝您晚安！", Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);
    }
}
