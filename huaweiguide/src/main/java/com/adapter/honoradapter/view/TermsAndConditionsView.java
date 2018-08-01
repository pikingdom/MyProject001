package com.adapter.honoradapter.view;

import android.content.Context;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.adapter.honoradapter.util.OOBEUtils;
import com.huawei.hwstartupguide.ChangeViewInterface;
import com.huawei.hwstartupguide.R;
import com.huawei.hwstartupguide.ui.wifi.NavigationVisibilityChange;

import java.lang.reflect.Method;
import java.util.Locale;

/**
 * Created by xuqunxing on 2017/7/20.
 */
public class TermsAndConditionsView extends RelativeLayout implements View.OnClickListener,NavigationVisibilityChange {

    private ChangeViewInterface changeViewInterface;
    private TextView mPrevBtn;
    private TextView mNextBtn;
    private CheckBox mFeedbackCheckBox;
    private PrivacyDialogView privacyDialogView;
    private RelativeLayout buttonBar;
    private View huaweiMobileServiceRl;

    public TermsAndConditionsView(Context context) {
        super(context);
    }

    public TermsAndConditionsView(Context context, ChangeViewInterface changeViewInterface1) {
        super(context);
        this.changeViewInterface = changeViewInterface1;
        initView();
    }

    private void initView() {
        View.inflate(getContext(), R.layout.page_terms_and_conditions,this);
        privacyDialogView = (PrivacyDialogView) findViewById(R.id.privacy_dialog_view);
        huaweiMobileServiceRl = (View) findViewById(R.id.huawei_mobile_service_include);
        getSystemPropertiesOS();
        initCopyrightView();
        initOperatorsStatementView();
        initGMSGoreView();
        initPermissonsManagerView();
        initSmartcareView();
        initUserFeedbackView();
        initButtonBar();
    }

    protected void initButtonBar() {
        buttonBar = (RelativeLayout) findViewById(R.id.button_bar);
        mPrevBtn = (TextView) findViewById(R.id.prev_btn);
        mPrevBtn.setVisibility(View.VISIBLE);
        mPrevBtn.setText(getContext().getString(R.string.disagree_format));
        mNextBtn = (TextView) findViewById(R.id.next_btn);
        mNextBtn.setVisibility(View.VISIBLE);
        mNextBtn.setText(getContext().getString(R.string.agree_format));
        mPrevBtn.setOnClickListener(this);
        mNextBtn.setOnClickListener(this);

        mPrevBtn.setCompoundDrawables(null,null,null,null);
        mNextBtn.setCompoundDrawables(null,null,null,null);
    }

    private void initUserFeedbackView() {
        View userFeedbackView = this.findViewById(R.id.user_feedback_view);
        boolean hasHwUE = OOBEUtils.hasPackageInfo(getContext().getPackageManager(), "com.huawei.bd");
        boolean hasHwLogUpload = OOBEUtils.hasPackageInfo(getContext().getPackageManager(), "com.huawei.logupload");
        if (hasHwUE || hasHwLogUpload) {
            mFeedbackCheckBox = (CheckBox) userFeedbackView.findViewById(R.id.user_feedback_btn);
            this.mFeedbackCheckBox.setChecked(true);
            this.mFeedbackCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    StartupGuidePrefs.getInstance().setUserFeedbackBoxState(isChecked ? 1 : 0);
//                    ReporterManager.reportUserFeedbackClicked(TermsAndConditionsFragment.this.getContext(), isChecked);
                }
            });
            TextView userFeedbackContent = (TextView) userFeedbackView.findViewById(R.id.user_feedback_content);
//            String url = FileUtil.getAssetPath(getContext(), getHtmlFileName(hasHwUE, hasHwLogUpload));
//            if (TextUtils.isEmpty(url)) {
//                Log.w("HwStartupGuide", "TermsAndConditions : user_improvement_plan.html is empty.");
//                return;
//            }
//            userFeedbackContent.setText(Html.fromHtml(FileUtil.getStringFromHtmlFile(getContext(), url)));
        } else {
            Log.w("HwStartupGuide", "TermsAndConditions : device does not support feedback.");
         //   userFeedbackView.setVisibility(GONE);
        }
    }

    private void initSmartcareView() {

    }

    private void initPermissonsManagerView() {
        View deviceManagerView = this.findViewById(R.id.device_manager_view);
        if (!OOBEUtils.hasPackageInfo(getContext().getPackageManager(), "com.huawei.systemmanager") || !OOBEUtils.isInternal()) {
         //   deviceManagerView.setVisibility(GONE);
        }
    }

    private void initGMSGoreView() {
        View gMSGoreView = findViewById(R.id.gms_gore_view);
        boolean hasGms = OOBEUtils.hasPackageInfo(getContext().getPackageManager(), "com.google.android.gms");
        boolean hasGsf = OOBEUtils.hasPackageInfo(getContext().getPackageManager(), "com.google.android.gsf");
        boolean hasGsf_login = OOBEUtils.hasPackageInfo(getContext().getPackageManager(), "com.google.android.gsf.login");
        Log.i("HwStartupGuide", "hasGms : " + hasGms + " ;hasGsf : " + hasGsf + "; hasGsf_login : " + hasGsf_login);
        if (OOBEUtils.isInternal() && hasGms && hasGsf && hasGsf_login) {
            Log.i("HwStartupGuide", "gMSGoreView is VISIBLE");
            gMSGoreView.setVisibility(View.VISIBLE);
            return;
        }
        Log.i("HwStartupGuide", "gMSGoreView is GONE");
      //  gMSGoreView.setVisibility(View.GONE);

    }

    private void initOperatorsStatementView() {
        View operatorsStatementView = findViewById(R.id.operators_statement_view);
        boolean autoregsms = OOBEUtils.hasPackageInfo(getContext().getPackageManager(), "com.huawei.android.AutoRegSms");
        boolean ueinfocheck = OOBEUtils.hasPackageInfo(getContext().getPackageManager(), "com.huawei.android.UEInfoCheck");
        boolean regservice = OOBEUtils.hasPackageInfo(getContext().getPackageManager(), "com.huawei.regservice");
        Log.i("HwStartupGuide", "autoregsms : " + autoregsms + " ;ueinfocheck : " + ueinfocheck + "; regservice : " + regservice);
        if ((autoregsms || ueinfocheck || regservice) && OOBEUtils.isInternal()) {
            Log.i("HwStartupGuide", "operatorsStatementView is VISIBLE");
            operatorsStatementView.setVisibility(View.VISIBLE);
            return;
        }
        Log.i("HwStartupGuide", "operatorsStatementView is GONE");
  //      operatorsStatementView.setVisibility(View.GONE);

    }

    private void initCopyrightView() {
        View copyrightView = findViewById(R.id.copyright_view);
        TextView copyrightContent = (TextView) copyrightView.findViewById(R.id.copyright_content);
        if (!(OOBEUtils.isInternal() || copyrightContent == null)) {
//            copyrightContent.setLineSpacing(copyrightContent.getLineSpacingExtra(), 1.5f);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) copyrightView.getLayoutParams();
            layoutParams.topMargin = getResources().getDimensionPixelSize(R.dimen.layout_margin_12);
            copyrightView.setLayoutParams(layoutParams);
        }
        String linkStr = "";
//        if (TextUtils.equals(Locale.getDefault().toLanguageTag(), "sk-SK")) {
//            linkStr = getContext().getString(R.string.eula_sk_1);
//        } else {
            linkStr = getContext().getString(R.string.view_more);
//        }
        setClickableSpanForTextView(copyrightContent, linkStr);

    }
    private void setClickableSpanForTextView(TextView tv, String linkStr) {
        if (tv != null && !TextUtils.isEmpty(linkStr)) {
            String content = tv.getText().toString();
            Locale defaultLocale = Locale.getDefault();
            if (!content.toLowerCase(defaultLocale).contains(linkStr.toLowerCase(defaultLocale))) {
                content = content + " " + linkStr;
            }
            int start = content.toLowerCase(defaultLocale).lastIndexOf(linkStr.toLowerCase(defaultLocale));
            int end = start + linkStr.length();
            if (start >= 0 && start < end && end <= content.length()) {
                SpannableString sp = new SpannableString(content);
                sp.setSpan(new MyClickableSpan(), start, end, 33);
                tv.setText(sp);
                tv.setMovementMethod(LinkMovementMethod.getInstance());
                tv.setFocusable(false);
                tv.setClickable(false);
                tv.setLongClickable(false);
            }
        }
    }

    @Override
    public void onClick(View view) {
        if(view == mPrevBtn){
            if(changeViewInterface != null){
                changeViewInterface.setCurrentlyShowView(ChangeViewInterface.SIM_VIEW);
            }
        }else if(view == mNextBtn){
            privacyDialogView.setVisibility(View.VISIBLE);
            privacyDialogView.setOnOKClickListener(new PrivacyDialogView.OnOKClickListener() {
                @Override
                public void onOKClick(View v) {
                    if(changeViewInterface != null){
                        changeViewInterface.setCurrentlyShowView(ChangeViewInterface.ACCESS_SWITCH_VIEW);
                    }
                }
            });
//            PrivacyDialog myPhoneDialog = new PrivacyDialog(getContext());
//            myPhoneDialog.show();
//            myPhoneDialog.setOnOKClickListener(new PrivacyDialog.OnOKClickListener() {
//                @Override
//                public void onOKClick(View v) {
//                    if(changeViewInterface != null){
//                        changeViewInterface.setCurrentlyShowView(ChangeViewInterface.ACCESS_SWITCH_VIEW);
//                    }
//                }
//            });
        }
    }

    public void onBackPressed() {
        if(changeViewInterface != null){
            changeViewInterface.setCurrentlyShowView(ChangeViewInterface.SIM_VIEW);
        }
    }

    @Override
    public void NavigationVisibilityChangeState(boolean isShow) {
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) buttonBar.getLayoutParams();
        FrameLayout.LayoutParams lp1 = (FrameLayout.LayoutParams)privacyDialogView.getLayoutParams();
        float currentDensity = getContext().getResources().getDisplayMetrics().density;
        int margin = (int)(30 * currentDensity + 0.5F);
        if (isShow){
            lp.bottomMargin = margin;
            lp1.bottomMargin = margin;
        }else{
            lp.bottomMargin = 0;
            lp1.bottomMargin = 0;
        }
        buttonBar.setLayoutParams(lp);
        privacyDialogView.setLayoutParams(lp1);
        requestLayout();
    }

    private class MyClickableSpan extends ClickableSpan {
        private MyClickableSpan() {
        }

        public void updateDrawState(TextPaint ds) {
            ds.setUnderlineText(false);
            ds.setColor(getContext().getResources().getColor(R.color.light_blue));
        }

        public void onClick(View v) {
            if (v instanceof TextView) {

            }
        }
    }


    public String getSystemPropertiesOS() {
        Class<?> classType = null;
        String buildVersion = null;
        try {
            classType = Class.forName("android.os.SystemProperties");
            Method getMethod = classType.getDeclaredMethod("get", new Class<?>[]{String.class});
            buildVersion = (String) getMethod.invoke(classType, new Object[]{"ro.build.version.emui"});

            if(!TextUtils.isEmpty(buildVersion) && "EmotionUI_5.1".equals(buildVersion)){
                huaweiMobileServiceRl.setVisibility(VISIBLE);
            }else {
                huaweiMobileServiceRl.setVisibility(GONE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return buildVersion;
    }

}
