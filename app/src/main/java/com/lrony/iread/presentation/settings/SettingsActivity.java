package com.lrony.iread.presentation.settings;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;

import com.lrony.iread.R;
import com.lrony.iread.base.BaseActivity;
import com.lrony.iread.pref.AppConfig;
import com.lrony.iread.ui.help.ToolbarHelper;
import com.lrony.iread.util.KLog;

public class SettingsActivity extends BaseActivity {

    private static final String TAG = "SettingsActivity";

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, SettingsActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
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

        setContentView(R.layout.activity_settings);

        // 初始化Toolbar
        ToolbarHelper.initToolbar(this, R.id.toolbar, true, getString(R.string.settings_title));

        // 加载设置Fragment
        SettingsFragment settingFragment = new SettingsFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.settingsFrameLayout, settingFragment)
                .commit();
    }
}
