package me.kkwang.commonlib.net;

import android.content.Context;

import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kw on 2016/3/4.
 */
public class RetrofitManager {

    private Retrofit retrofit;

    public Retrofit getRetrofitInstance(Context context, String baseUrl){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(OkHttpManager.getInstance().getOkHttpClient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();

        return retrofit;
    }

    private static class SingletonHolder{
        private static final RetrofitManager INSTANCE = new RetrofitManager();
    }

    public static final RetrofitManager getInstance(){
        return SingletonHolder.INSTANCE;
    }

    private RetrofitManager(){

    }
}
