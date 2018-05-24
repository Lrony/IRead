package com.lrony.iread.presentation.main;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lrony.iread.R;
import com.lrony.iread.util.KLog;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Lrony on 18-5-24.
 * 更新天气
 */
public class WeatherExecutor {

    private static final String TAG = "WeatherExecutor";

    @BindView(R.id.iv_weather_icon)
    ImageView mIvIcon;
    @BindView(R.id.tv_weather_temp)
    TextView mTvTemp;
    @BindView(R.id.tv_weather_city)
    TextView mTvCity;
    @BindView(R.id.tv_weather_wind)
    TextView mTvWind;

    private Context mContext;

    public WeatherExecutor(Context context, View navigationHeader) {
        mContext = context.getApplicationContext();
        ButterKnife.bind(this, navigationHeader);
    }

    public void execute() {
        KLog.d(TAG, "execute");

    }
}
