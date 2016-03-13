package com.kyle.commonlib;

import android.app.Application;
import android.content.Context;

import com.jakewharton.threetenabp.AndroidThreeTen;
import com.kyle.commonlib.base.AppContextUtil;
import com.kyle.commonlib.glide.GlideManager;
import com.kyle.commonlib.utils.SpUtils;

/**
 * Created by Administrator on 2016/3/5.
 */
public class CommonLibUtils {

    /*
     *使用ApplicationContext初始化
     */

    public static void init(Context context) {

        AppContextUtil.init(context);
        //必须先完成AppContextUtil的初始化，下面部分初始化会从中提取context

        SpUtils.init();
        //Glide此处的初始化是为了配置使用全局的OkHttpClient请求
        GlideManager.initConfig(context);
        //时间模块
        AndroidThreeTen.init((Application) context.getApplicationContext());

    }
}
