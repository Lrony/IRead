package com.lrony.iread;

import android.app.Application;
import android.content.Context;

/**
 * Created by Lrony on 18-5-21.
 */
public class App extends Application {

    private static final String TAG = "AppIRead";

    private static App mContext = null;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        mContext = this;
    }

    public static App context() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
