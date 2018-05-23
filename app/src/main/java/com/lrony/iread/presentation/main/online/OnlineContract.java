package com.lrony.iread.presentation.main.online;

import com.lrony.iread.model.bean.SortBookBean;
import com.lrony.iread.mvp.MvpPresenter;
import com.lrony.iread.mvp.MvpView;

import java.util.List;

/**
 * Created by Lrony on 18-5-22.
 */
public interface OnlineContract {

    interface View extends MvpView {

        void finishLoadMaleBooks(List<SortBookBean> books);

        void finishLoadFemaleBooks(List<SortBookBean> books);
    }

    interface Presenter extends MvpPresenter<View> {

        /**
         * 获取男生热门书籍
         *
         * @param num 数量
         */
        void loadMaleHotBooks(int num);

        /**
         * 获取女生热门书籍
         *
         * @param num 数量
         */
        void loadFemaleHotBooks(int num);
    }
}
