package com.kyle.zhihuDailyPRD.service;


import com.kyle.zhihuDailyPRD.model.News;
import com.kyle.zhihuDailyPRD.model.StartImage;
import com.kyle.zhihuDailyPRD.model.TodayNews;

import rx.Observable;

/**
 * @author lsxiao
 * @date 2015-11-03 22:28
 */
public class DataLayer {
    DailyService mDailyService;

    public DataLayer(DailyService dailyService) {
        mDailyService = dailyService;
    }

    public DailyService getDailyService() {
        return mDailyService;
    }

    public interface DailyService {

        /**
         * 获取最新日报新闻列表
         *
         * @return TodayNews
         */
        Observable<TodayNews> getTodayNews();

        /**
         * 获取启动图片
         *
         * @return StartImage
         */
        Observable<StartImage> getStartImage();

        /**
         * 获取新闻
         *
         * @param newsId long
         * @return News
         */
        Observable<News> getNews(long newsId);

        /**
         * 获取本地新闻
         *
         * @param id string
         * @return News
         */
        Observable<News> getLocalNews(final String id);


        /**
         * 获取本地今日热文
         *
         * @param date string
         * @return TodayNews
         */
        Observable<TodayNews> getLocalTodayNews(final String date);


        /**
         * 缓存新闻
         *
         * @param news News
         * @return Void
         */
        void cacheNews(final News news);


        /**
         * 获取本地新闻
         *
         * @param todayNews TodayNews
         * @return Void
         */
        void cacheTodayNews(final TodayNews todayNews);
    }
}
