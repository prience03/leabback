package com.duolebo.tvui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;

import com.duolebo.tvui.OnChildViewSelectedListener;

public class DynamicWin8ScrollView extends HorizontalScrollView implements OnChildViewSelectedListener {
	
	private DynamicWin8View win8;
	private float leftThreshold;
	private float rightThreshold;
	private int leftPadding;
	private int rightPadding;

	public DynamicWin8ScrollView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public DynamicWin8ScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public DynamicWin8ScrollView(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context) {
		leftThreshold = 0.f;
		rightThreshold = 0.f;
		leftPadding = 0;
		rightPadding = 0;

		setFocusable(false);
		setFocusableInTouchMode(false);
        setDescendantFocusability(FOCUS_AFTER_DESCENDANTS);

		win8 = new DynamicWin8View(context);
		win8.setOnChildViewSelectedListener(this);

		LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		win8.setGravity(Gravity.CENTER);
		win8.setPadding(leftPadding, 0, rightPadding, 0);
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		win8.setMinimumWidth(metrics.widthPixels);
		addView(win8, lp);
	}
	
	public DynamicWin8View getWin8() {
		return win8;
	}

	public float getLeftScrollThreshold() {
		return leftThreshold;
	}

	public void setLeftScrollThreshold(float leftThreshold) {
		this.leftThreshold = leftThreshold;
	}

	public float getRightScrollThreshold() {
		return rightThreshold;
	}

	public void setRightScrollThreshold(float rightThreshold) {
		this.rightThreshold = rightThreshold;
	}

	public void setLeftRightPadding(int leftPadding, int rightPadding) {
		this.leftPadding = leftPadding;
		this.rightPadding = rightPadding;
	}

	public int getLeftPadding() {
		return leftPadding;
	}

	public int getRightPadding() {
		return rightPadding;
	}
	
	@SuppressLint("NewApi")
	@Override
	public void OnChildViewSelected(View view, boolean selected) {
		if (null == view) return;
		int l = view.getLeft() - getScrollX();
		int r = getWidth() - (view.getRight() - getScrollX());
		if (l < leftThreshold) {
			smoothScrollTo((view.getRight() + rightPadding) - getWidth(), 0);
		} else if (r < rightThreshold) {
			smoothScrollTo(view.getLeft() - leftPadding, 0);
		}
	}
	
}
