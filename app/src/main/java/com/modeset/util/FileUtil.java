package com.modeset.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by JackShen on 2016/5/21.
 */
public class FileUtil {
    private static String FILENAME = "data";
    private static SharedPreferences sp = MyApplication.getContext()
            .getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
    private static SharedPreferences.Editor editor = sp.edit();
    private static final String FLAG = "flag";

    /***
     * SWITCH=false 不允许向日志文件中写入；
     * SWITCH=true 允许向日志文件中写入；
     * 详情见 addToFile()。
     */
    private static final boolean SWITCH = false;
//    private static final boolean SWITCH = true;

    public static boolean write(int index, String value) {
        try {
            editor.putInt(String.valueOf(index), index);
            editor.putString(String.valueOf(index) + FLAG, value);
            editor.commit();//不能忘了提交
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static int getIndex(String key) {
        int result = sp.getInt(key, 20);
        LogUtil.d("int_result_fileutil", result + "");
        return result;
    }

    public static Intent getIntent(String key) {

        return new Intent(sp.getString(key + FLAG, null));
    }

    public static boolean clearAll() {
        try {
            editor.clear();
            editor.commit();//即使是删除也不能忘了提交
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void addToFile(String content, final String fileName) {
        if (SWITCH) {
            content += "\n";
            // 判断SD卡是否存在，并且本程序是否拥有SD卡的权限
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

                // 获得SD卡的根目录
                File sdCardPath = Environment.getExternalStorageDirectory();

                File file = new File(sdCardPath, fileName);
                // 初始化文件输出流
                FileOutputStream fileOutputStream;
                OutputStreamWriter writer;
                // 以追加模式打开文件输出流(第二个参数设为true为追加模式)
                try {
                    fileOutputStream = new FileOutputStream(file, true);
                    /***
                     * 设置为 UTF-8 编码，防止中文乱码。
                     */
                    writer = new OutputStreamWriter(fileOutputStream, "UTF-8");
                    writer.write(content);
                    /**
                     * 不能忘了关闭文件输出流。
                     */
                    writer.close();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
