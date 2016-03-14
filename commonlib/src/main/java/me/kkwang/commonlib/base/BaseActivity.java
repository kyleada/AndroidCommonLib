package me.kkwang.commonlib.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import butterknife.ButterKnife;

/**
 * Created by kw on 2016/3/1.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected abstract int getLayoutId();

    protected abstract void afterCreate(Bundle savedInstanceState);



    //ActionBar ab;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        //ab = getSupportActionBar();
        //if (ab != null && isActionBarNeedBackEnable()) {
        //    ab.setHomeButtonEnabled(true);
        //    ab.setDefaultDisplayHomeAsUpEnabled(true);
        //    ab.setDisplayHomeAsUpEnabled(true);
        //}
        afterCreate(savedInstanceState);
    }


    @Override public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }


    public void startActivityClass(Class target){
        Intent intent=new Intent(this,target);
        startActivity(intent);
    }


    @Override protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    protected void showToast(String msg) {

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(@StringRes int resId) {

        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
    }


}
