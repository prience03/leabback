/**
 * 
 */
package com.duolebo.tvui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridView;

import com.duolebo.tvui.LayoutFocusHelper;
import com.duolebo.tvui.OnChildViewSelectedListener;
import com.duolebo.tvui.OnMovingFocusListener;
import com.duolebo.tvui.R;
import com.duolebo.tvui.utils.UIUtils;

/**
 * @author zlhl
 * @date 2014年3月25日
 */
public class FocusGridView extends GridView {

	private LayoutFocusHelper helper;
	private OnScrollListener onScrollListener;
	
	public static final int ROLLING_STYLE_BY_LINE = 0;
	public static final int ROLLING_STYLE_BY_PAGE = 1;
	
	private int rollingStyle = ROLLING_STYLE_BY_LINE;
	
	private int paddingOffset = 0;

	public FocusGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public FocusGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public FocusGridView(Context context) {
		super(context);
		init();
	}

	@Override
	public void setOnScrollListener(OnScrollListener l) {
		onScrollListener = l;
		super.setOnScrollListener(l);
	}

	private void init() {
		helper = new GridViewFocusHelper(this);
		setChildrenDrawingOrderEnabled(true);
		setSelector(R.drawable.tvui_selector_transparent);
		setOverScrollMode(OVER_SCROLL_NEVER);
		paddingOffset = (int)UIUtils.getPixelFromDpi(getContext(), 5);
	}

	public void setFocusHighlightDrawable(int resId) {
		helper.setFocusHighlightDrawable(resId);
	}

	public void setFocusShadowDrawable(int resId) {
		helper.setFocusShadowDrawable(resId);
	}

	public void setFocusScale(float scaleX, float scaleY) {
		helper.setFocusScale(scaleX, scaleY);
	}

	public void setFocusMovingDuration(long millSec) {
		helper.setFocusMovingDuration(millSec);
	}

	public void setKeepFocus(boolean keep) {
		helper.setKeepFocus(keep);
	}

	public void setExcludePadding(boolean exclude) {
		helper.setExcludePadding(exclude);
	}

	public void setOnChildViewSelectedListener(
			OnChildViewSelectedListener listener) {
		helper.setOnChildViewSelectedListener(listener);
	}

	public void setOnMovingFocusListener(OnMovingFocusListener listener) {
		helper.setOnMovingFocusListener(listener);
	}

	public void setSelectedViewIndex(int index) {
		helper.setSelectedViewIndex(index);
	}

    public int getSelectedViewIndex() {
    	return helper.getSelectedViewIndex();
    }
    
	public void refreshFocus() {
		helper.refreshFocus();
	}

	@Override
	protected int getChildDrawingOrder(int childCount, int i) {
		return helper.getChildDrawingOrder(childCount, i);
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		helper.dispatchDraw(canvas);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (!helper.onKeyUp(keyCode, event))
			return super.onKeyUp(keyCode, event);

		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (!helper.onKeyDown(keyCode, event))
			return super.onKeyDown(keyCode, event);

		return true;
	}

	@Override
	protected void onFocusChanged(boolean gainFocus, int direction,
			Rect previouslyFocusedRect) {
		helper.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
	}

	public void resetFocus() {
		((GridViewFocusHelper) helper).resetFocus();
	}

	class GridViewFocusHelper extends LayoutFocusHelper {
		public GridViewFocusHelper(ViewGroup layout) {
			super(layout);
		}

		@SuppressLint("NewApi")
		@Override
		public void setSelectedView(View view) { 

			View nextChildView = findViewByItsSubView(view);
			if (null != nextChildView) {

				final int pos = getPositionForView(nextChildView);
				Rect r = new Rect();
				nextChildView.getGlobalVisibleRect(r);

				if ((r.bottom - r.top) != nextChildView.getHeight()) {
					/// we need animate this process, simply scroll it.
					FocusGridView.super
							.setOnScrollListener(new OnScrollListener() {
								@Override
								public void onScrollStateChanged(
										AbsListView view, int scrollState) {
									if (OnScrollListener.SCROLL_STATE_IDLE == scrollState) {
										setSelection(pos);
									}

									if (null != onScrollListener) {
										onScrollListener.onScrollStateChanged(
												view, scrollState);
									}
								}

								@Override
								public void onScroll(AbsListView view,
										int firstVisibleItem,
										int visibleItemCount, int totalItemCount) {

									if (null != onScrollListener) {
										onScrollListener.onScroll(view,
												firstVisibleItem,
												visibleItemCount,
												totalItemCount);
									}
								}
							});


					if (getHeight() <= r.bottom) {
						if (rollingStyle == ROLLING_STYLE_BY_PAGE) {
							smoothScrollToPositionFromTop(pos, paddingOffset + getPaddingTop());
						} else {
							smoothScrollToPositionFromTop(pos, getHeight()
									- nextChildView.getHeight() - paddingOffset - getPaddingBottom());
						}
					} else {

						if (rollingStyle == ROLLING_STYLE_BY_PAGE) {
							smoothScrollToPositionFromTop(pos, getHeight()
									- nextChildView.getHeight() - paddingOffset - getPaddingBottom());
						} else {
							smoothScrollToPositionFromTop(pos, paddingOffset + getPaddingTop());
						}
					}

				} else {
					setSelection(pos);
				}

				super.setSelectedView(view);
			}
		}
		
		public void resetFocus() {
			setSelection(INVALID_POSITION);
			super.setSelectedView(null);
		}

	}

	public int getRollingStyle() {
		return rollingStyle;
	}

	public void setRollingStyle(int rollingStyle) {
		this.rollingStyle = rollingStyle;
	}

	/// obsoleted. only to keep backward compatibility.
	public static abstract class FocusGridViewAdapter extends FocusViewAdapter {
	}
}
