package com.wifi.xiaomiguide.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wifi.xiaomiguide.R;


/**用户协议
 * Created by xuqunxing on 2017/8/9.
 */

public class UserPrivacyView extends RelativeLayout{

    public UserPrivacyView(Context context) {
        this(context,null);
        initView();
    }

    public UserPrivacyView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public UserPrivacyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    private void initView() {
        View.inflate(getContext(), R.layout.view_user_privacy,this);
        TextView topBackTv = (TextView) findViewById(R.id.top_back);
        topBackTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                  if(onViewCLickListener != null){
                      onViewCLickListener.onClickBackListener();
                  }
            }
        });
    }

    private onViewCLickListener onViewCLickListener;

    public void setOnViewCLickListener(UserPrivacyView.onViewCLickListener onViewCLickListener) {
        this.onViewCLickListener = onViewCLickListener;
    }

    public interface onViewCLickListener{
        void onClickBackListener();
    }
}
