package com.zteguidedemo.v0840;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.zteguidedemo.R;
import com.zteguidedemo.view.BaseRelativilayout;
import com.zteguidedemo.widget.AutoBgButton;

/**
 * Created by xuqunxing on 2017/7/18.
 */
public class Zte0840VoicePrivacyView extends BaseRelativilayout implements View.OnClickListener {

    private AutoBgButton autoBgButton;
    private ImageView backIv;

    public Zte0840VoicePrivacyView(Context context) {
        this(context,null);
    }

    public Zte0840VoicePrivacyView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public Zte0840VoicePrivacyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View.inflate(getContext(), R.layout.zet0840_voice_privacy,this);
        backIv = (ImageView) findViewById(R.id.zte0840_back);
        autoBgButton = (AutoBgButton) findViewById(R.id.back);
        autoBgButton.setOnClickListener(this);
        backIv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == autoBgButton || view == backIv){
            if(onZteClickListener != null){
                onZteClickListener.OnDone(this);
            }
        }
    }
}
