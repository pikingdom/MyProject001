package com.zteguidedemo.v0840;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.zteguidedemo.R;
import com.zteguidedemo.view.BaseRelativilayout;

/**
 * Created by xuqunxing on 2017/7/17.
 */
public class ZTE0840SimView extends BaseRelativilayout implements View.OnClickListener {

    private TextView previousTv;
    private TextView skipTv;
    private ChangeViewInterface changeViewInterface;
    public ZTE0840SimView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ZTE0840SimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public ZTE0840SimView(Context context, ChangeViewInterface changeViewInterface1) {
        super(context);
        this.changeViewInterface = changeViewInterface1;
        initView();
    }

    private void initView() {
        View.inflate(getContext(), R.layout.view_zte0840_sim_card,this);
        previousTv = (TextView) findViewById(R.id.zte_previous_step_btn);
        skipTv = (TextView) findViewById(R.id.zte_next_step_btn);
        skipTv.setText(getContext().getResources().getString(R.string.skip_step));
        previousTv.setOnClickListener(this);
        skipTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == previousTv){
            if(changeViewInterface != null){
                changeViewInterface.setCurrentlyShowView(ChangeViewInterface.WIFI_VIEW);
            }
        }else if(view == skipTv){
            if(changeViewInterface != null){
                changeViewInterface.setCurrentlyShowView(ChangeViewInterface.MYPHONE_VIEW);
            }
        }
    }
}
