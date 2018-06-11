package com.duolebo.tvui.widget;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/// It's obsoleted, please use ordinary adapter instead.
abstract class FocusViewAdapter extends BaseAdapter {

	public abstract View prepareFocusView(int position, View convertView, ViewGroup parent);

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = prepareFocusView(position, convertView, parent);
		return view;
	}
}
