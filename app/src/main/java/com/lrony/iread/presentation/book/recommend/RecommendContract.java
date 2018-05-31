package com.lrony.iread.presentation.book.recommend;

import com.lrony.iread.model.bean.BookDetailRecommendBookBean;
import com.lrony.iread.mvp.MvpPresenter;
import com.lrony.iread.mvp.MvpView;

import java.util.List;

/**
 * Created by liuxiaobin on 18-5-30.
 */

public interface RecommendContract {

    interface View extends MvpView {
        void finshLoadRecommend(List<BookDetailRecommendBookBean> bookBean);
    }

    interface Presenter extends MvpPresenter<View> {
        void loadRecommendBook(boolean isRefresh, String id);
    }
}
