package com.modeset.activity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import com.modeset.R;
import com.modeset.model.GregorianCalendarSets;
import com.modeset.model.Setting;
import com.modeset.service.CancelService;
import com.modeset.util.FileUtil;
import com.modeset.util.LogUtil;
import com.modeset.util.MyApplication;

import java.util.Calendar;
import java.util.GregorianCalendar;

/***
 * @author JackShen
 */
public class MainActivity extends AppCompatActivity implements
        View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private Button submitButton;
    private Button cancelButton;
    private Button alarmShutdownButton;//定时关机按钮
    private CheckBox[] checkBoxs;
    private boolean[] results;
    private Switch seasonSwitch;
    private Switch modelSwitch;
    private CheckBox chooseAll;
    private Button guideButton;
    private Button autoSetButton;

    /***
     * true -> vibrate && false -> silence
     */
    private boolean model = true;

    /***
     * true -> summer  && false -> winter
     */
    private boolean season = true;

    /***
     * chooseAllFlag = false : choose all checkbox
     */
    private boolean chooseAllFlag;
    /***
     * isToStartResultHandler = true,
     * it can start ResultsHandler.
     */
    private boolean isToStartResultHandler;

    private PowerManager.WakeLock wakeLock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        /***
         * 每次启动成功后，指定定时任务 -> 大概在每天的晚上9点35将存储取消信息的文件清空
         * 并且，此任务不可取消。超过了21:35，每次启动都会执行此方法，也就形成了在21:35后打开
         * 此应用的时候都会收到"祝您晚安"的提示语的现象。
         */
        AlarmManager alarmManager =
                (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(MainActivity.this, CancelService.class);
        PendingIntent pi = PendingIntent.getService(MainActivity.this,
                1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        GregorianCalendar mGc =
                GregorianCalendarSets.getGregorianCalendar(21, 35, 0);
        if (Build.VERSION.SDK_INT >= 19) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, mGc.getTimeInMillis(), pi);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, mGc.getTimeInMillis(), pi);
        }

//        PowerManager pM = (PowerManager)
//                getSystemService(MyApplication.getContext().POWER_SERVICE);
//        wakeLock = pM.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "CPU");
//        wakeLock.acquire();

        autoSetButton = (Button) findViewById(R.id.auto_set);
        autoSetButton.setOnClickListener(this);

        alarmShutdownButton = (Button) findViewById(R.id.alarm_shutdown);
        alarmShutdownButton.setOnClickListener(this);

        guideButton = (Button) findViewById(R.id.guide);
        guideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Guide.class);
                startActivity(intent);
            }
        });
        chooseAllFlag = false;
        chooseAll = (CheckBox) findViewById(R.id.chooseAll);
        chooseAll.setOnCheckedChangeListener(this);

        isToStartResultHandler = false;
        cancelButton = (Button) findViewById(R.id.cancel);
        cancelButton.setOnClickListener(this);

        submitButton = (Button) findViewById(R.id.submit);
        submitButton.setOnClickListener(this);

        seasonSwitch = (Switch) findViewById(R.id.season);

        /***
         * to set the state of seasonSwitch automatic
         * and the number of month is based 0.
         */
        GregorianCalendar gc = new GregorianCalendar();
        if (!(gc.get(Calendar.MONTH) >= 4 && gc.get(Calendar.MONTH) <= 8)) {
            seasonSwitch.setChecked(false);
            season = false;
        }
        seasonSwitch.setOnCheckedChangeListener(this);

        modelSwitch = (Switch) findViewById(R.id.model);
        modelSwitch.setOnCheckedChangeListener(this);

        checkBoxs = new CheckBox[5];
        checkBoxs[0] = (CheckBox) findViewById(R.id.class1_2);
        checkBoxs[1] = (CheckBox) findViewById(R.id.class3_4);
        checkBoxs[2] = (CheckBox) findViewById(R.id.class5_6);
        checkBoxs[3] = (CheckBox) findViewById(R.id.class7_8);
        checkBoxs[4] = (CheckBox) findViewById(R.id.class9_10);
        initializeCheckBoxs();
    }

    private void initializeCheckBoxs() {
        GregorianCalendar gcc = new GregorianCalendar();
        int hour = gcc.get(Calendar.HOUR_OF_DAY);
        int minute = gcc.get(Calendar.MINUTE);

        if (hour > 9 || (hour == 9 && minute >= 50)) {
            setCheckBoxEnabled(0);
        }

        if (hour >= 12) {
            setCheckBoxEnabled(1);
        }

        if (seasonSwitch.isChecked()) {//summer

            if (hour > 16 || (hour == 16 && minute >= 20)) {
                setCheckBoxEnabled(2);
            }

            if (hour > 18 || (hour == 18 && minute >= 30)) {
                setCheckBoxEnabled(3);
            }

            if (hour > 21 || (hour == 21 && minute >= 20)) {
                setCheckBoxEnabled(4);
            }
        } else {//winter
            if (hour > 15 || (hour == 15 && minute >= 50)) {
                setCheckBoxEnabled(2);
            }

            if (hour >= 18) {
                setCheckBoxEnabled(3);
            }

            if (hour > 20 || (hour == 20 && minute >= 50)) {
                setCheckBoxEnabled(4);
            }
        }

    }

    private void setCheckBoxEnabled(int index) {
        checkBoxs[index].setEnabled(false);
        checkBoxs[index].setText("时间已过");
        checkBoxs[index].setTextColor(Color.RED);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                results = getResults();
                if (isToStartResultHandler) {
                    isToStartResultHandler = false;
                    Setting.setStateOfSeasonSwitch(season);
                    Setting.setStateOfModelSwitch(model);
                    ResultsHandler.actionStart(this, results);
                } else {
                    showWarning();
                }
                break;
            case R.id.cancel:
                cancelAllByFileUtil();
                break;
            case R.id.alarm_shutdown:
                showTimePickerDialog();
                break;
            case R.id.auto_set:
                AutoSetActivity.actionStart(this);
                break;
            default:
                break;
        }
    }

    private void showTimePickerDialog() {
        Calendar c = Calendar.getInstance();
        new TimePickerDialog(
                MainActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view,
                                          int hourOfDay, int minute) {
                        Intent intent =
                                new Intent("com.modeset.alarmshutdown");
                        PendingIntent pi = PendingIntent.
                                getBroadcast(MyApplication.getContext(),
                                        120, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                        AlarmManager alarmManger = (AlarmManager)
                                getSystemService(ALARM_SERVICE);
                        GregorianCalendar mGc =
                                GregorianCalendarSets.getGregorianCalendar(hourOfDay, minute, 0);
                        if (Build.VERSION.SDK_INT >= 19) {
                            alarmManger.setExact(AlarmManager.RTC_WAKEUP, mGc.getTimeInMillis(), pi);
                        } else {
                            alarmManger.set(AlarmManager.RTC_WAKEUP, mGc.getTimeInMillis(), pi);
                        }

                        Toast.makeText(MainActivity.this,
                                "设置成功",
                                Toast.LENGTH_SHORT).show();
                    }
                },
                c.get(Calendar.HOUR_OF_DAY),
                c.get(Calendar.MINUTE),
                true
        ).show();
    }

    private void cancelAllByFileUtil() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        boolean flag = false;
        for (int i = 0; i <= 19; i++) {
            int index = FileUtil.getIndex(String.valueOf(i));
            LogUtil.d("int_result_index", index + "");
            if (index != 20) {
                flag = true;
                Intent intent = FileUtil.getIntent(String.valueOf(index));
                PendingIntent pi = PendingIntent.getBroadcast
                        (MyApplication.getContext(), index, intent,
                                PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.cancel(pi);
            }
        }

//        if (flag) {
//            Toast.makeText(this, "已成功取消", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(this, "无取消项！", Toast.LENGTH_SHORT).show();
//        }

        /***
         * 以下代码待优化
         */
        boolean flag2 = false;
        for (int j = 199; j <= 200; j++) {
            int index = FileUtil.getIndex(String.valueOf(j));
            if (index != 20) {
                flag2 = true;
                Intent intent = FileUtil.getIntent(String.valueOf(index));
                PendingIntent pi = PendingIntent.getBroadcast
                        (MyApplication.getContext(), index, intent,
                                PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.cancel(pi);
            }
        }

        if (flag2 || flag) {
            Toast.makeText(this, "已成功取消", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "无取消项！", Toast.LENGTH_SHORT).show();
        }

        flag=false;
        flag2 = false;
        FileUtil.clearAll();
        onRestart();
    }

    private void showWarning() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setCancelable(true);
        alertDialog.setTitle("提醒！");
        alertDialog.setMessage("亲，当没有选中任何内容时，是不允许提交的。");
        alertDialog.setPositiveButton("我知道了",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onResume();
                    }
                });
        alertDialog.show();
    }

    private boolean[] getResults() {
        boolean[] rt = new boolean[5];
        for (int i = 0; i < 5; i++) {
            rt[i] = checkBoxs[i].isChecked();
            if (rt[i]) {
                isToStartResultHandler = true;
            }
        }
        return rt;
    }

    /***
     * 当开关状态未发生变化时，此方法是不会被调用的。
     *
     * @param buttonView 开关
     * @param isChecked  开关此时的状态-打开：true 关闭：false
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.season:
                /***
                 * to set state of seasonSwitch
                 */
//                Setting.setStateOfSeasonSwitch(isChecked);
                season = isChecked;
                LogUtil.d("seasonandmodel", "season is " + season);
                break;
            case R.id.model:
                /***
                 * to set state of modelSwitch
                 */
//                Setting.setStateOfModelSwitch(isChecked);
                model = isChecked;
                LogUtil.d("seasonandmodel", "model is " + model);
                break;
            case R.id.chooseAll:
                chooseAllFlag = (chooseAllFlag == false) ? true : false;
                toChooseAll(chooseAllFlag);
                break;
            default:
                break;
        }
    }

    private void toChooseAll(boolean flag) {
        for (int i = 0; i < checkBoxs.length; i++) {
            if (checkBoxs[i].isEnabled()) {
                checkBoxs[i].setChecked(flag);
            }
        }
    }


    /***
     * 为了测试用的
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
//        wakeLock.release();
        LogUtil.d("mydestroy", "destroy");
    }
}
