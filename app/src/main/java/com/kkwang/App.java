package com.kkwang;

import android.app.Application;

import com.kkwang.liteORM.LiteApp;
import com.kkwang.myapp.BuildConfig;
import com.kkwang.zhihuDailyPRD.DailyApp;

import timber.log.Timber;

/**
 * Created by Administrator on 2016/3/5.
 */
public class App extends Application {

    public final static boolean DEBUG = BuildConfig.DEBUG;

    @Override
    public void onCreate() {
        super.onCreate();
        DailyApp.init(this);
        LiteApp.init(this);

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
