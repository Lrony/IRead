package com.lrony.iread.presentation.ranking;

import com.lrony.iread.mvp.MvpPresenter;
import com.lrony.iread.mvp.MvpView;

/**
 * Created by Lrony on 18-6-7.
 */
public interface RankingContract {

    interface View extends MvpView {

    }

    interface Presenter extends MvpPresenter<View> {

    }
}
