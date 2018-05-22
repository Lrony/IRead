package com.lrony.iread.mvp;

import android.content.Context;
import android.support.annotation.NonNull;

import com.lrony.iread.base.BaseActivity;
import com.lrony.iread.util.ToastUtil;

/**
 * Created by lrony on 2018/4/9.
 */
public abstract class MvpActivity<P extends MvpPresenter> extends BaseActivity implements MvpView {

    private P mPresenter;

    @Override
    public void showToast(String message) {
        ToastUtil.showToast(message);
    }

    @Override
    public void showToast(int id) {
        ToastUtil.showToast(id);
    }

    @Override
    public Context provideContext() {
        return this;
    }

    @Override
    public void loading() {

    }

    @Override
    public void succeed() {

    }

    @Override
    public void error() {

    }

    @Override
    public void empty() {

    }

    @Override
    public void complete() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.destroy();
            mPresenter = null;
        }
    }

    @NonNull
    public abstract P createPresenter();

    /**
     * Subclass can get the bound presenter by calling this method.
     * <p>
     * 子类通过调用该方法，获得绑定的presenter
     *
     * @return Bound presenter
     */
    protected P getPresenter() {
        if (mPresenter == null) {
            mPresenter = createPresenter();
            mPresenter.attachView(this);
        }
        return mPresenter;
    }
}
