package com.kyle.commonlib.base;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import com.kyle.commonlib.R;
import com.kyle.commonlib.utils.ViewUtils;

/**
 * Created by kw on 2016/3/11.
 */
public abstract class BaseToolBarActivity extends BaseActivity {

    protected AppBarLayout mAppBar;
    protected Toolbar mToolbar;
    protected boolean mIsHidden = false;

    protected abstract boolean isActionBarNeedBackEnable();


    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAppBar = (AppBarLayout) findViewById(R.id.app_bar_layout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        if (mToolbar == null || mAppBar == null) {
            throw new IllegalStateException("No toolbar");
        }
        setSupportActionBar(mToolbar);

        if (isActionBarNeedBackEnable()) {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //if (Build.VERSION.SDK_INT >= 21) {
        //    mAppBar.setElevation(10.6f);
        //}
        ViewCompat.setElevation(mAppBar, ViewUtils.dpToPx(4, this));


    }

    protected void setAppBarAlpha(float alpha) {
        mAppBar.setAlpha(alpha);
    }


    protected void hideOrShowToolbar() {
        mAppBar.animate()
               .translationY(mIsHidden ? 0 : -mAppBar.getHeight())
               .setInterpolator(new DecelerateInterpolator(2))
               .start();

        mIsHidden = !mIsHidden;
    }

}
