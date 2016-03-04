package com.kyle.commonlib.net;

import android.content.Context;
import android.os.Build;

import com.kyle.commonlib.BuildConfig;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Authenticator;
import okhttp3.Cache;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by kw on 2016/3/4.
 * !!!!!这并不是一个真实完整的工具类，假定了采用基于Token的方式认证，如OAuth
 * 参考了 http://drakeet.me/retrofit-2-0-okhttp-3-0-config
 * Interceptor和NetworkInterceptor区别还没明白
 * http://www.jianshu.com/p/2710ed1e6b48
 */
public class OkHttpManager {

    /*
     * 采用静态内部类形式实现线程安全的懒汉式单例模式
     */

    private static  Context context;// 一定要是AppContext
    private static class SingletonHolder{
        private static final OkHttpManager INSTANCE = new OkHttpManager(context);
    }

    public static final OkHttpManager getInstance(Context appcontext){
        context = appcontext;
        return SingletonHolder.INSTANCE;
    }

    private static OkHttpClient okHttpClient;
    private static final ArrayList<Cookie> cookieStore = new ArrayList<>();




    public OkHttpClient getOkHttpClient(){
        return okHttpClient;
    }

    private OkHttpManager(Context context) {
        if (okHttpClient == null) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();

            /*
             * 如果你需要在遇到诸如 401 Not Authorised 的时候进行刷新 token，
             * 可以使用 Authenticator，这是一个专门设计用于当验证出现错误的时候，进行询问获取处理的拦截器：
             */
            Authenticator mAuthenticator = new Authenticator() {
                @Override public Request authenticate(Route route, Response response)
                        throws IOException {
                    NetCommon.sToken = TokenService.refreshToken();
                    return response.request().newBuilder()
                            .addHeader("Authorization", NetCommon.sToken)
                            .build();
                }
            };

            /*
             * 缓存目录
             */
            File baseDir = context.getCacheDir();
            if(baseDir == null){
                baseDir = context.getExternalCacheDir();
            }
            final File cacheDir = new File(baseDir, "HttpResponseCache");
            /*
             *构建
             */
            okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .retryOnConnectionFailure(true)
                    .readTimeout(NetCommon.HTTP_READ_TIMEOUT,TimeUnit.SECONDS)
                    .writeTimeout(NetCommon.HTTP_WRITE_TIMEOUT,TimeUnit.SECONDS)
                    .connectTimeout(NetCommon.HTTP_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                    .addNetworkInterceptor(new TokenInterceptor())
                    .addNetworkInterceptor(new UserAgentInterceptor())
                    .cookieJar(new CookieJar() {
                        @Override
                        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                            cookieStore.addAll(cookies);
                        }

                        @Override
                        public List<Cookie> loadForRequest(HttpUrl url) {
                            List<Cookie> result = new ArrayList<>();
                            for (Cookie cookie : cookieStore) {
                                if (cookie.matches(url)) {
                                    //此处缺少对超时的判断System.currentTimeMillis()<cookie.expireAt()
                                    result.add(cookie);
                                }
                            }
                            return result;
                        }
                    })
                    .cache(new Cache(cacheDir, NetCommon.HTTP_RESPONSE_DISK_CACHE_MAX_SIZE))
                    .build();
        }
    }

    public static String getCookie(String url) {

        HttpUrl httpUrl = HttpUrl.parse(url);
        StringBuilder cookieHeader = new StringBuilder();

        int i = 0;
        for (Cookie cookie : cookieStore) {
            if (cookie.matches(httpUrl)) {
                //此处缺少对超时的判断System.currentTimeMillis()<cookie.expireAt()
                if (i > 0) {
                    cookieHeader.append("; ");
                }
                cookieHeader.append(cookie.name()).append('=').append(cookie.value());
                i++;
            }
        }
        return cookieHeader.toString();
    }

    private boolean alreadyHasAuthorizationHeader(Request originalRequest) {

        return false;
    }


    private static class TokenInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
            if (NetCommon.sToken == null || alreadyHasAuthorizationHeader(originalRequest)) {
                return chain.proceed(originalRequest);
            }
            Request authorised = originalRequest.newBuilder()
                    .header("Authorization", NetCommon.sToken)
                    .build();
            return chain.proceed(authorised);
        }

        private boolean alreadyHasAuthorizationHeader(Request originalRequest) {

            return false;
        }
    }

    private static class UserAgentInterceptor implements Interceptor {
        private static final String USER_AGENT_HEADER_NAME = "User-Agent";
        private final String userAgentHeaderValue;

        public UserAgentInterceptor(){
            this.userAgentHeaderValue = Build.MODEL + " ; " + Build.BRAND + " ; " + Build.VERSION.SDK_INT + " ; " + BuildConfig.APPLICATION_ID
                    + " ; " + BuildConfig.VERSION_NAME +  " ; " + BuildConfig.VERSION_CODE ;
        }

        public UserAgentInterceptor(String userAgentHeaderValue) {
            this.userAgentHeaderValue = userAgentHeaderValue;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            final Request originalRequest = chain.request();
            final Request requestWithUserAgent = originalRequest.newBuilder()
                    .removeHeader(USER_AGENT_HEADER_NAME)
                    .addHeader(USER_AGENT_HEADER_NAME, userAgentHeaderValue)
                    .build();
            return chain.proceed(requestWithUserAgent);
        }
    }
}
