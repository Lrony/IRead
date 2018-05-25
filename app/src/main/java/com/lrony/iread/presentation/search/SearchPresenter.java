package com.lrony.iread.presentation.search;

import com.lrony.iread.model.remote.RemoteRepository;
import com.lrony.iread.mvp.MvpBasePresenter;
import com.lrony.iread.util.KLog;
import com.lrony.iread.util.RxUtils;

import io.reactivex.disposables.Disposable;

/**
 * Created by Lrony on 18-5-22.
 */
public class SearchPresenter extends MvpBasePresenter<SearchContract.View> implements SearchContract.Presenter {

    private static final String TAG = "SearchPresenter";

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void loadKeyWord(String content) {
        KLog.d(TAG, "loadKeyWord: " + content);
        Disposable disp = RemoteRepository.getInstance()
                .getKeyWords(content)
                .compose(RxUtils::toSimpleSingle)
                .subscribe(
                        bean -> {
                            if (!isViewAttached()) return;
                            getView().finishKeyWords(bean);
                            getView().complete();
                        },
                        e -> {

                        }
                );
        addDisposable(disp);
    }

    @Override
    public void loadSearchBook(String content) {
        KLog.d(TAG, "loadSearchBook: " + content);
        Disposable disp = RemoteRepository.getInstance()
                .getSearchBooks(content)
                .compose(RxUtils::toSimpleSingle)
                .subscribe(
                        bean -> {
                            if (!isViewAttached()) return;
                            if (bean.size() <= 0) {
                                getView().empty();
                            } else {
                                getView().finishBooks(bean);
                                getView().complete();
                            }
                        },
                        e -> {
                            if (!isViewAttached()) return;
                            getView().error();
                        }
                );
        addDisposable(disp);
    }
}
