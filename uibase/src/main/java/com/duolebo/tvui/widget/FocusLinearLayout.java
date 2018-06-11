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
import android.widget.LinearLayout;

import com.duolebo.tvui.LayoutFocusHelper;
import com.duolebo.tvui.OnChildViewSelectedListener;
import com.duolebo.tvui.OnMovingFocusListener;

/**
 * @author zlhl
 * @date 2014年2月23日
 */
public class FocusLinearLayout extends LinearLayout {
	
	private LayoutFocusHelper helper;

	@SuppressLint("NewApi")
	public FocusLinearLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public FocusLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public FocusLinearLayout(Context context) {
		super(context);
		init();
	}
	
	private void init() {
		helper = new LayoutFocusHelper(this);
		setChildrenDrawingOrderEnabled(true);
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

    public void setOnChildViewSelectedListener(OnChildViewSelectedListener listener) {
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
		super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
	}
}
