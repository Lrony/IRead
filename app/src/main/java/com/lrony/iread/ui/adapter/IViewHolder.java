package com.lrony.iread.ui.adapter;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Lrony on 18-5-31.
 */
public interface IViewHolder<T> {
    View createItemView(ViewGroup parent);
    void initView();
    void onBind(T data,int pos);
    void onClick();
}
