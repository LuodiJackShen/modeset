package com.modeset.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.modeset.util.FileUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by JackShen on 2016/5/8.
 */
public class NormalSetService extends Service {
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
                //to set model
                Date date = new Date();
                AudioManager audioManager =
                        (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 2, 1);

                SimpleDateFormat sdf =
                        new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
                String runTime = sdf.format(date);
                String log = "normal is run at " + runTime;
                FileUtil.addToFile(log, "mslog.txt");//写入日志

                stopSelf();
            }
        }).start();
        Toast.makeText(getApplicationContext(), "普通模式已启动", Toast.LENGTH_SHORT)
                .show();
        return super.onStartCommand(intent, flags, startId);
    }
}
