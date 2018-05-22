package com.lrony.iread.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by lrony on 2018/4/9.
 * Manage all Activity lifecycle, such as statistics and recovery.
 *
 * 管理所有Activity生命周期，如：统计、恢复
 */
public class BaseSuperActivity extends SupportActivity {

    private static final String TAG = "BaseSuperActivity";

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Statsu recovery
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Status save
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
