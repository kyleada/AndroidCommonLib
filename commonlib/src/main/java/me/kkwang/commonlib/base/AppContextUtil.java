package me.kkwang.commonlib.base;

import android.content.Context;

/**
 * Created by Administrator on 2016/3/5.
 */
public class AppContextUtil {

    private static Context sContext;

    private AppContextUtil() {

    }

    public static void init(Context context) {
        sContext = context;
    }

    public static Context getInstance() {
        if (sContext == null) {
            throw new NullPointerException("the context is null,please init AppContextUtil in Application first.");
        }
        return sContext;
    }

}
