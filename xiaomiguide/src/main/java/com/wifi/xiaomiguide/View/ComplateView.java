package com.wifi.xiaomiguide.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.wifi.xiaomiguide.ChangeViewInterface;
import com.wifi.xiaomiguide.R;

/**设置完毕
 * Created by xuqunxing on 2017/8/8.
 */
public class ComplateView extends RelativeLayout{
    private ChangeViewInterface mChangeViewInterface;

    public ComplateView(Context context, ChangeViewInterface changeViewInterface) {
        super(context,null);
        mChangeViewInterface = changeViewInterface;
        initView();
    }

    public ComplateView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ComplateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    private void initView() {
        View.inflate(getContext(), R.layout.miui_view_complate,this);
        ImageView complateIV = (ImageView) findViewById(R.id.miui_iv_complate);
        complateIV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mChangeViewInterface.setCurrentlyShowView(ChangeViewInterface.GUIDE_OVER,null);
            }
        });
    }
}
