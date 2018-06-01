package com.lrony.iread.presentation.book.catalog;

import com.lrony.iread.model.bean.packages.BookChapterPackage;
import com.lrony.iread.model.remote.RemoteRepository;
import com.lrony.iread.mvp.MvpBasePresenter;
import com.lrony.iread.util.KLog;
import com.lrony.iread.util.RxUtils;

import io.reactivex.disposables.Disposable;

/**
 * Created by liuxiaobin on 18-6-1.
 */

public class BookCatalogPresenter extends MvpBasePresenter<BookCatalogContract.View> implements BookCatalogContract.Presenter {

    private final static String TAG = "BookCatalogPresenter";

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void loadBookInfo(boolean isRefresh, String id) {
        KLog.d(TAG, "loadBookInfo: " + id);

        if (isRefresh) getView().loading();

        Disposable disp = RemoteRepository.getInstance()
                .getBookChapters(id)
                .compose(RxUtils::toSimpleSingle)
                .subscribe(
                        bean -> {
                            if (!isViewAttached()) return;
                            getView().finshLoadBookInfo(bean);
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

}
