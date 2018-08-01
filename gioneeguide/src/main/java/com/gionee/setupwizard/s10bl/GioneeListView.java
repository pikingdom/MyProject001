package com.gionee.setupwizard.s10bl;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by xuqunxing on 2017/8/16.
 */

public class GioneeListView extends ListView{
    public GioneeListView(Context context) {
        super(context);
    }

    public GioneeListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GioneeListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);

        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
