package com.lrony.iread.presentation.book.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lrony.iread.AppManager;
import com.lrony.iread.AppRouter;
import com.lrony.iread.R;
import com.lrony.iread.model.bean.BookDetailBean;
import com.lrony.iread.model.bean.BookDetailRecommendBookBean;
import com.lrony.iread.model.bean.CollBookBean;
import com.lrony.iread.model.db.DBManger;
import com.lrony.iread.model.remote.BookApi;
import com.lrony.iread.mvp.MvpActivity;
import com.lrony.iread.pref.AppConfig;
import com.lrony.iread.pref.Constant;
import com.lrony.iread.presentation.read.ReadActivity;
import com.lrony.iread.ui.help.ProgressCancelListener;
import com.lrony.iread.ui.help.ProgressDialogHandler;
import com.lrony.iread.ui.help.ToolbarHelper;
import com.lrony.iread.ui.widget.ShapeTextView;
import com.lrony.iread.util.ImageLoader;
import com.lrony.iread.util.KLog;
import com.lrony.iread.util.ScreenUtil;
import com.lrony.iread.util.StringUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liuxiaobin on 18-5-23.
 */

public class BookDetailActivity extends MvpActivity<BookDetailContract.Presenter> implements BookDetailContract.View, ProgressCancelListener, View.OnClickListener {

    private final static String TAG = "BookDetailActivity";

    public static final String RESULT_IS_COLLECTED = "result_is_collected";
    private static final String K_EXTRA_BOOK = "book";
    private static final int REQUEST_READ = 1;

    private BookApi mBookApi = null;

    //传入的书籍ID
    private String mBookId;

    private ProgressDialogHandler mDialogHandler;
    private ProgressDialogHandler mAddDialogHandler;
    private BookDetailRecommendAdapter mRecommendAdapter;
    private BookDetailBean mBook;
    private CollBookBean mCollBookBean;
    private List<BookDetailRecommendBookBean> mRecommendBooks = new ArrayList<>();

    private boolean mInfoLoadOK = false;
    private boolean mRecommendLoadOK = false;
    private boolean isCollected = false;

    @BindView(R.id.iv_cover)
    ImageView mIvCover;
    @BindView(R.id.tv_read_count)
    TextView mTvReadCount;
    @BindView(R.id.tv_is_finish)
    ShapeTextView mTvIsFinish;
    @BindView(R.id.tv_author)
    TextView mTvAuthor;
    @BindView(R.id.tv_word_count)
    TextView mTvWordCount;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_catalog_title)
    TextView mTvCatalogTitle;
    @BindView(R.id.tv_update_time)
    TextView mTvUpdateTime;
    @BindView(R.id.ll_book_detail_catalog)
    RelativeLayout mllBookDetailCatalog;
    @BindView(R.id.tv_describe)
    TextView mTvDescribe;
    @BindView(R.id.tv_recommend_more)
    TextView mTvRecommendMore;
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
    @BindView(R.id.tv_book_status)
    TextView mTvBookStatus;

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
        initListener();

        getPresenter().loadBookInfo(mBookId);
        getPresenter().loadBookDetailRecommendBooklist(mBookId);
    }

    private void initListener() {
        KLog.d(TAG, "initListener");

        bindOnClickLister(this, R.id.fl_add_bookcase, R.id.fl_download_book,
                R.id.fl_open_book, R.id.ll_book_detail_catalog, R.id.rl_recommend_more);
        mTvDescribe.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater menuInflater = mode.getMenuInflater();
                menuInflater.inflate(R.menu.textview_selection_action_menu, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.informal_search:

                        if (mTvDescribe == null) return false;
                        int min = 0;
                        int max = mTvDescribe.length();
                        if (mTvDescribe.isFocused()) {
                            final int selStart = mTvDescribe.getSelectionStart();
                            final int selEnd = mTvDescribe.getSelectionEnd();

                            min = Math.max(0, Math.min(selStart, selEnd));
                            max = Math.max(0, Math.max(selStart, selEnd));
                        } else {
                            KLog.d(TAG, "onActionItemClicked: mTvDescribe not focused");
                        }
                        String content = String.valueOf(mTvDescribe.getText().subSequence(min, max));
                        KLog.d(TAG, "onActionItemClicked: select content is " + content);
                        if (!TextUtils.isEmpty(content)) {
                            AppRouter.showSearchActivity(BookDetailActivity.this, content);
                        } else {
                            KLog.d(TAG, "onActionItemClicked: select content is empty");
                        }


                        break;
                }
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });

        mRecommendAdapter.setOnItemClickListener((adapter, view, position) ->
                AppRouter.showRecommendActivity(
                        BookDetailActivity.this, mRecommendBooks.get(position).get_id())
        );

        mIvCover.setOnClickListener(this);
        mRlRecommendMore.setOnClickListener(this);
    }

    private void initView() {
        KLog.d(TAG, "initView");
        mDialogHandler = new ProgressDialogHandler(this, this, true);
        mAddDialogHandler = new ProgressDialogHandler(this, this, false);
        mRvRecommendBook.setNestedScrollingEnabled(false);
        //设置layoutManager,根据横屏竖屏分别设置每行item数量
        mRvRecommendBook.setLayoutManager(new GridLayoutManager(
                this, ScreenUtil.isLAndscape(this) ? 4 : 3));
        mRecommendAdapter = new BookDetailRecommendAdapter(
                this, mRecommendBooks, ScreenUtil.isLAndscape(this) ? 8 : 6);
        mRvRecommendBook.setAdapter(mRecommendAdapter);
    }

    @Override
    public void loading() {
        super.loading();
        if (mDialogHandler != null) {
            mDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        } else {
            KLog.e(TAG, "loading mDialogHandler is null");
        }
    }

    @NonNull
    @Override
    public BookDetailContract.Presenter createPresenter() {
        return new BookDetailPresenter();
    }

    @Override
    public void onCancelProgress() {
        KLog.d(TAG, "onCancelProgress");
        if (mDialogHandler != null) {
            mDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
        }
        AppManager.getInstance().finishActivity();
    }

    @Override
    public void finshLoadBookInfo(BookDetailBean book) {
        KLog.d(TAG, "finshLoadBookInfo");
        if (null == book) {
            AppManager.getInstance().finishActivity(this);
        }
        mBook = book;
        refreshBookInfo();
        mInfoLoadOK = true;
        jugeCloseDialog();

    }

    @Override
    public void finshLoadBookDetailRecommendBooklist(List<BookDetailRecommendBookBean> books) {
        mRecommendBooks.clear();
        mRecommendBooks.addAll(books);
        mRvRecommendBook.setAdapter(mRecommendAdapter);
        mRecommendLoadOK = true;
        jugeCloseDialog();
    }

    @Override
    public void addBookLoading() {
        if (mAddDialogHandler != null) {
            mAddDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        } else {
            KLog.e(TAG, "loading mAddDialogHandler is null");
        }
    }

    @Override
    public void addBookError() {
        if (mAddDialogHandler != null) {
            mAddDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
        }
        isCollected = false;
        showToast("加入书架失败，请检查网络");
    }

    @Override
    public void succeed() {
        super.succeed();
        if (mAddDialogHandler != null) {
            mAddDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
        }
        isCollected = true;

    }

    /**
     * Called when pointer capture is enabled or disabled for the current window.
     *
     * @param hasCapture True if the window has pointer capture.
     */
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void refreshBookInfo() {
        KLog.d(TAG, "refreshBookInfo");
        if (mBook == null) return;

        ImageLoader.load(this, Constant.IMG_BASE_URL + mBook.getCover(), mIvCover);
        ToolbarHelper.initToolbar(this, R.id.toolbar, true, mBook.getTitle());
        mTvReadCount.setText(StringUtils.formatCount(mBook.getPostCount()) + "人看过");
        String isFinished = !mBook.isIsSerial() ? getString(R.string.bookdetail_finished) : getString(R.string.bookdetail_not_finished);
        mTvAuthor.setText(mBook.getMinorCate() + " | " + mBook.getAuthor());
        mTvIsFinish.setText(isFinished);
        mTvWordCount.setText(isFinished + " | " + StringUtils.formatCount(mBook.getWordCount()) + "字");
        mTvWordCountCopyright.setText(mTvWordCountCopyright.getText() + StringUtils.formatCount(mBook.getWordCount()) + "字");
        mTvCatalogTitle.setText(getString(R.string.bookdetail_newchapter) + mBook.getLastChapter());
        try {
            mTvUpdateTime.setText(StringUtils.formatSomeAgo(StringUtils.dealDateFormat(mBook.getUpdated())) + "更新");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mTvCreateDateCopyright.setText(mTvCreateDateCopyright.getText().toString() + mBook.getRetentionRatio() + "%");
        mTvDescribe.setText(mBook.getLongIntro());

        mCollBookBean = DBManger.getInstance().loadBookTbById(mBookId);
        //判断是否收藏
        if (mCollBookBean != null) {
            isCollected = true;
        } else {
            mCollBookBean = mBook.getCollBookBean();
        }

        refreshBookAddStatus();
    }

    private void refreshBookAddStatus() {
        KLog.d(TAG, "refreshBookAddStatus");
        if (DBManger.getInstance().hasBookTb(mBook.getCollBookBean())) {
            Log.d(TAG, "refreshBookAddStatus: have it");
            mTvBookStatus.setText(R.string.bookdetail_add_book_have);
        } else {
            Log.d(TAG, "refreshBookAddStatus: not have it");
            mTvBookStatus.setText(R.string.bookdetail_add_bookcase);
        }
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_add_bookcase:
                KLog.d(TAG, "onClick: fl_add_bookcase");
//                boolean hasBook = DBManger.getInstance().hasBookTb(mBookId);
//                KLog.d(TAG, "onClick: fl_add_bookcase hasBook = " + hasBook);
//                if (hasBook) {
//                    showComfirmDialog();
//                } else {
//                    DBManger.getInstance().saveBookTb(mBook.getCollBookBean());
//                    refreshBookAddStatus();
//                }

                //点击存储
                if (isCollected) {
                    showComfirmDialog();
                } else {
                    getPresenter().addToBookShelf(mCollBookBean);
                }

                break;
            case R.id.fl_download_book:
                KLog.d(TAG, "onClick: fl_download_book");
                break;
            case R.id.fl_open_book:
                KLog.d(TAG, "onClick: fl_open_book");
                startActivityForResult(new Intent(this, ReadActivity.class)
                        .putExtra(ReadActivity.EXTRA_IS_COLLECTED, isCollected)
                        .putExtra(ReadActivity.EXTRA_COLL_BOOK, mCollBookBean), REQUEST_READ);
                break;
            case R.id.ll_book_detail_catalog:
                KLog.d(TAG, "onClick: ll_book_detail_catalog");
                ReadActivity.startActivity(this, mCollBookBean, true);
                break;
            case R.id.rl_recommend_more:
                KLog.d(TAG, "onClick: rl_recommend_more");
                AppRouter.showRecommendActivity(
                        BookDetailActivity.this, mBookId);
                break;
            case R.id.iv_cover:
                KLog.d(TAG, "onClick: iv_cover");
                break;
        }
    }

    private void jugeCloseDialog() {
        KLog.d(TAG, "jugeCloseDialog mInfoLoadOK: " + mInfoLoadOK + ",mRecommendLoadOK: " + mRecommendLoadOK);
        if (mInfoLoadOK == true && mRecommendLoadOK == true) {
            if (mDialogHandler != null) {
                mDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            }
        }
    }

    private void showComfirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.bookdetail_is_delete_book) + "《" + mBook.getTitle() + "》");
        builder.setNegativeButton(R.string.commom_cancel, null);
        builder.setPositiveButton(R.string.commom_confirm, (dialog, which) -> {
            DBManger.getInstance().deleteBookTb(mBookId);
            isCollected = false;
            refreshBookAddStatus();
        });
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bookdetail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void error() {
        super.error();
        onCancelProgress();
        showToast("书籍打开失败");
    }

    @Override
    public void complete() {
        super.complete();
        refreshBookAddStatus();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(K_EXTRA_BOOK, mBookId);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //如果进入阅读页面收藏了，页面结束的时候，就需要返回改变收藏按钮
        if (requestCode == REQUEST_READ) {
            if (data == null) {
                return;
            }

            isCollected = data.getBooleanExtra(RESULT_IS_COLLECTED, false);
            refreshBookAddStatus();
        }
    }
}
