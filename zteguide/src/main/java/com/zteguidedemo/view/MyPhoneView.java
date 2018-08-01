package com.zteguidedemo.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
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
import com.zteguidedemo.widget.MyPhoneDialog;

import java.util.Random;

/**
 * Created by xuqunxing on 2017/7/17.
 */
public class MyPhoneView extends BaseRelativilayout implements View.OnClickListener {

    private TextView previousTv;
    private TextView nextTv;
    private TextView userAgreement;
    private RelativeLayout myPhoneRl;
    private AnnouncementView announcementView;
    private static final String DB_DEVICE_NAME = "system_device_name";
    public static final int UPGRADE_STATUS_NONE = 0;
    public static final int UPGRADE_STATUS_SYS = 1;
    private EditText myPhoneNumber;

    public MyPhoneView(Context context) {
        this(context,null);
    }

    public MyPhoneView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyPhoneView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View.inflate(getContext(), R.layout.view_my_phone,this);
        myPhoneNumber = (EditText) findViewById(R.id.my_phone_number);
        myPhoneRl = (RelativeLayout) findViewById(R.id.my_phone_rl);
        previousTv = (TextView) findViewById(R.id.view_previous);
        nextTv = (TextView) findViewById(R.id.view_next);
        userAgreement = (TextView) findViewById(R.id.my_phone_zte_user_agreement);

        userAgreement.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
        userAgreement.setTextColor(getContext().getResources().getColor(R.color.my_phone_blue));
        userAgreement.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

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

        previousTv.setOnClickListener(this);
        nextTv.setOnClickListener(this);
        userAgreement.setOnClickListener(this);
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
            if(onZteClickListener != null){
                onZteClickListener.OnPrevious(this);
            }
        }else if(view == nextTv){
            MyPhoneDialog myPhoneDialog = new MyPhoneDialog(getContext());
            myPhoneDialog.show();
            myPhoneDialog.setOnOKClickListener(new MyPhoneDialog.OnOKClickListener() {
                @Override
                public void onOKClick(View v) {
                    if (onZteClickListener != null) {
                        onZteClickListener.OnNext(MyPhoneView.this);
                    }
                }
            });
        }else if(view  ==  userAgreement){
            try {
//                Context thirdContext = getContext().createPackageContext("com.zte.setupwizard", Context.CONTEXT_INCLUDE_CODE | Context.CONTEXT_IGNORE_SECURITY);
//                Intent intent = new Intent();
//                //intent.addCategory(Intent.CATEGORY_LAUNCHER);
//                intent.setAction("com.zte.setupwizard.SetupWizardForRetrivePre");
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.setComponent(new ComponentName("com.zte.setupwizard","com.zte.setupwizard.SetupWizardActivity"));
//                thirdContext.startActivity(intent);
                announcementView = new AnnouncementView(getContext());
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
                announcementView.setVisibility(VISIBLE);
                myPhoneRl.addView(announcementView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
