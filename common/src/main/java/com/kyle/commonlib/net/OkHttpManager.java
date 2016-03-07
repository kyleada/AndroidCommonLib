package com.kyle.commonlib.net;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.kyle.commonlib.BuildConfig;
import com.kyle.commonlib.base.AppContextUtil;
import com.kyle.commonlib.utils.NetUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import okhttp3.Authenticator;
import okhttp3.Cache;
import okhttp3.CacheControl;
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

    private static final String TAG = "OkHttpManager";

    /*
     * 采用静态内部类形式实现线程安全的懒汉式单例模式
     */

    private static  Context context;// 一定要是AppContext
    private static class SingletonHolder{
        private static final OkHttpManager INSTANCE = new OkHttpManager(context);
    }

    public static final OkHttpManager getInstance() {
        context = AppContextUtil.getInstance();
        return SingletonHolder.INSTANCE;
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

            /*
             *调试时用
             */
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            /*
             * 如果你需要在遇到诸如 401 Not Authorised 的时候进行刷新 token，
             * 可以使用 Authenticator，这是一个专门设计用于当验证出现错误的时候，进行询问获取处理的拦截器：
             */
            //Authenticator mAuthenticator = new Authenticator() {
            //    @Override public Request authenticate(Route route, Response response)
            //            throws IOException {
            //        NetCommon.sToken = TokenService.refreshToken();
            //        return response.request().newBuilder()
            //                .addHeader("Authorization", NetCommon.sToken)
            //                .build();
            //    }
            //};

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

                    .retryOnConnectionFailure(true)
                    .readTimeout(NetCommon.HTTP_READ_TIMEOUT,TimeUnit.SECONDS)
                    .writeTimeout(NetCommon.HTTP_WRITE_TIMEOUT,TimeUnit.SECONDS)
                    .connectTimeout(NetCommon.HTTP_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                    .addInterceptor(loggingInterceptor)
                    .addInterceptor(new NoNetLoadFromCacheInterceptor())
                    .addNetworkInterceptor(new TokenInterceptor())
                    .authenticator(new Authenticator() {
                        @Override
                        public Request authenticate(Route route, Response response)
                                throws IOException {
                            return null;
                        }
                    })
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

    public static SSLSocketFactory setCertificates(InputStream... certificates) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            int index = 0;
            for (InputStream certificate : certificates) {
                String certificateAlias = Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));

                try {
                    if (certificate != null)
                        certificate.close();
                } catch (IOException e) {
                }
            }

            SSLContext sslContext = SSLContext.getInstance("TLS");

            TrustManagerFactory trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());

            trustManagerFactory.init(keyStore);
            sslContext.init
                    (
                            null,
                            trustManagerFactory.getTrustManagers(),
                            new SecureRandom()
                    );
            return sslContext.getSocketFactory();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    private static class MyAuthenticator implements Authenticator {
        @Override public Request authenticate(Route route, Response response)
                throws IOException {
            NetCommon.sToken = TokenService.refreshToken();
            return response.request().newBuilder()
                           .addHeader("Authorization", NetCommon.sToken)
                           .build();
        }
    }

    private static class ForceRewriteCacheCtrl implements Interceptor {

        @Override public Response intercept(Interceptor.Chain chain) throws IOException {

            okhttp3.Request request = chain.request();
            if(!NetUtils.isNetworkConnected()){
                request = request.newBuilder()
                                 .cacheControl(CacheControl.FORCE_CACHE)
                                 .build();
                Log.d(TAG, "intercept: no net");
            }
            Response originalResponse = chain.proceed(request);
            if(NetUtils.isNetworkConnected()){
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                //String cacheControl = request.cacheControl().toString();
                //return originalResponse.newBuilder()
                //                       .header("Cache-Control", cacheControl)
                //                       .removeHeader("Pragma")
                //                       .build();

                return originalResponse;
            }else{
                return originalResponse.newBuilder()
                                       .header("Cache-Control", "public, only-if-cached, max-stale=2419200")
                                       .removeHeader("Pragma")
                                       .build();
            }
        }
    }

    private static class NoNetLoadFromCacheInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetUtils.isNetworkConnected()) {//判断网络连接状况
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)//无网络时只从缓存中读取
                        .build();
                Log.i(TAG, "intercept: NoNetLoadFromCacheInterceptor : FORCE_CACHE");
            }
            return chain.proceed(request);
        }
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
