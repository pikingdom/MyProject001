package com.wifi.vivoguide.wifi.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.sax.RootElement;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bootreg.IGuideCallback;
import com.wifi.vivoguide.Business.WifiHelper;
import com.wifi.vivoguide.R;
import com.wifi.vivoguide.commonview.SlipButtonView;
import com.wifi.vivoguide.event.SystemEvent;
import com.wifi.vivoguide.event.SystemEventConst;
import com.wifi.vivoguide.wifi.Dialog.WifiStaticDialogView;

import org.w3c.dom.Text;

/**
 * @Description: 设置Login页面
 * @author llf
 * @date 2017-7 17
 * 
 */
public class WifiSetLoginView implements OnClickListener, SystemEvent.EventListener{

	private Context mActivity;
	private TextView mTitleTxt;
	private RelativeLayout mCancelBtn;
	private RelativeLayout mCommonTitleView;
	private TextView mSetLoginTxt;
	private TextView mJumpBtn;
	private RelativeLayout mView;

	private View mSetLoginView;
	private IGuideCallback mListener;


	public View getWifiSetLoginView(){
		return mSetLoginView;
	}

	public WifiSetLoginView(Context activity, IGuideCallback listener) {
		mActivity = activity;
		mListener = listener;
		mSetLoginView = LayoutInflater.from(activity).inflate(R.layout.vivo_set_view, null);

		findViewsById();
		setViews();
		setListeners();
	}

	private void findViewsById() {
		mCommonTitleView = (RelativeLayout)mSetLoginView.findViewById(R.id.login_toolbar);
		mTitleTxt = (TextView)mCommonTitleView.findViewById(R.id.toobarTitle);
		mCancelBtn = (RelativeLayout)mCommonTitleView.findViewById(R.id.cancel);

        mView = (RelativeLayout)mSetLoginView.findViewById(R.id.view);
		mSetLoginTxt = (TextView)mSetLoginView.findViewById(R.id.vivo_login_tips);
        mJumpBtn = (TextView)mSetLoginView.findViewById(R.id.jump_text);
	}

	private void setListeners() {
		mCancelBtn.setOnClickListener(this);
		mSetLoginTxt.setOnClickListener(this);
		mJumpBtn.setOnClickListener(this);
        SystemEvent.addListener(SystemEventConst.EVENT_HIDE_CHOOSE_SERVICE_VIEW, this);

	}

	public void onDestroyEvent() {
        SystemEvent.removeListener(SystemEventConst.EVENT_HIDE_CHOOSE_SERVICE_VIEW, this);
    }

	private void setViews() {
        mView.setVisibility(View.GONE);
		mTitleTxt.setText(mActivity.getString(R.string.set_vivo));
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.cancel) {
			SystemEvent.fireEvent(SystemEventConst.EVENT_HIDE_WIFI_CONNECT_CONTAINER_VIEW);
		} else if (id == R.id.vivo_login_tips) {
			try{
				Intent accountIntent = new Intent();
				accountIntent.setComponent(new ComponentName("com.bbk.account", "com.bbk.account.activity.LoginActivity"));
				accountIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				mActivity.startActivity(accountIntent);  //startActivityForResult(accountIntent, 1);
			}catch (Exception e) {
				e.printStackTrace();
			}

		} else if (id == R.id.jump_text) {
            mView.setVisibility(View.VISIBLE);
            mView.removeAllViews();
            mView.bringToFront();
            ChooseServiceView chooseServiceView = new ChooseServiceView(mActivity, mListener);
            mView.addView(chooseServiceView.getChooseServiceView());

        }


	}

    @Override
    public void onEvent(SystemEventConst eventType, Intent data) {
        if (eventType == SystemEventConst.EVENT_HIDE_CHOOSE_SERVICE_VIEW) {
            mView.setVisibility(View.GONE);
        }
    }
}
