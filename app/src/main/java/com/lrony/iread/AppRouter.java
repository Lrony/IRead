package com.lrony.iread;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import com.lrony.iread.presentation.search.SearchActivity;
import com.lrony.iread.presentation.settings.SettingsActivity;

/**
 * Created by Lrony on 18-5-21.
 * APP路由，Activity跳转等操作在这包装好方法
 */
public class AppRouter {

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
}
