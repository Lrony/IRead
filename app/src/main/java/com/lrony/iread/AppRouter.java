package com.lrony.iread;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lrony.iread.presentation.book.catalog.BookCatalogActivity;
import com.lrony.iread.presentation.book.catalog.BookCatalogContract;
import com.lrony.iread.presentation.book.detail.BookDetailActivity;
import com.lrony.iread.presentation.book.recommend.RecommendActivity;
import com.lrony.iread.presentation.main.MainActivity;
import com.lrony.iread.presentation.main.online.more.OnlineMoreActivity;
import com.lrony.iread.presentation.search.SearchActivity;
import com.lrony.iread.presentation.settings.SettingsActivity;
import com.lrony.iread.util.DensityUtil;

/**
 * Created by Lrony on 18-5-21.
 * APP路由，Activity跳转等操作在这包装好方法
 */
public class AppRouter {

    /**
     * 获取全局加载dialog
     */
    public static Dialog getLoadingDialog(Context context) {
        Dialog dialog = new Dialog(context, R.style.AlertDialogStyle);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
        dialog.setContentView(view, new ViewGroup.LayoutParams(DensityUtil.dp2px(context, 96), DensityUtil.dp2px(context, 96)));
        return dialog;
    }

    /**
     * 跳转到当前应用设置界面
     */
    public static void showAppDetailSetting(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        context.startActivity(localIntent);
    }

    public static void showMainActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    // 打开搜索
    public static void showSearchActivity(Context context) {
        showSearchActivity(context, null);
    }

    // 打开搜索，带搜索内容
    public static void showSearchActivity(Context context, String keyword) {
        context.startActivity(SearchActivity.newIntent(context, keyword));
    }

    // 打开设置
    public static void showSettingsActivity(Context context) {
        context.startActivity(SettingsActivity.newIntent(context));
    }

    //打开详情
    public static void showBookDetailActivity(Context context, String bookid) {
        context.startActivity(BookDetailActivity.newIntent(context, bookid));
    }

    //打开相关推荐
    public static void showRecommendActivity(Context context, String bookid) {
        context.startActivity(RecommendActivity.newIntent(context, bookid));
    }

    // 打开在线更多页面
    public static void showOnlineMoreActivity(Context context, String title) {
        context.startActivity(OnlineMoreActivity.newIntent(context, title));
    }

    //打开目录页面
    public static void showBookCatalogActivity(Context context, String bookid) {
        context.startActivity(BookCatalogActivity.newIntent(context, bookid));
    }
}
