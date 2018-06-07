package com.lrony.iread.ui.help;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

/**
 * Created by Lrony on 18-6-7.
 */
public abstract class OnMultiItemClickListener implements BaseQuickAdapter.OnItemClickListener {

    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    private static final int MIN_CLICK_DELAY_TIME = 1000;
    private static long lastClickTime;

    public abstract void OnMultiItemClick(BaseQuickAdapter adapter, View view, int position);

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            // 超过点击间隔后再将lastClickTime重置为当前点击时间
            lastClickTime = curClickTime;
            OnMultiItemClick(adapter, view, position);
        }
    }
}
