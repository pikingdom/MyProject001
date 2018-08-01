package com.wifi.xiaomiguide.View;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wifi.xiaomiguide.Business.AnimationTranslateBusiness;
import com.wifi.xiaomiguide.ChangeViewInterface;
import com.wifi.xiaomiguide.R;


/**网络通讯
 * Created by xuqunxing on 2017/8/8.
 */

public class NetConmunicationView extends RelativeLayout implements View.OnClickListener {

    private TextView backTv;
    private TextView nextTv;
    private TextView textView2;
    private RelativeLayout netCommunicationRl;
    private ChangeViewInterface mChangeViewInterface;
    private PrivacyPolityView privacyPolityView;

    public NetConmunicationView(Context context, ChangeViewInterface changeViewInterface) {
        super(context,null);
        mChangeViewInterface = changeViewInterface;
        initView();
    }

    public NetConmunicationView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public NetConmunicationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    private void initView() {
        View.inflate(getContext(), R.layout.view_net_communication,this);
        netCommunicationRl = (RelativeLayout) findViewById(R.id.net_communication_rl);
        textView2 = (TextView) findViewById(R.id.textView2);
        backTv = (TextView) findViewById(R.id.bottom_back);
        nextTv = (TextView) findViewById(R.id.bottom_next);
        nextTv.setText(getContext().getString(R.string.go_on));
        backTv.setOnClickListener(this);
        nextTv.setOnClickListener(this);

        privacyPolityView = new PrivacyPolityView(getContext());
        privacyPolityView.setOnViewCLickListener(new UserPrivacyView.onViewCLickListener() {
            @Override
            public void onClickBackListener() {
                privacyPolityView.setVisibility(GONE);
            }
        });
        privacyPolityView.setVisibility(GONE);
        netCommunicationRl.addView(privacyPolityView);

        SpannableString spanText=new SpannableString(textView2.getText().toString());
        spanText.setSpan(new ClickableSpan() {

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLACK);       //设置文件颜色
                ds.setUnderlineText(true);      //设置下划线
            }

            @Override
            public void onClick(View view) {
                privacyPolityView.setVisibility(VISIBLE);
            }
        }, spanText.length() - 4, spanText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView2.setHighlightColor(Color.TRANSPARENT);
        textView2.setText(spanText);
        textView2.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void onClick(View view) {
        if(view == backTv){
            mChangeViewInterface.saveReturnDirection(AnimationTranslateBusiness.FROM_LEFT_TO_RIGHT);
            mChangeViewInterface.setCurrentlyShowView(ChangeViewInterface.SIM_CAR_VIEW, null);

        }else if(view == nextTv){
            mChangeViewInterface.saveReturnDirection(AnimationTranslateBusiness.FROM_RIGHT_TO_LEFT);
            mChangeViewInterface.setCurrentlyShowView(ChangeViewInterface.OTHER_SETTING_VIEW, null);

        }
    }

    public boolean onBackPressed() {
        if(privacyPolityView != null){
            if(privacyPolityView.getVisibility() == VISIBLE){
                privacyPolityView.setVisibility(GONE);
                return false;
            }
        }
        return true;
    }
}
