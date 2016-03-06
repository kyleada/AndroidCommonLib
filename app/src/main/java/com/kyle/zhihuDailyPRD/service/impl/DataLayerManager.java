package com.kyle.zhihuDailyPRD.service.impl;


import com.kyle.zhihuDailyPRD.inject.component.ClientApiComponent;
import com.kyle.zhihuDailyPRD.inject.component.DaggerClientApiComponent;

/**
 * @author lsxiao
 * @date 2015-11-03 22:28
 */
public class DataLayerManager {
    public static void init() {
        ClientApiComponent.Instance.init(DaggerClientApiComponent.builder().build());
    }
}
