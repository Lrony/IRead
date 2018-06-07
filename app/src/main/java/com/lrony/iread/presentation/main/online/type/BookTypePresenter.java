package com.lrony.iread.presentation.main.online.type;

import com.lrony.iread.model.bean.packages.BookSortPackage;
import com.lrony.iread.model.bean.packages.BookSubSortPackage;
import com.lrony.iread.model.remote.RemoteRepository;
import com.lrony.iread.mvp.MvpBasePresenter;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Lrony on 18-6-7.
 */
public class BookTypePresenter extends MvpBasePresenter<BookTypeContract.View> implements BookTypeContract.Presenter {

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void loadTypeData(boolean showRefreshView) {
        // View无效
        if (!isViewAttached()) return;

        if (showRefreshView) getView().loading();

        //这个最好是设定一个默认时间采用Remote加载，如果Remote加载失败则采用数据中的数据。我这里先写死吧
        Single<BookSortPackage> sortSingle = RemoteRepository.getInstance()
                .getBookSortPackage();
        Single<BookSubSortPackage> subSortSingle = RemoteRepository.getInstance()
                .getBookSubSortPackage();

        Single<SortPackage> zipSingle = Single.zip(sortSingle, subSortSingle,
                (bookSortPackage, bookSubSortPackage) -> new SortPackage(bookSortPackage, bookSubSortPackage));

        Disposable disposable = zipSingle.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (bean) -> {
                            // View无效
                            if (!isViewAttached()) return;

                            getView().finishLoadType(bean.sortPackage, bean.subSortPackage);
                            getView().complete();
                        }
                        ,
                        (e) -> {
                            // View无效
                            if (!isViewAttached()) return;

                            getView().complete();
                            getView().error();
                        }
                );
        addDisposable(disposable);
    }

    class SortPackage {
        BookSortPackage sortPackage;
        BookSubSortPackage subSortPackage;

        public SortPackage(BookSortPackage sortPackage, BookSubSortPackage subSortPackage) {
            this.sortPackage = sortPackage;
            this.subSortPackage = subSortPackage;
        }
    }
}
