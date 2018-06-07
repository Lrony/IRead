package com.lrony.iread.presentation.main.online.type;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.classic.common.MultipleStatusView;
import com.lrony.iread.R;
import com.lrony.iread.mvp.MvpActivity;
import com.lrony.iread.pref.AppConfig;
import com.lrony.iread.ui.help.ToolbarHelper;
import com.lrony.iread.util.KLog;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookTypeActivity extends MvpActivity<BookTypeContract.Presenter> implements BookTypeContract.View {

    private static final String TAG = "BookTypeActivity";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.multiple_status_view)
    MultipleStatusView mStatusView;
    @BindView(R.id.refresh_view)
    SwipeRefreshLayout mRefreshView;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, BookTypeActivity.class);
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

        setContentView(R.layout.activity_book_type);
        // 使用ButterKnife代替findview
        ButterKnife.bind(this);
        // 代码规范，必须调用
        getPresenter().start();
        initView();
        initListener();
    }

    @NonNull
    @Override
    public BookTypeContract.Presenter createPresenter() {
        return new BookTypePresenter();
    }

    private void initView() {
        KLog.d(TAG, "initView");
        ToolbarHelper.initToolbar(this, R.id.toolbar, true, R.string.book_type_title);

        mRefreshView.setColorSchemeResources(R.color.colorAccent);

    }

    private void initListener() {
        KLog.d(TAG, "initListener");
    }
}
