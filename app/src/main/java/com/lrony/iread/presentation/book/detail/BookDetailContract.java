package com.lrony.iread.presentation.book.detail;

import com.lrony.iread.model.bean.BookDetailBean;
import com.lrony.iread.model.bean.BookDetailRecommendBookBean;
import com.lrony.iread.model.bean.CollBookBean;
import com.lrony.iread.mvp.MvpPresenter;
import com.lrony.iread.mvp.MvpView;

import java.util.List;

public interface BookDetailContract {

    interface View extends MvpView {

        void finshLoadBookInfo(BookDetailBean book);

        void finshLoadBookDetailRecommendBooklist(List<BookDetailRecommendBookBean> books);

        void addBookLoading();

        void addBookError();
    }

    interface Presenter extends MvpPresenter<View> {

        void loadBookInfo(String id);

        void loadBookDetailRecommendBooklist(String id);

        //添加到书架上
        void addToBookShelf(CollBookBean collBook);
    }
}