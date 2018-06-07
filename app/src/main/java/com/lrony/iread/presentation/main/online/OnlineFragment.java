package com.lrony.iread.presentation.main.online;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.classic.common.MultipleStatusView;
import com.lrony.iread.AppRouter;
import com.lrony.iread.R;
import com.lrony.iread.model.bean.BannerBean;
import com.lrony.iread.model.bean.CollBookBean;
import com.lrony.iread.model.bean.SortBookBean;
import com.lrony.iread.mvp.MvpFragment;
import com.lrony.iread.pref.Constant;
import com.lrony.iread.presentation.main.online.multi.BookInfo;
import com.lrony.iread.presentation.main.online.multi.BookInfoViewBinder;
import com.lrony.iread.presentation.main.online.multi.More;
import com.lrony.iread.presentation.main.online.multi.OpenMoreViewBinder;
import com.lrony.iread.presentation.main.online.multi.Type;
import com.lrony.iread.presentation.main.online.multi.TypeViewBinder;
import com.lrony.iread.ui.help.BannerImageLoader;
import com.lrony.iread.ui.help.OnMultiClickListener;
import com.lrony.iread.ui.help.RecyclerViewItemDecoration;
import com.lrony.iread.util.KLog;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by Lrony on 18-5-22.
 */
public class OnlineFragment extends MvpFragment<OnlineContract.Presenter> implements OnlineContract.View {

    private static final String TAG = "OnlineFragment";

    @BindView(R.id.ll_father)
    LinearLayout mFather;
    @BindView(R.id.ns_view)
    NestedScrollView mNestedScrollView;
    @BindView(R.id.fab_up)
    FloatingActionButton mFloatingBtnUp;
    @BindView(R.id.multiple_status_view)
    MultipleStatusView mStatusView;
    @BindView(R.id.banner)
    Banner mBanner;
    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;
    @BindView(R.id.ll_online_type_leaderboard)
    LinearLayout mLeaderboard;
    @BindView(R.id.ll_online_type_type)
    LinearLayout mType;

    // 轮播图滚动间隔
    private static final int BANNER_SCROLL_TIME = 2300;
    // 热门书籍获取数量
    private static final int HOT_BOOK_GET_NUMBER = 5;

    private List<String> mBanners = new ArrayList<>();

    private MultiTypeAdapter mAdapter;
    private Items mItems = new Items();

    // 男生热门书籍是否加载完成
    private boolean isLoadMaleBooks = false;
    // 女生热门书籍是否加载完成
    private boolean isLoadFemaleBooks = false;

    public static OnlineFragment newInstance() {
        OnlineFragment fragment = new OnlineFragment();
        return fragment;
    }

    @NonNull
    @Override
    public OnlineContract.Presenter createPresenter() {
        return new OnlinePresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_online;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getPresenter().start();
        initView();
        initListener();

        loadData();
    }

    private void loadData() {
        KLog.d(TAG, "loadData");
        getPresenter().loadRemoteBooks();
    }

    private void initView() {
        KLog.d(TAG, "initView");

        // Fragment切换界面跳动问题，临时对应
        mFather.setFocusable(true);
        mFather.setFocusableInTouchMode(true);
        mFather.requestFocus();

        // 模拟数据
        mBanners.add("http://img.hb.aicdn.com/869326eae314f5efd1ba5998dfb05350a99a79f317a48-OQQQ0G_fw658");
        mBanners.add("http://img.hb.aicdn.com/b7ed8a8ccdd1b99cfe300598e22b902b5667cc8d195a2-6lQTVT_fw658");
        mBanners.add("http://img.hb.aicdn.com/b98863aaec3b80555981330f54e4316b0247753c1ec500-3IX0hY_fw658");
        mBanners.add("http://img.hb.aicdn.com/37f6e8c621a4f6a0be117c6c7240ab7a5a2c02ee2da03-KkT9Rr_fw658");

        //设置banner样式
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        mBanner.setImageLoader(new BannerImageLoader());
        //设置图片集合
        mBanner.setImages(mBanners);
        //设置banner动画效果
        mBanner.setBannerAnimation(Transformer.Default);
        //设置标题集合（当banner样式有显示title时）
        // mBanner.setBannerTitles("Demo");
        //设置自动轮播，默认为true
        mBanner.isAutoPlay(true);
        //设置轮播时间
        mBanner.setDelayTime(BANNER_SCROLL_TIME);
        //设置指示器位置（当banner模式中有指示器时）
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        mBanner.start();

        // 使用MultiType完成多布局RecycleView
        // NestedScrollView嵌套RecyclerView时滑动不流畅问题 https://blog.csdn.net/u010839880/article/details/52672489
        mAdapter = new MultiTypeAdapter();
        mAdapter.register(Type.class, new TypeViewBinder(getContext()));
        mAdapter.register(BookInfo.class, new BookInfoViewBinder(getContext()));
        mAdapter.register(More.class, new OpenMoreViewBinder(getContext()));
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setAutoMeasureEnabled(true);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initListener() {
        KLog.d(TAG, "initListener");
        bindOnMultiClickListener(onMultiClickListener, mLeaderboard, mType);
        // 加载失败重试监听
        mStatusView.setOnRetryClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                loadData();
            }
        });

        mFloatingBtnUp.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                mNestedScrollView.scrollTo(0, 0);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        KLog.d(TAG, "onStart");
        //开始轮播
        if (mBanner != null) mBanner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        KLog.d(TAG, "onStop");
        //结束轮播
        if (mBanner != null) mBanner.stopAutoPlay();
    }

    private OnMultiClickListener onMultiClickListener = new OnMultiClickListener() {
        @Override
        public void onMultiClick(View v) {
            switch (v.getId()) {
                case R.id.ll_online_type_leaderboard:
                    KLog.d(TAG, "onClick leaderboard");
                    AppRouter.showRankingActivity(getContext());
                    break;
                case R.id.ll_online_type_type:
                    KLog.d(TAG, "onClick type");
                    AppRouter.showBookTypeActivity(getContext());
                    break;
            }
        }
    };

    @Override
    public void finishLoad(List<CollBookBean> maleData, List<CollBookBean> femaleData) {
        KLog.d(TAG, "finishLoad");
        mItems.add(new Type(getString(R.string.noline_male_hot_recommend)));
        for (CollBookBean bean : maleData) {
            mItems.add(new BookInfo(bean.get_id(), bean.getTitle(), bean.getShortIntro(), bean.getAuthor()
                    , Constant.IMG_BASE_URL + bean.getCover(), bean.getLatelyFollower() + "", bean.getRetentionRatio()));
        }

        mItems.add(new Type(getString(R.string.noline_female_hot_recommend)));
        for (CollBookBean bean : femaleData) {
            mItems.add(new BookInfo(bean.get_id(), bean.getTitle(), bean.getShortIntro(), bean.getAuthor()
                    , Constant.IMG_BASE_URL + bean.getCover(), bean.getLatelyFollower() + "", bean.getRetentionRatio()));
        }
        mAdapter.setItems(mItems);
        mAdapter.notifyDataSetChanged();
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
    }

    @Override
    public void nonetword() {
        super.nonetword();
        KLog.d(TAG, "complete");
        mStatusView.showNoNetwork();
    }

    @Override
    public void error() {
        super.error();
        KLog.d(TAG, "error");
        mStatusView.showError();
    }
}
