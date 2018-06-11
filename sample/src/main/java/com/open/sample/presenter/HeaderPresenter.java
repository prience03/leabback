package com.open.sample.presenter;

import android.graphics.Color;

import com.open.leanback.widget.Presenter;
import com.open.leanback.widget.RowHeaderPresenter;
import com.open.leanback.widget.RowHeaderView;

/**
 *  关于横向item的头样式.
 */
public class HeaderPresenter extends RowHeaderPresenter {

    @Override
    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object item) {
        super.onBindViewHolder(viewHolder, item);
        RowHeaderView headerView = (RowHeaderView) viewHolder.view;
        headerView.setTextSize(25);
        headerView.setTextColor(Color.WHITE);
        headerView.setPadding(0, 0, 0, 20);
    }

}
