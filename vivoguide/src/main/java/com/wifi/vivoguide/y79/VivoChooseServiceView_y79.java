package com.wifi.vivoguide.y79;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bootreg.IGuideCallback;
import com.wifi.vivoguide.R;
import com.wifi.vivoguide.commonview.SlipButtonView;
import com.wifi.vivoguide.event.SystemEvent;
import com.wifi.vivoguide.event.SystemEventConst;
import com.wifi.vivoguide.y79a.AutoUpdateDialog;
import com.wifi.vivoguide.y79a.ChangeViewInterface;

/**
 * @Description: 选择服务页面
 * @author llf
 * @date 2017-7 17
 * 
 */
public class VivoChooseServiceView_y79 extends RelativeLayout implements OnClickListener{
	private Context mActivity;
	private TextView mTitleTxt;
	private RelativeLayout mCancelBtn;
	private RelativeLayout mCommonTitleView;
	private ChangeViewInterface changeViewInterface;
	private IGuideCallback mListener;

	public VivoChooseServiceView_y79(Context activity, ChangeViewInterface changeViewInterface1) {
		super(activity);
		this.mActivity = activity;
		this.changeViewInterface = changeViewInterface1;
		LayoutInflater.from(activity).inflate(R.layout.choose_service_view_y79, this);

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
		mCommonTitleView = (RelativeLayout)findViewById(R.id.toolbar);
		mTitleTxt = (TextView)mCommonTitleView.findViewById(R.id.toobarTitle);
		mCancelBtn = (RelativeLayout)mCommonTitleView.findViewById(R.id.cancel);

		mView = (RelativeLayout)findViewById(R.id.view);
		mAddUserSlipbtn = (SlipButtonView)findViewById(R.id.add_user_slipbtn);
		mAutoSlipbtn = (SlipButtonView)findViewById(R.id.auto_slipbtn);
		mServiceSlipbtn = (SlipButtonView)findViewById(R.id.service_slipbtn);
		mJumpBtn = (TextView)findViewById(R.id.jump_text);
		mAddUserSlipbtn.setBackgrounds(R.drawable.switch_on,R.drawable.switch_off);
		mAutoSlipbtn.setBackgrounds(R.drawable.switch_on,R.drawable.switch_off);
		mServiceSlipbtn.setBackgrounds(R.drawable.switch_on,R.drawable.switch_off);
		mAutoSlipbtn.SetOnChangedListener(new SlipButtonView.OnChangedListener() {
			@Override
			public void OnChanged(boolean CheckState) {
				if(!CheckState){
					AutoUpdateDialog autoUpdateDialog = new AutoUpdateDialog(getContext());
					autoUpdateDialog.show();
					autoUpdateDialog.setCanceledOnTouchOutside(false);
					autoUpdateDialog.setOnGnClickListener(new AutoUpdateDialog.OnGnClickListener() {
						@Override
						public void onSubmitClick(View v) {

						}

						@Override
						public void onCancleClick(View v) {
							mAutoSlipbtn.setCheck(true);
							mAutoSlipbtn.refreshView();
						}
					});
				}
			}
		});
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
			if(changeViewInterface != null){
				changeViewInterface.setCurrentlyShowView(ChangeViewInterface.WIFI_VIEW);
			}
		} else if (id == R.id.jump_text) {
			if(changeViewInterface != null){
				changeViewInterface.setCurrentlyShowView(ChangeViewInterface.IMPORT_DATA);
			}
		}
	}

	public void onBackPressed() {
		if(changeViewInterface != null){
			changeViewInterface.setCurrentlyShowView(ChangeViewInterface.WIFI_VIEW);
		}
	}
}
