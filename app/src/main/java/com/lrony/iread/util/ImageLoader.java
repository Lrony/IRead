package com.lrony.iread.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lrony.iread.R;
import com.lrony.iread.ui.help.GlideApp;

/**
 * Created by Lrony on 18-5-22.
 * 在线图像加载类，封装一下方便改
 */
public class ImageLoader {

    public static void load(Context context, String image, ImageView view) {
        GlideApp
                .with(context)
                .load(image)
                .placeholder(R.drawable.ic_book_cover_default)
                .into(view);
    }

//    public static void load(Context context, String image, PhotoView view) {
//        GlideApp
//                .with(context)
//                .load(image)
//                .into(view);
//    }

    public static void clear(Context context) {

        Glide.getPhotoCacheDir(context).delete();
    }
}
