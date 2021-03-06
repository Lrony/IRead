package com.lrony.iread.presentation.main;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lrony.iread.R;
import com.lrony.iread.model.bean.WeatherBean;
import com.lrony.iread.model.remote.OtherApi;
import com.lrony.iread.pref.Constant;
import com.lrony.iread.util.KLog;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Lrony on 18-5-24.
 * 更新天气
 */
public class WeatherExecutor {

    private static final String TAG = "WeatherExecutor";

    @BindView(R.id.ll_weather)
    LinearLayout mWeatherView;
    @BindView(R.id.iv_weather_icon)
    ImageView mIvIcon;
    @BindView(R.id.tv_weather_temp)
    TextView mTvTemp;
    @BindView(R.id.tv_weather_city)
    TextView mTvCity;
    @BindView(R.id.tv_weather_wind)
    TextView mTvWind;

    private Context mContext;

    private OtherApi otherApi;

    public WeatherExecutor(Context context, View navigationHeader) {
        mContext = context.getApplicationContext();
        ButterKnife.bind(this, navigationHeader);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.API_OTHER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        otherApi = retrofit.create(OtherApi.class);
    }

    public void execute(String city) {
        KLog.d(TAG, "execute");
        Call<WeatherBean> call = otherApi.getWeatherInfo(city);
        call.enqueue(new Callback<WeatherBean>() {
            @Override
            public void onResponse(Call<WeatherBean> call, Response<WeatherBean> response) {
                KLog.d(TAG, response.body().getStatus() + "");
                if (response.body().getStatus() == 200) {
                    mTvCity.setText(response.body().getCity());
                    mTvTemp.setText(response.body().getData().getWendu() + "°");
                    mTvWind.setText(response.body().getData().getQuality() + " PM2.5: "
                            + response.body().getData().getPm25() + " 湿度: " + response.body().getData().getShidu());
                    // 数据加载完成后显示
                    // mWeatherView.setVisibility(View.VISIBLE);
                } else {
                    KLog.d(TAG, "接口信息获取失败");
                    mTvTemp.setVisibility(View.INVISIBLE);
                    mTvCity.setText(city);
                    mTvWind.setText("愿你拥有比阳光明媚的心情");
                }

            }

            @Override
            public void onFailure(Call<WeatherBean> call, Throwable t) {
                KLog.d(TAG, "加载失败");
            }
        });
    }
}
