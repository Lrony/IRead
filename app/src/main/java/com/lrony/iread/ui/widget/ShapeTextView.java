package com.lrony.iread.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.lrony.iread.ui.help.SuperConfig;

/**
 * Created by Lrony on 18-5-22.
 */
public class ShapeTextView extends AppCompatTextView {

    public ShapeTextView(Context context) {
        super(context);
    }

    public ShapeTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initSuperShapeView(attrs);
    }

    public ShapeTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initSuperShapeView(attrs);
    }

    private void initSuperShapeView(AttributeSet attrs) {
        new SuperConfig().beSuperView(attrs, this);
    }
}
