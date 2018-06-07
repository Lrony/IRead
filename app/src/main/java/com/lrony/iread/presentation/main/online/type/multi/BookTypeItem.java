package com.lrony.iread.presentation.main.online.type.multi;

import android.support.annotation.NonNull;

/**
 * Created by Lrony on 18-6-7.
 */
public class BookTypeItem {

    @NonNull
    public final String title;
    @NonNull
    public final String num;

    public BookTypeItem(@NonNull String title, @NonNull String num) {
        this.title = title;
        this.num = num;
    }
}
