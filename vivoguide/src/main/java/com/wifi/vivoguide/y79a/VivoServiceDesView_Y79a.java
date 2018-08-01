package com.wifi.vivoguide.y79a;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wifi.vivoguide.R;

/**服务说明View
 * Created by xuqunxing on 2017/11/27.
 */
public class VivoServiceDesView_Y79a extends RelativeLayout implements View.OnClickListener {

    private TextView titleTv;
    private RelativeLayout cancleRl;
    private TextView unAgreeTv;
    private TextView agreeTv;
    private boolean isSkipVivoServiceDesc;//是否跳过显示服务说明页面
    public VivoServiceDesView_Y79a(Context context) {
        super(context);
    }

    public VivoServiceDesView_Y79a(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VivoServiceDesView_Y79a(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public VivoServiceDesView_Y79a(Context context,boolean isSkipVivoServiceDesc1,ChangeViewInterface changeViewInterface1,onVivoClickListener onVivoClickListener1) {
        super(context);
        this.context = context;
        this.isSkipVivoServiceDesc = isSkipVivoServiceDesc1;
        this.changeViewInterface = changeViewInterface1;
        this.onVivoClickListener = onVivoClickListener1;
        if(isSkipVivoServiceDesc){
            if(changeViewInterface != null){
                changeViewInterface.setCurrentlyShowView(ChangeViewInterface.WIFI_VIEW);
            }
        }else {
            initView();
        }
    }
    private ChangeViewInterface changeViewInterface;
    private Context context;

    private void initView() {
        View.inflate(getContext(), R.layout.vivo_service_dec_view_y79a,this);
        cancleRl = (RelativeLayout) findViewById(R.id.cancel);
        titleTv = (TextView) findViewById(R.id.toobarTitle);
        titleTv.setText(getContext().getString(R.string.vivo_service_des));
        unAgreeTv = (TextView) findViewById(R.id.service_un_agree_tv);
        agreeTv = (TextView) findViewById(R.id.service_agree_tv);
        agreeTv.setOnClickListener(this);
        unAgreeTv.setOnClickListener(this);
        cancleRl.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == agreeTv){
            if(changeViewInterface != null){
                changeViewInterface.setCurrentlyShowView(ChangeViewInterface.WIFI_VIEW);
            }
            if(onVivoClickListener != null){
                onVivoClickListener.onAgreeClickListener(true);
            }
        }else if(view == unAgreeTv){
            if(changeViewInterface != null){
                changeViewInterface.setCurrentlyShowView(ChangeViewInterface.LANGUAGE_VIEW);
            }
        }else if(view ==cancleRl){
            if(changeViewInterface != null){
                changeViewInterface.setCurrentlyShowView(ChangeViewInterface.LANGUAGE_VIEW);
            }
        }
    }

    public void onBackPressed() {
        if(changeViewInterface != null){
            changeViewInterface.setCurrentlyShowView(ChangeViewInterface.LANGUAGE_VIEW);
        }
    }

    private onVivoClickListener onVivoClickListener ;
    public interface onVivoClickListener{
        void onAgreeClickListener(boolean isSkipVivoServiceDesc);//是否跳过服务说明页面
    }
}
