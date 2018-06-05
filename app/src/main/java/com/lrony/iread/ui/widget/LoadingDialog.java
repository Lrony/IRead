package com.lrony.iread.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lrony.iread.R;
import com.lrony.iread.util.DensityUtil;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class LoadingDialog {
    //    MaterialProgressBar mLoadingView;
    Dialog mLoadingDialog;

    public LoadingDialog(Context context) {
        // 首先得到整个View
        View view = LayoutInflater.from(context).inflate(
                R.layout.dialog_loading, null);
        // 获取整个布局
//        LinearLayout layout = (LinearLayout) view.findViewById(R.id.dialog_view);
        // 页面中的LoadingView
//        mLoadingView = (MaterialProgressBar) view.findViewById(R.id.progress);
        // 创建自定义样式的Dialog
        mLoadingDialog = new Dialog(context, R.style.AlertDialogStyle);
        // 设置返回键有效
        mLoadingDialog.setCancelable(true);
        mLoadingDialog.setContentView(view, new ViewGroup.LayoutParams(
                DensityUtil.dp2px(context, 96), DensityUtil.dp2px(context, 96)));
    }

    public void show() {
        mLoadingDialog.show();
    }

    public void close() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }
}