package com.lrony.iread.presentation.main.local;

import com.lrony.iread.model.bean.CollBookBean;
import com.lrony.iread.model.db.DBManger;
import com.lrony.iread.mvp.MvpBasePresenter;
import com.lrony.iread.util.KLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lrony on 18-5-22.
 */
public class LocalPresenter extends MvpBasePresenter<LocalContract.View> implements LocalContract.Presenter {
    private static final String TAG = "LocalPresenter";

    private List<CollBookBean> mCollBookBeans = new ArrayList<>();

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void doLoadData(boolean refresh) {
        //if the view is invalid
        if (!isViewAttached()) return;

        if (refresh){
            getView().loading();
        }

        mCollBookBeans = DBManger.getInstance().loadAllBookTb();
        KLog.d(TAG,"doLoadData mCollBookBeans.size() = " + mCollBookBeans.size());

        if (mCollBookBeans.size() <= 0){
            getView().complete();
            getView().empty();
        }else{
            getView().finishLoad(mCollBookBeans);
            getView().complete();
        }
    }

    @Override
    public void deleteData(CollBookBean collBookBean) {
        //if the view is invalid
        if (!isViewAttached()) return;

        DBManger.getInstance().deleteBookTb(collBookBean);
        getView().finishDelete();
    }
}
