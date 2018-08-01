package com.wifi.vivoguide.y79;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wifi.vivoguide.R;
import com.wifi.vivoguide.y79a.ChangeViewInterface;

/**导入数据
 * Created by xuqunxing on 2017/12/8.
 */
public class VivoImportData_y79 extends RelativeLayout {

    private ChangeViewInterface changeViewInterface;
    private Context mActivity;
    private RelativeLayout mBackBtn;
    private TextView mTitleTxt;
    private TextView mLeftText;
    private TextView jumpText;

    public VivoImportData_y79(Context context) {
        super(context);
        init();
    }

    public VivoImportData_y79(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VivoImportData_y79(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public VivoImportData_y79(Context activity,ChangeViewInterface changeViewInterface1) {
        super(activity);
        this.mActivity = activity;
        this.changeViewInterface = changeViewInterface1;
        init();
    }

    private void init(){
        View.inflate(getContext(), R.layout.vivo_import_data_y79,this);
        mBackBtn = (RelativeLayout) findViewById(R.id.cancel);
        mTitleTxt = (TextView) findViewById(R.id.toobarTitle);
        mLeftText = (TextView) findViewById(R.id.tooba_left_text);
        mTitleTxt.setText(getContext().getString(R.string.vivo_import_data_y79));
        jumpText = (TextView) findViewById(R.id.jump_text);
        jumpText.setText(getContext().getString(R.string.vivo_import_data_y79_import));
        mLeftText.setVisibility(VISIBLE);

        mBackBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(changeViewInterface != null){
                    changeViewInterface.setCurrentlyShowView(ChangeViewInterface.SELECT_SERVICE);
                }
            }
        });
        mLeftText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(changeViewInterface != null){
                    changeViewInterface.setCurrentlyShowView(ChangeViewInterface.FINISH_VIEW);
                }
            }
        });
        jumpText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.EASYSHARE_CAPTURE");
                    intent.putExtra("intent_from", 2);
                    getContext().startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void onBackPressed() {
        if(changeViewInterface != null){
            changeViewInterface.setCurrentlyShowView(ChangeViewInterface.SELECT_SERVICE);
        }
    }
}
