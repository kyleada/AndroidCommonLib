package com.kkwang.zhihuDailyPRD.inject.component;

import android.support.annotation.NonNull;

import com.kkwang.zhihuDailyPRD.base.BaseDialogFragment;
import com.kkwang.zhihuDailyPRD.base.BaseFragment;
import com.kkwang.zhihuDailyPRD.base.ZhihuBaseActivity;
import com.kkwang.zhihuDailyPRD.inject.module.AppLayerModule;

import javax.inject.Singleton;

import dagger.Component;


/**
 * @author lsxiao
 * @date 2015-11-04 00:47
 */
@Singleton
@Component(dependencies = AppLayerModule.class)
public interface AppLayerComponent {

    void inject(ZhihuBaseActivity activity);

    void inject(BaseFragment fragment);

    void inject(BaseDialogFragment dialogFragment);

    class Instance {
        private static AppLayerComponent sComponent;

        public static void init(@NonNull AppLayerComponent component) {
            sComponent = component;
        }

        public static AppLayerComponent get() {
            return sComponent;
        }
    }
}
