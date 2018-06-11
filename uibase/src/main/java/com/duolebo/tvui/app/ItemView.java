package com.duolebo.tvui.app;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.duolebo.tvui.R;
import com.duolebo.tvui.utils.UIUtils;
import com.duolebo.tvui.widget.IFocusPos;
import com.duolebo.tvui.widget.RecyclingNetworkImageView;
import com.duolebo.tvui.widget.RoundedFrameLayout;

/**
 * @author zlhl
 * @date 2014年3月21日
 */
public class ItemView extends RoundedFrameLayout implements IFocusPos {
	
	private RecyclingNetworkImageView backgroundView = null;
    private RecyclingNetworkImageView foregroundView = null;
    private TextView titleView = null;
    private ViewStub innerViewStub = null;
    private ViewStub outerViewStub = null;
    
    private FrameLayout frame = null;

	public ItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs);
	}

	public ItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public ItemView(Context context) {
		super(context);
		init(context, null);
	}
	
	protected void init(Context context,AttributeSet attrs) {
		LayoutInflater.from(context).inflate(R.layout.tvui_view_item, this);
		backgroundView = (RecyclingNetworkImageView) findViewById(R.id.background);
		foregroundView = (RecyclingNetworkImageView) findViewById(R.id.foreground);
		titleView = (TextView) findViewById(R.id.title);
		innerViewStub = (ViewStub) findViewById(R.id.innerViewStub);
		outerViewStub = (ViewStub) findViewById(R.id.outerViewStub);
		frame = (FrameLayout) findViewById(R.id.frame);
		
		setFocusable(true);
	}

	public void setForegroundViewHeightInPixel(int height) {
		ViewGroup.LayoutParams lp = foregroundView.getLayoutParams();
		lp.height = height;
		foregroundView.setLayoutParams(lp);

		lp = backgroundView.getLayoutParams();
		lp.height = height;
		backgroundView.setLayoutParams(lp);
	}
	
	public RecyclingNetworkImageView getBackgroundView() {
		return backgroundView;
	}
	
	public RecyclingNetworkImageView getForegroundView() {
		return foregroundView;
	}
	
	public TextView getTitleView() {
		return titleView;
	}
	
	@Override
	public View getFocusPosView() {
		return frame;
	}

	/*
	Please use getInnerViewStub instead.
	 */
	@Deprecated
	public ViewStub getViewStub() {
		return innerViewStub;
	}
	
	public ViewStub getInnerViewStub() {
		return innerViewStub;
	}
	
	public ViewStub getOuterViewStub() {
		return outerViewStub;
	}

	public void setFrameSizeInDp(int width, int height) {
		ViewGroup.LayoutParams lp = frame.getLayoutParams();
		lp.width = (int)UIUtils.getPixelFromDpi(getContext(), width);
		lp.height = (int)UIUtils.getPixelFromDpi(getContext(), height);
		frame.setLayoutParams(lp);
	}

	public void setFrameSizeInPixel(int width, int height) {
		ViewGroup.LayoutParams lp = frame.getLayoutParams();
		lp.width = width;
		lp.height = height;
		frame.setLayoutParams(lp);
	}
}
