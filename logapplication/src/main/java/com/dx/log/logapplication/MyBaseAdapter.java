package com.dx.log.logapplication;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;


public abstract class MyBaseAdapter<T> extends BaseAdapter {

    public List<T> list;
    public Context activity;
    public int layoutId;

    public MyBaseAdapter(List<T> list, Context activity, int layoutId) {
        super();
        this.list = list;
        this.activity = activity;
        this.layoutId = layoutId;
    }

    public void setData(ArrayList<T> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public T getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (isHaveUsed()) {
            holder = ViewHolder.getViewHolder(activity, convertView,
                    parent, layoutId, position);
        } else {
            holder = ViewHolder.getViewHolder(activity, null,
                    parent, layoutId, position);
        }

        setValues(holder, list.get(position), position);
        return holder.getConvertView();
    }

    public abstract void setValues(ViewHolder holder, T t, int position);

    public boolean isHaveUsed() {
        return true;
    }

}
