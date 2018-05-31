package com.lrony.iread.presentation.book.recommend;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.classic.common.MultipleStatusView;
import com.lrony.iread.R;
import com.lrony.iread.model.bean.BookDetailRecommendBookBean;
import com.lrony.iread.mvp.MvpActivity;
import com.lrony.iread.ui.help.RecyclerViewItemDecoration;
import com.lrony.iread.ui.help.ToolbarHelper;
import com.lrony.iread.util.KLog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liuxiaobin on 18-5-30.
 */

public class RecommendActivity extends MvpActivity<RecommendContract.Presenter> implements RecommendContract.View, SwipeRefreshLayout.OnRefreshListener {

    private final static String TAG = "RecommendActivity";

    private static final String K_EXTRA_RECOMMEND = "recommend";

    private String mBookId;

    private RecommendAdapter mAdapter;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_view)
    SwipeRefreshLayout mRefreshView;
    @BindView(R.id.multiple_status_view)
    MultipleStatusView mStatusView;

    public static Intent newIntent(Context context, String bookid) {
        Intent intent = new Intent(context, RecommendActivity.class);
        intent.putExtra(K_EXTRA_RECOMMEND, bookid);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);
        ButterKnife.bind(this);

        mBookId = getIntent().getStringExtra(K_EXTRA_RECOMMEND);
        KLog.d(TAG, "mBookId: " + mBookId);

        ToolbarHelper.initToolbar(this, R.id.toolbar, true, R.string.recommend_title);
        getPresenter().start();

        initView();
        initListener();

        getPresenter().loadRecommendBook(true, mBookId);
    }

    private void initView() {
        mStatusView.setOnRetryClickListener(mRetryClickListener);
        mRefreshView.setColorSchemeResources(R.color.colorAccent);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new RecyclerViewItemDecoration.Builder(this)
                .color(ContextCompat.getColor(this, R.color.colorDivider))
                .thickness(1)
                .create());

        mAdapter = new RecommendAdapter(this, null);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initListener() {
        KLog.d(TAG, "initListener");


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

    private View.OnClickListener mRetryClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

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
