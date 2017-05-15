package com.modeset.activity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.modeset.R;
import com.modeset.model.GregorianCalendarSets;
import com.modeset.model.Setting;
import com.modeset.util.FileUtil;
import com.modeset.util.LogUtil;
import com.modeset.util.MyApplication;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by JackShen on 2016/5/8.
 */
public class ResultsHandler extends Activity {
    private boolean[] rts;
    private List<GregorianCalendar> gcs;
    private AlarmManager alarmManager;

    public static void actionStart(Context context, boolean[] results) {
        Intent intent = new Intent(context, ResultsHandler.class);
        intent.putExtra("results", results);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results_handler);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        /**
         * 从Intent中获取结果。
         */
        rts = getIntent().getBooleanArrayExtra("results");
        gcs = getGCSFromRTS(rts);
//        testGCS();
        handle();
    }

//    private void testGCS() {
//        for (int i = 0; i < gcs.size(); i++) {
//            LogUtil.d("testGCS", "hour is " + gcs.get(i).get(Calendar.HOUR_OF_DAY));
//            LogUtil.d("testGCS", "minute is " + gcs.get(i).get(Calendar.MINUTE));
//        }
//    }

    private void handle() {
        final String NORMAL = "android.com.setnormalmodel";
        final String SILENCE = "android.com.setsilencemodel";
        final String VIBRATE = "android.com.setvibratemodel";
        String flag;
        for (int i = 0; i < gcs.size(); i++) {
            Intent intent;
            if (i % 2 == 0) {
                if (Setting.getStateOfModelSwitch()) {
                    intent = new Intent(VIBRATE);
                    flag = VIBRATE;
                } else {
                    intent = new Intent(SILENCE);
                    flag = SILENCE;
                }

            } else {
                intent = new Intent(NORMAL);
                flag = NORMAL;
            }

            PendingIntent pi = PendingIntent.getBroadcast(MyApplication.getContext(),
                    i, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            /***
             * 0 <= i <= 19
             * 将 i 和对应的 Intent 写入文件，便于恢复相应的 PendingIntent.
             */
            boolean result = FileUtil.write(i, flag);
            LogUtil.d("writeresult", String.valueOf(result));

            GregorianCalendar gc = gcs.get(i);
            if (Build.VERSION.SDK_INT >= 19) {
                alarmManager
                        .setExact(AlarmManager.RTC_WAKEUP, gc.getTimeInMillis(), pi);
//                alarmManager
//                        .setWindow(
//                                AlarmManager.RTC_WAKEUP,
//                                gc.getTimeInMillis(),
//                                2000,
//                                pi);
            } else {
                alarmManager
                        .set(AlarmManager.RTC_WAKEUP, gc.getTimeInMillis(), pi);
            }
        }
    }

    private List<GregorianCalendar> getGCSFromRTS(boolean[] rts) {
        List<GregorianCalendar> tgcs = new ArrayList<>();
        for (int i = 0; i < rts.length; i++) {
            if (rts[i]) {
                GregorianCalendar gc1 = new GregorianCalendar();
                GregorianCalendar gc11 = new GregorianCalendar();
                GregorianCalendar gc2 = new GregorianCalendar();
                GregorianCalendar gc22 = new GregorianCalendar();
                switch (i) {
                    case 0:
                        gc1 = GregorianCalendarSets.getMorning(0);
                        gc11 = GregorianCalendarSets.getMorning(1);
                        gc2 = GregorianCalendarSets.getMorning(2);
                        gc22 = GregorianCalendarSets.getMorning(3);
                        break;
                    case 1:
                        gc1 = GregorianCalendarSets.getMorning(4);
                        gc11 = GregorianCalendarSets.getMorning(5);
                        gc2 = GregorianCalendarSets.getMorning(6);
                        gc22 = GregorianCalendarSets.getMorning(7);
                        break;
                    case 2:
                        if (Setting.getStateOfSeasonSwitch()) {
                            gc1 = GregorianCalendarSets.getSummerAfternoon(0);
                            gc11 = GregorianCalendarSets.getSummerAfternoon(1);
                            gc2 = GregorianCalendarSets.getSummerAfternoon(2);
                            gc22 = GregorianCalendarSets.getSummerAfternoon(3);
                        } else {
                            gc1 = GregorianCalendarSets.getWinterAfternoon(0);
                            gc11 = GregorianCalendarSets.getWinterAfternoon(1);
                            gc2 = GregorianCalendarSets.getWinterAfternoon(2);
                            gc22 = GregorianCalendarSets.getWinterAfternoon(3);
                        }
                        break;
                    case 3:
                        if (Setting.getStateOfSeasonSwitch()) {
                            gc1 = GregorianCalendarSets.getSummerAfternoon(4);
                            gc11 = GregorianCalendarSets.getSummerAfternoon(5);
                            gc2 = GregorianCalendarSets.getSummerAfternoon(6);
                            gc22 = GregorianCalendarSets.getSummerAfternoon(7);
                        } else {
                            gc1 = GregorianCalendarSets.getWinterAfternoon(4);
                            gc11 = GregorianCalendarSets.getWinterAfternoon(5);
                            gc2 = GregorianCalendarSets.getWinterAfternoon(6);
                            gc22 = GregorianCalendarSets.getWinterAfternoon(7);
                        }
                        break;
                    case 4:
                        if (Setting.getStateOfSeasonSwitch()) {
                            gc1 = GregorianCalendarSets.getSummerAfternoon(8);
                            gc11 = GregorianCalendarSets.getSummerAfternoon(9);
                            gc2 = GregorianCalendarSets.getSummerAfternoon(10);
                            gc22 = GregorianCalendarSets.getSummerAfternoon(11);
                        } else {
                            gc1 = GregorianCalendarSets.getWinterAfternoon(8);
                            gc11 = GregorianCalendarSets.getWinterAfternoon(9);
                            gc2 = GregorianCalendarSets.getWinterAfternoon(10);
                            gc22 = GregorianCalendarSets.getWinterAfternoon(11);
                        }
                        break;
                    default:
                        break;
                }
                /***
                 * Warning!
                 * The order of "gc1 gc11 gc2 gc22" mustn't change.
                 */
                tgcs.add(gc1);
                tgcs.add(gc11);
                tgcs.add(gc2);
                tgcs.add(gc22);
            }
        }
        return tgcs;
    }

//    private GregorianCalendar getGregorianCalendar(
//            int hourOfDay, int minute, int second) {
//        GregorianCalendar gc = new GregorianCalendar();
//        gc.set(Calendar.HOUR_OF_DAY, hourOfDay);
//        gc.set(Calendar.MINUTE, minute);
//        gc.set(Calendar.SECOND, second);
//        return gc;
//    }
}
