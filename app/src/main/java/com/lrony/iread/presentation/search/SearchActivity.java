package com.lrony.iread.presentation.search;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.classic.common.MultipleStatusView;
import com.lrony.iread.AppRouter;
import com.lrony.iread.R;
import com.lrony.iread.model.bean.packages.SearchBookPackage;
import com.lrony.iread.model.db.DBManger;
import com.lrony.iread.model.db.table.SearchHistory;
import com.lrony.iread.mvp.MvpActivity;
import com.lrony.iread.pref.AppConfig;
import com.lrony.iread.ui.help.OnMultiClickListener;
import com.lrony.iread.ui.help.OnMultiItemClickListener;
import com.lrony.iread.ui.help.RecyclerViewItemDecoration;
import com.lrony.iread.util.DensityUtil;
import com.lrony.iread.util.InputMethodUtils;
import com.lrony.iread.util.KLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends MvpActivity<SearchContract.Presenter> implements SearchContract.View {

    private static final String TAG = "SearchActivity";

    private static final String K_KEYWORD = "search";

    @BindView(R.id.multiple_status_view)
    MultipleStatusView mStatusView;
    @BindView(R.id.iv_arrow_back)
    ImageView mIvArrowBack;
    @BindView(R.id.iv_action_search)
    ImageView mIvActionSearch;
    @BindView(R.id.iv_action_clear)
    ImageView mIvActionClear;
    @BindView(R.id.et_search)
    EditText mEtSearch;
    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;

    // 传入的搜索内容，可空
    private String mDefaultSearch;

    // 书籍名补全列表
    private List<String> mKeyWords = new ArrayList<>();
    // 书籍列表
    private List<SearchBookPackage.BooksBean> mSearchBooks = new ArrayList<>();
    // 搜索历史
    private List<SearchHistory> mSearchHistorys = new ArrayList<>();

    private KeyWordAdapter mKeyWordAdapter;
    private SearchBookAdapter mSearchBookAdapter;
    private SearchHistoryAdapter mSearchHistoryAdapter;

    public static Intent newIntent(Context context, String str) {
        Intent intent = new Intent(context, SearchActivity.class);
        intent.putExtra(K_KEYWORD, str);
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

        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        getPresenter().start();
        mDefaultSearch = getIntent().getStringExtra(K_KEYWORD);
        KLog.d(TAG, "mDefaultSearch = " + mDefaultSearch);
        initView();
        initListener();

        // 有默认传入的关键之就设置上
        if (mDefaultSearch != null) {
            mEtSearch.setText(mDefaultSearch);
            mEtSearch.setSelection(mDefaultSearch.length());
        }
    }

    @NonNull
    @Override
    public SearchContract.Presenter createPresenter() {
        return new SearchPresenter();
    }

    private void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new RecyclerViewItemDecoration.Builder(this)
                .color(ContextCompat.getColor(this, R.color.colorDivider))
                .thickness(1)
                .create());

        mKeyWordAdapter = new KeyWordAdapter(mKeyWords);
        mSearchBookAdapter = new SearchBookAdapter(this, mSearchBooks);
        mSearchHistorys = DBManger.getInstance().loadSearchKeyword();
        mSearchHistoryAdapter = new SearchHistoryAdapter(mSearchHistorys);
        if (mSearchHistorys.size() > 0) {
            TextView tvClearSearchHistory = new TextView(this);
            tvClearSearchHistory.setText(getString(R.string.search_clear_history));
            tvClearSearchHistory.setTextColor(ContextCompat.getColor(this, R.color.textPrimary));
            tvClearSearchHistory.setBackgroundResource(R.drawable.bg_list_item);
            tvClearSearchHistory
                    .setLayoutParams(
                            new RecyclerView.LayoutParams(
                                    RecyclerView.LayoutParams.MATCH_PARENT,
                                    DensityUtil.dp2px(this, 32)
                            )
                    );
            tvClearSearchHistory.setGravity(Gravity.CENTER);
            tvClearSearchHistory.setOnClickListener(new OnMultiClickListener() {
                @Override
                public void onMultiClick(View v) {
                    mSearchHistoryAdapter.removeAllFooterView();
                    DBManger.getInstance().clearSearchKeyword();
                    mSearchHistoryAdapter.getData().clear();
                    mSearchHistoryAdapter.notifyDataSetChanged();
                }
            });
            mSearchHistoryAdapter.addFooterView(tvClearSearchHistory);
        }
        mRecyclerView.setAdapter(mSearchHistoryAdapter);
    }

    private void initListener() {
        // 下面用到了lambda表达式简化Listener的写法

        bindOnMultiClickLister(onMultiClickListener, mIvActionSearch, mIvArrowBack, mIvActionClear);

        // 搜索框内文字改变事件
        mEtSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                goSearchResult();
                return true;
            }
            return false;
        });

        mEtSearch.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus && mEtSearch.getText().length() > 0) {
                mIvActionClear.setVisibility(View.VISIBLE);
            } else {
                mIvActionClear.setVisibility(View.GONE);
            }
        });

        mEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0 && mEtSearch.isFocusable()) {
                    mIvActionClear.setVisibility(View.VISIBLE);
                } else {
                    mIvActionClear.setVisibility(View.GONE);
                }
                getKeyWords();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mEtSearch.post(() -> InputMethodUtils.showSoftInput(mEtSearch));

        mKeyWordAdapter.setOnItemClickListener(new OnMultiItemClickListener() {
            @Override
            public void OnMultiItemClick(BaseQuickAdapter adapter, View view, int position) {
                String keyWord = mKeyWords.get(position);
                KLog.d(TAG, keyWord);
                mEtSearch.setText(keyWord);
                mEtSearch.setSelection(keyWord.length());
                goSearchResult();
            }
        });

        mSearchBookAdapter.setOnItemClickListener((adapter, view, position) -> {
            String bookid = mSearchBooks.get(position).get_id();
            KLog.d(TAG, "bookid: " + bookid);
            AppRouter.showBookDetailActivity(this, bookid);
        });

        mSearchHistoryAdapter.setOnItemClickListener((adapter, view, position) -> {
            String keyWord = mSearchHistorys.get(position).getKeyword();
            KLog.d(TAG, "Historys Keyword = " + keyWord);
            mEtSearch.setText(keyWord);
            mEtSearch.setSelection(keyWord.length());
            goSearchResult();
        });

        mSearchHistoryAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.iv_keyword_clear) {
                DBManger.getInstance().deleteSearchKeyword(mSearchHistorys.get(position).getKeyword());
                refreshHistory();
                if (mSearchHistorys.size() <= 0) {
                    mSearchHistoryAdapter.removeAllFooterView();
                }
            }
        });
    }

    private OnMultiClickListener onMultiClickListener = new OnMultiClickListener() {
        @Override
        public void onMultiClick(View v) {
            switch (v.getId()) {
                case R.id.iv_arrow_back:
                    onBackPressed();
                    InputMethodUtils.hideSoftInput(SearchActivity.this);
                    break;
                case R.id.iv_action_search:
                    goSearchResult();
                    break;
                case R.id.iv_action_clear:
                    mEtSearch.setText("");
                    break;
            }
        }
    };

    private void goSearchResult() {
        KLog.d(TAG, "goSearchResult");
        Editable etSearchText = mEtSearch.getText();
        if (etSearchText != null) {
            String trim = etSearchText.toString().trim();
            KLog.d(TAG, "goSearchResult trim = " + trim);
            if (trim.length() > 0) {
                DBManger.getInstance().saveSearchKeyword(trim);
                getPresenter().loadSearchBook(trim);
            }
        }
        mEtSearch.post(() -> InputMethodUtils.hideSoftInput(mEtSearch));
    }

    private void getKeyWords() {
        KLog.d(TAG, "getKeyWords");
        Editable etSearchText = mEtSearch.getText();
        if (etSearchText != null) {
            String trim = etSearchText.toString().trim();
            KLog.d(TAG, "getKeyWords trim = " + trim);
            if (trim.length() > 0) {
                getPresenter().loadKeyWord(trim);
            } else {
                refreshHistory();
            }
        }
    }

    private void refreshHistory() {
        KLog.d(TAG, "refreshHistory");
        mSearchHistorys.clear();
        mSearchHistorys.addAll(DBManger.getInstance().loadSearchKeyword());
        mRecyclerView.setAdapter(mSearchHistoryAdapter);
    }

    @Override
    public void finishKeyWords(List<String> keyWords) {
        mKeyWords.clear();
        mKeyWords.addAll(keyWords);
        mRecyclerView.setAdapter(mKeyWordAdapter);
    }

    @Override
    public void finishBooks(List<SearchBookPackage.BooksBean> books) {
        mSearchBooks.clear();
        mSearchBooks.addAll(books);
        mRecyclerView.setAdapter(mSearchBookAdapter);
    }

    @Override
    public void complete() {
        super.complete();
        KLog.d(TAG, "complete");
        mStatusView.showContent();
    }

    @Override
    public void error() {
        super.error();
        KLog.d(TAG, "error");
        mStatusView.showError();
    }

    @Override
    public void empty() {
        super.empty();
        KLog.d(TAG, "empty");
        mStatusView.showEmpty();
    }
}
