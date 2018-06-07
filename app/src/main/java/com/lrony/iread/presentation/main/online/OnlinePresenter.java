package com.lrony.iread.presentation.main.online;

import com.lrony.iread.model.bean.CollBookBean;
import com.lrony.iread.model.bean.packages.BookSortPackage;
import com.lrony.iread.model.bean.packages.BookSubSortPackage;
import com.lrony.iread.model.remote.RemoteRepository;
import com.lrony.iread.mvp.MvpBasePresenter;
import com.lrony.iread.util.NetworkUtils;
import com.lrony.iread.util.RxUtils;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;

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
    public void loadRemoteBooks() {
        // View无效
        if (!isViewAttached()) return;

        getView().loading();

        if (!NetworkUtils.isAvailable()) {
            getView().nonetword();
            return;
        }

        //这个最好是设定一个默认时间采用Remote加载，如果Remote加载失败则采用数据中的数据。我这里先写死吧
        Single<List<CollBookBean>> maleData = RemoteRepository.getInstance()
                .getRecommendBooks("male");
        Single<List<CollBookBean>> femaleData = RemoteRepository.getInstance()
                .getRecommendBooks("female");

        Single<CollBookPackage> zipSingle = Single.zip(maleData, femaleData
                , (collBookBeans, collBookBeans2) -> new CollBookPackage(collBookBeans, collBookBeans2));

        Disposable disposable = zipSingle.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (bean) -> {
                            // View无效
                            if (!isViewAttached()) return;

                            getView().finishLoad(bean.maleData, bean.femaleData);
                            getView().complete();
                        }
                        ,
                        (e) -> {
                            if (!isViewAttached()) return;
                            getView().complete();
                            getView().error();
                        }
                );
        addDisposable(disposable);
    }

    class CollBookPackage {
        List<CollBookBean> maleData;
        List<CollBookBean> femaleData;

        public CollBookPackage(List<CollBookBean> maleData, List<CollBookBean> femaleData) {
            this.maleData = maleData;
            this.femaleData = femaleData;
        }
    }
}
