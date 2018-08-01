package com.wifi.xiaomiguide.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wifi.xiaomiguide.Business.AnimationTranslateBusiness;
import com.wifi.xiaomiguide.ChangeViewInterface;
import com.wifi.xiaomiguide.R;

/**其他设置view
 * Created by xuqunxing on 2017/8/8.
 */
public class OtherSettingView extends RelativeLayout implements View.OnClickListener {

    private TextView nextTv;
    private TextView backTv;
    private ChangeViewInterface mChangeViewInterface;
    public OtherSettingView(Context context, ChangeViewInterface changeViewInterface) {
        super(context,null);
        mChangeViewInterface = changeViewInterface;
        initView();
    }

    public OtherSettingView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public OtherSettingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    private void initView() {
        View.inflate(getContext(), R.layout.view_other_settting,this);
        nextTv = (TextView) findViewById(R.id.bottom_next);
        backTv = (TextView) findViewById(R.id.bottom_back);

        nextTv.setText(getContext().getString(R.string.go_on));
        nextTv.setOnClickListener(this);
        backTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == backTv){
            mChangeViewInterface.saveReturnDirection(AnimationTranslateBusiness.FROM_LEFT_TO_RIGHT);
            mChangeViewInterface.setCurrentlyShowView(ChangeViewInterface.NET_CONMUNICATION_VIEW, null);
        }else if(view == nextTv){
            mChangeViewInterface.saveReturnDirection(AnimationTranslateBusiness.FROM_RIGHT_TO_LEFT);
            mChangeViewInterface.setCurrentlyShowView(ChangeViewInterface.FINISH_VIEW, null);

        }
    }
}
