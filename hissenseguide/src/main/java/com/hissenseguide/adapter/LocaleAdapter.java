package com.hissenseguide.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuqunxing on 2017/7/19.
 */
public abstract class LocaleAdapter<Locale> extends BaseAdapter {
    protected Context mContext;
    protected List<java.util.Locale> mData = new ArrayList();
    protected LayoutInflater mInflater;
    protected int selectPosition = 0;

    public abstract void bindView(int i, View view);

    public abstract View newView(int i, ViewGroup viewGroup);

    public LocaleAdapter(Context context) {
        this.mContext = context;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return this.mData.size();
    }

    public java.util.Locale getItem(int position) {
        return this.mData.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = newView(position, parent);
        }
        bindView(position, convertView);
        return convertView;
    }

    protected void refresh() {
        notifyDataSetChanged();
    }
}