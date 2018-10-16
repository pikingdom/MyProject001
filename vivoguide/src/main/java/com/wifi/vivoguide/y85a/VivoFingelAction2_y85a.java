package com.wifi.vivoguide.y85a;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wifi.vivoguide.R;
import com.wifi.vivoguide.y79a.ChangeViewInterface;

/**导入数据
 * Created by xuqunxing on 2017/12/8.
 */
public class VivoFingelAction2_y85a extends RelativeLayout {

    private ChangeViewInterface changeViewInterface;
    private Context mActivity;
    private RelativeLayout mBackBtn;
    private TextView mTitleTxt;
    private TextView mLeftText;
    private TextView jumpText;

    public VivoFingelAction2_y85a(Context context) {
        super(context);
        init();
    }

    public VivoFingelAction2_y85a(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VivoFingelAction2_y85a(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public VivoFingelAction2_y85a(Context activity, ChangeViewInterface changeViewInterface1) {
        super(activity);
        this.mActivity = activity;
        this.changeViewInterface = changeViewInterface1;
        init();
    }

    private void init(){
        View.inflate(getContext(), R.layout.vivo_fingle_action2_y85a,this);
        mBackBtn = (RelativeLayout) findViewById(R.id.cancel);
        mTitleTxt = (TextView) findViewById(R.id.toobarTitle);
        mLeftText = (TextView) findViewById(R.id.tooba_left_text);
        mTitleTxt.setText(getContext().getString(R.string.vivo_finge_action_title));
        jumpText = (TextView) findViewById(R.id.jump_text);
        jumpText.setText(getContext().getString(R.string.nextStep));
        mLeftText.setVisibility(INVISIBLE);

        mBackBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(changeViewInterface != null){
                    changeViewInterface.setCurrentlyShowView(ChangeViewInterface.LANGUAGE_VIEW);
                }
            }
        });
        jumpText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(changeViewInterface != null){
                    changeViewInterface.setCurrentlyShowView(ChangeViewInterface.SERVICE_DES);
                }
            }
        });
    }

    public void onBackPressed() {
        if(changeViewInterface != null){
            changeViewInterface.setCurrentlyShowView(ChangeViewInterface.LANGUAGE_VIEW);
        }
    }
}
