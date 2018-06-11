package com.open.sample.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.open.sample.R;

/**
 * Author: liuyang
 * Maintainer: liuyang
 * Date: 2016/11/3
 * Copyright: 2016 www.xgimi.com Inc. All rights reserved.
 * Desc: NULL
 */

public class CardView extends LinearLayout {

    private Context mContext;
    private ImageView img_cover;
    private TextView tv_name;

    public CardView(Context context) {
        this(context, null);
        setClipChildren(false);
        setClipToPadding(false);
    }

    public CardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = getContext().getApplicationContext();
        LayoutInflater.from(context).inflate(R.layout.item_recycler_list, this, true);
        img_cover = (ImageView) findViewById(R.id.metroitem_img);
        tv_name = (TextView) findViewById(R.id.metroitem_name);
    }

}
