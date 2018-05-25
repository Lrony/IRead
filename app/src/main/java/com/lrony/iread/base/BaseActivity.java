package com.lrony.iread.base;

import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;

import com.lrony.iread.pref.AppConfig;
import com.lrony.iread.util.KLog;

/**
 * Created by lrony on 2018/4/9.
 * Providing functionality for all activity
 * <p>
 * 为所有的activity封装功能
 */
public class BaseActivity extends BaseSuperActivity {

    private static final String TAG = "BaseActivity";

    // 设置夜间模式
    public void swichNightMode() {
        boolean isNight = AppConfig.isNightMode();
        KLog.d(TAG, "isNight = " + isNight);
        if (isNight) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            AppConfig.setNightMode(false);
        } else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            AppConfig.setNightMode(true);
        }
        recreate();
    }

    public void bindOnClickLister(View.OnClickListener listener, @IdRes int... ids) {
        for (int id : ids) {
            View view = findViewById(id);
            if (view != null) {
                view.setOnClickListener(listener);
            }
        }
    }

    public void bindOnClickLister(View rootView, View.OnClickListener listener, @IdRes int... ids) {
        for (int id : ids) {
            View view = rootView.findViewById(id);
            if (view != null) {
                view.setOnClickListener(listener);
            }
        }
    }

    public void bindOnClickLister(View.OnClickListener listener, View... views) {
        for (View view : views) {
            view.setOnClickListener(listener);
        }
    }


}
