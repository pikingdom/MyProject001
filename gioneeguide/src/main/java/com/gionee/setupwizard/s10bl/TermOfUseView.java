package com.gionee.setupwizard.s10bl;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.gionee.setupwizard.R;

import java.util.ArrayList;
import java.util.List;

/**使用条款
 * Created by xuqunxing on 2017/8/16.
 */
public class TermOfUseView extends RelativeLayout{

    private ChangeViewInterface changeViewInterface;
    private ListView policyList;
    private ArrayAdapter<String> arrayAdapter;
    private RelativeLayout contractMain;
    private UserContractView userContractView;
    private PrivacyProtocolView privacyProtocolView;
    private Button previousBt;
    private Button nextBt;

    public TermOfUseView(Context context) {
        super(context);
        initView();
    }

    public TermOfUseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public TermOfUseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public TermOfUseView(Context context, ChangeViewInterface changeViewInterface1) {
        super(context);
        this.changeViewInterface = changeViewInterface1;
        initView();
    }

    private void initView() {
        View.inflate(getContext(), R.layout.gn_sw_layout_contract,this);
        previousBt = (Button) findViewById(R.id.gn_sw_layout_contract_last_step);
        nextBt = (Button) findViewById(R.id.gn_sw_layout_contract_next);
        contractMain = (RelativeLayout) findViewById(R.id.contract_main);
        userContractView = new UserContractView(getContext());
        privacyProtocolView = new PrivacyProtocolView(getContext());
        userContractView.setVisibility(GONE);
        privacyProtocolView.setVisibility(GONE);
        contractMain.addView(userContractView);
        contractMain.addView(privacyProtocolView);

        policyList = (ListView) findViewById(R.id.gn_sw_layout_policy_list);
        arrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.gn_sw_layout_contract_policy, R.id.gn_sw_layout_contract_policy_title, getTitles());
        policyList.setAdapter(arrayAdapter);
        policyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    if(position == 0){
                        userContractView.setVisibility(VISIBLE);
                    }else if(position == 1){
                        privacyProtocolView.setVisibility(VISIBLE);
                    }
            }
        });
        userContractView.setOnGnClickListener(new UserContractView.onGnClickListener() {
            @Override
            public void onGnBackListener() {
                userContractView.setVisibility(GONE);
            }
        });
        privacyProtocolView.setOnGnClickListener(new UserContractView.onGnClickListener() {
            @Override
            public void onGnBackListener() {
                privacyProtocolView.setVisibility(GONE);
            }
        });
        previousBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(changeViewInterface != null){
                    changeViewInterface.setCurrentlyShowView(ChangeViewInterface.GIONEE_ACCOUNT);
                }
            }
        });
        nextBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(changeViewInterface != null){
                    changeViewInterface.setCurrentlyShowView(ChangeViewInterface.FINISH_VIEW);
                }
            }
        });
    }


    private List<String> getTitles() {
        List<String> list = new ArrayList();
        list.add("用户协议");
        list.add("隐私政策");
        return list;
    }

    public void onBackPressed() {
        try {
            if(userContractView.getVisibility() == VISIBLE){
                userContractView.setVisibility(GONE);
            }else if(privacyProtocolView.getVisibility() == VISIBLE){
                privacyProtocolView.setVisibility(GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
