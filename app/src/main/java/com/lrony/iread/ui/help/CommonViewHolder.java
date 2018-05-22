package com.lrony.iread.ui.help;

import android.support.annotation.IdRes;
import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by Lrony on 18-5-22.
 * 如有其他定制化的封装可以自行添加
 */
public class CommonViewHolder extends BaseViewHolder {

    public CommonViewHolder(View view) {
        super(view);
    }

    public CommonViewHolder setSelected(@IdRes int viewId, boolean selected) {
        getView(viewId).setSelected(selected);
        return this;
    }
}