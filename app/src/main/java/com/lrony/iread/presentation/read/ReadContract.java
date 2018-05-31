package com.lrony.iread.presentation.read;

import com.lrony.iread.mvp.MvpPresenter;
import com.lrony.iread.mvp.MvpView;

/**
 * Created by Lrony on 18-5-31.
 */
public class ReadContract {

    interface View extends MvpView {

    }

    interface Presenter extends MvpPresenter<View> {

    }
}
