package com.duolebo.tvui.app;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.duolebo.tvui.R;
import com.duolebo.tvui.volley.ForceCachedImageLoader.ImageContainer;
import com.duolebo.tvui.volley.ForceCachedImageLoader.ImageListener;
import com.duolebo.tvui.volley.ImageReq;
import com.duolebo.tvui.widget.RecyclingNetworkImageView;
import com.duolebo.tvui.widget.RoundedFrameLayout;

public class PosterView extends RoundedFrameLayout {
	
	private RecyclingNetworkImageView backgroundView = null;
    private RecyclingNetworkImageView foregroundView = null;
    private TextView titleView = null;
    private ViewStub viewStub = null;
	private FrameLayout frameTextBg ;
	private TextView  textViewFocus ;
	public PosterView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public PosterView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public PosterView(Context context) {
		super(context);
		init(context);
	}
	
	private void init(Context context) {
		LayoutInflater.from(context).inflate(R.layout.tvui_view_poster, this);
		backgroundView = (RecyclingNetworkImageView) findViewById(R.id.background);
		foregroundView = (RecyclingNetworkImageView) findViewById(R.id.foreground);
		titleView = (TextView) findViewById(R.id.title);
		viewStub = (ViewStub) findViewById(R.id.viewStub);
		textViewFocus = (TextView) findViewById(R.id.title_focus);
		frameTextBg = (FrameLayout) findViewById(R.id.framenlayout_textbg);
		setFocusable(true);
	}

	public TextView getTextViewFocus() {
		return textViewFocus;
	}

	public FrameLayout getFrameTextBg() {
		return frameTextBg;
	}

	public RecyclingNetworkImageView getBackgroundView() {
		return backgroundView;
	}
	
	private void loadImageUrl(final ImageView view, String url, int width, int height, final int errResId) {
		ImageReq.get(getContext(), url, new ImageListener() {
	        @Override
	        public void onErrorResponse(VolleyError error) {
	            if (0 < errResId) {
	                view.setImageResource(errResId);
	            }
	        }

	        @Override
	        public void onResponse(ImageContainer response, boolean isImmediate) {
	            if (null != response && null != response.getBitmapDrawable()) {
	                view.setImageDrawable(response.getBitmapDrawable());
	            }
	        }
	    }, width, height);
	}
	
	public void loadBackgroundImageUrl(final String url, int width, int height, int errResId) {
	    loadImageUrl(getBackgroundView(), url, width, height, errResId);
	}
	
	/*
	public void setBackgroundViewImageRes(int resId) {
		backgroundView.setImageResource(resId);
	}
	
	public void setBackgroundViewImageBitmap(Bitmap bm) {
		backgroundView.setImageBitmap(bm);
	}
	*/
	
	public RecyclingNetworkImageView getForegroundView() {
		return foregroundView;
	}
	
	public void loadForegroundImageUrl(String url, int width, int height, int errResId) {
	    loadImageUrl(getForegroundView(), url, width, height, errResId);
	}
	
	/*
	public void setForegroundViewImageRes(int resId) {
		foregroundView.setImageResource(resId);
	}
	
	public void setForegroundViewImageBitmap(Bitmap bm) {
		foregroundView.setImageBitmap(bm);
	}
	*/
	
	public TextView getTitleView() {
		return titleView;
	}
	
	/*
	public void setTitleViewText(String text) {
		titleView.setText(text);
	}
	*/

	public ViewStub getViewStub() {
		return viewStub;
	}

}
