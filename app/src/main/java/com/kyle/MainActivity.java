package com.kyle;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.kyle.commonlib.base.BaseToolBarActivity;
import com.kyle.liteORM.LiteActivity;
import com.kyle.myapp.R;
import com.kyle.myapp.auth.ui.LoginActivity;
import com.kyle.recyclerViewHeaderFooter.RVMainActivity;
import com.kyle.tab.TabActivity;
import com.kyle.zhihuDailyPRD.ui.Activity.ZhihuHomeActivity;

public class MainActivity extends BaseToolBarActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected int getAppBarLayoutId() {
        return R.id.app_bar_layout;
    }

    @Override
    protected int getToolbarLayoutId() {
        return R.id.toolbar;
    }

    @Override
    protected boolean isActionBarNeedBackEnable() {
        return false;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(
                R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action",
                        Snackbar.LENGTH_LONG).setAction("Action", null).show();
                startActivityClass(TabActivity.class);
            }
        });
    }


    @Override public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickToTabActivity(View view) {
        startActivityClass(TabActivity.class);
    }

    public void onClickToZhihuDaily(View view) {
        startActivityClass(ZhihuHomeActivity.class);
    }

    public void onClickLiteORM(View view) {
        startActivityClass(LiteActivity.class);
    }

    public void onClickRecyclerView(View view) {
        startActivityClass(RVMainActivity.class);
    }

    public void onClickLogin(View view) {
        startActivityClass(LoginActivity.class);
    }
}
