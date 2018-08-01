package com.coloros.bootreg.view;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2017/7/15.
 */

public abstract class GuidePage extends LinearLayout {
    protected OnOpListener onOPListener;
    protected IWelcomePage welcomePage;
    protected GuidePage last;

    public GuidePage(Context context) {
        super(context);
    }

    public GuidePage(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void setOnOPListener(OnOpListener listener) {
        this.onOPListener = listener;
    }

    public void setWelcomePage(IWelcomePage welcomePage) {
        this.welcomePage = welcomePage;
    }

    public void setLastPage(GuidePage page) {
        last = page;
    }

    public GuidePage getLastPage() {
        return last;
    }

    public abstract void onActivityResult(int requestCode, int resultCode, Intent data);

    public void addView(View view) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(view, lp);
    }

}
