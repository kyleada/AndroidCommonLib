package com.kyle.zhihuDailyPRD.base;

import android.os.Bundle;

import com.kyle.zhihuDailyPRD.inject.component.AppLayerComponent;
import com.kyle.zhihuDailyPRD.service.DataLayer;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;


/**
 * @author lsxiao
 * @date 2015-11-03 22:28
 */
public abstract class BaseActivity extends RxAppCompatActivity {
    @Inject
    DataLayer mDataLayer;

    public BaseActivity() {
        AppLayerComponent.Instance.get().inject(this);
    }

    public DataLayer getDataLayer() {
        return mDataLayer;
    }

    protected abstract int getLayoutId();

    protected abstract void afterCreate(Bundle savedInstanceState);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        afterCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
