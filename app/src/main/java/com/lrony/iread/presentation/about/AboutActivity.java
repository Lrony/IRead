package com.lrony.iread.presentation.about;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.lrony.iread.BuildConfig;
import com.lrony.iread.R;
import com.lrony.iread.base.BaseActivity;
import com.lrony.iread.pref.AppConfig;
import com.lrony.iread.ui.help.ToolbarHelper;
import com.lrony.iread.util.KLog;
import com.lrony.iread.util.Shares;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutActivity extends BaseActivity {

    private static final String TAG = "AboutActivity";

    @BindView(R.id.tv_version_name)
    TextView mTvVersion;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, AboutActivity.class);
        return intent;
    }

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

        setContentView(R.layout.activity_about);
        // 使用ButterKnife代替findview
        ButterKnife.bind(this);
        ToolbarHelper.initToolbar(this, R.id.toolbar, true, "关于悦读");
        mTvVersion.setText(getString(R.string.app_name) + " " + BuildConfig.VERSION_NAME);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_about, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                Shares.share(this, R.string.share_content);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
