package com.lrony.iread.presentation.main.local;

import com.lrony.iread.model.bean.CollBookBean;
import com.lrony.iread.mvp.MvpPresenter;
import com.lrony.iread.mvp.MvpView;

import java.util.List;

/**
 * Created by Lrony on 18-5-22.
 */
public interface LocalContract {

    interface View extends MvpView {
        void finishLoad(List<CollBookBean> collBookBeans);
        void finishDelete();

    }

    interface Presenter extends MvpPresenter<View> {
        /**
         * the refresh's status is show
         * @param refresh
         */
        void doLoadData(boolean refresh);


        void deleteData(CollBookBean collBookBean);
    }
}
