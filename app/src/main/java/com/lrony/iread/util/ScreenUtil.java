package com.lrony.iread.util;

import android.content.Context;
import android.content.res.Configuration;

public class ScreenUtil {

    public static boolean isLAndscape(Context context) {
        Configuration mConfiguration = context.getResources().getConfiguration();
        int ori = mConfiguration.orientation; //获取屏幕方向
        if (ori == mConfiguration.ORIENTATION_LANDSCAPE) {
            return true;
        } else if (ori == mConfiguration.ORIENTATION_PORTRAIT) {
            return false;
        }
        return false;
    }
}