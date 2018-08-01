package com.wifi.vivoguide.y79;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wifi.vivoguide.R;
import com.wifi.vivoguide.y79a.ChangeViewInterface;

/**
 * @Description: 恭喜您页面
 * @author llf
 * @date 2017-7 17
 * 
 */
public class CongratulationsView_y79 extends RelativeLayout implements OnClickListener{

	private Context mActivity;
	private View mCongratulationsView;
	private TextView mBeginToUse;


	public View getCongratulationsView(){
		return mCongratulationsView;
	}

	public CongratulationsView_y79(Context activity, ChangeViewInterface changeViewInterface1) {
		super(activity);
		this.mActivity = activity;
		this.changeViewInterface = changeViewInterface1;
		mCongratulationsView = LayoutInflater.from(activity).inflate(R.layout.congratulations_view_y79, this);

		findViewsById();
		setViews();
		setListeners();
	}
	private ChangeViewInterface changeViewInterface;

	private void findViewsById() {
		mBeginToUse = (TextView)mCongratulationsView.findViewById(R.id.jump_text);
		mBeginToUse.setText(R.string.vivo_finish_start_use);
	}

	private void setListeners() {
		mBeginToUse.setOnClickListener(this);
	}

	private void setViews() {
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.jump_text) {
			if(changeViewInterface != null){
				changeViewInterface.setCurrentlyShowView(ChangeViewInterface.GUIDE_OVER);
			}
		}

	}

	public void onBackPressed() {
		if(changeViewInterface != null){
			changeViewInterface.setCurrentlyShowView(ChangeViewInterface.IMPORT_DATA);
		}
	}
}
