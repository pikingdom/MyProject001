package com.wifi.xiaomiguide.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wifi.xiaomiguide.R;

/**隐私政策
 * Created by xuqunxing on 2017/8/9.
 */

public class PrivacyPolityView extends RelativeLayout implements View.OnClickListener {

    private TextView topBackTv;

    public PrivacyPolityView(Context context) {
        this(context,null);
        initView();
    }

    public PrivacyPolityView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PrivacyPolityView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    private void initView() {
        View.inflate(getContext(), R.layout.view_privacy_polity,this);
        topBackTv = (TextView) findViewById(R.id.top_back);
        topBackTv.setText(getContext().getString(R.string.privacy_policy));
        topBackTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == topBackTv){
             if(onViewCLickListener != null){
                 onViewCLickListener.onClickBackListener();
             }
        }
    }


    private UserPrivacyView.onViewCLickListener onViewCLickListener;

    public void setOnViewCLickListener(UserPrivacyView.onViewCLickListener onViewCLickListener) {
        this.onViewCLickListener = onViewCLickListener;
    }

    public interface onViewCLickListener{
        void onClickBackListener();
    }

}
