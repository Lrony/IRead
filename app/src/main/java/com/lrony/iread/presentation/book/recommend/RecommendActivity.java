package com.lrony.iread.presentation.book.recommend;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;

import com.lrony.iread.model.bean.BookDetailRecommendBookBean;
import com.lrony.iread.mvp.MvpActivity;
import com.lrony.iread.util.KLog;

import java.util.List;

/**
 * Created by liuxiaobin on 18-5-30.
 */

public class RecommendActivity extends MvpActivity<RecommendContract.Presenter> implements RecommendContract.View, SwipeRefreshLayout.OnRefreshListener {

    private final static String TAG = "RecommendActivity";

    private static final String K_EXTRA_RECOMMEND = "recommend";

    private String mBookId;

    public static Intent newIntent(Context context, String bookid) {
        Intent intent = new Intent(context, RecommendActivity.class);
        intent.putExtra(K_EXTRA_RECOMMEND, bookid);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBookId = getIntent().getStringExtra(K_EXTRA_RECOMMEND);
        KLog.d(TAG, "mBookId: " + mBookId);
    }

    @Override
    public void loading() {
        super.loading();
    }

    @Override
    public void finshLoadRecommend(List<BookDetailRecommendBookBean> bookBean) {

    }

    @NonNull
    @Override
    public RecommendContract.Presenter createPresenter() {
        return null;
    }

    /**
     * Called when pointer capture is enabled or disabled for the current window.
     *
     * @param hasCapture True if the window has pointer capture.
     */
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    /**
     * Called when a swipe gesture triggers a refresh.
     */
    @Override
    public void onRefresh() {
        getPresenter().loadRecommendBook(false, mBookId);
    }

    @Override
    public void complete() {
        super.complete();
    }

    @Override
    public void error() {
        super.error();
    }
}
