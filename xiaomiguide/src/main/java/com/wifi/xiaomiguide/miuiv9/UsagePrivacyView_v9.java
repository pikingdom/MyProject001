package com.wifi.xiaomiguide.miuiv9;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.wifi.xiaomiguide.Business.AnimationTranslateBusiness;
import com.wifi.xiaomiguide.ChangeViewInterface;
import com.wifi.xiaomiguide.R;
import com.wifi.xiaomiguide.View.PrivacyPolityView;
import com.wifi.xiaomiguide.View.UserPrivacyView;

/**使用条款
 * Created by xuqunxing on 2017/8/9.
 */
public class UsagePrivacyView_v9 extends RelativeLayout implements View.OnClickListener {

    private TextView backTv;
    private TextView nextTv;
    private LinearLayout policyLL;
    private LinearLayout userPrivacyLL;
    private RelativeLayout usagePrivacyRl;
    private PrivacyPolityView privacyPolityView;
    private UserPrivacyView userPrivacyView;
    private ChangeViewInterface mChangeViewInterface;

    private boolean mIsPrivacyPolityViewHide = false;
    private boolean mIsUserPrivacyViewHide = false;

    public UsagePrivacyView_v9(Context context, ChangeViewInterface changeViewInterface1) {
        super(context);
        mChangeViewInterface = changeViewInterface1;
        initView();
    }

    public UsagePrivacyView_v9(Context context, AttributeSet attrs) {
        super(context, attrs,0);
    }

    public UsagePrivacyView_v9(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public void hideChilderView() {
        mIsPrivacyPolityViewHide = false;
        mIsUserPrivacyViewHide = false;
        if (privacyPolityView != null) {
            if (privacyPolityView.getVisibility() == View.VISIBLE) {
                privacyPolityView.setVisibility(GONE);
                mIsPrivacyPolityViewHide = true;
            }
        }
        if (userPrivacyView != null) {
            if (userPrivacyView.getVisibility() == View.VISIBLE) {
                userPrivacyView.setVisibility(GONE);
                mIsUserPrivacyViewHide = true;
            }
        }

        mChangeViewInterface.saveReturnDirection(AnimationTranslateBusiness.FROM_LEFT_TO_RIGHT);
        if (mIsPrivacyPolityViewHide || mIsUserPrivacyViewHide) {
            mChangeViewInterface.setCurrentlyShowView(ChangeViewInterface.USAGE_PRIVACY_VIEW, null);
            return;

        }

        mChangeViewInterface.setCurrentlyShowView(ChangeViewInterface.WIFI_VIEW, null);

    }

    private void initView() {
        View.inflate(getContext(), R.layout.view_usage_privacy_v9,this);
        ToggleButton toggleButton = (ToggleButton) findViewById(R.id.toggleButton);
        usagePrivacyRl = (RelativeLayout) findViewById(R.id.usage_privacy_rl);
        policyLL = (LinearLayout) findViewById(R.id.ll_privacy_policy);
        userPrivacyLL = (LinearLayout) findViewById(R.id.ll_user_privacy);
        backTv = (TextView) findViewById(R.id.bottom_back);
        nextTv = (TextView) findViewById(R.id.bottom_next);

        nextTv.setText(getContext().getString(R.string.go_on));

        privacyPolityView = new PrivacyPolityView(getContext());
        privacyPolityView.setOnViewCLickListener(new UserPrivacyView.onViewCLickListener() {
            @Override
            public void onClickBackListener() {
               // privacyPolityView.setVisibility(GONE);
                hideChilderView();
            }
        });
        privacyPolityView.setVisibility(GONE);
        usagePrivacyRl.addView(privacyPolityView);

        userPrivacyView = new UserPrivacyView(getContext());
        userPrivacyView.setOnViewCLickListener(new UserPrivacyView.onViewCLickListener() {
            @Override
            public void onClickBackListener() {
               // userPrivacyView.setVisibility(GONE);
                hideChilderView();
            }
        });
        userPrivacyView.setVisibility(GONE);
        usagePrivacyRl.addView(userPrivacyView);

        policyLL.setOnClickListener(this);
        userPrivacyLL.setOnClickListener(this);
        nextTv.setOnClickListener(this);
        backTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == backTv){
            mChangeViewInterface.saveReturnDirection(AnimationTranslateBusiness.FROM_LEFT_TO_RIGHT);
            mChangeViewInterface.setCurrentlyShowView(ChangeViewInterface.WIFI_VIEW, null);

        }else if(view == nextTv){
            mChangeViewInterface.saveReturnDirection(AnimationTranslateBusiness.FROM_RIGHT_TO_LEFT);
            mChangeViewInterface.setCurrentlyShowView(ChangeViewInterface.SIM_CAR_VIEW, null);
//            Intent accountIntent = new Intent("com.xiaomi.account.action.XIAOMI_ACCOUNT_WELCOME");
//            accountIntent.setPackage("com.xiaomi.account");
//            accountIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            Bundle bundle = new Bundle();
//            bundle.putBoolean("extra_show_find_device", true);
//            bundle.putBoolean("extra_show_skip_login", true);
//            bundle.putBoolean("show_sync_settings", false);
//            bundle.putBoolean("extra_disable_back_key", false);
//            bundle.putBoolean("extra_find_pwd_on_pc", true);
//            bundle.putBoolean("extra_add_account_from_provision", true);
//            bundle.putString("androidPackageName", getContext().getPackageName());
//            accountIntent.putExtras(bundle);
//            getContext().startActivity(accountIntent);


        }else if(view == policyLL){
            privacyPolityView.setVisibility(VISIBLE);
        }else if(view == userPrivacyLL){
            userPrivacyView.setVisibility(VISIBLE);
        }
    }
}
