package com.hissenseguide;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.hissenseguide.listener.ChangeViewInterface;


/**
 * Created by xuqunxing on 2017/11/22.
 */
public class GuideCompleteView extends RelativeLayout{

    public GuideCompleteView(Context context) {
        super(context);
        initView();
    }

    public GuideCompleteView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public GuideCompleteView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public GuideCompleteView(Context context,ChangeViewInterface changeViewInterface1) {
        super(context);
        this.changeViewInterface = changeViewInterface1;
        initView();
    }

    private ChangeViewInterface changeViewInterface;

    private void initView(){
        View.inflate(getContext(), R.layout.guide_complete ,this);
        Button finishBt = (Button) findViewById(R.id.guide_finish_button);
        finishBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                  if(changeViewInterface != null){
                      changeViewInterface.setCurrentlyShowView(ChangeViewInterface.GUIDE_OVER);
                  }
            }
        });
    }
}
