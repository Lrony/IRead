package com.lrony.iread.presentation.main.online.type;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.classic.common.MultipleStatusView;
import com.lrony.iread.R;
import com.lrony.iread.model.bean.BookSortBean;
import com.lrony.iread.model.bean.packages.BookSortPackage;
import com.lrony.iread.model.bean.packages.BookSubSortPackage;
import com.lrony.iread.mvp.MvpActivity;
import com.lrony.iread.pref.AppConfig;
import com.lrony.iread.presentation.main.online.multi.Type;
import com.lrony.iread.presentation.main.online.multi.TypeViewBinder;
import com.lrony.iread.presentation.main.online.type.multi.BookTypeItem;
import com.lrony.iread.presentation.main.online.type.multi.BookTypeItemViewBinder;
import com.lrony.iread.presentation.main.online.type.multi.BookTypeTitleViewBinder;
import com.lrony.iread.ui.help.ToolbarHelper;
import com.lrony.iread.util.KLog;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class BookTypeActivity extends MvpActivity<BookTypeContract.Presenter> implements BookTypeContract.View, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private static final String TAG = "BookTypeActivity";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.multiple_status_view)
    MultipleStatusView mStatusView;
    @BindView(R.id.refresh_view)
    SwipeRefreshLayout mRefreshView;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private static final int SPAN_COUNT = 3;

    private MultiTypeAdapter mAdapter;
    private Items mItems = new Items();

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
        loadData(true);
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

        mRecyclerView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorForeground));
        GridLayoutManager layoutManager = new GridLayoutManager(this, SPAN_COUNT);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (mItems.get(position) instanceof Type) ? SPAN_COUNT : 1;
            }
        });
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new MultiTypeAdapter();
        mAdapter.register(Type.class, new BookTypeTitleViewBinder());
        mAdapter.register(BookTypeItem.class, new BookTypeItemViewBinder());
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initListener() {
        KLog.d(TAG, "initListener");
        mRefreshView.setOnRefreshListener(this);
        mStatusView.setOnRetryClickListener(this);
    }

    private void loadData(boolean showRefreshView) {
        KLog.d(TAG, "initListener");
        getPresenter().loadTypeData(showRefreshView);
    }

    @Override
    public void finishLoadType(BookSortPackage sort, BookSubSortPackage subSort) {
        KLog.d(TAG, "finishLoadType");
        mItems.add(new Type("男生"));
        for (BookSortBean bean : sort.getMale()) {
            mItems.add(new BookTypeItem(bean.getName(), bean.getBookCount() + ""));
        }
        mItems.add(new Type("女生"));
        for (BookSortBean bean : sort.getFemale()) {
            mItems.add(new BookTypeItem(bean.getName(), bean.getBookCount() + ""));
        }
        mAdapter.setItems(mItems);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void complete() {
        super.complete();
        KLog.d(TAG, "complete");
        mStatusView.showContent();
        mRefreshView.setRefreshing(false);
    }

    @Override
    public void loading() {
        super.loading();
        KLog.d(TAG, "loading");
        mStatusView.showLoading();
    }

    @Override
    public void error() {
        super.error();
        KLog.d(TAG, "error");
        mStatusView.showError();
    }

    @Override
    public void onRefresh() {
        loadData(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.empty_retry_view:
            case R.id.error_retry_view:
            case R.id.no_network_retry_view:
                KLog.d(TAG, "Retry");
                loadData(true);
                break;
        }
    }
}
