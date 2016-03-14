package me.kkwang.commonlib.utils;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Set;

import me.kkwang.commonlib.base.AppContextUtil;

/**
 * Created by Administrator on 2016/3/5.
 */
public class SpUtils {

    private static SharedPreferences mSp;

    public static void init() {
        init(PreferenceManager.getDefaultSharedPreferences(AppContextUtil.getInstance()));
    }

    public static void init(SharedPreferences sp) {
        mSp = sp;
    }

    public static void clearAll() {
        mSp.edit().clear().apply();
    }
    

    public static void saveOrUpdate(String key, String value) {
        mSp.edit().putString(key, value).apply();
    }

    public static String find(String key) {
        return mSp.getString(key, null);
    }

    public static void delete(String key) {
        mSp.edit().remove(key).apply();
    }


    public static void putString(String key, String value) {
        mSp.edit().putString(key, value).apply();
        ;
    }


    public static String getString(String key, String defaultValue) {
        return mSp.getString(key, defaultValue);
    }


    public static void putInt(String key, int value) {
        mSp.edit().putInt(key, value).apply();
    }


    public static int getInt(String key, int defaultValue) {
        return mSp.getInt(key, defaultValue);
    }


    public static void putLong(String key, long value) {
        mSp.edit().putLong(key, value).apply();
    }


    public static long getLong(String key, long defaultValue) {
        return mSp.getLong(key, defaultValue);
    }


    public static void putFloat(String key, float value) {
        mSp.edit().putFloat(key, value);
    }


    public static float getFloat(String key, float defaultValue) {
        return mSp.getFloat(key, defaultValue);
    }


    public static void putBoolean(String key, boolean value) {
        mSp.edit().putBoolean(key, value);
    }


    public static void getBoolean(String key, boolean defaultValue) {
        mSp.getBoolean(key, defaultValue);
    }

    /*
    Set<String> siteno = new HashSet<String>();
    siteno.add()
     */
    public static void putStringset(String key, Set<String> stringSet) {
        mSp.edit().putStringSet(key, stringSet);
    }

    public static Set<String> getStringSet(String key, Set<String> defaultStringSet) {
        return mSp.getStringSet(key, defaultStringSet);
    }

    public static void clear() {
        clear(mSp);
    }

    public static void clear(SharedPreferences sharedPreferences) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }
}
