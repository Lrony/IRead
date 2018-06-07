package com.lrony.iread.presentation.main.local;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.classic.common.MultipleStatusView;
import com.lrony.iread.AppRouter;
import com.lrony.iread.R;
import com.lrony.iread.model.bean.CollBookBean;
import com.lrony.iread.mvp.MvpFragment;
import com.lrony.iread.presentation.read.ReadActivity;
import com.lrony.iread.ui.help.OnMultiClickListener;
import com.lrony.iread.util.KLog;
import com.lrony.iread.util.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Lrony on 18-5-22.
 */
public class LocalFragment extends MvpFragment<LocalContract.Presenter> implements LocalContract.View, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "LocalFragment";

    private List<CollBookBean> mCollBookbeans = new ArrayList<>();

    @BindView(R.id.multiple_status_view)
    MultipleStatusView mMultipleStatusView;

    @BindView(R.id.refresh_view)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private Context mContext;

    private LocalAdapter mAdapter;

    public static LocalFragment newInstance() {
        LocalFragment fragment = new LocalFragment();
        return fragment;
    }

    @NonNull
    @Override
    public LocalContract.Presenter createPresenter() {
        return new LocalPresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_local;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        KLog.d(TAG, "onViewCreated()");
        getPresenter().start();

        initView();
        initListener();

        getPresenter().doLoadData(true);
    }

    private void initListener() {
        KLog.d(TAG, "initListener()");
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.addOnItemTouchListener(onItemClickListener);
    }

    private void initView() {
        KLog.d(TAG, "initView()");

        mContext = getContext();

        if (null != mMultipleStatusView) {
            mMultipleStatusView.setOnRetryClickListener(mRetryClickListener);
        }

        if (null != mSwipeRefreshLayout) {
            mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        }

        if (null != mRecyclerView) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, ScreenUtil.isLAndscape(mContext) ? 4 : 3));
            mRecyclerView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorForeground));
        }

        initAdapter();
    }

    private void initAdapter() {
        KLog.d(TAG, "initAdapter()");
        mAdapter = new LocalAdapter(mCollBookbeans, mContext);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void finishLoad(List<CollBookBean> collBookBeans) {
        KLog.d(TAG, "finishLoad()");
        refreshData(collBookBeans);
    }

    private void refreshData(List<CollBookBean> collBookBeans) {
        KLog.d(TAG, "refreshData()");
        mCollBookbeans.clear();
        mCollBookbeans.addAll(collBookBeans);
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void finishDelete() {
        KLog.d(TAG, "finishDelete");
        getPresenter().doLoadData(false);

    }

    private View.OnClickListener mRetryClickListener = new OnMultiClickListener() {
        @Override
        public void onMultiClick(View v) {
            KLog.d(TAG, "mRetryClickListener onClick()");
            getPresenter().doLoadData(true);
        }
    };

    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
            KLog.d(TAG, "onSimpleItemClick() position = " + position);
            ReadActivity.startActivity(mContext, mCollBookbeans.get(position), true);
        }

        @Override
        public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
            super.onItemLongClick(adapter, view, position);
            KLog.d(TAG, "onItemLongClick() position = " + position);
            getPresenter().deleteData(mCollBookbeans.get(position));
        }
    };

    @Override
    public void onRefresh() {
        KLog.d(TAG, "swipe refresh onRefresh()");
        getPresenter().doLoadData(false);
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        KLog.d(TAG, "onSupportVisible()");
        getPresenter().doLoadData(false);
    }

    @Override
    public void complete() {
        super.complete();
        KLog.d(TAG, "complete()");
        mSwipeRefreshLayout.setRefreshing(false);
        mMultipleStatusView.showContent();
        mAdapter.loadMoreComplete();
    }

    @Override
    public void loading() {
        super.loading();
        KLog.d(TAG, "loading()");
        mMultipleStatusView.showLoading();
    }

    @Override
    public void empty() {
        super.empty();
        KLog.d(TAG, "empty()");
        mMultipleStatusView.showEmpty();
    }

    @Override
    public void error() {
        super.error();
        KLog.d(TAG, "error()");
        mMultipleStatusView.showError();
    }
}
