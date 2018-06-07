package com.lrony.iread.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lrony.iread.ui.help.OnMultiClickListener;
import com.lrony.iread.util.ToastUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by lrony on 2018/4/9.
 * Providing functionality for all fragment
 * <p>
 * 为所有的fragment封装功能
 */
public abstract class BaseFragment extends BaseSuperFragment {

    private static final String TAG = "BaseFragment";

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, root);
        return root;
    }

    public abstract int getLayoutId();

    public void showToast(String msg) {
        ToastUtil.showToast(msg);
    }

    public void showToast(int id) {
        ToastUtil.showToast(id);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    public void bindOnClickLister(View rootView, View.OnClickListener listener, @IdRes int... ids) {
        for (int id : ids) {
            View view = rootView.findViewById(id);
            if (view != null) {
                view.setOnClickListener(listener);
            }
        }
    }

    public void bindOnClickLister(View.OnClickListener listener, View... views) {
        for (View view : views) {
            view.setOnClickListener(listener);
        }
    }

    public void bindOnMultiClickListener(View rootView, OnMultiClickListener listener, @IdRes int... ids) {
        for (int id : ids) {
            View view = rootView.findViewById(id);
            if (view != null) {
                view.setOnClickListener(listener);
            }
        }
    }

    public void bindOnMultiClickListener(OnMultiClickListener listener, View... views) {
        for (View view : views) {
            view.setOnClickListener(listener);
        }
    }
}
