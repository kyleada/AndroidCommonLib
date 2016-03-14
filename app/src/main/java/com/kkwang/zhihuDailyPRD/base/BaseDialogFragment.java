package com.kkwang.zhihuDailyPRD.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kkwang.zhihuDailyPRD.inject.component.AppLayerComponent;
import com.kkwang.zhihuDailyPRD.service.DataLayer;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.Lazy;


/**
 * @author lsxiao
 * @date 2015-11-03 22:28
 */
public abstract class BaseDialogFragment extends DialogFragment {
    public static final String TAG = BaseDialogFragment.TAG;
    @Inject
    Lazy<DataLayer> mDataLayer;
    protected View mRootView;

    public BaseDialogFragment() {
        AppLayerComponent.Instance.get().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutId(), container, false);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
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
