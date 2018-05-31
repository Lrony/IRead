package com.lrony.iread.presentation.main.online.more;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;

import com.lrony.iread.R;
import com.lrony.iread.mvp.MvpActivity;
import com.lrony.iread.pref.AppConfig;
import com.lrony.iread.util.KLog;

import butterknife.ButterKnife;

public class OnlineMoreActivity extends MvpActivity<OnlineMoreContract.Presenter> implements OnlineMoreContract.View {

    private static final String TAG = "OnlineMoreActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 判断是否需要刷新Activity来应用夜间模式切换所导致的更改，Activity必须配置
        if (savedInstanceState == null) {
            boolean isNight = AppConfig.isNightMode();
            KLog.d(TAG, "initTheme isNight = " + isNight);
            if (isNight) {
                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        }

        setContentView(R.layout.activity_online_more);
        // 使用ButterKnife代替findview
        ButterKnife.bind(this);
        // 代码规范，必须调用
        getPresenter().start();
    }

    @NonNull
    @Override
    public OnlineMoreContract.Presenter createPresenter() {
        return new OnlineMorePresenter();
    }
}
