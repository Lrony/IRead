package com.lrony.iread.presentation.main.online.more;

import com.lrony.iread.mvp.MvpBasePresenter;

/**
 * Created by Lrony on 18-5-31.
 */
public class OnlineMorePresenter extends MvpBasePresenter<OnlineMoreContract.View> implements OnlineMoreContract.Presenter {

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void loadData(boolean showStatusView, String gender, String type, String major, String minor, int start, int limit) {

    }
}
