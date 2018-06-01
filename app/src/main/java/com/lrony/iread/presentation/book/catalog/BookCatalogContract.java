package com.lrony.iread.presentation.book.catalog;

import com.lrony.iread.model.bean.BookChapterBean;
import com.lrony.iread.mvp.MvpPresenter;
import com.lrony.iread.mvp.MvpView;

import java.util.List;

/**
 * Created by liuxiaobin on 18-6-1.
 */

public class BookCatalogContract {

    interface View extends MvpView {

        void finshLoadBookInfo(List<BookChapterBean> bean);

    }

    interface Presenter extends MvpPresenter<View> {

        void loadBookInfo(boolean isRefresh, String id);

    }
}
