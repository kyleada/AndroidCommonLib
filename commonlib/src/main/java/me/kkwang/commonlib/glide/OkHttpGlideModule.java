package me.kkwang.commonlib.glide;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.GlideModule;

import java.io.InputStream;

/**
 * Created by kw on 2016/3/4.
 */
public class OkHttpGlideModule  implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        // Do nothing.
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        glide.register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory());
    }
}
