package com.lrony.iread.presentation.read;

import com.lrony.iread.model.bean.BookChapterBean;
import com.lrony.iread.model.bean.ChapterInfoBean;
import com.lrony.iread.model.db.DBManger;
import com.lrony.iread.model.remote.RemoteRepository;
import com.lrony.iread.mvp.MvpBasePresenter;
import com.lrony.iread.ui.widget.page.TxtChapter;
import com.lrony.iread.util.MD5Utils;
import com.lrony.iread.util.RxUtils;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Lrony on 18-5-31.
 */
public class ReadPresenter extends MvpBasePresenter<ReadContract.View> implements ReadContract.Presenter {

    private static final String TAG = "ReadPresenter";

    private Subscription mChapterSub;

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void loadCategory(String bookId) {
        Disposable disposable = RemoteRepository.getInstance()
                .getBookChapters(bookId)
                .doOnSuccess(bookChapterBeans -> {
                    //进行设定BookChapter所属的书的id。
                    for (BookChapterBean bookChapter : bookChapterBeans) {
                        bookChapter.setId(MD5Utils.strToMd5By16(bookChapter.getLink()));
                        bookChapter.setBookId(bookId);
                    }
                })
                .compose(RxUtils::toSimpleSingle)
                .subscribe(
                        beans -> {
                            if (!isViewAttached()) return;
                            getView().showCategory(beans);
                        }
                        ,
                        e -> {
                            //TODO: Haven't grate conversation method.
                        }
                );
        addDisposable(disposable);
    }

    @Override
    public void loadChapter(String bookId, List<TxtChapter> bookChapterList) {
        int size = bookChapterList.size();

        //取消上次的任务，防止多次加载
        if (mChapterSub != null) {
            mChapterSub.cancel();
        }

        List<Single<ChapterInfoBean>> chapterInfos = new ArrayList<>(bookChapterList.size());
        ArrayDeque<String> titles = new ArrayDeque<>(bookChapterList.size());

        // 将要下载章节，转换成网络请求。
        for (int i = 0; i < size; ++i) {
            TxtChapter bookChapter = bookChapterList.get(i);
            // 网络中获取数据
            Single<ChapterInfoBean> chapterInfoSingle = RemoteRepository.getInstance()
                    .getChapterInfo(bookChapter.getLink());

            chapterInfos.add(chapterInfoSingle);

            titles.add(bookChapter.getTitle());
        }

        Single.concat(chapterInfos)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Subscriber<ChapterInfoBean>() {
                            String title = titles.poll();

                            @Override
                            public void onSubscribe(Subscription s) {
                                s.request(Integer.MAX_VALUE);
                                mChapterSub = s;
                            }

                            @Override
                            public void onNext(ChapterInfoBean chapterInfoBean) {
                                if (!isViewAttached()) return;
                                //存储数据
                                DBManger.getInstance().saveChapterInfo(bookId, title, chapterInfoBean.getBody());
                                getView().finishChapter();
                                //将获取到的数据进行存储
                                title = titles.poll();
                            }

                            @Override
                            public void onError(Throwable t) {
                                if (!isViewAttached()) return;
                                //只有第一个加载失败才会调用errorChapter
                                if (bookChapterList.get(0).getTitle().equals(title)) {
                                    getView().errorChapter();
                                }
                            }

                            @Override
                            public void onComplete() {
                            }
                        }
                );
    }
}
