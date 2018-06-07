package com.lrony.iread.presentation.start;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lrony.iread.AppRouter;
import com.lrony.iread.BuildConfig;
import com.lrony.iread.R;
import com.lrony.iread.base.BaseActivity;
import com.lrony.iread.ui.help.OnMultiClickListener;
import com.lrony.iread.util.DisplayUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class StartActivity extends BaseActivity {

    private static final String TAG = "StartActivity";

    @BindView(R.id.tv_skip)
    TextView mTvSkip;

    private Disposable mSubscribe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        initDisPlay();
        startCountDown(3);
    }

    private void initDisPlay() {
        mTvSkip = findViewById(R.id.tv_skip);
        mTvSkip.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                skip();
            }
        });
        View contentView = findViewById(R.id.content_view);
        if (DisplayUtil.hasVirtualNavigationBar(this)) {
            contentView.setPadding(0, 0, 0, DisplayUtil.getNavigationBarHeight(this));
        }
        TextView tvVersionName = findViewById(R.id.tv_version_name);
        tvVersionName.setText(BuildConfig.VERSION_NAME);
        LinearLayout llWelcome = (LinearLayout) findViewById(R.id.ll_welcome);
        TextView tvDate = findViewById(R.id.tv_date);
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy年MM月dd日，EEEE");
        tvDate.setText(format2.format(new Date()));
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.alpha_in);
        llWelcome.startAnimation(animation);
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
