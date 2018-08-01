package com.wifi.vivoguide.wifi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wifi.vivoguide.R;

/**
 * Created by llf
 * on 2017/07/12.
 */

public class LanguageAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private String[] mLanguageArr;

    public LanguageAdapter(Context context, String[] data) {
        this.mContext = context;
        mLanguageArr = data;
        mInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return mLanguageArr==null? 0 :mLanguageArr.length;
    }

    @Override
    public String getItem(int i) {
        return mLanguageArr[i];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View converView, ViewGroup viewGroup) {
        final LanguageHolder holder;
        if (converView == null) {
            converView = mInflater.inflate(R.layout.language_view_item, null);
            holder = new LanguageHolder();
            holder.languageLayout = (RelativeLayout)converView.findViewById(R.id.language_layout);
            holder.text = (TextView)converView.findViewById(R.id.language_item);

            converView.setTag(holder);
        } else {
            holder = (LanguageHolder)converView.getTag();
        }

        holder.text.setText(mLanguageArr[position]);

        holder.languageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onRecyclerViewListener != null){
                    onRecyclerViewListener.onItemClick(holder, position);
                }

            }
        });

        return converView;
    }

    public class LanguageHolder {
        public RelativeLayout languageLayout;
        public TextView text;
    }

    public interface OnRecyclerViewListener {
        void onItemClick(LanguageHolder holder, int position);
    }

    private OnRecyclerViewListener onRecyclerViewListener;
    public void setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener) {
        this.onRecyclerViewListener = onRecyclerViewListener;
    }
}
