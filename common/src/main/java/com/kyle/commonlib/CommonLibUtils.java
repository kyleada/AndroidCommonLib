package com.kyle.commonlib;

import android.content.Context;

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
        GlideManager.initConfig(context);

    }
}
