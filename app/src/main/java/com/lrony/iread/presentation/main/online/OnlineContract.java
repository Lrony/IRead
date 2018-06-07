package com.lrony.iread.presentation.main.online;

import com.lrony.iread.model.bean.CollBookBean;
import com.lrony.iread.model.bean.SortBookBean;
import com.lrony.iread.mvp.MvpPresenter;
import com.lrony.iread.mvp.MvpView;

import java.util.List;

/**
 * Created by Lrony on 18-5-22.
 */
public interface OnlineContract {

    interface View extends MvpView {

        void finishLoad(List<CollBookBean> maleData, List<CollBookBean> femaleData);
    }

    interface Presenter extends MvpPresenter<View> {

        /**
         * 获取热门书籍
         */
        void loadRemoteBooks();
    }
}
