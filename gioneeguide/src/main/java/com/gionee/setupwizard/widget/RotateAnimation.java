package com.gionee.setupwizard.widget;

/**
 * 作者：xiaomao on 2017/7/14.
 */

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;

public class RotateAnimation extends Animation {
    public static final long DURATION = 1000;
    private Camera mCamera;
    private final float mCenterX;
    private final float mCenterY;
    private InterpolatedTimeListener mListener;

    public interface InterpolatedTimeListener {
        public static final int resId = 0;

        void interpolatedTime(float f);
    }

    public RotateAnimation(float cX, float cY) {
        this.mCenterX = cX;
        this.mCenterY = cY;
        setInterpolator(new LinearInterpolator());
        setDuration(DURATION);
        setFillAfter(true);
    }

    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        this.mCamera = new Camera();
    }

    public void setInterpolatedTimeListener(InterpolatedTimeListener listener) {
        this.mListener = listener;
    }

    protected void applyTransformation(float interpolatedTime, Transformation transformation) {
        if (this.mListener != null) {
            this.mListener.interpolatedTime(interpolatedTime);
        }
        float degree = 0.0f + (180.0f * interpolatedTime);
        if (interpolatedTime > 0.5f) {
            degree -= 180.0f;
        }
        Matrix matrix = transformation.getMatrix();
        this.mCamera.save();
        this.mCamera.rotateX(degree);
        this.mCamera.getMatrix(matrix);
        this.mCamera.restore();
        matrix.preTranslate(-this.mCenterX, -this.mCenterY);
        matrix.postTranslate(this.mCenterX, this.mCenterY);
    }
}
