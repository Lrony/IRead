package com.lrony.iread.presentation.search;

import com.lrony.iread.model.bean.packages.SearchBookPackage;
import com.lrony.iread.mvp.MvpPresenter;
import com.lrony.iread.mvp.MvpView;

import java.util.List;

/**
 * Created by Lrony on 18-5-22.
 */
public interface SearchContract {

    interface View extends MvpView {

        void finishKeyWords(List<String> keyWords);

        void finishBooks(List<SearchBookPackage.BooksBean> books);
    }

    interface Presenter extends MvpPresenter<View> {

        void loadKeyWord(String content);

        void loadSearchBook(String content);
    }
}
