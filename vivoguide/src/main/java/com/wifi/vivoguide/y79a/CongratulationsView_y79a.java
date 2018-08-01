package com.wifi.vivoguide.y79a;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bootreg.IGuideCallback;
import com.wifi.vivoguide.R;

import static android.content.Context.CONTEXT_IGNORE_SECURITY;
import static android.content.Context.CONTEXT_INCLUDE_CODE;

/**
 * @Description: 恭喜您页面
 * @author llf
 * @date 2017-7 17
 * 
 */
public class CongratulationsView_y79a extends RelativeLayout implements OnClickListener{

	private Context mActivity;
	private View mCongratulationsView;
	private TextView mBeginToUse;
	private TextView mStartInerchangeTxt;
	private IGuideCallback mListener;


	public View getCongratulationsView(){
		return mCongratulationsView;
	}

	public CongratulationsView_y79a(Context activity,ChangeViewInterface changeViewInterface1) {
		super(activity);
		this.mActivity = activity;
		this.changeViewInterface = changeViewInterface1;
		mCongratulationsView = LayoutInflater.from(activity).inflate(R.layout.congratulations_view_y79a, this);

		findViewsById();
		setViews();
		setListeners();
	}
	private ChangeViewInterface changeViewInterface;

	private void findViewsById() {
		mStartInerchangeTxt = (TextView)mCongratulationsView.findViewById(R.id.start_interchange);
		mBeginToUse = (TextView)mCongratulationsView.findViewById(R.id.jump_text);
		mBeginToUse.setText(R.string.begin_use_vivo);
	}

	private void setListeners() {
		mStartInerchangeTxt.setOnClickListener(this);
		mBeginToUse.setOnClickListener(this);
	}

	private void setViews() {
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.start_interchange) {
			Context shareContext = null;
			try {
				shareContext = mActivity.createPackageContext("com.vivo.easyshare", CONTEXT_INCLUDE_CODE | CONTEXT_IGNORE_SECURITY);
				Intent shareIntent = new Intent();
				shareIntent.setComponent(new ComponentName("com.vivo.easyshare", "com.vivo.easyshare.activity.SplashScreenActivity"));
				shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				shareContext.startActivity(shareIntent);

			} catch (PackageManager.NameNotFoundException e) {
				e.printStackTrace();
			}

		} else if (id == R.id.jump_text) {
			if(changeViewInterface != null){
				changeViewInterface.setCurrentlyShowView(ChangeViewInterface.GUIDE_OVER);
			}
		}

	}

	public void onBackPressed() {
		if(changeViewInterface != null){
			changeViewInterface.setCurrentlyShowView(ChangeViewInterface.SELECT_SERVICE);
		}
	}
}
