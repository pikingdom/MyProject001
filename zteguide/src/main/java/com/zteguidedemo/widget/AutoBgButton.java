package com.zteguidedemo.widget;

import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.widget.Button;

import com.zteguidedemo.R;
/**
 * Created by xuqunxing on 2017/7/18.
 */
public class AutoBgButton extends Button {
    private boolean mIsChecked;
    private GradientDrawable mShape;

    protected class AutoBgDrawable extends LayerDrawable {
        protected int _disabledAlpha = 100;
        protected int _fullAlpha = 0xff;
        protected ColorFilter _pressedFilter = new LightingColorFilter(-3355444, 1);

        public AutoBgDrawable(Drawable d) {
            super(new Drawable[]{d});
        }

        protected boolean onStateChange(int[] states) {
            boolean enabled = false;
            boolean pressed = false;
            for (int state : states) {
                if (state == 16842910) {
                    enabled = true;
                } else if (state == 16842919) {
                    pressed = true;
                }
            }
            mutate();
            if (enabled && pressed) {
                setColorFilter(this._pressedFilter);
            } else if (enabled) {
                setColorFilter(null);
                setAlpha(this._fullAlpha);
            } else {
                setColorFilter(null);
                setAlpha(this._disabledAlpha);
            }
            invalidateSelf();
            return super.onStateChange(states);
        }

        public boolean isStateful() {
            return true;
        }
    }

    public AutoBgButton(Context context) {
        this(context, null);
    }

    public AutoBgButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoBgButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mIsChecked = false;
    }

    private GradientDrawable getBgDrawable() {
        if (this.mShape == null) {
            this.mShape = (GradientDrawable) getResources().getDrawable(R.drawable.btn_shape);
            this.mShape.mutate();
        }
        return this.mShape;
    }

    public void setBackground(Drawable d) {
//        if (d instanceof ColorDrawable) {
//            Drawable bg = getBgDrawable();
//            if (bg != null) {
//                int color = ((ColorDrawable) d).getColor();
//               // bg.setColor(color);
//                Drawable wrappedDrawable = DrawableCompat.wrap(bg);
//                DrawableCompat.setTintList(wrappedDrawable, ColorStateList.valueOf(color));
//                d = bg;
//            }
//        }
        super.setBackground(d);
    }

    public void setBackgroundColor(int color) {
        GradientDrawable bg = getBgDrawable();
        if (bg != null) {
            bg.setColor(color);
        }
        setBackground(bg);
    }

    public void setChecked(boolean isChecked) {
        if (this.mIsChecked != isChecked) {
            this.mIsChecked = isChecked;
            GradientDrawable bg = getBgDrawable();
            if (bg != null) {
                if (this.mIsChecked) {
                    bg.setStroke(6, 0xff000000);
                } else {
                    bg.setStroke(0, 0);
                }
                setBackground(bg);
            }
        }
    }

    public boolean isChecked() {
        return this.mIsChecked;
    }
}
