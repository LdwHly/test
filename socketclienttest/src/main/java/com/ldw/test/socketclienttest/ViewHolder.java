package com.ldw.test.socketclienttest;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ViewHolder {

	private SparseArray<View> views;
	private View convertView;
	@SuppressWarnings("unused")
	private Context context;
	@SuppressWarnings("unused")
	private int position;

	public ViewHolder(Context context, ViewGroup parent, int layoutId,
                      int position) {
		this.context = context;
		this.position = position;
		this.views = new SparseArray<View>();
		this.convertView = LayoutInflater.from(context).inflate(layoutId,
				parent, false);
		this.convertView.setTag(this);

	}

	public static ViewHolder getViewHolder(Context context, View convertView,
			ViewGroup parent, int layoutId, int position) {
		if (convertView == null) {
			return new ViewHolder(context, parent, layoutId, position);
		} else {
			return (ViewHolder) convertView.getTag();
		}
	}

	public View getView(int viewId) {
		View view = views.get(viewId);

		if (view == null) {
			view = convertView.findViewById(viewId);
			views.put(viewId, view);
		}
		return view;
	}

	public ViewHolder setText(int viewId, CharSequence text) {
		TextView view = (TextView) getView(viewId);
		view.setText(text);
		return this;
	}



	public ViewHolder setButton(int viewId, String text) {
		Button view = (Button) getView(viewId);
		view.setText(text);
		return this;
	}

	/**
	 * @return the convertView
	 */
	public View getConvertView() {
		return convertView;
	}

}
