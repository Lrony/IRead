package com.lrony.iread.model.remote;

import com.lrony.iread.model.bean.WeatherBean;
import com.lrony.iread.model.bean.packages.RecommendBookPackage;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Lrony on 18-5-24.
 */
public interface OhterApi {

    /**
     * 获取天气信息
     *
     * @param city 城市名
     * @return
     */
    @GET("/open/api/weather/json.shtml")
    Single<WeatherBean> getWeatherInfo(@Query("city") String city);
}
