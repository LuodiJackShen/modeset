package com.modeset.util;

import android.content.Context;
import android.os.PowerManager;

/**
 * Created by JackShen on 2016/6/2.
 */
public class ShutdownUtil {
    public static void shutdown() {
        PowerManager pw = (PowerManager)
                MyApplication.getContext().getSystemService(Context.POWER_SERVICE);
        pw.reboot("jack");

//        try {
//
//            //获得ServiceManager类
//            Class<?> ServiceManager = Class
//                    .forName("android.os.ServiceManager");
//
//            //获得ServiceManager的getService方法
//            Method getService = ServiceManager.getMethod("getService", java.lang.String.class);
//
//            //调用getService获取RemoteService
//            Object oRemoteService = getService.invoke(null, Context.POWER_SERVICE);
//
//            //获得IPowerManager.Stub类
//            Class<?> cStub = Class
//                    .forName("android.os.IPowerManager$Stub");
//            //获得asInterface方法
//            Method asInterface = cStub.getMethod("asInterface", android.os.IBinder.class);
//            //调用asInterface方法获取IPowerManager对象
//            Object oIPowerManager = asInterface.invoke(null, oRemoteService);
//            //获得shutdown()方法
//            Method shutdown = oIPowerManager.getClass().getMethod("shutdown", boolean.class, boolean.class);
//            //调用shutdown()方法
//            shutdown.invoke(oIPowerManager, false, true);
//
//        } catch (Exception e) {
//            Log.e("mmmmm", e.toString(), e);
//        }
    }
}
