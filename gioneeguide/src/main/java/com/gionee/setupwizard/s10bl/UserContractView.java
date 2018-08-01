package com.gionee.setupwizard.s10bl;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gionee.setupwizard.R;


/**用户协议
 * Created by xuqunxing on 2017/8/16.
 */
public class UserContractView extends RelativeLayout implements View.OnClickListener {

    private TextView title;
    private ImageView backIv;

    public UserContractView(Context context) {
        super(context);
        initView();
    }

    public UserContractView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public UserContractView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View.inflate(getContext(), R.layout.gn_sw_layout_user_contract,this);
        title = (TextView) findViewById(R.id.gn_sw_layout_content_title);
        backIv = (ImageView) findViewById(R.id.gn_sw_layout_back);
        backIv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == backIv){
            if(onGnClickListener != null){
                onGnClickListener.onGnBackListener();
            }
        }
    }


    private onGnClickListener onGnClickListener;

    public void setOnGnClickListener(UserContractView.onGnClickListener onGnClickListener) {
        this.onGnClickListener = onGnClickListener;
    }

    public interface onGnClickListener{
        void onGnBackListener();
    }
}
