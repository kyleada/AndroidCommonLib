package com.kyle.api;

import com.kyle.entity.MeizhiData;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by kw on 2016/3/4.
 */
public interface GankApi {

    @GET("/data/福利/" + ApiConfig.meizhiSize + "/{page}")
    Observable<MeizhiData> getMeizhiData(@Path("page") int page);
}
