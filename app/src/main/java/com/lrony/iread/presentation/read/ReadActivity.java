package com.lrony.iread.presentation.read;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.lrony.iread.AppRouter;
import com.lrony.iread.R;
import com.lrony.iread.model.bean.BookChapterBean;
import com.lrony.iread.model.bean.CollBookBean;
import com.lrony.iread.model.db.DBManger;
import com.lrony.iread.mvp.MvpActivity;
import com.lrony.iread.pref.Constant;
import com.lrony.iread.pref.PreferencesHelper;
import com.lrony.iread.pref.ReadSettingManager;
import com.lrony.iread.presentation.book.detail.BookDetailActivity;
import com.lrony.iread.ui.adapter.CategoryAdapter;
import com.lrony.iread.ui.help.ReadSettingDialog;
import com.lrony.iread.ui.widget.page.PageLoader;
import com.lrony.iread.ui.widget.page.PageView;
import com.lrony.iread.ui.widget.page.TxtChapter;
import com.lrony.iread.util.BrightnessUtils;
import com.lrony.iread.util.KLog;
import com.lrony.iread.util.RxUtils;
import com.lrony.iread.util.ScreenUtil;
import com.lrony.iread.util.StringUtils;
import com.lrony.iread.util.SystemBarUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;

import static android.support.v4.view.ViewCompat.LAYER_TYPE_SOFTWARE;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class ReadActivity extends MvpActivity<ReadContract.Presenter> implements ReadContract.View {

    private static final String TAG = "ReadActivity";

    public static final int REQUEST_MORE_SETTING = 1;
    public static final String EXTRA_COLL_BOOK = "extra_coll_book";
    public static final String EXTRA_IS_COLLECTED = "extra_is_collected";

    // 注册 Brightness 的 uri
    private final Uri BRIGHTNESS_MODE_URI =
            Settings.System.getUriFor(Settings.System.SCREEN_BRIGHTNESS_MODE);
    private final Uri BRIGHTNESS_URI =
            Settings.System.getUriFor(Settings.System.SCREEN_BRIGHTNESS);
    private final Uri BRIGHTNESS_ADJ_URI =
            Settings.System.getUriFor("screen_auto_brightness_adj");

    private static final int WHAT_CATEGORY = 1;
    private static final int WHAT_CHAPTER = 2;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.read_dl_slide)
    DrawerLayout mDlSlide;
    /*************top_menu_view*******************/
    @BindView(R.id.read_abl_top_menu)
    AppBarLayout mAblTopMenu;
    @BindView(R.id.read_tv_community)
    TextView mTvCommunity;
    @BindView(R.id.read_tv_brief)
    TextView mTvBrief;
    /***************content_view******************/
    @BindView(R.id.read_pv_page)
    PageView mPvPage;
    /***************bottom_menu_view***************************/
    @BindView(R.id.read_tv_page_tip)
    TextView mTvPageTip;

    @BindView(R.id.read_ll_bottom_menu)
    LinearLayout mLlBottomMenu;
    @BindView(R.id.read_tv_pre_chapter)
    TextView mTvPreChapter;
    @BindView(R.id.read_sb_chapter_progress)
    SeekBar mSbChapterProgress;
    @BindView(R.id.read_tv_next_chapter)
    TextView mTvNextChapter;
    @BindView(R.id.read_tv_category)
    TextView mTvCategory;
    @BindView(R.id.read_tv_night_mode)
    TextView mTvNightMode;
    /*    @BindView(R.id.read_tv_download)
        TextView mTvDownload;*/
    @BindView(R.id.read_tv_setting)
    TextView mTvSetting;
    /***************left slide*******************************/
    @BindView(R.id.read_iv_category)
    ListView mLvCategory;
    /*****************view******************/
    private ReadSettingDialog mSettingDialog;
    private PageLoader mPageLoader;
    private Animation mTopInAnim;
    private Animation mTopOutAnim;
    private Animation mBottomInAnim;
    private Animation mBottomOutAnim;
    private CategoryAdapter mCategoryAdapter;
    private CollBookBean mCollBook;
    //控制屏幕常亮
    private PowerManager.WakeLock mWakeLock;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case WHAT_CATEGORY:
                    mLvCategory.setSelection(mPageLoader.getChapterPos());
                    break;
                case WHAT_CHAPTER:
                    mPageLoader.openChapter();
                    break;
            }
        }
    };
    // 接收电池信息和时间更新的广播
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)) {
                int level = intent.getIntExtra("level", 0);
                mPageLoader.updateBattery(level);
            }
            // 监听分钟的变化
            else if (intent.getAction().equals(Intent.ACTION_TIME_TICK)) {
                mPageLoader.updateTime();
            }
        }
    };

    // 亮度调节监听
    // 由于亮度调节没有 Broadcast 而是直接修改 ContentProvider 的。所以需要创建一个 Observer 来监听 ContentProvider 的变化情况。
    private ContentObserver mBrightObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            onChange(selfChange, null);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            super.onChange(selfChange);

            // 判断当前是否跟随屏幕亮度，如果不是则返回
            if (selfChange || !mSettingDialog.isBrightFollowSystem()) return;

            // 如果系统亮度改变，则修改当前 Activity 亮度
            if (BRIGHTNESS_MODE_URI.equals(uri)) {
                Log.d(TAG, "亮度模式改变");
            } else if (BRIGHTNESS_URI.equals(uri) && !BrightnessUtils.isAutoBrightness(ReadActivity.this)) {
                Log.d(TAG, "亮度模式为手动模式 值改变");
                BrightnessUtils.setBrightness(ReadActivity.this, BrightnessUtils.getScreenBrightness(ReadActivity.this));
            } else if (BRIGHTNESS_ADJ_URI.equals(uri) && BrightnessUtils.isAutoBrightness(ReadActivity.this)) {
                Log.d(TAG, "亮度模式为自动模式 值改变");
                BrightnessUtils.setDefaultBrightness(ReadActivity.this);
            } else {
                Log.d(TAG, "亮度调整 其他");
            }
        }
    };

    /***************params*****************/
    private boolean isCollected = false; // isFromSDCard
    private boolean isNightMode = false;
    private boolean isFullScreen = false;
    private boolean isRegistered = false;

    private String mBookId;

    public static void startActivity(Context context, CollBookBean collBook, boolean isCollected) {
        context.startActivity(new Intent(context, ReadActivity.class)
                .putExtra(EXTRA_IS_COLLECTED, isCollected)
                .putExtra(EXTRA_COLL_BOOK, collBook));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        KLog.d(TAG, "onCreate");
        setContentView(R.layout.activity_read);
        // 使用ButterKnife代替findview
        ButterKnife.bind(this);
        // 代码规范，必须调用
        getPresenter().start();

        initData();
        initToolbar();
        initWidget();
        initListener();
        processLogic();
    }

    private void initData() {
        KLog.d(TAG, "initData");
        mCollBook = getIntent().getParcelableExtra(EXTRA_COLL_BOOK);
        isCollected = getIntent().getBooleanExtra(EXTRA_IS_COLLECTED, false);
        isNightMode = ReadSettingManager.getInstance().isNightMode();
        isFullScreen = PreferencesHelper.getInstance().isFullscreen();

        mBookId = mCollBook.get_id();
    }

    private void initToolbar() {
        KLog.d(TAG, "initToolbar");
        //设置标题
        mToolbar.setTitle(mCollBook.getTitle());
        //半透明化StatusBar
        SystemBarUtils.transparentStatusBar(this);
    }

    private void initWidget() {
        KLog.d(TAG, "initWidget");
        // 如果 API < 18 取消硬件加速
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2
                && Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            mPvPage.setLayerType(LAYER_TYPE_SOFTWARE, null);
        }

        //获取页面加载器
        mPageLoader = mPvPage.getPageLoader(mCollBook);
        //禁止滑动展示DrawerLayout
        mDlSlide.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        //侧边打开后，返回键能够起作用
        mDlSlide.setFocusableInTouchMode(false);
        mSettingDialog = new ReadSettingDialog(this, mPageLoader);

        setUpAdapter();

        //夜间模式按钮的状态
        toggleNightMode();

        //注册广播
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        intentFilter.addAction(Intent.ACTION_TIME_TICK);
        registerReceiver(mReceiver, intentFilter);

        //设置当前Activity的Brightness
        if (ReadSettingManager.getInstance().isBrightnessAuto()) {
            BrightnessUtils.setDefaultBrightness(this);
        } else {
            BrightnessUtils.setBrightness(this, ReadSettingManager.getInstance().getBrightness());
        }

        //初始化屏幕常亮类
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "keep bright");

        //隐藏StatusBar
        mPvPage.post(
                () -> hideSystemBar()
        );

        //初始化TopMenu
        initTopMenu();

        //初始化BottomMenu
        initBottomMenu();
    }

    private void initListener() {
        mPageLoader.setOnPageChangeListener(
                new PageLoader.OnPageChangeListener() {

                    @Override
                    public void onChapterChange(int pos) {
                        KLog.d(TAG, "mPageLoader onChapterChange pos = " + pos);
                        mCategoryAdapter.setChapter(pos);
                    }

                    @Override
                    public void requestChapters(List<TxtChapter> requestChapters) {
                        KLog.d(TAG, "mPageLoader requestChapters");
                        getPresenter().loadChapter(mBookId, requestChapters);
                        mHandler.sendEmptyMessage(WHAT_CATEGORY);
                        //隐藏提示
                        mTvPageTip.setVisibility(GONE);
                    }

                    @Override
                    public void onCategoryFinish(List<TxtChapter> chapters) {
                        KLog.d(TAG, "mPageLoader onCategoryFinish");
                        for (TxtChapter chapter : chapters) {
                            chapter.setTitle(chapter.getTitle());
                        }
                        mCategoryAdapter.refreshItems(chapters);
                    }

                    @Override
                    public void onPageCountChange(int count) {
                        KLog.d(TAG, "mPageLoader onPageCountChange count = " + count);
                        mSbChapterProgress.setMax(Math.max(0, count - 1));
                        mSbChapterProgress.setProgress(0);
                        // 如果处于错误状态，那么就冻结使用
                        if (mPageLoader.getPageStatus() == PageLoader.STATUS_LOADING
                                || mPageLoader.getPageStatus() == PageLoader.STATUS_ERROR) {
                            mSbChapterProgress.setEnabled(false);
                        } else {
                            mSbChapterProgress.setEnabled(true);
                        }
                    }

                    @Override
                    public void onPageChange(int pos) {
                        KLog.d(TAG, "mPageLoader onPageChange");
                        mSbChapterProgress.post(
                                () -> mSbChapterProgress.setProgress(pos)
                        );
                    }
                }
        );

        mSbChapterProgress.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        KLog.d(TAG, "mSbChapterProgress onProgressChanged");
                        if (mLlBottomMenu.getVisibility() == VISIBLE) {
                            //显示标题
                            mTvPageTip.setText((progress + 1) + "/" + (mSbChapterProgress.getMax() + 1));
                            mTvPageTip.setVisibility(VISIBLE);
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        KLog.d(TAG, "mSbChapterProgress onStopTrackingTouch");
                        //进行切换
                        int pagePos = mSbChapterProgress.getProgress();
                        if (pagePos != mPageLoader.getPagePos()) {
                            mPageLoader.skipToPage(pagePos);
                        }
                        //隐藏提示
                        mTvPageTip.setVisibility(GONE);
                    }
                }
        );

        mPvPage.setTouchListener(new PageView.TouchListener() {
            @Override
            public boolean onTouch() {
                KLog.d(TAG, "mPvPage onTouch");
                return !hideReadMenu();
            }

            @Override
            public void center() {
                KLog.d(TAG, "mPvPage center");
                toggleMenu(true);
            }

            @Override
            public void prePage() {
            }

            @Override
            public void nextPage() {
            }

            @Override
            public void cancel() {
            }
        });

        mLvCategory.setOnItemClickListener(
                (parent, view, position, id) -> {
                    mDlSlide.closeDrawer(Gravity.START);
                    mPageLoader.skipToChapter(position);
                }
        );

        mTvCategory.setOnClickListener(
                (v) -> {
                    //移动到指定位置
                    if (mCategoryAdapter.getCount() > 0) {
                        mLvCategory.setSelection(mPageLoader.getChapterPos());
                    }
                    //切换菜单
                    toggleMenu(true);
                    //打开侧滑动栏
                    mDlSlide.openDrawer(Gravity.START);
                }
        );
        mTvSetting.setOnClickListener(
                (v) -> {
                    toggleMenu(false);
                    mSettingDialog.show();
                }
        );

        mTvPreChapter.setOnClickListener(
                (v) -> {
                    if (mPageLoader.skipPreChapter()) {
                        mCategoryAdapter.setChapter(mPageLoader.getChapterPos());
                    }
                }
        );

        mTvNextChapter.setOnClickListener(
                (v) -> {
                    if (mPageLoader.skipNextChapter()) {
                        mCategoryAdapter.setChapter(mPageLoader.getChapterPos());
                    }
                }
        );

        mTvNightMode.setOnClickListener(
                (v) -> {
                    if (isNightMode) {
                        isNightMode = false;
                    } else {
                        isNightMode = true;
                    }
                    mPageLoader.setNightMode(isNightMode);
                    toggleNightMode();
                }
        );

        mTvBrief.setOnClickListener(
                (v) -> AppRouter.showBookDetailActivity(this, mBookId)
        );

        mTvCommunity.setOnClickListener(
                (v) -> {
                    //TODO 打开社区？
//                    Intent intent = new Intent(this, CommunityActivity.class);
//                    startActivity(intent);
                }
        );

        mSettingDialog.setOnDismissListener(
                dialog -> hideSystemBar()
        );
    }

    private void processLogic() {
        KLog.d(TAG, "processLogic isCollected = " + isCollected);
        // 如果是已经收藏的，那么就从数据库中获取目录
        if (isCollected) {
            DBManger.getInstance()
                    .getBookChaptersInRx(mBookId)
                    .compose(RxUtils::toSimpleSingle)
                    .subscribe(
                            (bookChapterBeen, throwable) -> {
                                // 设置 CollBook
                                mPageLoader.getCollBook().setBookChapters(bookChapterBeen);
                                // 刷新章节列表
                                mPageLoader.refreshChapterList();
                                // 如果是网络小说并被标记更新的，则从网络下载目录
                                if (mCollBook.getIsUpdate() && !mCollBook.getIsLocal()) {
                                    getPresenter().loadCategory(mBookId);
                                }
                            }
                    );
        } else {
            // 从网络中获取目录
            getPresenter().loadCategory(mBookId);
        }
    }

    @NonNull
    @Override
    public ReadContract.Presenter createPresenter() {
        return new ReadPresenter();
    }

    private void toggleNightMode() {
        KLog.d(TAG, "toggleNightMode");
        if (isNightMode) {
            mTvNightMode.setText(R.string.nb_mode_morning);
            Drawable drawable = ContextCompat.getDrawable(this, R.drawable.ic_read_menu_morning);
            mTvNightMode.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
        } else {
            mTvNightMode.setText(R.string.nb_mode_night);
            Drawable drawable = ContextCompat.getDrawable(this, R.drawable.ic_read_menu_night);
            mTvNightMode.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
        }
    }

    private void initTopMenu() {
        KLog.d(TAG, "initTopMenu");
        if (Build.VERSION.SDK_INT >= 19) {
            mAblTopMenu.setPadding(0, ScreenUtil.getStatusBarHeight(), 0, 0);
        }
    }

    private void initBottomMenu() {
        KLog.d(TAG, "initBottomMenu");
        //判断是否全屏
        if (PreferencesHelper.getInstance().isFullscreen()) {
            //还需要设置mBottomMenu的底部高度
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mLlBottomMenu.getLayoutParams();
            params.bottomMargin = ScreenUtil.getNavigationBarHeight();
            mLlBottomMenu.setLayoutParams(params);
        } else {
            //设置mBottomMenu的底部距离
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mLlBottomMenu.getLayoutParams();
            params.bottomMargin = 0;
            mLlBottomMenu.setLayoutParams(params);
        }
    }

    private void setUpAdapter() {
        KLog.d(TAG, "setUpAdapter");
        mCategoryAdapter = new CategoryAdapter();
        mLvCategory.setAdapter(mCategoryAdapter);
        mLvCategory.setFastScrollEnabled(true);
    }

    private void showSystemBar() {
        KLog.d(TAG, "showSystemBar");
        //显示
        SystemBarUtils.showUnStableStatusBar(this);
        if (isFullScreen) {
            SystemBarUtils.showUnStableNavBar(this);
        }
    }

    private void hideSystemBar() {
        KLog.d(TAG, "hideSystemBar");
        //隐藏
        SystemBarUtils.hideStableStatusBar(this);
        if (isFullScreen) {
            SystemBarUtils.hideStableNavBar(this);
        }
    }

    /**
     * 隐藏阅读界面的菜单显示
     *
     * @return 是否隐藏成功
     */
    private boolean hideReadMenu() {
        KLog.d(TAG, "hideReadMenu");
        hideSystemBar();
        if (mAblTopMenu.getVisibility() == VISIBLE) {
            toggleMenu(true);
            return true;
        } else if (mSettingDialog.isShowing()) {
            mSettingDialog.dismiss();
            return true;
        }
        return false;
    }

    //初始化菜单动画
    private void initMenuAnim() {
        KLog.d(TAG, "initMenuAnim");
        if (mTopInAnim != null) return;

        mTopInAnim = AnimationUtils.loadAnimation(this, R.anim.slide_top_in);
        mTopOutAnim = AnimationUtils.loadAnimation(this, R.anim.slide_top_out);
        mBottomInAnim = AnimationUtils.loadAnimation(this, R.anim.slide_bottom_in);
        mBottomOutAnim = AnimationUtils.loadAnimation(this, R.anim.slide_bottom_out);
        //退出的速度要快
        mTopOutAnim.setDuration(200);
        mBottomOutAnim.setDuration(200);
    }

    /**
     * 切换菜单栏的可视状态
     * 默认是隐藏的
     */
    private void toggleMenu(boolean hideStatusBar) {
        KLog.d(TAG, "toggleMenu");
        initMenuAnim();

        if (mAblTopMenu.getVisibility() == View.VISIBLE) {
            //关闭
            mAblTopMenu.startAnimation(mTopOutAnim);
            mLlBottomMenu.startAnimation(mBottomOutAnim);
            mAblTopMenu.setVisibility(GONE);
            mLlBottomMenu.setVisibility(GONE);
            mTvPageTip.setVisibility(GONE);

            if (hideStatusBar) {
                hideSystemBar();
            }
        } else {
            mAblTopMenu.setVisibility(View.VISIBLE);
            mLlBottomMenu.setVisibility(View.VISIBLE);
            mAblTopMenu.startAnimation(mTopInAnim);
            mLlBottomMenu.startAnimation(mBottomInAnim);

            showSystemBar();
        }
    }

    // 注册亮度观察者
    private void registerBrightObserver() {
        KLog.d(TAG, "registerBrightObserver");
        try {
            if (mBrightObserver != null) {
                if (!isRegistered) {
                    final ContentResolver cr = getContentResolver();
                    cr.unregisterContentObserver(mBrightObserver);
                    cr.registerContentObserver(BRIGHTNESS_MODE_URI, false, mBrightObserver);
                    cr.registerContentObserver(BRIGHTNESS_URI, false, mBrightObserver);
                    cr.registerContentObserver(BRIGHTNESS_ADJ_URI, false, mBrightObserver);
                    isRegistered = true;
                }
            }
        } catch (Throwable throwable) {
            KLog.e(TAG, "register mBrightObserver error! " + throwable);
        }
    }

    //解注册
    private void unregisterBrightObserver() {
        KLog.d(TAG, "unregisterBrightObserver");
        try {
            if (mBrightObserver != null) {
                if (isRegistered) {
                    getContentResolver().unregisterContentObserver(mBrightObserver);
                    isRegistered = false;
                }
            }
        } catch (Throwable throwable) {
            KLog.e(TAG, "unregister BrightnessObserver error! " + throwable);
        }
    }

    @Override
    public void showCategory(List<BookChapterBean> bookChapterList) {
        KLog.d(TAG, "showCategory");
        mPageLoader.getCollBook().setBookChapters(bookChapterList);
        mPageLoader.refreshChapterList();

        // 如果是目录更新的情况，那么就需要存储更新数据
        if (mCollBook.getIsUpdate() && isCollected) {
            DBManger.getInstance().saveBookChaptersWithAsync(bookChapterList);

        }
    }

    @Override
    public void finishChapter() {
        KLog.d(TAG, "finishChapter");
        if (mPageLoader.getPageStatus() == PageLoader.STATUS_LOADING) {
            mHandler.sendEmptyMessage(WHAT_CHAPTER);
        }
        // 当完成章节的时候，刷新列表
        mCategoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void errorChapter() {
        KLog.d(TAG, "errorChapter");
        if (mPageLoader.getPageStatus() == PageLoader.STATUS_LOADING) {
            mPageLoader.chapterError();
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                KLog.d(TAG, "onKeyUp KEYCODE_BACK");
                if (mAblTopMenu.getVisibility() == View.VISIBLE) {
                    // 非全屏下才收缩，全屏下直接退出
                    if (!PreferencesHelper.getInstance().isFullscreen()) {
                        toggleMenu(true);
                        return true;
                    }
                } else if (mSettingDialog.isShowing()) {
                    mSettingDialog.dismiss();
                    return true;
                } else if (mDlSlide.isDrawerOpen(Gravity.START)) {
                    mDlSlide.closeDrawer(Gravity.START);
                    return true;
                }

                if (!mCollBook.getIsLocal() && !isCollected
                        && !mCollBook.getBookChapters().isEmpty()) {
                    AlertDialog alertDialog = new AlertDialog.Builder(this)
                            .setTitle("加入书架")
                            .setMessage("喜欢《" + mCollBook.getTitle() + "》就加入书架吧")
                            .setPositiveButton("确定", (dialog, which) -> {
                                //设置为已收藏
                                isCollected = true;
                                //设置阅读时间
                                mCollBook.setLastRead(StringUtils.
                                        dateConvert(System.currentTimeMillis(), "yyyy-MM-dd'T'HH:mm:ss"));

                                DBManger.getInstance().saveCollBookWithAsync(mCollBook);

                                exit();
                            })
                            .setNegativeButton("取消", (dialog, which) -> {
                                exit();
                            }).create();
                    alertDialog.show();
                    return true;
                } else {
                    exit();
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
    }

    // 退出
    private void exit() {
        KLog.d(TAG, "exit");
        // 返回给BookDetail。
        Intent result = new Intent();
        result.putExtra(BookDetailActivity.RESULT_IS_COLLECTED, isCollected);
        setResult(Activity.RESULT_OK, result);
        // 退出
        super.onBackPressed();
    }

    @Override
    protected void onStart() {
        super.onStart();
        KLog.d(TAG, "onStart");
        registerBrightObserver();
    }

    @Override
    protected void onResume() {
        super.onResume();
        KLog.d(TAG, "onResume");
        mWakeLock.acquire();
    }

    @Override
    protected void onPause() {
        super.onPause();
        KLog.d(TAG, "onPause");
        mWakeLock.release();
        if (isCollected) {
            mPageLoader.saveRecord();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        KLog.d(TAG, "onStop");
        unregisterBrightObserver();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        KLog.d(TAG, "onDestroy");
        unregisterReceiver(mReceiver);

        mHandler.removeMessages(WHAT_CATEGORY);
        mHandler.removeMessages(WHAT_CHAPTER);

        mPageLoader.closeBook();
        mPageLoader = null;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean isVolumeTurnPage = PreferencesHelper.getInstance().isSupportVolumekey();
        KLog.d(TAG, "isVolumeTurnPage = " + isVolumeTurnPage);
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (isVolumeTurnPage) {
                    return mPageLoader.skipToPrePage();
                }
                break;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (isVolumeTurnPage) {
                    return mPageLoader.skipToNextPage();
                }
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        SystemBarUtils.hideStableStatusBar(this);
        if (requestCode == REQUEST_MORE_SETTING) {
            boolean fullScreen = PreferencesHelper.getInstance().isFullscreen();
            KLog.d(TAG, "fullScreen = " + fullScreen);
            if (isFullScreen != fullScreen) {
                isFullScreen = fullScreen;
                // 刷新BottomMenu
                initBottomMenu();
            }

            // 设置显示状态
            if (isFullScreen) {
                SystemBarUtils.hideStableNavBar(this);
            } else {
                SystemBarUtils.showStableNavBar(this);
            }
        }
    }
}
