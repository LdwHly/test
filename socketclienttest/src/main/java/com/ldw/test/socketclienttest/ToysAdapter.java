package com.ldw.test.socketclienttest;

import android.app.Activity;

import com.ldw.test.socketclienttest.game.bean.bean.ToyBean;

import java.util.List;

public class ToysAdapter extends MyBaseAdapter<ToyBean> {
    public ToysAdapter(List<ToyBean> list, Activity activity, int layoutId) {
        super(list, activity, layoutId);
    }

    @Override
    public void setValues(ViewHolder holder, ToyBean toyBean, int position) {
        holder.setText(R.id.tv_name,toyBean.getId());
    }
}
