package com.modeset.activity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.modeset.R;
import com.modeset.model.GregorianCalendarSets;
import com.modeset.util.FileUtil;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by JackShen on 2016/6/18.
 */
public class AutoSetActivity extends Activity implements View.OnClickListener {
    private Button setStartTime;
    private Button setEndTime;
    private final String startBroadcast = "android.com.setvibratemodel";
    private final String endBroadcast = "android.com.setnormalmodel";
    private String broadcast;
    private int requestCode;
    private MTime mTime = new MTime();

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, AutoSetActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.auto_set);

        setStartTime = (Button) findViewById(R.id.set_start_time);
        setStartTime.setOnClickListener(this);
        setEndTime = (Button) findViewById(R.id.set_end_time);
        setEndTime.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.set_start_time:
                broadcast = startBroadcast;
                requestCode = 199;
                setTimeByTimePicker();
                break;
            case R.id.set_end_time:
                broadcast = endBroadcast;
                requestCode = 200;
                setTimeByTimePicker();
                break;
            default:
                break;
        }
    }

    private void setTimeByTimePicker() {
        Calendar calendar = Calendar.getInstance();
        final TimePickerDialog tpg = new TimePickerDialog(
                AutoSetActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, final int hourOfDay, final int minute) {
                        mTime.setHour(hourOfDay);
                        mTime.setMinute(minute);

                        Log.d("testTime", mTime.getHour() + ":" + mTime.getMinute());

                        Intent intent = new Intent(broadcast);
                        PendingIntent pi = PendingIntent.getBroadcast(AutoSetActivity.this,
                                requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                        GregorianCalendar gc = GregorianCalendarSets.
                                getGregorianCalendar(mTime.getHour(), mTime.getMinute(), 0);
                        AlarmManager alarmManger = (AlarmManager) getSystemService(ALARM_SERVICE);
                        if (Build.VERSION.SDK_INT >= 19) {
                            alarmManger.setExact(AlarmManager.RTC_WAKEUP, gc.getTimeInMillis(), pi);
                        } else {
                            alarmManger.set(AlarmManager.RTC_WAKEUP, gc.getTimeInMillis(), pi);
                        }
                        Toast.makeText(AutoSetActivity.this,
                                "您设置的时间是 " + mTime.getHour() + ":" + mTime.getMinute(),
                                Toast.LENGTH_SHORT)
                                .show();
                        FileUtil.write(requestCode, broadcast);
                    }
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
        );
        tpg.setTitle("请选择时间");
        tpg.setCancelable(true);
//        tpg.setButton(DialogInterface.BUTTON_POSITIVE, "完成",
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Intent intent = new Intent(broadcast);
//                        PendingIntent pi = PendingIntent.getBroadcast(AutoSetActivity.this,
//                                requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//                        GregorianCalendar gc = GregorianCalendarSets.
//                                getGregorianCalendar(mTime.getHour(), mTime.getMinute(), 0);
//                        AlarmManager alarmManger = (AlarmManager) getSystemService(ALARM_SERVICE);
////                        Log.d("testTime", mHour + ":" + mMinute);
//                        if (Build.VERSION.SDK_INT >= 19) {
//                            alarmManger.setExact(AlarmManager.RTC_WAKEUP, gc.getTimeInMillis(), pi);
//                        } else {
//                            alarmManger.set(AlarmManager.RTC_WAKEUP, gc.getTimeInMillis(), pi);
//                        }
//                        Toast.makeText(AutoSetActivity.this,
//                                "您设置的时间是 " + mTime.getHour() + ":" + mTime.getMinute(),
//                                Toast.LENGTH_SHORT)
//                                .show();
//                        FileUtil.write(requestCode, broadcast);
//                    }
//                });

//        tpg.setButton(DialogInterface.BUTTON_NEGATIVE, "取消",
//                new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        if (which == Dialog.BUTTON_NEGATIVE) {
//
//                        }
//                    }
//                });

        tpg.show();
    }
}

class MTime {
    private int hour;
    private int minute;

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
}