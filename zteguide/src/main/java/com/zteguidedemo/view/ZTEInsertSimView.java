package com.zteguidedemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.zteguidedemo.R;

/**
 * Created by xuqunxing on 2017/7/17.
 */
public class ZTEInsertSimView extends BaseRelativilayout implements View.OnClickListener {

    private TextView previousTv;
    private TextView skipTv;
    public ZTEInsertSimView(Context context) {
        this(context,null);
    }

    public ZTEInsertSimView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ZTEInsertSimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View.inflate(getContext(), R.layout.view_sim_card,this);
        previousTv = (TextView) findViewById(R.id.view_previous);
        skipTv = (TextView) findViewById(R.id.view_next);
        skipTv.setText(getContext().getResources().getString(R.string.skip_step));
        previousTv.setOnClickListener(this);
        skipTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == previousTv){
            if(onZteClickListener != null){
                onZteClickListener.OnPrevious(this);
            }
        }else if(view == skipTv){
            if(onZteClickListener != null){
                onZteClickListener.OnSkip(this);
            }
        }
    }
}
