/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.kkwang.commonlib.utils;

import android.content.Context;
import android.widget.Toast;

import me.kkwang.commonlib.base.AppContextUtil;

public class ToastUtils {

    private ToastUtils() {}

    public static void show(CharSequence text, int duration, Context context) {
        Toast.makeText(context, text, duration).show();
    }
    public static void show(int resId, int duration, Context context) {
        show(context.getText(resId), duration, context);
    }

    public static void show(CharSequence text, Context context) {
        show(text, Toast.LENGTH_SHORT, context);
    }

    public static void show(CharSequence text) {
        show(text, Toast.LENGTH_SHORT, AppContextUtil.getInstance());
    }

    public static void show(int resId, Context context) {
        show(context.getText(resId), context);
    }

    public static void show(int resId) {
        show(resId, Toast.LENGTH_SHORT, AppContextUtil.getInstance());
    }
}
