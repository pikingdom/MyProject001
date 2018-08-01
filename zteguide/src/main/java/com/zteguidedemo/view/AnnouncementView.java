package com.zteguidedemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.zteguidedemo.R;
import com.zteguidedemo.widget.AutoBgButton;

/**
 * Created by xuqunxing on 2017/7/18.
 */
public class AnnouncementView extends BaseRelativilayout implements View.OnClickListener {

    private AutoBgButton autoBgButton;

    public AnnouncementView(Context context) {
        this(context,null);
    }

    public AnnouncementView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AnnouncementView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View.inflate(getContext(), R.layout.user_declare_activity,this);
        autoBgButton = (AutoBgButton) findViewById(R.id.back);
        autoBgButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == autoBgButton){
            if(onZteClickListener != null){
                onZteClickListener.OnDone(this);
            }
        }
    }
}
