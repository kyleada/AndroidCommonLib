package com.kyle.zhihuDailyPRD;

import android.app.Application;
import android.content.Context;
import android.preference.PreferenceManager;

import com.kyle.commonlib.base.AppContextUtil;
import com.kyle.zhihuDailyPRD.inject.component.AppLayerComponent;
import com.kyle.zhihuDailyPRD.inject.component.DaggerAppLayerComponent;
import com.kyle.zhihuDailyPRD.service.impl.DataLayerManager;
import com.kyle.zhihuDailyPRD.util.SpUtil;


/**
 * @author lsxiao
 * @date 2015-11-03 22:24
 */
public class DailyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public static void init(Context context) {

        AppContextUtil.init(context);
        SpUtil.init(PreferenceManager.getDefaultSharedPreferences(context));
        DataLayerManager.init();
        AppLayerComponent.Instance.init(DaggerAppLayerComponent.builder().build());
    }
}
