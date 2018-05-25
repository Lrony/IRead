package com.lrony.iread.presentation.book.detail;

import com.lrony.iread.model.remote.RemoteRepository;
import com.lrony.iread.mvp.MvpBasePresenter;
import com.lrony.iread.util.KLog;
import com.lrony.iread.util.RxUtils;

import io.reactivex.disposables.Disposable;

/**
 * Created by liuxiaobin on 18-5-23.
 */

public class BookDetailPresenter extends MvpBasePresenter<BookDetailContract.View> implements BookDetailContract.Presenter {

    private final static String TAG = "BookDetailPresenter";

    @Override
    public void start() {
        super.start();

    }

    @Override
    public void loadBookInfo(String id) {
        KLog.d(TAG, "loadBookInfo: " + id);

        getView().loading();
        Disposable disp = RemoteRepository.getInstance()
                .getBookDetail(id)
                .compose(RxUtils::toSimpleSingle)
                .subscribe(
                        bean -> {
                            if (!isViewAttached()) return;
                            if (bean.isOk()){
                                getView().finshLoadBookInfo(bean);
                            } else {
                                getView().error();
                            }
                        },
                        e -> {
                            if (!isViewAttached()) return;
                            getView().error();
                        }

                );
        addDisposable(disp);
    }

    @Override
    public void loadBookDetailRecommendBooklist(String id) {
        KLog.d(TAG, "loadRecommendBookList: " + id);
        Disposable disp = RemoteRepository.getInstance()
                .getDetailRecommendBookPackage(id)
                .compose(RxUtils::toSimpleSingle)
                .subscribe(
                        bean -> {
                            if (!isViewAttached()) return;
                            getView().finshLoadBookDetailRecommendBooklist(bean);
                        },
                        e -> {
                            if (!isViewAttached()) return;
                            getView().error();
                        }

                );
        addDisposable(disp);
    }
}
