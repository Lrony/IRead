package com.lrony.iread.presentation.book.detail;

import com.lrony.iread.model.bean.BookChapterBean;
import com.lrony.iread.model.bean.CollBookBean;
import com.lrony.iread.model.db.DBManger;
import com.lrony.iread.model.remote.RemoteRepository;
import com.lrony.iread.mvp.MvpBasePresenter;
import com.lrony.iread.util.KLog;
import com.lrony.iread.util.MD5Utils;
import com.lrony.iread.util.RxUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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

    @Override
    public void addToBookShelf(CollBookBean collBook) {
        Disposable disposable = RemoteRepository.getInstance()
                .getBookChapters(collBook.get_id())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(
                        (d) -> getView().addBookLoading() //等待加载
                )
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        beans -> {
                            if (!isViewAttached()) return;
                            //设置 id
                            for(BookChapterBean bean :beans){
                                bean.setId(MD5Utils.strToMd5By16(bean.getLink()));
                            }

                            //设置目录
                            collBook.setBookChapters(beans);
                            //存储收藏
                            DBManger.getInstance().saveCollBookWithAsync(collBook);

                            getView().succeed();
                            getView().complete();
                        }
                        ,
                        e -> {
                            if (!isViewAttached()) return;
                            getView().addBookError();
                            getView().complete();
                        }
                );
        addDisposable(disposable);
    }
}
