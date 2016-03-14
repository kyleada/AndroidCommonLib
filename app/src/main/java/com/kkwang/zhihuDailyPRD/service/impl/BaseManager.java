package com.kkwang.zhihuDailyPRD.service.impl;


import com.google.gson.Gson;
import com.kkwang.zhihuDailyPRD.inject.component.ClientApiComponent;
import com.kkwang.zhihuDailyPRD.protocol.ClientApi;

import javax.inject.Inject;


/**
 * @author lsxiao
 * @date 2015-11-03 22:28
 */
public class BaseManager {
    @Inject
    ClientApi mApi;
    @Inject
    Gson mGson;

    public BaseManager() {
        ClientApiComponent.Instance.get().inject(this);
    }

    public ClientApi getApi() {
        return mApi;
    }

    public Gson getGson() {
        return mGson;
    }
}
