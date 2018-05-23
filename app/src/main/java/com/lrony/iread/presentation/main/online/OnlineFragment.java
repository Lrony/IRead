package com.lrony.iread.presentation.main.online;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.classic.common.MultipleStatusView;
import com.lrony.iread.R;
import com.lrony.iread.model.bean.BannerBean;
import com.lrony.iread.model.bean.SortBookBean;
import com.lrony.iread.mvp.MvpFragment;
import com.lrony.iread.pref.Constant;
import com.lrony.iread.presentation.main.online.multi.BookInfo;
import com.lrony.iread.presentation.main.online.multi.BookInfoViewBinder;
import com.lrony.iread.presentation.main.online.multi.Type;
import com.lrony.iread.presentation.main.online.multi.TypeViewBinder;
import com.lrony.iread.ui.help.BannerImageLoader;
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

    @BindView(R.id.banner)
    Banner mBanner;
    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;

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

        getPresenter().loadFemaleHotBooks(HOT_BOOK_GET_NUMBER);
        getPresenter().loadMaleHotBooks(HOT_BOOK_GET_NUMBER);
    }

    private void initView() {
        // 模拟数据
        for (int i = 0; i < 5; i++) {
            mBanners.add("http://img.hb.aicdn.com/37f6e8c621a4f6a0be117c6c7240ab7a5a2c02ee2da03-KkT9Rr_fw658");
        }

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
        mAdapter = new MultiTypeAdapter();
        mAdapter.register(Type.class, new TypeViewBinder());
        mAdapter.register(BookInfo.class, new BookInfoViewBinder(getContext()));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        if (mBanner != null) mBanner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        if (mBanner != null) mBanner.stopAutoPlay();
    }

    /**
     * 设置Adapter的Items
     * 只有全部的数据获取到了才能设置
     */
    private void jugeSetItems() {
        if (isLoadMaleBooks == true && isLoadFemaleBooks == true) {
            KLog.d(TAG, "load ok");
            mAdapter.setItems(mItems);
            mAdapter.notifyDataSetChanged();
        } else {
            KLog.d(TAG, "loading data... isLoadMaleBooks: " + isLoadMaleBooks + ",isLoadFemaleBooks: " + isLoadFemaleBooks);
        }
    }

    @Override
    public void finishLoadMaleBooks(List<SortBookBean> books) {
        KLog.d(TAG, "finishLoadMaleBooks");
        mItems.add(new Type("男频热推"));
        for (SortBookBean bean : books) {
            mItems.add(new BookInfo(bean.getTitle(), bean.getShortIntro(), bean.getAuthor()
                    , Constant.IMG_BASE_URL + bean.getCover(), bean.getMajorCate(), bean.getRetentionRatio()));
        }
        isLoadMaleBooks = true;
        jugeSetItems();
    }

    @Override
    public void finishLoadFemaleBooks(List<SortBookBean> books) {
        KLog.d(TAG, "finishLoadFemaleBooks");
        mItems.add(new Type("女频热推"));
        for (SortBookBean bean : books) {
            mItems.add(new BookInfo(bean.getTitle(), bean.getShortIntro(), bean.getAuthor()
                    , Constant.IMG_BASE_URL + bean.getCover(), bean.getMajorCate(), bean.getRetentionRatio()));
        }
        isLoadFemaleBooks = true;
        jugeSetItems();
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
