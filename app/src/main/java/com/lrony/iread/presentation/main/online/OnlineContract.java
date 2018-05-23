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

        // 男生热门书籍获取完毕
        void finishLoadMaleBooks(List<SortBookBean> books);

        // 女生热门书籍获取完毕
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
