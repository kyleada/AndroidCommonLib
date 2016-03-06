package com.kyle.zhihuDailyPRD.inject.component;

import android.support.annotation.NonNull;

import com.kyle.zhihuDailyPRD.base.BaseActivity;
import com.kyle.zhihuDailyPRD.base.BaseDialogFragment;
import com.kyle.zhihuDailyPRD.base.BaseFragment;
import com.kyle.zhihuDailyPRD.inject.module.AppLayerModule;

import javax.inject.Singleton;

import dagger.Component;


/**
 * @author lsxiao
 * @date 2015-11-04 00:47
 */
@Singleton
@Component(dependencies = AppLayerModule.class)
public interface AppLayerComponent {

    void inject(BaseActivity activity);

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
