package me.kkwang.commonlib.utils;

/**
 * Created by Administrator on 2016/3/5.
 */
public class ClickUtils {

    private static long lastClickTime;

    public static boolean isClickAgain() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 1500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    private static long lastFastClickTime;

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastFastClickTime;
        if (0 < timeD && timeD < 400) {
            return true;
        }
        lastFastClickTime = time;
        return false;
    }
}
