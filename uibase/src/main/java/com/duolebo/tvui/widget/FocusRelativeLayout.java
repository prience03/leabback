package com.duolebo.tvui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.duolebo.tvui.LayoutFocusHelper;
import com.duolebo.tvui.OnChildViewSelectedListener;
import com.duolebo.tvui.OnMovingFocusListener;

public class FocusRelativeLayout extends RelativeLayout {
	
	private LayoutFocusHelper helper;
	private boolean drawFocusOnTop = true;

	@SuppressLint("NewApi")
	public FocusRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public FocusRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public FocusRelativeLayout(Context context) {
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
    
    public void setFocusEdgeOffset(int offset) {
		helper.setFocusEdgeOffset(offset);
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
    
    public void setSelectedView(View view) {
        helper.setSelectedView(view);
    }
    
    public View getSelectedView() {
        return helper.getSelectedView();
    }

	@Override
	protected int getChildDrawingOrder(int childCount, int i) {
		return helper.getChildDrawingOrder(childCount, i);
	}
	
	@Override
	protected void dispatchDraw(Canvas canvas) {
		if (drawFocusOnTop) {
			super.dispatchDraw(canvas);
			helper.dispatchDraw(canvas);
		} else {
			helper.dispatchDraw(canvas);
			super.dispatchDraw(canvas);
		}
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

	public void setDrawFocusOnTop(boolean drawFocusOnTop) {
		this.drawFocusOnTop = drawFocusOnTop;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (!helper.onInterceptTouchEvent(ev))
			return super.onInterceptTouchEvent(ev);

		return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return super.onTouchEvent(event);
	}
}