package com.lrony.iread.presentation.book.recommend;

import com.lrony.iread.model.remote.RemoteRepository;
import com.lrony.iread.mvp.MvpBasePresenter;
import com.lrony.iread.util.KLog;
import com.lrony.iread.util.RxUtils;

import io.reactivex.disposables.Disposable;


/**
 * Created by liuxiaobin on 18-5-30.
 */

public class RecommendPresenter extends MvpBasePresenter<RecommendContract.View> implements RecommendContract.Presenter {

    private final static String TAG = "RecommendPresenter";

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void loadRecommendBook(boolean showStatusView, String id) {
        KLog.d(TAG, "loadBookInfo: " + id);
        getView().loading();
        Disposable disp = RemoteRepository.getInstance()
                .getDetailRecommendBookPackage(id)
                .compose(RxUtils::toSimpleSingle)
                .subscribe(
                        bean -> {
                            if (!isViewAttached()) return;
                            ;
                            getView().finshLoadRecommend(bean);
                            getView().complete();
                        },
                        e -> {
                            if (!isViewAttached()) return;
                            getView().complete();
                            getView().error();
                        }
                );

    }

}
