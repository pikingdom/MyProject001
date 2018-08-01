package com.coloros.bootreg.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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

/**
 * Created by Administrator on 2017/7/15.
 */

public class AccountPage extends GuidePage implements View.OnClickListener {
    public AccountPage(Context context) {
        super(context);
        init(context);
    }

    private TextView mDealChooseBtn;
    private TextView mSetUpLater;

    public AccountPage(Context context, AttributeSet attr) {
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
    View backTxt;


    private OnClickListener mSetUpLaterListener = new OnClickListener() {
        public void onClick(View v) {
            AccountPage.this.showNextStepDialog();
        }
    };

    private OnClickListener mChooseListener = new OnClickListener() {
        public void onClick(View v) {
            if (!AccountPage.this.jumpWifiSettingIfNeed()) {
                if (AccountPage.this.mPosition == 0) {
                    AccountPage.this.welcomePage.callLoginActivity();
                } else if (AccountPage.this.mPosition == AccountPage.TYPE_RESTORE) {
                    AccountPage.this.chooseRegisterWay();
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
        View v = mInflater.inflate(R.layout.activity_account_page, null);
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
                AccountPage.this.mPosition = (int) arg3;
                AccountPage.this.mChooseListener.onClick(AccountPage.this.mList);
                AccountPage.this.mLoginAdapter.notifyDataSetChanged();
            }
        });
        this.mDealChooseBtn = (TextView) findViewById(R.id.go_on);
        this.mSetUpLater = (TextView) findViewById(R.id.set_up_later);
        this.mDealChooseBtn.setOnClickListener(this.mChooseListener);
        this.mSetUpLater.setOnClickListener(this.mSetUpLaterListener);
        // initMobileState();

        back = findViewById(R.id.action_bar_button);
        backTxt = findViewById(R.id.backTxt);
        back.setOnClickListener(this);
        backTxt.setOnClickListener(this);

//        if (JumpToHelper.containFingerprint(this)) {
//            backButton.setVisibility(8);
//        }
    }


    @Override
    public void onClick(View view) {
        if (view == back || view == backTxt) {
            onOPListener.OnBack(this);
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
            convertView = AccountPage.this.mInflater.inflate(R.layout.account_temp_item, parent, false);
            TextView tempValue = (TextView) convertView.findViewById(R.id.tempValue);
            TextView summery = (TextView) convertView.findViewById(R.id.summery);
            RadioButton radiobutton = (RadioButton) convertView.findViewById(R.id.radiobutton);
            if (AccountPage.this.mPosition == position) {
                radiobutton.setChecked(true);
            } else {
                radiobutton.setChecked(false);
            }
            if (position == 0) {
                tempValue.setText(R.string.activity_boot_login_title);
                summery.setText(R.string.account_discription);
            } else if (position == AccountPage.TYPE_RESTORE) {
                tempValue.setText(R.string.activity_boot_register_title);
                if (AccountPage.this.mIsOnekeyReg) {
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
        dialog = createColorOSV2StyleDialog(welcomePage.getActivity(), TYPE_RESTORE, R.string.dialog_tips_title, R.string.activity_boot_cancle_dialog_message, R.string.oppo_account_skip, R.string.accout_dialog_tips, new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    if(dialog!=null)dialog.dismiss();
                    //JumpToHelper.jumpToGuideNextApp(AccountPage.this, Constants.ACTION_FRAGMENT_PAGE_GUIDE, false);
                    if (onOPListener != null) {
                        onOPListener.OnNext(AccountPage.this);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new View.OnClickListener() {
            public void onClick(View view) {
                if(dialog!=null)dialog.dismiss();
                if (!AccountPage.this.jumpWifiSettingIfNeed()) {
                    AccountPage.this.welcomePage.callLoginActivity();
                }
                
            }
        });
        //if (!isFinishing()) {
        dialog.show();
        // dialog.setButtonIsBold(-1, -2, LOGIN);
        // }
    }

    public static Dialog createColorOSV2StyleDialog(Context context, int id, int titleId, int content, int sureText, int cancleText, View.OnClickListener sureLsn, View.OnClickListener cancleLsn) {
        Dialog builder = new Dialog(context, R.style.OppoCustomDialog);
        View view = View.inflate(context, R.layout.oppo_alert_dialog, null);
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
