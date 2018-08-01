package com.hissenseguide;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hissenseguide.listener.ChangeViewInterface;

/**特色功能
 * Created by xuqunxing on 2017/11/22.
 */
public class GuideFeaturedFeaturesView extends RelativeLayout implements View.OnClickListener {

    private TextView nextTv;
    private TextView backTv;

    public GuideFeaturedFeaturesView(Context context) {
        super(context);
        initView();
    }

    public GuideFeaturedFeaturesView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public GuideFeaturedFeaturesView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public GuideFeaturedFeaturesView(Context context, ChangeViewInterface changeViewInterface1) {
        super(context);
        this.changeViewInterface = changeViewInterface1;
        initView();
    }

    private ChangeViewInterface changeViewInterface;

    private void initView(){
        View.inflate(getContext(), R.layout.guide_featured_features,this);
        nextTv = (TextView) findViewById(R.id.guide_activity_next_textview);
        backTv = (TextView) findViewById(R.id.guide_activity_back_textview);
        nextTv.setText(getContext().getString(R.string.guide_next));
        nextTv.setOnClickListener(this);
        backTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == backTv){
            if(changeViewInterface != null){
                changeViewInterface.setCurrentlyShowView(ChangeViewInterface.DATA_SETTING);
            }
        }else if(view == nextTv){
            if(changeViewInterface != null){
                changeViewInterface.setCurrentlyShowView(ChangeViewInterface.FINISH_VIEW);
            }
        }
    }
}
