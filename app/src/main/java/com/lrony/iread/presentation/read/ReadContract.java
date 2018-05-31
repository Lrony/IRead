package com.lrony.iread.presentation.read;

import com.lrony.iread.model.bean.BookChapterBean;
import com.lrony.iread.mvp.MvpPresenter;
import com.lrony.iread.mvp.MvpView;
import com.lrony.iread.ui.widget.page.TxtChapter;

import java.util.List;

/**
 * Created by Lrony on 18-5-31.
 */
public class ReadContract {

    interface View extends MvpView {

        void showCategory(List<BookChapterBean> bookChapterList);

        void finishChapter();

        void errorChapter();
    }

    interface Presenter extends MvpPresenter<View> {

        void loadCategory(String bookId);

        void loadChapter(String bookId, List<TxtChapter> bookChapterList);
    }
}
