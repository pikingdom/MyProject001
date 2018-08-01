package com.wifi.xiaomiguide.Dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wifi.xiaomiguide.R;

/**
 * @Description: 动态和静态设置
 * @author llf
 * @date 2017-08-08
 *
 */
public class IpChooseDialogView extends Dialog implements OnClickListener {

    private Context mContext;

    private RelativeLayout mChooseDhcpLayout, mChooseStaticLayout;
    private TextView mChooseDhcpTxt, mChooseStaticTxt;

    @SuppressLint("WifiManagerLeak")
    public IpChooseDialogView(Context activity) {
        super(activity, R.style.Dialog);
        mContext = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ip_set_choose_view);

        //设置为点击对话框之外的区域对话框不消失
        this.setCanceledOnTouchOutside(false);

        findViewsById();
        setViews();
        setListeners();
    }

    private ImageView mDhcpImg, mStaticImg;
    private void findViewsById() {
        mChooseDhcpLayout = (RelativeLayout) findViewById(R.id.layout_dhcp);
        mDhcpImg = (ImageView) findViewById(R.id.sel_image);
        mChooseDhcpTxt = (TextView) findViewById(R.id.choose_dhcp);

        mChooseStaticLayout = (RelativeLayout) findViewById(R.id.layout_static);
        mStaticImg = (ImageView) findViewById(R.id.sel_image2);
        mChooseStaticTxt = (TextView) findViewById(R.id.choose_static);
    }

    private void setListeners() {
        mChooseDhcpLayout.setOnClickListener(this);
        mChooseStaticLayout.setOnClickListener(this);
    }

    private void setViews() {
        mDhcpImg.setVisibility(View.VISIBLE);
        mChooseDhcpTxt.setTextColor(this.mContext.getResources().getColor(R.color.light_blue));

        mStaticImg.setVisibility(View.GONE);
        mChooseStaticTxt.setTextColor(this.mContext.getResources().getColor(R.color.black));
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.layout_static) {
            mStaticImg.setVisibility(View.VISIBLE);
            mDhcpImg.setVisibility(View.GONE);
            mChooseStaticTxt.setTextColor(this.mContext.getResources().getColor(R.color.light_blue));
            mChooseDhcpTxt.setTextColor(this.mContext.getResources().getColor(R.color.black));
            if (mIChooseWhichIpSet != null)
                mIChooseWhichIpSet.showChangeIpSet("STATIC");
        } else if (id == R.id.layout_dhcp) {
            mDhcpImg.setVisibility(View.VISIBLE);
            mStaticImg.setVisibility(View.GONE);
            mChooseDhcpTxt.setTextColor(this.mContext.getResources().getColor(R.color.light_blue));
            mChooseStaticTxt.setTextColor(this.mContext.getResources().getColor(R.color.black));
            if (mIChooseWhichIpSet != null)
                mIChooseWhichIpSet.showChangeIpSet("DHCP");
        }
        dismiss();
    }

    private IChooseWhichIpSet mIChooseWhichIpSet;
    public interface IChooseWhichIpSet {
        void showChangeIpSet(String chooseWhich);
    }

    public void setChooseWhichIpSetListener(IChooseWhichIpSet l) {
        mIChooseWhichIpSet = l;
    }
}
