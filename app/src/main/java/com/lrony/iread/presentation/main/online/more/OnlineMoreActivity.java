package com.lrony.iread.presentation.main.online.more;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
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
import com.lrony.iread.model.bean.SortBookBean;
import com.lrony.iread.mvp.MvpActivity;
import com.lrony.iread.pref.AppConfig;
import com.lrony.iread.ui.help.OnMultiClickListener;
import com.lrony.iread.ui.help.OnMultiItemClickListener;
import com.lrony.iread.ui.help.RecyclerViewItemDecoration;
import com.lrony.iread.ui.help.ToolbarHelper;
import com.lrony.iread.ui.widget.CustomLoadMoreView;
import com.lrony.iread.util.KLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OnlineMoreActivity extends MvpActivity<OnlineMoreContract.Presenter> implements OnlineMoreContract.View, BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "OnlineMoreActivity";

    private static final String K_TITLE = "title";

    private String mTitle;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.fab_up)
    FloatingActionButton mFloatingBtnUp;
    @BindView(R.id.multiple_status_view)
    MultipleStatusView mStatusView;
    @BindView(R.id.refresh_view)
    SwipeRefreshLayout mRefreshView;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private String mGender = "male";
    private String mMajor = "玄幻";
    private String mType = "hot";
    private String mMinor = "";
    private int mStart = 0;
    private int mLimit = 15;

    private OnlineMoreAdapter mAdapter;

    private List<SortBookBean> mBooks = new ArrayList<>();

    public static Intent newIntent(Context context, String str) {
        Intent intent = new Intent(context, OnlineMoreActivity.class);
        intent.putExtra(K_TITLE, str);
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

        mTitle = getIntent().getStringExtra(K_TITLE);
        KLog.d(TAG, "mTitle: " + mTitle);
        setContentView(R.layout.activity_online_more);
        // 使用ButterKnife代替findview
        ButterKnife.bind(this);
        // 代码规范，必须调用
        getPresenter().start();

        initView();
        initListener();
        initData();
    }

    @NonNull
    @Override
    public OnlineMoreContract.Presenter createPresenter() {
        return new OnlineMorePresenter();
    }

    private void initView() {
        KLog.d(TAG, "initView");
        ToolbarHelper.initToolbar(this, R.id.toolbar, true, mTitle);

        mRefreshView.setColorSchemeResources(R.color.colorAccent);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorForeground));
        mRecyclerView.addItemDecoration(new RecyclerViewItemDecoration.Builder(this)
                .color(ContextCompat.getColor(this, R.color.colorDivider))
                .thickness(1)
                .create());

        mAdapter = new OnlineMoreAdapter(this, mBooks);
        mAdapter.setLoadMoreView(new CustomLoadMoreView());
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initListener() {
        KLog.d(TAG, "initListener");

        mRefreshView.setOnRefreshListener(this);
        mFloatingBtnUp.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                mRecyclerView.smoothScrollToPosition(0);
            }
        });

        // 加载失败重试监听
        mStatusView.setOnRetryClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                loadData(true);
            }
        });

        mAdapter.setOnLoadMoreListener(this);
        mAdapter.setOnItemClickListener(new OnMultiItemClickListener() {
            @Override
            public void OnMultiItemClick(BaseQuickAdapter adapter, View view, int position) {
                AppRouter.showBookDetailActivity(OnlineMoreActivity.this, mBooks.get(position).get_id());
            }
        });
    }

    private void initData() {
        KLog.d(TAG, "initData");
        if (mTitle.equals("男频热推")) {
            mGender = "male";
            mMajor = "玄幻";
        } else if (mTitle.equals("女频热推")) {
            mGender = "female";
            mMajor = "古代言情";
        }

        KLog.d(TAG, "mGender: " + mGender + ",mMajor: " + mMajor);

        loadData(true);
    }

    private void loadData(boolean show) {
        KLog.d(TAG, "loadData");
        getPresenter().loadData(show, mGender, mType, mMajor, mMinor, mStart, mLimit);
    }

    @Override
    public void finishLoadData(List<SortBookBean> books) {
        KLog.d(TAG, "finishLoadData " + books.size());
        if (books.isEmpty()) {
            mStatusView.showEmpty();
            return;
        }

        mBooks.clear();
        mBooks.addAll(books);
        mAdapter.notifyDataSetChanged();
        mStart = books.size();
    }

    @Override
    public void finishLoadMoreData(List<SortBookBean> books) {
        KLog.d(TAG, "finishLoadMoreData");
        if (books.isEmpty()) {
            mAdapter.loadMoreEnd();
            return;
        }
        for (SortBookBean book : books) {
            mBooks.add(book);
        }
        mStart += mBooks.size();
    }

    @Override
    public void showLoadMoreError() {
        KLog.d(TAG, "showLoadMoreError");
        mAdapter.loadMoreFail();
    }

    @Override
    public void loading() {
        super.loading();
        KLog.d(TAG, "loading");
        mStatusView.showLoading();
    }

    @Override
    public void complete() {
        super.complete();
        KLog.d(TAG, "complete");
        mStatusView.showContent();
        mRefreshView.setRefreshing(false);
        mAdapter.loadMoreComplete();
    }

    @Override
    public void error() {
        super.error();
        KLog.d(TAG, "error");
        mStatusView.showError();
    }

    @Override
    public void onLoadMoreRequested() {
        getPresenter().loadMoreData(mGender, mType, mMajor, mMinor, mStart, mLimit);
    }

    @Override
    public void onRefresh() {
        mStart = 0;
        loadData(false);
    }
}
