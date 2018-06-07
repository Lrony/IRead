package com.lrony.iread.presentation.book.recommend;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.classic.common.MultipleStatusView;
import com.lrony.iread.AppManager;
import com.lrony.iread.AppRouter;
import com.lrony.iread.R;
import com.lrony.iread.model.bean.BookDetailRecommendBookBean;
import com.lrony.iread.mvp.MvpActivity;
import com.lrony.iread.pref.AppConfig;
import com.lrony.iread.ui.help.OnMultiClickListener;
import com.lrony.iread.ui.help.OnMultiItemClickListener;
import com.lrony.iread.ui.help.RecyclerViewItemDecoration;
import com.lrony.iread.ui.help.ToolbarHelper;
import com.lrony.iread.util.KLog;

import java.util.ArrayList;
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
    private int distance = 0;
    private boolean visible = true;

    private RecommendAdapter mAdapter;

    private List<BookDetailRecommendBookBean> mRecommendBooks = new ArrayList<>();

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_view)
    SwipeRefreshLayout mRefreshView;
    @BindView(R.id.multiple_status_view)
    MultipleStatusView mStatusView;
    @BindView(R.id.fab_up)
    FloatingActionButton mFabUp;

    public static Intent newIntent(Context context, String bookid) {
        Intent intent = new Intent(context, RecommendActivity.class);
        intent.putExtra(K_EXTRA_RECOMMEND, bookid);
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
        KLog.d(TAG, "initView");
        mRefreshView.setColorSchemeResources(R.color.colorAccent);

        mRecyclerView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorForeground));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new RecyclerViewItemDecoration.Builder(this)
                .color(ContextCompat.getColor(this, R.color.colorDivider))
                .thickness(1)
                .create());

        mAdapter = new RecommendAdapter(this, mRecommendBooks);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initListener() {
        KLog.d(TAG, "initListener");
        mRefreshView.setOnRefreshListener(this);
        mStatusView.setOnRetryClickListener(v ->
                v.setOnClickListener(new OnMultiClickListener() {
                    @Override
                    public void onMultiClick(View v) {
                        getPresenter().loadRecommendBook(true, mBookId);
                    }
                })
        );

        mAdapter.setOnItemClickListener(new OnMultiItemClickListener() {
            @Override
            public void OnMultiItemClick(BaseQuickAdapter adapter, View view, int position) {
                AppRouter.showBookDetailActivity(
                        RecommendActivity.this, mRecommendBooks.get(position).get_id());
            }
        });

        mFabUp.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                mRecyclerView.scrollToPosition(0);
            }
        });

    }

    @Override
    public void loading() {
        super.loading();
        mStatusView.showLoading();
    }

    @Override
    public void finshLoadRecommend(List<BookDetailRecommendBookBean> bookBean) {
        KLog.d(TAG, "finshLoadRecommend");
        if (null == bookBean) {
            AppManager.getInstance().finishActivity(this);
        }
        mRecommendBooks.clear();
        mRecommendBooks.addAll(bookBean);
        mAdapter.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecommendContract.Presenter createPresenter() {
        return new RecommendPresenter();
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
        mStatusView.showContent();
        mRefreshView.setRefreshing(false);

        if (mRecommendBooks.size() <= 0) mStatusView.showEmpty();
    }
}
