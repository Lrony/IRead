package com.lrony.iread.presentation.book.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDelegate;

import com.lrony.iread.R;
import com.lrony.iread.model.bean.BookDetailBean;
import com.lrony.iread.model.bean.BookDetailRecommendBookBean;
import com.lrony.iread.model.remote.BookApi;
import com.lrony.iread.mvp.MvpActivity;
import com.lrony.iread.pref.AppConfig;
import com.lrony.iread.ui.help.ProgressCancelListener;
import com.lrony.iread.util.KLog;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by liuxiaobin on 18-5-23.
 */

public class BookDetailActivity extends MvpActivity<BookDetailContract.Presenter> implements BookDetailContract.View, ProgressCancelListener {

    private final static String TAG = "BookDetailActivity";

    private static final String K_EXTRA_BOOK = "book";



    private BookApi mBookApi = null;

    public static Intent newIntent(Context context, String str) {
        Intent intent = new Intent(context, BookDetailActivity.class);
        intent.putExtra(K_EXTRA_BOOK, str);
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

        setContentView(R.layout.activity_book_detail);
        ButterKnife.bind(this);
        getPresenter().start();
    }

    @NonNull
    @Override
    public BookDetailContract.Presenter createPresenter() {
        return new BookDetailPresenter();
    }

    @Override
    public void onCancelProgress() {

    }

    @Override
    public void finshLoadBookInfo(BookDetailBean book) {

    }

    @Override
    public void finshLoadBookDetailRecommendBooklist(List<BookDetailRecommendBookBean> books) {

    }

    /**
     * Called when pointer capture is enabled or disabled for the current window.
     *
     * @param hasCapture True if the window has pointer capture.
     */
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
