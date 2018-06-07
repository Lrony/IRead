package com.lrony.iread.presentation.main.online.type;

import com.lrony.iread.model.bean.packages.BookSortPackage;
import com.lrony.iread.model.bean.packages.BookSubSortPackage;
import com.lrony.iread.mvp.MvpPresenter;
import com.lrony.iread.mvp.MvpView;

/**
 * Created by Lrony on 18-6-7.
 */
public interface BookTypeContract {

    interface View extends MvpView {

        void finishLoadType(BookSortPackage sort, BookSubSortPackage subSort);
    }

    interface Presenter extends MvpPresenter<View> {

        void loadTypeData(boolean showRefreshView);
    }
}
