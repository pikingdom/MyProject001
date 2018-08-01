package com.wifi.vivoguide.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;

public class InputMethodsManager {

    /**
     * 显示软键盘
     */
    @SuppressLint("ServiceCast")
    public static void showKeyboard(View view) {
        if (null == view)
            return;
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 隐藏软键盘
     */
    @SuppressLint("ServiceCast")
    public static void hideKeyboard(View view) {
        if (null == view)
            return;
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 隐藏软键盘
     */
    @SuppressLint("ServiceCast")
    public static void createHideInputMethod(Activity ctx) {
        final InputMethodManager manager = (InputMethodManager) ctx.getSystemService(Activity.INPUT_METHOD_SERVICE);
        ctx.getWindow().getDecorView().setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (manager.isActive()) {
                    manager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                return true;
            }
        });
    }
}