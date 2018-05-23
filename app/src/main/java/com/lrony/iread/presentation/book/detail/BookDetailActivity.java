package com.lrony.iread.presentation.book.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lrony.iread.R;
import com.lrony.iread.model.bean.BookDetailBean;
import com.lrony.iread.model.bean.BookDetailRecommendBookBean;
import com.lrony.iread.model.remote.BookApi;
import com.lrony.iread.mvp.MvpActivity;
import com.lrony.iread.pref.AppConfig;
import com.lrony.iread.ui.help.ProgressCancelListener;
import com.lrony.iread.ui.help.ProgressDialogHandler;
import com.lrony.iread.ui.widget.ShapeTextView;
import com.lrony.iread.util.KLog;
import com.lrony.iread.util.ScreenUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liuxiaobin on 18-5-23.
 */

public class BookDetailActivity extends MvpActivity<BookDetailContract.Presenter> implements BookDetailContract.View, ProgressCancelListener {

    private final static String TAG = "BookDetailActivity";

    private static final String K_EXTRA_BOOK = "book";

    private BookApi mBookApi = null;

    //传入的书籍ID
    private String mBookId;

    private ProgressDialogHandler mDialogHandler;
    private BookDetailRecommendAdapter mRecommendAdapter;

    @BindView(R.id.status_bar_view)
    View mStatusBarView;
    @BindView(R.id.iv_cover)
    ImageView mIvCover;
    @BindView(R.id.cover)
    CardView mCover;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_read_count)
    TextView mTvReadCount;
    @BindView(R.id.tv_is_finish)
    ShapeTextView mTvIsFinish;
    @BindView(R.id.tv_author)
    TextView mTvAuthor;
    @BindView(R.id.tv_word_count)
    TextView mTvWordCount;
    @BindView(R.id.tv_type_name)
    TextView mTvTypeName;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout mToolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;
    @BindView(R.id.tv_catalog_title)
    TextView mTvCatalogTitle;
    @BindView(R.id.iv_catalog_arrow)
    AppCompatImageView mIvCatalogArrow;
    @BindView(R.id.tv_update_time)
    TextView mTvUpdateTime;
    @BindView(R.id.ll_book_detail_catalog)
    RelativeLayout mllBookDetailCatalog;
    @BindView(R.id.tv_describe)
    TextView mTvDescribe;
    @BindView(R.id.tv_recommend_more)
    TextView mTvRecommendMore;
    @BindView(R.id.iv_recommend_arrow)
    AppCompatImageView mIvRecommendArrow;
    @BindView(R.id.rl_recommend_more)
    RelativeLayout mRlRecommendMore;
    @BindView(R.id.rv_recommend_book)
    RecyclerView mRvRecommendBook;
    @BindView(R.id.frm_recommend)
    FrameLayout mFrmRecommend;
    @BindView(R.id.tv_word_count_copyright)
    TextView mTvWordCountCopyright;
    @BindView(R.id.tv_create_date_copyright)
    TextView mTvCreateDateCopyright;
    @BindView(R.id.tv_copyright_info)
    TextView mTvCopyrightInfo;
    @BindView(R.id.tv_book_status)
    TextView mTvBookStatus;
    @BindView(R.id.fl_add_bookcase)
    FrameLayout mFlAddBookcase;
    @BindView(R.id.fl_open_book)
    FrameLayout mFlOpenBook;
    @BindView(R.id.fl_download_book)
    FrameLayout mFlDownloadBook;

    public static Intent newIntent(Context context, String str) {
        Intent intent = new Intent(context, BookDetailActivity.class);
        intent.putExtra(K_EXTRA_BOOK, str);
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

        setContentView(R.layout.activity_book_detail);
        ButterKnife.bind(this);
        getPresenter().start();
        mBookId = getIntent().getStringExtra(K_EXTRA_BOOK);
        KLog.d(TAG, "mBookId: " + mBookId);

        initView();
    }

    private void initView() {
        KLog.d(TAG, "initView");
        mDialogHandler = new ProgressDialogHandler(this, this, true);
        mRvRecommendBook.setNestedScrollingEnabled(false);
        //设置layoutManager,根据横屏竖屏分别设置每行item数量
        mRvRecommendBook.setLayoutManager(new GridLayoutManager(this, ScreenUtil.isLAndscape(this) ? 4 : 3));


    }

    @NonNull
    @Override
    public BookDetailContract.Presenter createPresenter() {
        return new BookDetailPresenter();
    }

    @Override
    public void onCancelProgress() {

    }

    @Override
    public void finshLoadBookInfo(BookDetailBean book) {

    }

    @Override
    public void finshLoadBookDetailRecommendBooklist(List<BookDetailRecommendBookBean> books) {

    }

    /**
     * Called when pointer capture is enabled or disabled for the current window.
     *
     * @param hasCapture True if the window has pointer capture.
     */
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
