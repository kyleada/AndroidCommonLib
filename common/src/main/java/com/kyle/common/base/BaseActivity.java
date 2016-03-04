package com.kyle.common.base;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by kw on 2016/3/1.
 */
public class BaseActivity extends AppCompatActivity {

    public void startActivity(Class target){
        Intent intent=new Intent(this,target);
        startActivity(intent);
    }
}
