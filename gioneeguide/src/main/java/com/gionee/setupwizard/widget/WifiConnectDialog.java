package com.gionee.setupwizard.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gionee.setupwizard.R;

/**
 * wifi连接对话框
 * 作者：xiaomao on 2017/7/15.
 */

public class WifiConnectDialog extends Dialog {
    private OnCustomDialogListener customDialogListener;
    //定义dialog的回调事件
    public interface OnCustomDialogListener{
        void back(String str);
    }

    public WifiConnectDialog(Context context, int themeResId,OnCustomDialogListener customDialogListener) {
        super(context, themeResId);
        this.customDialogListener = customDialogListener;
    }


    public static class Builder {
        private Context context;
        private String title;

        public Builder(Context context) {
            this.context = context;
        }

        /**
         * Set the Dialog title from String
         *
         * @param title
         * @return
         */

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        private TextView titleTextView;
        private TextView positiveButton;
        private TextView negativeButton;
        private EditText passwordEdittext;
        private LinearLayout showPasswordCheckBoxLayout;
        private CheckBox showPasswordCheckBox;

        public WifiConnectDialog create(final OnCustomDialogListener customDialogListener) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final WifiConnectDialog dialog = new WifiConnectDialog(context,R.style.WifiDialog,customDialogListener);
            View layout = inflater.inflate(R.layout.gn_dialog_wifi_connect, null);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            titleTextView = (TextView) layout.findViewById(R.id.title);
            positiveButton = (TextView) layout.findViewById(R.id.positiveButton);
            negativeButton = (TextView) layout.findViewById(R.id.negativeButton);
            passwordEdittext = (EditText) layout.findViewById(R.id.gn_sw_layout_wifi_connect_dialog_edittext);
            showPasswordCheckBox = (CheckBox)layout.findViewById(R.id.gn_sw_layout_wifi_connect_dialog_checkbox);
            showPasswordCheckBoxLayout = (LinearLayout) layout.findViewById(R.id.gn_sw_layout_wifi_connect_dialog_checkbox_layout);
            titleTextView.setText(title);
            positiveButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            if (TextUtils.isEmpty(passwordEdittext.getText().toString()) || passwordEdittext.getText().toString().length()<8){
                                return;
                            }
                            customDialogListener.back(passwordEdittext.getText().toString());
                            dialog.dismiss();
                        }
                    });
            negativeButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
            passwordEdittext.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }
                @Override
                public void afterTextChanged(Editable editable) {
                    if (TextUtils.isEmpty(passwordEdittext.getText().toString())
                            || passwordEdittext.getText().toString().length() < 8){
                        positiveButton.setTextColor(Color.parseColor("#fffee1bb"));
                    }else{
                        positiveButton.setTextColor(Color.parseColor("#ffff9000"));
                    }
                }
            });
            showPasswordCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b){//显示密码
                        passwordEdittext.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    }else{//不显示密码
                        passwordEdittext.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    }
                    passwordEdittext.setText(passwordEdittext.getText().toString());
                    passwordEdittext.setSelection(passwordEdittext.getText().toString().length());
                }
            });
            showPasswordCheckBoxLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPasswordCheckBox.performClick();
                }
            });
            dialog.setContentView(layout);
            return dialog;
        }
    }

    /**
     * 获取屏幕宽高
     * @return int[]
     */
    public static int[] getScreenWH(Context ctx) {
        int[] screenWH = { 720, 1280 };
        try{
            final WindowManager windowManager = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
            final Display display = windowManager.getDefaultDisplay();
            boolean isPortrait = display.getWidth() < display.getHeight();
            final int width = isPortrait ? display.getWidth() : display.getHeight();
            final int height = isPortrait ? display.getHeight() : display.getWidth();
            screenWH[0] = width;
            screenWH[1] = height;
        }catch(Exception e){
            e.printStackTrace();
        }

        return screenWH;
    }
}
