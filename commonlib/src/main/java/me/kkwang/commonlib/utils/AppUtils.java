/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.kkwang.commonlib.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class AppUtils {

    public static PackageInfo getPackageInfo(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            // Should not happen.
            throw new RuntimeException(e);
        }
    }

    public static int getStatusBarHeight(Context context) {
        int result = ViewUtils.dpToPxInt(25,context);
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

}
