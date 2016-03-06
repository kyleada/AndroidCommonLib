package com.kyle.zhihuDailyPRD.inject.module;

import com.kyle.zhihuDailyPRD.service.DataLayer;
import com.kyle.zhihuDailyPRD.service.impl.DailyManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


/**
 * @author lsxiao
 * @date 2015-11-04 00:44
 */
@Module
public class AppLayerModule {

    @Singleton
    @Provides
    public DataLayer provideDataLayer() {
        return new DataLayer(new DailyManager());
    }
}
