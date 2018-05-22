package com.lrony.iread.pref;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.lrony.iread.App;
import com.lrony.iread.R;

/**
 * Created by Lrony on 18-5-18.
 * 通过系统设置的值都可以在这里面操作
 */
public class PreferencesHelper {

    private Context context;
    private static PreferencesHelper sPreferencesHelper;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mPreferencesEditor;

    private PreferencesHelper(Context context) {
        this.context = context;
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        mPreferencesEditor = mPreferences.edit();
    }

    public static PreferencesHelper getInstance() {
        if (sPreferencesHelper == null) {
            sPreferencesHelper = new PreferencesHelper(App.context());
        }
        return sPreferencesHelper;
    }

    public void putBoolean(int id, boolean value) {
        mPreferencesEditor.putBoolean(context.getString(id), value);
    }

    public boolean getBoolean(int id, boolean defValue) {
        return mPreferences.getBoolean(context.getString(id), defValue);
    }

    // *********************************

    // 是否是夜间模式
    public boolean isNightMode() {
        return getBoolean(R.string.pref_key_mode_night, false);
    }

    // 设置模式
    public void setNightMode(boolean value) {
        putBoolean(R.string.pref_key_mode_night, value);
    }
}
