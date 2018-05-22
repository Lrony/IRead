package com.lrony.iread;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatDelegate;

import com.lrony.iread.model.db.DBRepository;
import com.lrony.iread.pref.AppConfig;
import com.lrony.iread.util.KLog;

/**
 * Created by Lrony on 18-5-21.
 */
public class App extends Application {

    private static final String TAG = "AppIRead";

    private static App mContext = null;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        mContext = this;
    }

    public static App context() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        AppManager.init(this);
        DBRepository.initDatabase(this);
        KLog.init(BuildConfig.DEBUG);
        initTheme();
    }

    // 初始化是否为夜间模式
    private void initTheme() {
        if (AppConfig.isNightMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}
