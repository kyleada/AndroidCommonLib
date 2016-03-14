package com.kkwang.zhihuDailyPRD.ui.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.kkwang.myapp.R;
import com.kkwang.zhihuDailyPRD.base.BundleKey;
import com.kkwang.zhihuDailyPRD.base.ZhihuBaseActivity;
import com.kkwang.zhihuDailyPRD.model.TodayNews;
import com.kkwang.zhihuDailyPRD.ui.Fragment.NewsDetailFragment;


public class NewsDetailActivity extends ZhihuBaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.zhprd_activity_news;
    }

    public static void start(Context context, TodayNews.Story story) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        intent.putExtra(BundleKey.STORY, story);
        context.startActivity(intent);
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        TodayNews.Story story = (TodayNews.Story) getIntent().getSerializableExtra(BundleKey.STORY);
        showNewsFragment(story);
    }

    private void showNewsFragment(TodayNews.Story story) {
        Fragment fragment = NewsDetailFragment.newInstance(story);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.rl_news_container, fragment, NewsDetailFragment.TAG);
        ft.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
