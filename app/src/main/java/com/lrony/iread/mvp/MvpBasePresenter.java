package com.lrony.iread.mvp;

import android.support.annotation.Nullable;
import android.support.annotation.UiThread;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by lrony on 2018/4/9.
 */
public class MvpBasePresenter<V extends MvpView> implements MvpPresenter<V> {

    private WeakReference<V> viewRef;
    protected CompositeDisposable mDisposable;

    protected void unSubscribe() {
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }

    protected void addDisposable(Disposable subscription) {
        if (mDisposable == null) {
            mDisposable = new CompositeDisposable();
        }
        mDisposable.add(subscription);
    }

    /**
     * A connection between Presenter and View
     * <p>
     * Presenter与View建立连接
     */
    @UiThread
    @Override
    public void attachView(V view) {
        viewRef = new WeakReference<>(view);
    }

    @UiThread
    @Override
    public void start() {
    }

    @UiThread
    @Nullable
    public V getView() {
        return viewRef == null ? null : viewRef.get();
    }


    /**
     * Each time the business request is invoked, is getView ().ShowXxx ().
     * Please first call the method to check whether to establish connection with View, or no null pointer exception.
     * <p>
     * 每次调用业务请求的时候 即：getView().showXxx();时
     * 请先调用方法检查是否与View建立连接，没有则可能会空指针异常
     */
    @UiThread
    public final boolean isViewAttached() {
        return viewRef != null && viewRef.get() != null;
    }

    /**
     * Presenter and View connection disconnect
     * <p>
     * Presenter与View连接断开
     */
    @UiThread
    @Override
    public void detachView() {
        if (viewRef != null) {
            viewRef.clear();
            viewRef = null;
        }
        unSubscribe();
    }

    @UiThread
    @Override
    public void destroy() {
        if (isViewAttached()) {
            detachView();
        }
    }
}

