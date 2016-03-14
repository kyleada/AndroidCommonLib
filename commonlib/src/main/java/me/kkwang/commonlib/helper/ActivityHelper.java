package me.kkwang.commonlib.helper;

import android.app.Activity;

import java.util.LinkedList;

/**
 * User: lulee007@live.com
 * Date: 2016-01-01
 * Time: 11:49
 */
public class ActivityHelper {

    private static LinkedList<Activity> activities;

    private static ActivityHelper activitiesHelper;

    private ActivityHelper() {
    }

    public static ActivityHelper instance() {
        if (activitiesHelper == null) {
            activitiesHelper = new ActivityHelper();
        }
        return activitiesHelper;
    }

    public void add(Activity activity) {
        if (activities == null) {
            activities = new LinkedList<>();
        }
        activities.add(activity);
    }

    public void remove(Activity activity) {
        if (activities != null)
            activities.remove(activity);
    }

    public void finish(Class<? extends Activity> cls) {
        if (activities == null) {
            return;
        }
        for (int i = 0; i < activities.size(); i++) {
            Activity activity1 = activities.get(i);
            if (activity1.getClass().equals(cls)) {

                if (!activity1.isFinishing()) {
                    activity1.finish();
                }
                activities.remove(activity1);
                return;
            }
        }
    }

    public void finishAllBut(Class<? extends Activity> cls) {
        if (activities == null)
            return;
        for (int i = 0; i < activities.size(); i++) {
            Activity activity1 = activities.get(i);
            if (!activity1.getClass().equals(cls)) {
                i--;
                if (!activity1.isFinishing()) {
                    activity1.finish();

                }
                activities.remove(activity1);
            }
        }
    }
}
