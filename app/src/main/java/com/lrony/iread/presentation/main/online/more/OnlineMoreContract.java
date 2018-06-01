package com.lrony.iread.presentation.main.online.more;

import com.lrony.iread.model.bean.SortBookBean;
import com.lrony.iread.mvp.MvpPresenter;
import com.lrony.iread.mvp.MvpView;

import java.util.List;

/**
 * Created by Lrony on 18-5-31.
 */
public class OnlineMoreContract {

    interface View extends MvpView {

        void finishLoadData(List<SortBookBean> books);

        void finishLoadMoreData(List<SortBookBean> books);

        void showLoadMoreError();
    }

    interface Presenter extends MvpPresenter<View> {

        /**
         * @param showStatusView 是否显示StatusView
         * @param gender
         * @param type
         * @param major
         * @param minor
         * @param start
         * @param limit
         */
        void loadData(boolean showStatusView, String gender, String type, String major, String minor, int start, int limit);

        void loadMoreData(String gender, String type, String major, String minor, int start, int limit);
    }
}
