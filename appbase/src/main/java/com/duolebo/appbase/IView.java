package com.duolebo.appbase;

import android.view.View;

public interface IView {
	public View getView(int type, View convertView);
	public void onViewClick(View view);
}
