package com.zteguidedemo.v0840;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zteguidedemo.R;
import com.zteguidedemo.listener.OnZteClickListener;
import com.zteguidedemo.view.BaseRelativilayout;
import com.zteguidedemo.widget.ZTE0840Dialog;

import java.util.Random;

/**
 * Created by xuqunxing on 2017/7/17.
 */
public class ZTE0840MyPhoneView extends BaseRelativilayout implements View.OnClickListener {

    private TextView previousTv;
    private TextView nextTv;
    private TextView userAgreement;
    private TextView voicePrivacy;
    private RelativeLayout myPhoneRl;
    private Zte0840AnnouncementView announcementView;
    private Zte0840VoicePrivacyView voicePrivacyView;
    private static final String DB_DEVICE_NAME = "system_device_name";
    public static final int UPGRADE_STATUS_NONE = 0;
    public static final int UPGRADE_STATUS_SYS = 1;
    private EditText myPhoneNumber;
    private ChangeViewInterface changeViewInterface;

    public ZTE0840MyPhoneView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ZTE0840MyPhoneView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public ZTE0840MyPhoneView(Context context, ChangeViewInterface changeViewInterface1) {
        super(context);
        this.changeViewInterface= changeViewInterface1;
        initView();
    }

    private void initView() {
        View.inflate(getContext(), R.layout.view_zte0840_my_phone,this);
        myPhoneNumber = (EditText) findViewById(R.id.my_phone_number);
        myPhoneRl = (RelativeLayout) findViewById(R.id.my_phone_rl);
        previousTv = (TextView) findViewById(R.id.zte_previous_step_btn);
        nextTv = (TextView) findViewById(R.id.zte_next_step_btn);
        userAgreement = (TextView) findViewById(R.id.my_phone_zte_user_agreement);
        voicePrivacy = (TextView) findViewById(R.id.my_phone_zte_voice_privacy);

        userAgreement.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
        userAgreement.setTextColor(getContext().getResources().getColor(R.color.zte0840_blue));
        userAgreement.getPaint().setFakeBoldText(true);

        voicePrivacy.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
        voicePrivacy.setTextColor(getContext().getResources().getColor(R.color.zte0840_blue));
        voicePrivacy.getPaint().setFakeBoldText(true);

        String strDeviceName = Settings.System.getString(getContext().getContentResolver(), DB_DEVICE_NAME);
        if (TextUtils.isEmpty(strDeviceName)) {
            strDeviceName = generateDeviceName();
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if(!Settings.System.canWrite(getContext())){
                    Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS,Uri.parse("package:" + getContext().getPackageName()));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getContext().startActivity(intent);
                }else {
                    Settings.System.putString(getContext().getContentResolver(), DB_DEVICE_NAME, strDeviceName);
                }
            }
        }
        if (this.myPhoneNumber != null) {
            this.myPhoneNumber.setText(strDeviceName);
            this.myPhoneNumber.setSelection(strDeviceName.length());
        }

        announcementView = new Zte0840AnnouncementView(getContext());
        announcementView.setVisibility(GONE);
        myPhoneRl.addView(announcementView);
        voicePrivacyView = new Zte0840VoicePrivacyView(getContext());
        voicePrivacyView.setVisibility(GONE);
        myPhoneRl.addView(voicePrivacyView);

        announcementView.setOnZteClickListener(new OnZteClickListener() {
            @Override
            public void OnNext(View view) {

            }

            @Override
            public void OnSkip(View view) {

            }

            @Override
            public void OnPrevious(View view) {

            }

            @Override
            public void OnDone(View view) {
                announcementView.setVisibility(GONE);
            }
        });

        voicePrivacyView.setOnZteClickListener(new OnZteClickListener() {
            @Override
            public void OnNext(View view) {

            }

            @Override
            public void OnSkip(View view) {

            }

            @Override
            public void OnPrevious(View view) {

            }

            @Override
            public void OnDone(View view) {
                voicePrivacyView.setVisibility(GONE);
            }
        });


        previousTv.setOnClickListener(this);
        nextTv.setOnClickListener(this);
        userAgreement.setOnClickListener(this);
        voicePrivacy.setOnClickListener(this);
    }

    public static String generateDeviceName() {
        return Build.MODEL + "_" + getRandomNumber();
    }

    public static String getRandomNumber() {
        char[] randomArray = new char[4];
        Random rd = new Random();
        for (int i = UPGRADE_STATUS_NONE; i < 4; i += UPGRADE_STATUS_SYS) {
            randomArray[i] = (char) (rd.nextInt(10) + 48);
        }
        return String.valueOf(randomArray);
    }


    @Override
    public void onClick(View view) {
        if(view == previousTv){
            if(changeViewInterface != null){
                changeViewInterface.setCurrentlyShowView(ChangeViewInterface.SIM_VIEW);
            }
        }else if(view == nextTv){
            ZTE0840Dialog myPhoneDialog = new ZTE0840Dialog(getContext());
            myPhoneDialog.show();
            myPhoneDialog.setOnOKClickListener(new ZTE0840Dialog.OnOKClickListener() {
                @Override
                public void onOKClick(View v) {
                    if(changeViewInterface != null){
                        changeViewInterface.setCurrentlyShowView(ChangeViewInterface.ACCOUNT_VIEW);
                    }
                }
            });
        }else if(view  ==  userAgreement){
            try {
                announcementView.setVisibility(VISIBLE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if(view == voicePrivacy){
            try {
                voicePrivacyView.setVisibility(VISIBLE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void onBackPressed() {
        if(voicePrivacyView.getVisibility() == VISIBLE){
            voicePrivacyView.setVisibility(GONE);
        }
        if(announcementView.getVisibility() == VISIBLE){
            announcementView.setVisibility(GONE);
        }
    }
}
