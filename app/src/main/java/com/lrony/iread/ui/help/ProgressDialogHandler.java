package com.lrony.iread.ui.help;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;

import com.lrony.iread.AppRouter;
import com.lrony.iread.util.KLog;

public class ProgressDialogHandler extends Handler {

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
    }

    private void initProgressDialog() {
        if (mDialog == null) {
            mDialog = AppRouter.getLoadingDialog(mContext);
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
                        mDialog.dismiss();
                        mContext = null;
                        mDialog = null;
                    }
                });
            }

            if (!mDialog.isShowing()) {
                mDialog.show();
            }
        }
    }

    private void dismissProgressDialog() {
        KLog.d(TAG, "dismissProgressDialog");
        if (mDialog != null && mDialog.isShowing()) {
            KLog.d(TAG, "dismiss");
            mDialog.dismiss();
        }
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case SHOW_PROGRESS_DIALOG:
                initProgressDialog();
                break;
            case DISMISS_PROGRESS_DIALOG:
                KLog.d(TAG, "DISMISS_PROGRESS_DIALOG");
                dismissProgressDialog();
                break;
        }
    }
}