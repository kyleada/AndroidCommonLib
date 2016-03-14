package com.kkwang.zhihuDailyPRD;

import android.app.Application;
import android.content.Context;
import android.preference.PreferenceManager;

import com.kkwang.zhihuDailyPRD.inject.component.AppLayerComponent;
import com.kkwang.zhihuDailyPRD.inject.component.DaggerAppLayerComponent;
import com.kkwang.zhihuDailyPRD.service.impl.DataLayerManager;
import com.kkwang.zhihuDailyPRD.util.SpUtil;

import me.kkwang.commonlib.base.AppContextUtil;


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
