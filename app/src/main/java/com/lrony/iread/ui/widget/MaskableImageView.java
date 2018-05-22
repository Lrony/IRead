package com.lrony.iread.ui.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * Created by Lrony on 18-5-22.
 */
public class MaskableImageView extends AppCompatImageView {

    private boolean isEnabledMaskable = true;

    public MaskableImageView(Context context) {
        super(context);
    }

    public MaskableImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MaskableImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setPressed(boolean pressed) {
        super.setPressed(pressed);
        if (isEnabledMaskable) {
            handlerPressed(pressed);
        }
    }

    private void handlerPressed(boolean pressed) {
        if (pressed) {
            Drawable drawable = getDrawable();
            if (drawable != null) {
                drawable.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
                ViewCompat.postInvalidateOnAnimation(this);
            }
        } else {
            Drawable drawableUp = getDrawable();
            if (drawableUp != null) {
                drawableUp.clearColorFilter();
                ViewCompat.postInvalidateOnAnimation(this);
            }
        }
    }

    public void setEnabledMaskable(boolean enabledMaskable) {
        isEnabledMaskable = enabledMaskable;
        Drawable drawableUp = getDrawable();
        if (drawableUp != null) {
            drawableUp.clearColorFilter();
            ViewCompat.postInvalidateOnAnimation(this);
        }

    }

    public boolean isEnabledMaskable() {
        return isEnabledMaskable;
    }
}
