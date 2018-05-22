package com.lrony.iread.pref;

import android.content.Context;
import android.content.SharedPreferences;

import com.lrony.iread.App;

/**
 * Created by Lrony on 18-5-22.
 * 保存APP的一些配置
 */
public class AppConfig {

    public final static String APP_CONFIG = "appConfig";

    private final static String K_NIGHT_MODE = "night_mode";

    private static SharedPreferences sPref;

    private static SharedPreferences getPreferences() {
        if (sPref == null) {
            sPref = App.context().getSharedPreferences(APP_CONFIG, Context.MODE_PRIVATE);
        }
        return sPref;
    }

    public static boolean isNightMode() {
        return getPreferences().getBoolean(K_NIGHT_MODE, false);
    }

    public static void setNightMode(boolean isNightMode) {
        getPreferences()
                .edit()
                .putBoolean(K_NIGHT_MODE, isNightMode)
                .apply();
    }
}
