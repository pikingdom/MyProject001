package com.wifi.vivoguide.wifi.Activity;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bootreg.IGuideCallback;
import com.wifi.vivoguide.R;
import com.wifi.vivoguide.commonview.SlipButtonView;
import com.wifi.vivoguide.event.SystemEvent;
import com.wifi.vivoguide.event.SystemEventConst;

/**
 * @Description: 选择服务页面
 * @author llf
 * @date 2017-7 17
 * 
 */
public class ChooseServiceView implements OnClickListener{

	private Context mActivity;
	private TextView mTitleTxt;
	private RelativeLayout mCancelBtn;
	private RelativeLayout mCommonTitleView;

	private View mChooseServiceView;
	private IGuideCallback mListener;


	public View getChooseServiceView(){
		return mChooseServiceView;
	}

	public ChooseServiceView(Context activity, IGuideCallback listener) {
		mActivity = activity;
		mListener = listener;
		mChooseServiceView = LayoutInflater.from(activity).inflate(R.layout.choose_service_view, null);

		findViewsById();
		setViews();
		setListeners();
	}

	private SlipButtonView mAddUserSlipbtn;
	private SlipButtonView mAutoSlipbtn;
	private SlipButtonView mServiceSlipbtn;
	private TextView mJumpBtn;
	private RelativeLayout mView;
	private void findViewsById() {
		mCommonTitleView = (RelativeLayout)mChooseServiceView.findViewById(R.id.toolbar);
		mTitleTxt = (TextView)mCommonTitleView.findViewById(R.id.toobarTitle);
		mCancelBtn = (RelativeLayout)mCommonTitleView.findViewById(R.id.cancel);

		mView = (RelativeLayout)mChooseServiceView.findViewById(R.id.view);
		mAddUserSlipbtn = (SlipButtonView)mChooseServiceView.findViewById(R.id.add_user_slipbtn);
		mAutoSlipbtn = (SlipButtonView)mChooseServiceView.findViewById(R.id.auto_slipbtn);
		mServiceSlipbtn = (SlipButtonView)mChooseServiceView.findViewById(R.id.service_slipbtn);
		mJumpBtn = (TextView)mChooseServiceView.findViewById(R.id.jump_text);
	}

	private void setListeners() {
		mCancelBtn.setOnClickListener(this);
		mJumpBtn.setOnClickListener(this);
	}

	private void setViews() {
		mJumpBtn.setText(mActivity.getString(R.string.nextStep));
		mView.setVisibility(View.GONE);
		mTitleTxt.setText(mActivity.getString(R.string.choose_service));
		mAddUserSlipbtn.setCheck(true);
		mAutoSlipbtn.setCheck(true);
		mServiceSlipbtn.setCheck(true);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.cancel) {
			SystemEvent.fireEvent(SystemEventConst.EVENT_HIDE_CHOOSE_SERVICE_VIEW);
		} else if (id == R.id.jump_text) {
			mView.setVisibility(View.VISIBLE);
			mView.removeAllViews();
			mView.bringToFront();
			CongratulationsView congratulationsView = new CongratulationsView(mActivity, mListener);
			mView.addView(congratulationsView.getCongratulationsView());
		}
	}

}
