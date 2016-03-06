package com.kyle;

import android.app.Application;

import com.kyle.zhihuDailyPRD.DailyApp;

/**
 * Created by Administrator on 2016/3/5.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DailyApp.init(this);
    }
}
