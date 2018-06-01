package com.lrony.iread.presentation.main.online.more;

import com.lrony.iread.model.remote.RemoteRepository;
import com.lrony.iread.mvp.MvpBasePresenter;
import com.lrony.iread.util.KLog;
import com.lrony.iread.util.RxUtils;

import io.reactivex.disposables.Disposable;

/**
 * Created by Lrony on 18-5-31.
 */
public class OnlineMorePresenter extends MvpBasePresenter<OnlineMoreContract.View> implements OnlineMoreContract.Presenter {

    private static final String TAG = "OnlineMorePresenter";

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void loadData(boolean showStatusView, String gender, String type, String major, String minor, int start, int limit) {

        KLog.d(TAG, "gender: " + gender + ",major: " + major);

        if (showStatusView) getView().loading();

        Disposable disp = RemoteRepository.getInstance()
                .getSortBookPackage(gender, type, major, minor, start, limit)
                .compose(RxUtils::toSimpleSingle)
                .subscribe(
                        bean -> {
                            if (!isViewAttached()) return;
                            getView().finishLoadData(bean);
                            getView().complete();
                        },
                        e -> {
                            if (!isViewAttached()) return;
                            getView().complete();
                            getView().error();
                        }
                );
        addDisposable(disp);
    }

    @Override
    public void loadMoreData(String gender, String type, String major, String minor, int start, int limit) {
        Disposable disp = RemoteRepository.getInstance()
                .getSortBookPackage(gender, type, major, minor, start, limit)
                .compose(RxUtils::toSimpleSingle)
                .subscribe(
                        bean -> {
                            if (!isViewAttached()) return;
                            getView().finishLoadMoreData(bean);
                            getView().complete();
                        },
                        e -> {
                            if (!isViewAttached()) return;
                            getView().complete();
                            getView().showLoadMoreError();
                        }
                );
        addDisposable(disp);
    }
}
