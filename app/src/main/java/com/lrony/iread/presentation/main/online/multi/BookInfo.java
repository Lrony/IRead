package com.lrony.iread.presentation.main.online.multi;

import android.support.annotation.NonNull;

/**
 * Created by Lrony on 18-5-23.
 */
public class BookInfo {

    // 标题
    @NonNull
    public final String title;
    // 简介
    @NonNull
    public final String shortIntro;
    // 作者
    @NonNull
    public final String author;
    // 图片
    @NonNull
    public final String cover;
    // 分类
    @NonNull
    public final String majorCate;
    // 留存率
    @NonNull
    public final double retentionRatio;

    public BookInfo(@NonNull String title, @NonNull String shortIntro, @NonNull String author, @NonNull String cover, @NonNull String majorCate, @NonNull double retentionRatio) {
        this.title = title;
        this.shortIntro = shortIntro;
        this.author = author;
        this.cover = cover;
        this.majorCate = majorCate;
        this.retentionRatio = retentionRatio;
    }
}
