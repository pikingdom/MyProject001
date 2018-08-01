package com.coloros.bootreg.view.view2;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.coloros.boot.guide.R;
import com.coloros.bootreg.util.NetInfoHelper;
import com.coloros.bootreg.view.GuidePage;

/**
 * Created by Administrator on 2017/7/15.
 */

public class AccountPage2 extends GuidePage implements View.OnClickListener {
    public AccountPage2(Context context) {
        super(context);
        init(context);
    }

    private TextView mDealChooseBtn;
    private TextView mSetUpLater;

    public AccountPage2(Context context, AttributeSet attr) {
        super(context, attr);
        init(context);
    }

    private LayoutInflater mInflater;
    private ListView mList;
    private LoginAdapter mLoginAdapter;
    private int mPosition;
    public static final int LOGIN = 0;
    public static final int TYPE_RESTORE = 1;
    boolean mIsOnekeyReg = false;
    View back;


    private OnClickListener mSetUpLaterListener = new OnClickListener() {
        public void onClick(View v) {
            AccountPage2.this.showNextStepDialog();
        }
    };

    private OnClickListener mChooseListener = new OnClickListener() {
        public void onClick(View v) {
            if (!AccountPage2.this.jumpWifiSettingIfNeed()) {
                if (AccountPage2.this.mPosition == 0) {
                    AccountPage2.this.welcomePage.callLoginActivity();
                } else if (AccountPage2.this.mPosition == AccountPage2.TYPE_RESTORE) {
                    AccountPage2.this.chooseRegisterWay();
                }
            }
        }
    };

    private boolean jumpWifiSettingIfNeed() {
        if (NetInfoHelper.isConnectNet(getContext())) {
            return false;
        }
        try {
            //JumpToHelper.jumpToGuideWifiSetting(this, REQUEST_CODE);
            return false;
        } catch (Exception e) {
            return false;
        }
    }


    protected void chooseRegisterWay() {

        try {
            if (this.mIsOnekeyReg) {
                welcomePage.callOneKeyProgressActivity();
            } else {
                welcomePage.callRegPage(getContext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void init(Context context) {
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = mInflater.inflate(R.layout.activity_account_page2, null);
        addView(v);
        initView();
    }


    private void initView() {
        this.mPosition = TYPE_RESTORE;
        this.mList = (ListView) findViewById(R.id.list);
        this.mLoginAdapter = new LoginAdapter(getContext());
        this.mList.setAdapter(this.mLoginAdapter);
        this.mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
                AccountPage2.this.mPosition = (int) arg3;
                AccountPage2.this.mLoginAdapter.notifyDataSetChanged();
            }
        });
        this.mDealChooseBtn = (TextView) findViewById(R.id.go_on);
        this.mSetUpLater = (TextView) findViewById(R.id.set_up_later);
        this.mDealChooseBtn.setOnClickListener(this.mChooseListener);
        this.mSetUpLater.setOnClickListener(this.mSetUpLaterListener);
        // initMobileState();

        back = findViewById(R.id.action_bar_button);
        back.setOnClickListener(this);

//        if (JumpToHelper.containFingerprint(this)) {
//            backButton.setVisibility(8);
//        }
    }


    @Override
    public void onClick(View view) {
        if (view == back) {
            onOPListener.OnBack(this);
            return;
        }
    }


    private class LoginAdapter extends BaseAdapter {
        protected LoginAdapter(Context context) {

        }

        public int getCount() {
            return 2;
        }

        public Object getItem(int position) {
            return Integer.valueOf(position);
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = AccountPage2.this.mInflater.inflate(R.layout.account_temp_item2, parent, false);
            TextView tempValue = (TextView) convertView.findViewById(R.id.tempValue);
            TextView summery = (TextView) convertView.findViewById(R.id.summery);
            RadioButton radiobutton = (RadioButton) convertView.findViewById(R.id.radiobutton);
            if (AccountPage2.this.mPosition == position) {
                radiobutton.setChecked(true);
            } else {
                radiobutton.setChecked(false);
            }
            if (position == 0) {
                tempValue.setText(R.string.activity_boot_login_title);
                summery.setText(R.string.account_discription);
            } else if (position == AccountPage2.TYPE_RESTORE) {
                tempValue.setText(R.string.activity_boot_register_title);
                if (AccountPage2.this.mIsOnekeyReg) {
                    summery.setText(R.string.activity_boot_register_summary);
                    summery.setVisibility(VISIBLE);
                } else {
                    summery.setVisibility(GONE);
                }
            }
            return convertView;
        }
    }

    Dialog dialog;
    private void showNextStepDialog() {
        dialog = createColorOSV2StyleDialog2(welcomePage.getActivity(), TYPE_RESTORE, R.string.dialog_tips_title, R.string.activity_boot_cancle_dialog_message, R.string.accout_dialog_tips,R.string.setup_later , new OnClickListener() {
            public void onClick(View view) {
                if(dialog!=null)dialog.dismiss();
                if (!AccountPage2.this.jumpWifiSettingIfNeed()) {
                    AccountPage2.this.welcomePage.callLoginActivity();
                }

            }
        }, new OnClickListener() {

            public void onClick(View view) {
                try {
                    if(dialog!=null)dialog.dismiss();
                    //JumpToHelper.jumpToGuideNextApp(AccountPage.this, Constants.ACTION_FRAGMENT_PAGE_GUIDE, false);
                    if (onOPListener != null) {
                        onOPListener.OnNext(AccountPage2.this);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            
        });
        //if (!isFinishing()) {
        dialog.show();
        // dialog.setButtonIsBold(-1, -2, LOGIN);
        // }
    }

    public static Dialog createColorOSV2StyleDialog2(Context context, int id, int titleId, int content, int sureText, int cancleText, OnClickListener sureLsn, OnClickListener cancleLsn) {
        Dialog builder = new Dialog(context, R.style.OppoCustomDialog);
        View view = View.inflate(context, R.layout.oppo_alert_dialog2, null);
        builder.setContentView(view);
        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(content);

        TextView cancel = (TextView) view.findViewById(R.id.cancel);
        cancel.setText(sureText);

        TextView ok = (TextView) view.findViewById(R.id.ok);
        ok.setText(cancleText);

        cancel.setOnClickListener(sureLsn);
        ok.setOnClickListener(cancleLsn);
        return builder;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1) {
            onOPListener.OnNext(this);
        }
    }
}
