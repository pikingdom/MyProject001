package com.wifi.xiaomiguide.miuiv9.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wifi.xiaomiguide.R;

/**
 * Created by llf
 * on 2017/07/12.
 */

public class Language_v9_Adapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private String[] mLanguageArr;

    private int mSelectPosition = 0;

    public Language_v9_Adapter(Context context, String[] data, int position) {
        this.mContext = context;
        mLanguageArr = data;
        mSelectPosition = position;
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
            converView = mInflater.inflate(R.layout.language_item_view_v9, viewGroup, false);
            holder = new LanguageHolder();
            holder.languageLayout = (RelativeLayout)converView.findViewById(R.id.language_layout);
            holder.text = (TextView)converView.findViewById(R.id.language_item);
            holder.selectImg = (ImageView) converView.findViewById(R.id.sel_image);

            converView.setTag(holder);
        } else {
            holder = (LanguageHolder)converView.getTag();
        }

        if (position == mSelectPosition) {
            holder.text.setTextColor(this.mContext.getResources().getColor(R.color.light_blue));
            holder.selectImg.setVisibility(View.VISIBLE);
        } else {
            holder.text.setTextColor(this.mContext.getResources().getColor(R.color.black));
            holder.selectImg.setVisibility(View.GONE);
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

//    public void setSelectPostion(int position) {
//        this.mSelectPosition = position;
//        refresh();
//    }
//
//    protected void refresh() {
//        notifyDataSetChanged();
//    }

    public class LanguageHolder {
        public RelativeLayout languageLayout;
        public TextView text;
        private ImageView selectImg;
    }

    public interface OnRecyclerViewListener {
        void onItemClick(LanguageHolder holder, int position);
    }

    private OnRecyclerViewListener onRecyclerViewListener;
    public void setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener) {
        this.onRecyclerViewListener = onRecyclerViewListener;
    }
}
