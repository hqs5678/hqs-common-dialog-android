package com.hqs.common.utils;

/**
 * Created by apple on 16/9/27.
 */

public class Log {

    public static boolean enable = true;
    public static String tag = "---log---";

    // log
    public static void print(String logString) {
        if (enable == false){
            return;
        }
        if(logString == null){
            logString = "null";
        }
        android.util.Log.e(tag, logString);
    }

    public static void print(int log) {

        if (enable == false){
            return;
        }
        android.util.Log.e(tag, log + "");
    }

    public static void print(float log) {

        if (enable == false){
            return;
        }
        android.util.Log.e(tag, log + "");
    }

    public static void print(double log) {

        if (enable == false){
            return;
        }
        android.util.Log.e(tag, log + "");
    }

    public static void print(Object log) {

        if (enable == false){
            return;
        }
        if (log == null){
            android.util.Log.e(tag, "null");
        }
        else{
            android.util.Log.e(tag, log.toString());
        }
    }

}
