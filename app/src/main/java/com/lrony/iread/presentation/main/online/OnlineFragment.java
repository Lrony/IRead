package com.lrony.iread.presentation.main.online;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.classic.common.MultipleStatusView;
import com.lrony.iread.R;
import com.lrony.iread.model.bean.BannerBean;
import com.lrony.iread.mvp.MvpFragment;
import com.lrony.iread.ui.help.BannerImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Lrony on 18-5-22.
 */
public class OnlineFragment extends MvpFragment<OnlineContract.Presenter> implements OnlineContract.View {

    private static final String TAG = "OnlineFragment";

    @BindView(R.id.banner)
    Banner mBanner;

    private List<String> mBanners = new ArrayList<>();

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
        mBanner.setDelayTime(2300);
        //设置指示器位置（当banner模式中有指示器时）
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        mBanner.start();
    }

    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        mBanner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        mBanner.stopAutoPlay();
    }
}
