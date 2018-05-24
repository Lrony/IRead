package com.lrony.iread.model.remote;

import com.lrony.iread.model.bean.WeatherBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Lrony on 18-5-24.
 */
public interface OtherApi {

    /**
     * 获取天气信息
     *
     * @param city 城市名
     * @return
     */
    @GET("weather/json.shtml")
    Call<WeatherBean> getWeatherInfo(@Query("city") String city);
}
