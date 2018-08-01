package com.gionee.setupwizard.s10bl;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.gionee.setupwizard.R;


/**
 * Created by xuqunxing on 2017/8/16.
 */
public class PrivacyProtocolView extends RelativeLayout{


    private ImageView backIv;

    public PrivacyProtocolView(Context context) {
        super(context);
        initView();
    }

    public PrivacyProtocolView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public PrivacyProtocolView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View.inflate(getContext(), R.layout.gn_sw_layout_privacy_protocol,this);
        backIv = (ImageView) findViewById(R.id.gn_sw_layout_back);
        backIv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onGnClickListener != null){
                    onGnClickListener.onGnBackListener();
                }
            }
        });
    }


    private UserContractView.onGnClickListener onGnClickListener;

    public void setOnGnClickListener(UserContractView.onGnClickListener onGnClickListener) {
        this.onGnClickListener = onGnClickListener;
    }

    public interface onGnClickListener{
        void onGnBackListener();
    }

}
