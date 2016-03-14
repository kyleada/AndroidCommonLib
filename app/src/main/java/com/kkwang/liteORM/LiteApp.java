package com.kkwang.liteORM;

import android.app.Application;
import android.content.Context;

import com.kkwang.myapp.BuildConfig;
import com.litesuits.orm.LiteOrm;

/**
 * Created by kw on 2016/3/8.
 */
public class LiteApp extends Application {

    private static final String DB_NAME = "gank.db";
    public static Context sContext;
    public static LiteOrm sDb;
    @Override public void onCreate() {
        super.onCreate();
        init(this);
    }

    public static void init(Context context){
        sContext = context;
        sDb = LiteOrm.newSingleInstance(context, DB_NAME);
        if (BuildConfig.DEBUG) {
            sDb.setDebugged(true);
        }
    }
}
