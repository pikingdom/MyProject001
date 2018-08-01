package com.gionee.setupwizard.view;

import android.content.Context;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.gionee.setupwizard.R;

import java.util.List;

/**
 * 输入法选择界面
 * 作者：xiaomao on 2017/7/18.
 */

public class InputMethodView extends LinearLayout{
    private Context context;
    private GioneeSetupWizardView gioneeSetupWizardView;

    private List<InputMethodInfo> mEnabledInputMethods;//输入法信息
    private ListView mInputMethodListView;
    private InputMethodManager mMethodManager;
    private Button mNextButton;

    private InputMethodAdapter mAdapter;
    private String defaultInputMethodPackageName = "";

    public InputMethodView(Context context,GioneeSetupWizardView gioneeSetupWizardView) {
        super(context);
        this.context = context;
        this.gioneeSetupWizardView = gioneeSetupWizardView;
        initData();
        initView();
    }

    private void initData(){
        this.mMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        this.mEnabledInputMethods = this.mMethodManager.getEnabledInputMethodList();
        this.mAdapter = new InputMethodAdapter(context, this.mEnabledInputMethods);
        if (mEnabledInputMethods.size() > 0) {
            defaultInputMethodPackageName = mEnabledInputMethods.get(mEnabledInputMethods.size() - 1).getPackageName();
        }
    }
    /**
     * 初始化视图
     */
    private void initView(){
        View mRoot = inflate(context, R.layout.gn_layout_input_method,null);
        addView(mRoot,new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));

        this.mNextButton = (Button) findViewById(R.id.gn_sw_layout_inputmethod_nextbutton);
        this.mNextButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {//进入账号视图
                gioneeSetupWizardView.setCurrentlyShowView(GioneeSetupWizardView.ACCOUNT_VIEW);
            }
        });
        this.mInputMethodListView = (ListView) findViewById(R.id.gn_sw_layout_inputmethod_listview);
        this.mInputMethodListView.setAdapter(mAdapter);
        this.mInputMethodListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                InputMethodInfo inputMethodInfo = mEnabledInputMethods.get(i);
                defaultInputMethodPackageName = inputMethodInfo.getPackageName();
                mAdapter.notifyDataSetChanged();
            }
        });

    }

    private class InputMethodAdapter extends BaseAdapter {
        private Context context;
        private List<InputMethodInfo> inputMethodInfos;
        private PackageManager packageManager;

        InputMethodAdapter(Context context, List<InputMethodInfo> inputMethodInfos) {
            this.context = context;
            this.inputMethodInfos = inputMethodInfos;
            this.packageManager = context.getPackageManager();
        }
        public int getCount() {
            return this.inputMethodInfos.size();
        }
        public InputMethodInfo getItem(int position) {
            return (InputMethodInfo) this.inputMethodInfos.get(position);
        }
        public long getItemId(int position) {
            return 0;
        }
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                view = newView();
            }
            ViewHolder holder = (ViewHolder) view.getTag();
            InputMethodInfo info = (InputMethodInfo) this.inputMethodInfos.get(position);
            holder.textView.setText(info.loadLabel(this.packageManager));
            if (defaultInputMethodPackageName.startsWith(info.getPackageName())) {
                holder.radioButton.setChecked(true);
            } else {
                holder.radioButton.setChecked(false);
            }
            return view;
        }

        private View newView() {
            View view = LayoutInflater.from(this.context).inflate(R.layout.gn_item_layout_language, null);
            ViewHolder holder = new ViewHolder();
            holder.textView = (TextView) view.findViewById(R.id.gn_sw_layout_single_item_textView);
            holder.radioButton = (RadioButton) view.findViewById(R.id.gn_sw_layout_single_item_RadioButton);
            view.setTag(holder);
            return view;
        }
    }
    private class ViewHolder {
        public RadioButton radioButton;
        public TextView textView;
    }
}
