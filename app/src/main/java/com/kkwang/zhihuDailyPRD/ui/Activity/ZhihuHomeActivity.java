package com.kkwang.zhihuDailyPRD.ui.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.kkwang.myapp.R;
import com.kkwang.zhihuDailyPRD.base.ZhihuBaseActivity;
import com.kkwang.zhihuDailyPRD.ui.Fragment.NewsListFragment;


/**
 * @author lsxiao
 * @date 2015-11-03 22:28
 */
public class ZhihuHomeActivity extends ZhihuBaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.zhprd_activity_home;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        Fragment fragment = NewsListFragment.newInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fl_container, fragment, NewsListFragment.TAG);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
//        if (!ExitClickUtil.isClickAgain()) {
//            Toast.makeText(this, "退出应用吗", Toast.LENGTH_SHORT).show();
//            return;
//        }
        super.onBackPressed();
    }
}
