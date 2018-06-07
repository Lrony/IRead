package com.lrony.iread.ui.help;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lrony.iread.R;
import com.lrony.iread.util.DensityUtil;
import com.lrony.iread.util.KLog;

public class ProgressDialogHandler {

    private final static String TAG = "ProgressDialogHandler";
    public static final int SHOW_PROGRESS_DIALOG = 1;
    public static final int DISMISS_PROGRESS_DIALOG = 2;

    private Dialog mDialog;

    private Context mContext;
    private boolean mCancelable;
    private ProgressCancelListener mProgressCancelListener;

    public ProgressDialogHandler(Context context, ProgressCancelListener mProgressCancelListener,
                                 boolean cancelable) {
        super();
        this.mContext = context;
        this.mProgressCancelListener = mProgressCancelListener;
        this.mCancelable = cancelable;
        initProgressDialog();
    }

    public void initProgressDialog() {
        if (mDialog == null) {
            initDialog();
//            mDialog = AppRouter.getLoadingDialog(mContext);
            mDialog.setCancelable(mCancelable);

            if (mCancelable) {
                mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        mProgressCancelListener.onCancelProgress();
                    }
                });
                mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                    }
                });
            }

        }
    }

    public void showProgressDialog() {

        if (null != mDialog && !mDialog.isShowing()) {
            mDialog.show();

        }
    }

    public void dismissProgressDialog() {
        KLog.d(TAG, "dismissProgressDialog");
        if (mDialog != null && mDialog.isShowing()) {
            KLog.d(TAG, "dismiss");
            mDialog.dismiss();
        }
    }

    private void initDialog() {
        // 首先得到整个View
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.dialog_loading, null);
        // 创建自定义样式的Dialog
        mDialog = new Dialog(mContext, R.style.AlertDialogStyle);
        mDialog.setContentView(view, new ViewGroup.LayoutParams(
                DensityUtil.dp2px(mContext, 96), DensityUtil.dp2px(mContext, 96)));
    }
}