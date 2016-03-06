package com.kyle.zhihuDailyPRD.inject.component;


import com.kyle.zhihuDailyPRD.inject.module.ClientApiModule;
import com.kyle.zhihuDailyPRD.service.impl.BaseManager;

import javax.inject.Singleton;

import dagger.Component;


/**
 * @author lsxiao
 * @date 2015-11-03 22:28
 */
@Singleton
@Component(modules = ClientApiModule.class)
public interface ClientApiComponent {

    void inject(BaseManager manager);

    class Instance {
        private static ClientApiComponent sComponent;

        public static void init(ClientApiComponent component) {
            sComponent = component;
        }

        public static ClientApiComponent get() {
            return sComponent;
        }
    }

}
