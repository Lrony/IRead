package com.lrony.iread.model.remote;

import com.lrony.iread.model.bean.WYHotBean;
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
     * 网上找的临时接口，请勿详细接口返回的任何广告信息
     *
     * @param city 城市名
     * @return
     */
    @GET("weather/json.shtml")
    Call<WeatherBean> getWeatherInfo(@Query("city") String city);

    @GET("music/wy.php")
    Call<WYHotBean> getWYHotData();
}
