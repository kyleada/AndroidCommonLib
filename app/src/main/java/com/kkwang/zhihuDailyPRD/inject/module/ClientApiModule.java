package com.kkwang.zhihuDailyPRD.inject.module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kkwang.zhihuDailyPRD.protocol.ClientApi;
import com.kkwang.zhihuDailyPRD.protocol.GsonConverterFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.kkwang.commonlib.net.OkHttpManager;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;


/**
 * @author xiaolishang
 * @date 2015-11-03 23:28
 */
@Module
public class ClientApiModule {
    private static final String API_VERSION = "4";
    private static final String BASE_URL = "http://news-at.zhihu.com/api/4/";

    @Provides
    @Singleton
    public ClientApi provideClientApi(OkHttpClient client, Converter.Factory converterFactory) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();
        return retrofit.create(ClientApi.class);
    }

    @Provides
    @Singleton
    public HttpLoggingInterceptor providerLog() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        return interceptor;
    }


    @Provides
    @Singleton
    public Converter.Factory provideConverter(Gson gson) {
        return GsonConverterFactory.create(gson);
    }


    @Provides
    @Singleton
    public Gson provideGson() {
        return new GsonBuilder().serializeNulls().create();
    }

    @Provides
    @Singleton
    public OkHttpClient provideClient(HttpLoggingInterceptor loggingInterceptor) {
        return OkHttpManager.getInstance().getOkHttpClient();
    }
}
