package me.kkwang.commonlib.glide;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;

import java.io.InputStream;

import me.kkwang.commonlib.net.OkHttpManager;

/**
 * Created by kw on 2016/3/4.
 * 参考 https://github.com/bumptech/glide/wiki/Integration-Libraries
 * 直接把官方的集成库三个文件拷贝到了本地，因为通过gradle引入报错
 * 注意proguard
 * -keep class com.kyle.common.glide.OkHttpGlideModule
 * -keep public class * implements com.bumptech.glide.module.GlideModule
 *
 * http://blog.csdn.net/fandong12388/article/details/46372255
 *  Glide默认是用InternalCacheDiskCacheFactory类来创建硬盘缓存的，这个类会在应用的内部缓存目录下面创建一个最大容量250MB的缓存文件夹，
 *  使用这个缓存目录而不用sd卡，意味着除了本应用之外，其他应用是不能访问缓存的图片文件的。
 */
public class GlideManager {

    public static void initConfig(Context context){

        Glide.get(context).register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(OkHttpManager.getInstance().getOkHttpClient()));
    }

}
