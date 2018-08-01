package com.zteguidedemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.zteguidedemo.listener.OnZteClickListener;

/**
 * Created by xuqunxing on 2017/7/17.
 */

public class BaseRelativilayout extends RelativeLayout{
    protected OnZteClickListener onZteClickListener;
    public BaseRelativilayout(Context context) {
        this(context,null);
    }

    public BaseRelativilayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BaseRelativilayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public void setOnZteClickListener(OnZteClickListener onZteClickListener) {
        this.onZteClickListener = onZteClickListener;
    }
}
