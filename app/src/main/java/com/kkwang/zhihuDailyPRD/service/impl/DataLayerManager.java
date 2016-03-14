package com.kkwang.zhihuDailyPRD.service.impl;


import com.kkwang.zhihuDailyPRD.inject.component.ClientApiComponent;
import com.kkwang.zhihuDailyPRD.inject.component.DaggerClientApiComponent;

/**
 * @author lsxiao
 * @date 2015-11-03 22:28
 */
public class DataLayerManager {
    public static void init() {
        ClientApiComponent.Instance.init(DaggerClientApiComponent.builder().build());
    }
}
