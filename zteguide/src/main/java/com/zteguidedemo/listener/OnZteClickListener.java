package com.zteguidedemo.listener;

import android.view.View;

/**
 * Created by xuqunxing on 2017/7/17.
 */

public interface OnZteClickListener {

    void OnNext(View view);
    void OnSkip(View view);
    void OnPrevious(View view);
    void OnDone(View view);
}
