package com.kyle.zhihuDailyPRD.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kyle.zhihuDailyPRD.inject.component.AppLayerComponent;
import com.kyle.zhihuDailyPRD.service.DataLayer;
import com.trello.rxlifecycle.components.support.RxFragment;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.Lazy;

/**
 * @author lsxiao
 * @date 2015-11-03 22:28
 */
public abstract class BaseFragment extends RxFragment {
    public static final String TAG = BaseFragment.class.getSimpleName();
    protected View mRootView;
    @Inject
    Lazy<DataLayer> mDataLayer;

    public BaseFragment() {
        AppLayerComponent.Instance.get().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutId(), container, false);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        afterCreate(savedInstanceState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ButterKnife.unbind(this);
    }

    public DataLayer getDataLayer() {
        return mDataLayer.get();
    }

    protected abstract int getLayoutId();

    protected abstract void afterCreate(Bundle savedInstanceState);

}
