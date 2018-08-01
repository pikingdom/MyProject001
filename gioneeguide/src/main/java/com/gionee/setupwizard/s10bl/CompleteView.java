package com.gionee.setupwizard.s10bl;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.gionee.setupwizard.R;


/**
 * Created by xuqunxing on 2017/8/15.
 */
public class CompleteView extends RelativeLayout{

    private ChangeViewInterface changeViewInterface;
    public CompleteView(Context context) {
        super(context);
    }

    public CompleteView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CompleteView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public CompleteView(Context context, ChangeViewInterface changeViewInterface1) {
        super(context);
        this.changeViewInterface = changeViewInterface1;
        initView();
    }

    private void initView() {
        View.inflate(getContext(), R.layout.gn_view_complate,this);
        findViewById(R.id.welcom_btn).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                changeViewInterface.setCurrentlyShowView(ChangeViewInterface.OVER);
            }
        });
    }


}
