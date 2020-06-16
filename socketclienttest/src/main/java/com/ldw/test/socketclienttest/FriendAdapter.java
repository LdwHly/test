package com.ldw.test.socketclienttest;

import android.app.Activity;

import java.util.List;

public class FriendAdapter extends MyBaseAdapter<FriendBean> {
    public FriendAdapter(List<FriendBean> list, Activity activity, int layoutId) {
        super(list, activity, layoutId);
    }

    @Override
    public void setValues(ViewHolder holder, FriendBean friendBean, int position) {
        holder.setText(R.id.tv_name, friendBean.ip + ":" + friendBean.port);
    }
}
