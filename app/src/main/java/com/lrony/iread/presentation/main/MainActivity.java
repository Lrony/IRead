package com.lrony.iread.presentation.main;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lrony.iread.AppManager;
import com.lrony.iread.AppRouter;
import com.lrony.iread.R;
import com.lrony.iread.mvp.MvpActivity;
import com.lrony.iread.pref.AppConfig;
import com.lrony.iread.util.KLog;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends MvpActivity<MainContract.Presenter> implements MainContract.View, View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.navigation_view)
    NavigationView mNavigationView;
    @BindView(R.id.iv_menu)
    ImageView mIvMenu;
    @BindView(R.id.iv_search)
    ImageView mIvSearch;
    @BindView(R.id.tv_local)
    TextView mTvLocal;
    @BindView(R.id.tv_online)
    TextView mTvOnline;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    private View mNavigationHeader;

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

        setContentView(R.layout.activity_main);
        // 使用ButterKnife代替findview
        ButterKnife.bind(this);
        // 代码规范，必须调用
        getPresenter().start();
        initView();
        initListener();
    }

    @NonNull
    @Override
    public MainContract.Presenter createPresenter() {
        return new MainPresenter();
    }

    private void initView() {
        // 添加侧滑菜单的头部VIEW
        mNavigationHeader = LayoutInflater.from(this).inflate(R.layout.view_navigation_header, mNavigationView, false);
        mNavigationView.addHeaderView(mNavigationHeader);
        // 更新天气信息
        new WeatherExecutor(this, mNavigationHeader).execute("上海");

        // 默认选中本地图书
        mTvLocal.setSelected(true);

        // 添加两个Fragment
        mViewPager.setAdapter(new MainFragmentAdapter(getSupportFragmentManager(), "LOCAL", "ONLINE"));
    }

    private void initListener() {
        // 绑定一些View的点击事件
        bindOnClickLister(this, mIvMenu, mIvSearch, mTvLocal, mTvOnline);

        // ViewPager切换事件
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // Fragment切换了
                KLog.d(TAG, "onPageSelected: " + position);
                swichTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // 侧边栏Item点击事件
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_menu:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.iv_search:
                AppRouter.showSearchActivity(this);
                break;
            case R.id.tv_local:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.tv_online:
                mViewPager.setCurrentItem(1);
                break;
        }
    }

    // 切换Tab
    private void swichTab(int position) {
        KLog.d(TAG, "swichTab: " + position);
        if (position == 0) {
            mTvLocal.setSelected(true);
            mTvOnline.setSelected(false);
        } else if (position == 1) {
            mTvLocal.setSelected(false);
            mTvOnline.setSelected(true);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // 关闭侧边栏
        mDrawerLayout.closeDrawers();
        switch (item.getItemId()) {
            case R.id.action_setting:
                AppRouter.showSettingsActivity(this);
                break;
            case R.id.action_night:
                // 加个延时，等待侧边栏关闭后再切换模式
                new Handler().postDelayed(() -> swichNightMode(), 500);
                break;
        }
        return false;
    }

    private long firstTime = 0;

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {
                    showToast(R.string.app_double_click_exit);
                    firstTime = secondTime;
                    return true;
                } else {
                    AppManager.getInstance().exit();
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
    }
}
