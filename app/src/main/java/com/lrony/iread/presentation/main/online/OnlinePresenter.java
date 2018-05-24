package com.lrony.iread.presentation.main.online;

import com.lrony.iread.model.remote.RemoteRepository;
import com.lrony.iread.mvp.MvpBasePresenter;
import com.lrony.iread.util.RxUtils;

import io.reactivex.disposables.Disposable;

/**
 * Created by Lrony on 18-5-22.
 */
public class OnlinePresenter extends MvpBasePresenter<OnlineContract.View> implements OnlineContract.Presenter {

    private static final String TAG = "OnlinePresenter";

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void loadMaleHotBooks(int num) {
        // View无效
        if (!isViewAttached()) return;

        getView().loading();

        Disposable disp = RemoteRepository.getInstance()
                .getSortBookPackage("male", "hot", "玄幻", "", 0, num)
                .compose(RxUtils::toSimpleSingle)
                .subscribe(
                        bean -> {
                            if (!isViewAttached()) return;
                            getView().finishLoadMaleBooks(bean);
                        },
                        e -> {
                            if (!isViewAttached()) return;
                            getView().error();
                        }
                );
    }

    @Override
    public void loadFemaleHotBooks(int num) {
        // View无效
        if (!isViewAttached()) return;

        getView().loading();

        Disposable disp = RemoteRepository.getInstance()
                .getSortBookPackage("female", "hot", "古代言情", "", 0, num)
                .compose(RxUtils::toSimpleSingle)
                .subscribe(
                        bean -> {
                            if (!isViewAttached()) return;
                            getView().finishLoadFemaleBooks(bean);
                        },
                        e -> {
                            if (!isViewAttached()) return;
                            getView().error();
                        }
                );
    }
}
