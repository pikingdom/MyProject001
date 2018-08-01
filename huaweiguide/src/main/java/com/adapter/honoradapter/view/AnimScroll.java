package com.adapter.honoradapter.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

/**
 * Created by xuqunxing on 2017/7/20.
 */
public class AnimScroll extends ScrollView {
    private boolean canPullDown = false;
    private boolean canPullUp = false;
    private View contentView = null;
    private boolean isMoved = false;
    private OnScrollChangedListener onScrollChangedListener;
    private Rect originalRect = new Rect();
    private float startY;

    public static class MyAnim extends TranslateAnimation {
        public MyAnim(float fromXDelta, float toXDelta, float fromYDelta, float toYDelta) {
            super(fromXDelta, toXDelta, fromYDelta, toYDelta);
        }

        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation((float) Math.pow((double) interpolatedTime, 0.1d), t);
        }
    }

    public interface OnScrollChangedListener {
        void onScrollChanged(int i, int i2, int i3, int i4);
    }

    public AnimScroll(Context context) {
        super(context);
    }

    public AnimScroll(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 0) {
            this.contentView = getChildAt(0);
        }
    }

    protected void onScrollChanged(int x, int y, int oldX, int oldY) {
        super.onScrollChanged(x, y, oldX, oldY);
        if (this.onScrollChangedListener != null) {
            this.onScrollChangedListener.onScrollChanged(x, y, oldX, oldY);
        }
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (this.contentView != null) {
            this.originalRect.set(this.contentView.getLeft(), this.contentView.getTop(), this.contentView.getRight(), this.contentView.getBottom());
        }
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean shouldMove = false;
        if (this.contentView == null) {
            return super.dispatchTouchEvent(ev);
        }
        switch (ev.getAction()) {
            case 0:
                this.canPullDown = isCanPullDown();
                this.canPullUp = isCanPullUp();
                this.startY = ev.getY();
                break;
            case 1:
                if (this.isMoved) {
                    MyAnim anim = new MyAnim(0.0f, 0.0f, (float) this.contentView.getTop(), (float) this.originalRect.top);
                    anim.setDuration(567);
                    this.contentView.startAnimation(anim);
                    this.contentView.layout(this.originalRect.left, this.originalRect.top, this.originalRect.right, this.originalRect.bottom);
                    this.canPullDown = false;
                    this.canPullUp = false;
                    this.isMoved = false;
                    break;
                }
                break;
            case 2:
                if (!this.canPullDown && !this.canPullUp) {
                    this.startY = ev.getY();
                    this.canPullDown = isCanPullDown();
                    this.canPullUp = isCanPullUp();
                    break;
                }
                int deltaY = (int) (ev.getY() - this.startY);
                if ((this.canPullDown && deltaY > 0) || (this.canPullUp && deltaY < 0)) {
                    shouldMove = true;
                } else if (this.canPullUp) {
                    shouldMove = this.canPullDown;
                }
                if (shouldMove) {
                    int offset = (int) (((float) deltaY) * 0.3f);
                    this.contentView.layout(this.originalRect.left, this.originalRect.top + offset, this.originalRect.right, this.originalRect.bottom + offset);
                    this.isMoved = true;
                    break;
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean isCanPullDown() {
        boolean z = true;
        if (this.contentView == null) {
            return false;
        }
        if (getScrollY() != 0 && this.contentView.getHeight() >= getHeight() + getScrollY()) {
            z = false;
        }
        return z;
    }

    private boolean isCanPullUp() {
        boolean z = false;
        if (this.contentView == null) {
            return false;
        }
        if (this.contentView.getHeight() <= getHeight() + getScrollY()) {
            z = true;
        }
        return z;
    }
}