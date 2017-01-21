package com.hqs.common.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by apple on 2016/10/14.
 */

public class SharedPreferenceUtil {

    private static SharedPreferences sharedPreferences;

    public static void initSharedPreference(Context context){
        sharedPreferences = context.getSharedPreferences("sp_fstm", MODE_PRIVATE);
    }


    // string
    public static void set(String key, String value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String get(String key, String defaultValue){
        return sharedPreferences.getString(key, defaultValue);
    }

    // boolean
    public static void set(String key, boolean value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean get(String key, boolean defaultValue){
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    // int
    public static void set(String key, int value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static int get(String key, int defaultValue){
        return sharedPreferences.getInt(key, defaultValue);
    }

    // float
    public static void set(String key, float value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(key, value);
        editor.commit();
    }

    public static float get(String key, float defaultValue){
        return sharedPreferences.getFloat(key, defaultValue);
    }

    //long
    public static void set(String key, long value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(key, value);
        editor.commit();
    }

    public static long get(String key, long defaultValue){
        return sharedPreferences.getLong(key, defaultValue);
    }

    // getall
    public static Map<String, ?> getAll(){
        return sharedPreferences.getAll();
    }

    // string set
    public static void set(String key, Set<String> value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(key, value);
        editor.commit();
    }

    public static Set<String> get(String key){
        return sharedPreferences.getStringSet(key, null);
    }

    public static void removeKey(String key){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.commit();
    }


}
