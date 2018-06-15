package com.lrony.iread.presentation.start;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lrony.iread.AppRouter;
import com.lrony.iread.BuildConfig;
import com.lrony.iread.R;
import com.lrony.iread.base.BaseActivity;
import com.lrony.iread.model.bean.WYHotBean;
import com.lrony.iread.model.db.DBManger;
import com.lrony.iread.ui.help.OnMultiClickListener;
import com.lrony.iread.util.DisplayUtil;
import com.lrony.iread.util.KLog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class StartActivity extends BaseActivity {

    private static final String TAG = "StartActivity";

    @BindView(R.id.tv_skip)
    TextView mTvSkip;
    @BindView(R.id.tv_content)
    TextView mTvContent;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_username)
    TextView mTvUserName;
    @BindView(R.id.tv_date_normal)
    TextView mTvDateNormal;
    @BindView(R.id.tv_date_hot)
    TextView mTvDateHot;
    @BindView(R.id.ll_welcome_normal)
    LinearLayout mLlWelcomeNormal;
    @BindView(R.id.ll_welcome_hot)
    LinearLayout mLlWelcomeHot;
    @BindView(R.id.tv_version_name)
    TextView mTvVersion;
    @BindView(R.id.content_view)
    FrameLayout mFrmContent;


    private Disposable mSubscribe;

    private List<WYHotBean> mWYHotDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        // 使用ButterKnife代替findview
        ButterKnife.bind(this);
        initDisPlay();
        startCountDown(4);
    }

    private void initDisPlay() {
        mTvSkip = findViewById(R.id.tv_skip);
        mTvSkip.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                skip();
            }
        });
        if (DisplayUtil.hasVirtualNavigationBar(this)) {
            mFrmContent.setPadding(0, 0, 0, DisplayUtil.getNavigationBarHeight(this));
        }
        mTvVersion.setText(BuildConfig.VERSION_NAME);
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy年MM月dd日，EEEE");
        mTvDateNormal.setText(format2.format(new Date()));
        mTvDateHot.setText(format2.format(new Date()));

        mWYHotDatas = DBManger.getInstance().loadWYHotData();
        KLog.d(TAG, "mWYHotDatas size " + mWYHotDatas.size());
        if (mWYHotDatas != null && mWYHotDatas.size() > 0) {
            mLlWelcomeNormal.setVisibility(View.GONE);
            mLlWelcomeHot.setVisibility(View.VISIBLE);
            WYHotBean bean = mWYHotDatas.get(mWYHotDatas.size() - 1);
            mTvContent.setText(bean.getContent());
            mTvName.setText("歌曲《" + bean.getName() + "》");
            mTvUserName.setText("摘自网易云用户 " + bean.getUsername());
        } else {
            mLlWelcomeNormal.setVisibility(View.VISIBLE);
            mLlWelcomeHot.setVisibility(View.GONE);
        }

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.alpha_in);
        mLlWelcomeNormal.startAnimation(animation);
        mLlWelcomeHot.startAnimation(animation);
    }

    private void startCountDown(int second) {

        final int countTime = second;

        mSubscribe = Observable.interval(0, 1, TimeUnit.SECONDS)
                .map(aLong -> {
                    return countTime - aLong.intValue();
                })
                .take(countTime + 1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> {
                    if (integer == 0) {
                        skip();
                    } else {
                        mTvSkip.setText(getString(R.string.format_skip, integer));
                    }
                });
    }

    private void skip() {
        AppRouter.showMainActivity(StartActivity.this);
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(0, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSubscribe != null && !mSubscribe.isDisposed()) {
            mSubscribe.dispose();
        }
    }
}
