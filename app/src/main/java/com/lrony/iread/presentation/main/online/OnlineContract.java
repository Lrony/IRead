package com.lrony.iread.presentation.main.online;

import com.lrony.iread.mvp.MvpPresenter;
import com.lrony.iread.mvp.MvpView;

/**
 * Created by Lrony on 18-5-22.
 */
public interface OnlineContract {

    interface View extends MvpView {

    }

    interface Presenter extends MvpPresenter<View> {

    }
}
