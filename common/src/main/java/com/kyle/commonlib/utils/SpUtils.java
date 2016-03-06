package com.kyle.commonlib.utils;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.kyle.commonlib.base.AppContextUtil;

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

    public static void saveOrUpdate(String key, String json) {
        mSp.edit().putString(key, json).apply();
    }

    public static String find(String key) {
        return mSp.getString(key, null);
    }

    public static void delete(String key) {
        mSp.edit().remove(key).apply();
    }

    public static void clearAll() {
        mSp.edit().clear().apply();
    }
}
