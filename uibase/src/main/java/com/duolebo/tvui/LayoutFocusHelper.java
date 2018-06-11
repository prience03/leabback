/**
 * 
 */
package com.duolebo.tvui;

import java.lang.ref.WeakReference;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.duolebo.tvui.widget.IFocusPos;
import com.duolebo.tvui.widget.IFocusPosScale;

/**
 * @author zlhl
 * @date 2014年2月23日
 */
public class LayoutFocusHelper {

    /// Is it currently animating?
    private boolean isAnimating = false;

    /// Drawables that hold focus resources.
    private Drawable focusHighlightDrawable = null;
    private Drawable focusShadowDrawable = null;

    /// How much to scale focused view.
    private float scaleRatioX = 1.0f;
    private float scaleRatioY = 1.0f;

    /// Index of current selected view
    private int selectedViewIndex = -1;
    private View selectedView = null;

    /// View and it's positions holder
    private View fromFocusedView = null;
    private View toFocusedView = null;

    /// Animation parameters
    private long focusMovingDuration = 100;
    private long focusAnimationStartTime = 0;

    /// Listener
    private OnChildViewSelectedListener childViewSelectedListener = null;
    private OnMovingFocusListener movingFocusListener = null;

    private boolean delaySelect = false;
    private boolean excludePadding = false;
    private boolean keepFocus = false;
    private View firstView = null;
	
	private ViewGroup layout;
	
	private Rect toFocusedViewRectLast = null;
	
	private LayoutHandler handler = null;
	
	//for focus draw
	private int mFocusEdgeOffset = 0;

    private boolean shouldCompensateFocusDrawing = true;
	
	public LayoutFocusHelper(ViewGroup layout) {
		this.layout = layout;
        this.layout.setFocusable(true);
        this.layout.setFocusableInTouchMode(true);
        this.layout.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);

        //add default focus style.
        setFocusHighlightDrawable(R.drawable.tvui_focus_highlight);

        handler = new LayoutHandler(this);

	}

    public void setShouldCompensateFocusDrawing(boolean compensate) {
        shouldCompensateFocusDrawing = compensate;
    }

    public void setFocusHighlightDrawable(int resId) {
    	if (resId <= 0) {
    		focusHighlightDrawable = null;
    		return;
    	}
        focusHighlightDrawable = layout.getContext().getResources().getDrawable(resId);
    }

    public void setFocusShadowDrawable(int resId) {
    	if (resId <= 0) {
    		focusShadowDrawable = null;
    		return;
    	}
        focusShadowDrawable = layout.getContext().getResources().getDrawable(resId);
    }

    public void setFocusScale(float scaleX, float scaleY) {
        scaleRatioX = scaleX;
        scaleRatioY = scaleY;
    }
    
    private float getScaleRatioX(View view) {
        if (view instanceof IFocusPosScale) {
            return ((IFocusPosScale)view).getScaleRatioX();
        }
        return scaleRatioX;
    }
    
    private float getScaleRatioY(View view) {
        if (view instanceof IFocusPosScale) {
            return ((IFocusPosScale)view).getScaleRatioY();
        }
        return scaleRatioY;
    }
    
	public void setFocusEdgeOffset(int offset) {
		this.mFocusEdgeOffset = offset;
	}

    public void setFocusMovingDuration(long millSec) {
        focusMovingDuration = millSec;
    }
    
    public void setKeepFocus(boolean keep) {
    	keepFocus = keep;
    }

    public void setOnChildViewSelectedListener(OnChildViewSelectedListener listener) {
        this.childViewSelectedListener = listener;
    }

    public void setOnMovingFocusListener(OnMovingFocusListener listener) {
    	this.movingFocusListener = listener;
    }

    public void setExcludePadding(boolean exclude) {
    	this.excludePadding = exclude;
    }

    public boolean isFrom(View view) {
        return fromFocusedView != null && findViewToDrawFocus(fromFocusedView).equals(view);
    }

    public boolean isTo(View view) {
        return toFocusedView != null && findViewToDrawFocus(toFocusedView).equals(view);
    }

    public void revertFromView() {
        if (fromFocusedView != null && fromFocusedView.getScaleX() != 1.0f) {
            fromFocusedView.setScaleX(1.0f);
            fromFocusedView.setScaleY(1.0f);
        }
    }
    
    public void setSelectedViewIndex(int index) {
    	if (index < layout.getChildCount()) {
    		View curr = layout.getChildAt(index);
    		if (curr instanceof ViewGroup) {
    			curr = findFirstFocusableView((ViewGroup)curr);
    		}
    		setSelectedView(curr);
    	}
    }

    public int getSelectedViewIndex() {
    	return indexOfAnyView(getSelectedView());
    }

    public int getChildDrawingOrder(int childCount, int i) {
        if (selectedViewIndex < 0)
            return i;

        if (i < (childCount - 1)) {
            if (selectedViewIndex == i) {
                return childCount - 1;
            } else {
                return i;
            }
        } else {
        	if (selectedViewIndex < childCount)
        		return selectedViewIndex;
        	
        	return i;
        }
    }
    
    public void setDelaySelect(boolean isDelay) {
    	delaySelect = isDelay;
    }
    
    public void dispatchDraw(Canvas canvas) {
        drawFocusDynamic(canvas);
        drawFocusStatic(canvas);
    }

    private View findViewByPos(float x, float y) {
        View view;
        Rect hitRect = new Rect();
        for (int i=0; i<layout.getChildCount(); i++) {
            view = layout.getChildAt(i);
            view.getHitRect(hitRect);
            if (hitRect.contains((int)x, (int)y)) {
                return view;
            }
        }
        return null;
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //Log.i("LayoutFocusHelper", "action:" + ev.getAction());
        if (MotionEvent.ACTION_UP == ev.getAction()) {
            View view = findViewByPos(ev.getX(), ev.getY());
            if (null != view && view.isFocusable()) {
                setSelectedView(view);
            }
        }
        return false;
    }

    private boolean pressed = false;

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        View currSelectedView = getSelectedView();
        switch (keyCode) {
        	case KeyEvent.KEYCODE_BUTTON_A:
            case KeyEvent.KEYCODE_DPAD_CENTER:
            case KeyEvent.KEYCODE_ENTER:
            	if (pressed && null != currSelectedView && currSelectedView.isClickable()) {
            	    pressed = false;
            		return currSelectedView.performClick();
            	}
        }
        pressed = false;
    	return false;
    }
    
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	pressed = true;

        /// find current selected view
        View currSelectedView = getSelectedView();
        if (null == currSelectedView)
        	return false;

        /// find the view that to be selected by 'focus direction'
        View nextSelectedView = null;
        int direction = -1;
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_LEFT:
                direction = ViewGroup.FOCUS_LEFT;
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                direction = ViewGroup.FOCUS_UP;
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                direction = ViewGroup.FOCUS_RIGHT;
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                direction = ViewGroup.FOCUS_DOWN;
                break;
        }

        if (-1 != direction) {
        	nextSelectedView = currSelectedView.focusSearch(direction);
        	if (movingFocusListener != null) {
        		movingFocusListener.onMovingFocus(direction, selectedView, nextSelectedView);
        	}
        }

        if (null != nextSelectedView) {
        	
            //if (nextSelectedView.getParent() == this) {
            if (isDescentantOf(layout, nextSelectedView)) {
                /// If Focus changing has occurred, start to draw focus animation.
                setSelectedView(nextSelectedView);
                return true;
            } else {
                //Log.i("Focus is going somewhere else.");
            }
        }
        
        return false;
    }

    public void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        //Log.i("======== LayoutFocusHelper onFocusChanged gainFocus:" + gainFocus + ", view:" + layout);

        if (true == gainFocus) {
        	
        	/// Now we have focus, restore children's focus.
        	layout.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);

            /// This layout is being activated, let find the first selected item.
            if (null == getSelectedView()) {
                //FIXME: should find first focusable view.
                //setSelectedViewIndex(findFirstFocusableViewIndex());
            	firstView = findFirstFocusableView(layout);

            	//Log.i("the default firstView is " + ((null == firstView) ? "not Found." : ("Found. view:" + firstView)));

            	if (null != firstView) {
            		setSelectedView(firstView);
            	}

            } else {
            	View view = getSelectedView();
            	
            	if (!delaySelect && null != view)
            		view.setSelected(true);
            	
            	if (null != childViewSelectedListener)
            		childViewSelectedListener.OnChildViewSelected(view, true);
            	
            	if (!keepFocus)
            		startFocusAnimation(null, view);
            }

        } else {

        	/// Now we lose focus, disable children's focus
        	layout.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);

            /// This layout is being deactivated
            View view = getSelectedView();
            if (null != view)
            	view.setSelected(false);

    		if (null != childViewSelectedListener)
    			childViewSelectedListener.OnChildViewSelected(view, false);
            
            if (!keepFocus)
            	startFocusAnimation(view, null);
        }
    }

    @SuppressLint("NewApi")
	private void startFocusAnimation(View fromView, View toView) {
        if (null == fromView && null == toView) {
            return;
        }

        /// Reset states
        if (isAnimating) {
            if (null != fromFocusedView) {
                fromFocusedView.setScaleX(1.0f);
                fromFocusedView.setScaleY(1.0f);
            }
            if (null != toFocusedView) {
                toFocusedView.setScaleX(getScaleRatioX(toFocusedView));
                toFocusedView.setScaleY(getScaleRatioY(toFocusedView));
            }
        }

        /// Set initial data.
        fromFocusedView = fromView;
        toFocusedView = toView;
        focusAnimationStartTime = System.currentTimeMillis();
        isAnimating = true;


        /// start animating
        layout.postInvalidate();
    }

    private void endFocusAnimation() {
        focusAnimationStartTime = -1;
        isAnimating = false;
        drawFocusStaticCounter = 10;
		if (delaySelect && null != selectedView) {
			selectedView.setSelected(true);
		}
    }

    private int drawFocusStaticCounter = 0;
    private void drawFocusStatic(Canvas canvas) {
        /// is it animating or have no selected view?
        if(isAnimating || null == selectedView) {
            return;
        }

        /// should we keep drawing focus even we don't have the focus?
        if (!keepFocus && !layout.hasFocus()) {
            return;
        }
        
        Rect toFocusedViewRect = new Rect();
        /// Calculate Focus' ending position.
        if (null != toFocusedView) {
        	View view = findViewToDrawFocus(toFocusedView);
            getViewRect(view, toFocusedViewRect);
        } else {
            /// Same trick here.
        	View view = findViewToDrawFocus(fromFocusedView);
            getViewRect(view, toFocusedViewRect);
        }

        /// draw shadow and highlighted focus.
        if (!toFocusedViewRect.isEmpty()) {
        	//Log.i("drawFocusStatic - drawing shadow..........." + layout);
        	drawDrawableAt(canvas, toFocusedViewRect, focusShadowDrawable);
        	//Log.i("drawFocusStatic - drawing focus..........." + layout);
        	drawDrawableAt(canvas, toFocusedViewRect, focusHighlightDrawable);

            if (shouldCompensateFocusDrawing) {
                if (null == toFocusedViewRectLast || !isSameRect(toFocusedViewRect, toFocusedViewRectLast)) {
                    handler.sendEmptyMessageDelayed(LayoutHandler.MESSAGE_REDRAW, 50);
                } else {
                    if (0 < --drawFocusStaticCounter) {
                        //Log.i("drawFocusStaticCounter: " + drawFocusStaticCounter);
                        handler.sendEmptyMessageDelayed(LayoutHandler.MESSAGE_REDRAW, 50);
                    }
                }
                toFocusedViewRectLast = toFocusedViewRect;
            }

        } else {
        	/// maybe layout is not ready yet,
        	/// try redraw it later.
        	handler.sendEmptyMessageDelayed(LayoutHandler.MESSAGE_REDRAW, 500);
        }
    }
    
    private boolean isSameRect(Rect rect1, Rect rect2) {
    	return rect1.left == rect2.left
    			&& rect1.top == rect2.top
    			&& rect1.right == rect2.right
    			&& rect1.bottom == rect2.bottom;
    }

    private void drawFocusDynamic(Canvas canvas) {
        /// Quick check.
        if (!isAnimating) {
            return;
        }

        /// Calculate time based progress.
        float progress = 0 < focusMovingDuration ?
                (float) (System.currentTimeMillis() - focusAnimationStartTime) /focusMovingDuration :
                1.0f;
        if (1.0f < progress) {
            progress = 1.0f;
        }

        /// Scale Views
        if (null != fromFocusedView) {
            scaleView(fromFocusedView, getScaleRatioX(fromFocusedView), getScaleRatioY(fromFocusedView), progress);
        }
        if (null != toFocusedView) {
            scaleView(toFocusedView, getScaleRatioX(toFocusedView), getScaleRatioY(toFocusedView), 1f - progress);
        }

        Rect fromFocusedViewRect = new Rect();
        Rect toFocusedViewRect = new Rect();

        /// Calculate Focus' starting position.
        if (null != fromFocusedView) {
        	View view = findViewToDrawFocus(fromFocusedView);
            getViewRect(view, fromFocusedViewRect);
        } else {
            /// A little trick here.
        	View view = findViewToDrawFocus(toFocusedView);
            getViewRect(view, fromFocusedViewRect);
        }

        /// Calculate Focus' ending position.
        if (null != toFocusedView) {
        	View view = findViewToDrawFocus(toFocusedView);
            getViewRect(view, toFocusedViewRect);
        } else {
            /// Same trick here.
        	//View view = findViewToDrawFocus(fromFocusedView);
            //getViewRect(view, toFocusedViewRect);
        }
        
        if (!(fromFocusedViewRect.isEmpty() || toFocusedViewRect.isEmpty())) {
        	/// Calculate the position of the focus, and draw it.
        	Rect movingFocusRect = new Rect();
        	calculateFocusPosition(movingFocusRect, fromFocusedViewRect, toFocusedViewRect, progress);
        	logRect("drawFocusDynamic", fromFocusedViewRect);
        	//Log.i("drawFocusDynamic - drawing focus...........");
        	drawDrawableAt(canvas, movingFocusRect,focusHighlightDrawable);
        }

        if (progress >= 1.0f) {
            /// Animation should have stopped
            endFocusAnimation();
        }

        /// Tell system to keep drawing us.
        Rect padding = new Rect();
        Rect invalidRect = combineRects(fromFocusedViewRect, toFocusedViewRect);
        if (null != focusShadowDrawable) {
            focusShadowDrawable.getPadding(padding);
            invalidRect = includPadding(invalidRect, padding);
        } else if (null != focusHighlightDrawable) {
            focusHighlightDrawable.getPadding(padding);
            invalidRect = includPadding(invalidRect, padding);
        }
        layout.postInvalidate(invalidRect.left, invalidRect.top, invalidRect.right, invalidRect.bottom);
    }

    @SuppressLint("NewApi")
	private void scaleView(View view, float scaleX, float scaleY, float progress) {
        if (null == view) {
            return;
        }
        view.setScaleX(1f + (scaleX - 1f) * (1f - progress));
        view.setScaleY(1f + (scaleY - 1f) * (1f - progress));
    }

    protected void drawDrawableAt(Canvas canvas, Rect position, Drawable drawable) {
        if (null == canvas || null == position || null == drawable) {
            return;
        }
        Rect padding = new Rect();
        drawable.getPadding(padding);
        Rect bounds = new Rect();
        bounds.left = position.left - padding.left;
        bounds.top = position.top - padding.top;
        bounds.right = position.right + padding.right;
        bounds.bottom = position.bottom + padding.bottom;
        drawable.setBounds(bounds);
        drawable.draw(canvas);
        logRect("drawDrawableAt", position);
    }
    
    private void logRect(String name, Rect rect) {
    	if (null == rect) return;
        /*
        Log.i(name + ", l:" + rect.left 
        		+ ", t:" + rect.top 
        		+ ", r:" + rect.right 
        		+ ", b:" + rect.bottom);
        		*/
    }

    public void refreshFocus() {
    	layout.post(new Runnable() {
			@Override
			public void run() {
				View find = getSelectedView();
				if (null == find || !isDescentantOf(layout, find)) {
					find = findFirstFocusableView(layout);
				}
		    	if (null != find) {
		    		setSelectedView(find);
		    	}
			}
		});
    }
    
    private void drawSelectedView(Canvas canvas) {
    	if (null != selectedView) {
    		Rect rect = new Rect();
    		Paint paint = new Paint();
    		paint.setStrokeWidth(2.f);

    		paint.setColor(Color.GREEN);
    		getViewRect(selectedView, rect);
    		drawRect(canvas, paint, rect);

    		paint.setColor(Color.YELLOW);
    		getViewRect2(selectedView, rect);
    		drawRect(canvas, paint, rect);

    	}
    }
    private void drawFirstFocusableView(Canvas canvas) {
    	View find = findFirstFocusableView(layout);
    	
    	if (null != find && find.equals(firstView)) {
    		//Log.i("firstView equals find");
    	} else {
    		//Log.i("firstView does not equal find");
    	}
    	
    	//Log.i("the default firstView is " + ((null == find) ? "not Found." : ("Found. view:" + find)));
    	if (null != find) {
    		Rect rect = new Rect();
    		getViewRect(find, rect);
    		
    		Paint paint = new Paint();
    		paint.setColor(Color.RED);
    		paint.setStrokeWidth(2.f);
    		
    		drawRect(canvas, paint, rect);
    	}
    }
    
    private void drawRect(Canvas canvas, Paint paint, Rect rect) {
        /*
        Log.i("draweRect...l:" + rect.left
        		+ ", t:" + rect.top 
        		+ ", r:" + rect.right 
        		+ ", b:" + rect.bottom);
        		*/
        canvas.drawLine(rect.left, rect.top, rect.right, rect.top, paint);
        canvas.drawLine(rect.left, rect.top, rect.left, rect.bottom, paint);
        canvas.drawLine(rect.right, rect.top, rect.right, rect.bottom, paint);
        canvas.drawLine(rect.left, rect.bottom, rect.right, rect.bottom, paint);
    }
    
    public View findFirstFocusableView(ViewGroup viewGroup) {
    	int[] pos = new int[2];
        int left = Integer.MAX_VALUE;
        View firstView = null;
        
        if (View.VISIBLE != viewGroup.getVisibility())
        	return null;
        
        if (viewGroup.isFocusable() && viewGroup != layout)
        	return viewGroup;

        for (int i=0; i<viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);
            if (View.VISIBLE != child.getVisibility())
                continue;
            
            if (child.isFocusable() != true) {
            	if (child instanceof ViewGroup) {
            		child = findFirstFocusableView((ViewGroup)child);
            	} else {
            		continue;
            	}
            }

            if (null != child) {
	            child.getLocationInWindow(pos);
	            if (pos[0] < left) {
	                left = pos[0];
	                firstView = child;
	            } else {
	            	continue;
	            }
            }
        }
        
        // FIXME: whatif no visible view?
        return firstView;
    }

    public View getSelectedView() {
        //return getChildAt(selectedViewIndex );
        return selectedView;
    }
    
    public void setSelectedView(View view) {
    	// Wonder if below code will work.
    	if (selectedView == view) {
    		return;
    	}

    	View prev = selectedView;
    	View curr = view;
    	selectedView = view;
    	startFocusAnimation(prev, curr);
    	
    	selectedViewIndex = indexOfAnyView(selectedView);

    	if (null != prev) {
    		prev.setSelected(false);
    		if (null != childViewSelectedListener)
    			childViewSelectedListener.OnChildViewSelected(prev, false);
    	}
    	if (!delaySelect && null != curr) {
    		curr.setSelected(true);
    		if (null != childViewSelectedListener)
    			childViewSelectedListener.OnChildViewSelected(curr, true);
    	}
    }

    protected void getViewRect(View view, Rect rect) {
        if (null == view || null == rect || !isDescentantOf(layout, view)) {
            return;
        }

        int[] l1 = new int[2];
        layout.getLocationInWindow(l1);
        int[] l2 = new int[2];
        view.getLocationInWindow(l2);
        view.getGlobalVisibleRect(rect, new Point(0, 0));

        int offsetX = l2[0] - l1[0];
        int offsetY = l2[1] - l1[1];
        
        int widthOrig = view.getWidth();
        int heightOrig = view.getHeight();
        int widthCurr = rect.width();
        int heightCurr = rect.height();
        
        float widthRatio = (float)widthCurr / (float)widthOrig;
        float heightRatio = (float)heightCurr / (float)heightOrig;
        
        int width = 0, height = 0;
        if (widthRatio > heightRatio) {
        	width = widthCurr;
        	height = (int)(heightOrig * widthRatio);
        } else {
        	height = heightCurr;
        	width = (int)(widthOrig * heightRatio);
        }

        rect.left = offsetX;
        rect.top = offsetY;
        rect.right = rect.left + width;
        rect.bottom = rect.top + height;

        if (excludePadding) {
        	rect.left += view.getPaddingLeft();
        	rect.top += view.getPaddingTop();
        	rect.right -= view.getPaddingRight();
        	rect.bottom -= view.getPaddingBottom();
        }
        
        rect.top += mFocusEdgeOffset;
        rect.left += mFocusEdgeOffset;
        rect.bottom -= mFocusEdgeOffset;
        rect.right -= mFocusEdgeOffset;
    }

    private void getViewRect2(View view, Rect rect) {
        if (null == view || null == rect) {
            return;
        }
        
        rect.left = view.getLeft();
        rect.top = view.getTop();
        rect.right = view.getRight();
        rect.bottom = view.getBottom();
    }
    

    private Rect combineRects(Rect ...rects) {
        // quick initialize
        int minLeft = rects[0].left;
        int minTop = rects[0].top;
        int maxRight = rects[0].right;
        int maxBottom = rects[0].bottom;

        // find min/max values
        for (Rect rect : rects) {
            minLeft = Math.min(minLeft, rect.left);
            minTop = Math.min(minTop, rect.top);
            maxRight = Math.max(maxRight, rect.right);
            maxBottom = Math.max(maxBottom, rect.bottom);
        }

        return new Rect(minLeft, minTop, maxRight, maxBottom);
    }

    private Rect includPadding(Rect bounds, Rect padding) {
        return new Rect(
                bounds.left - padding.left,
                bounds.top - padding.top,
                bounds.right + padding.right,
                bounds.bottom + padding.bottom);
    }

    private void scaleRect(Rect rect, float ratioX, float ratioY) {
        if (null == rect) {
            return;
        }

        int offsetX = (int)((float)(rect.right - rect.left) * (ratioX - 1f) / 2);
        int offsetY = (int)((float)(rect.bottom - rect.top) * (ratioY - 1f) / 2);

        rect.left -= offsetX;
        rect.right += offsetX;
        rect.top -= offsetY;
        rect.bottom += offsetY;
    }

    private void calculateFocusPosition(Rect focusRect, Rect fromRect, Rect toRect, float progress) {
        focusRect.left = (int)((float)(toRect.left - fromRect.left) * progress + fromRect.left);
        focusRect.top = (int)((float)(toRect.top - fromRect.top) * progress + fromRect.top);
        focusRect.right = (int)((float)(toRect.right - fromRect.right) * progress + fromRect.right);
        focusRect.bottom = (int)((float)(toRect.bottom - fromRect.bottom) * progress + fromRect.bottom);
    }

    
    private boolean isDescentantOf(View parent, View child) {
    	boolean isDescent = false;

    	if (null == parent || null == child)
    		return isDescent;

    	View view = child;
    	while (null != view.getParent()) {
    		if (!(view.getParent() instanceof View))
    			break;
    		
    		view = (View)view.getParent();
    		
    		if (view == parent) {
    			isDescent = true;
    			break;
    		}
    	}
    	return isDescent;
    }

    protected View findViewByItsSubView(View subView) {
    	if (null == subView) return null;
    	View view = subView;
    	if (isDescentantOf(layout, view)) {
    		while (null != view.getParent()) {
	    		if (!(view.getParent() instanceof View))
	    			break;
    			
	    		if (layout == view.getParent()) {
	    			return view;
	    		}
	    		
	    		view = (View) view.getParent();
    		}
    	}
    	return null;
    }

    protected View findViewToDrawFocus(View view) {
    	if (view instanceof IFocusPos) {
    		IFocusPos pos = (IFocusPos)view;
    		return pos.getFocusPosView();
    	}
    	View v = findViewByItsSubView(view);
    	if (v instanceof IFocusPos) {
    		IFocusPos pos = (IFocusPos)v;
    		return pos.getFocusPosView();
    	}
    	return view;
    }
    
    private int indexOfAnyView(View anyView) {
    	int id = -1;
    	
    	if (null == anyView)
    		return id;

    	View view = anyView;
    	if (isDescentantOf(layout, view)) {
    		while (null != view.getParent()) {
	    		if (!(view.getParent() instanceof View))
	    			break;
    			
	    		if (layout == view.getParent()) {
	    			id = layout.indexOfChild(view);
	    			break;
	    		}
	    		
	    		view = (View) view.getParent();
    		}
    	}

    	return id;
    }
    
    protected ViewGroup getLayout() {
    	return layout;
    }
    
    static class LayoutHandler extends Handler {
    	public static final int MESSAGE_REDRAW = 9000;

		private WeakReference<LayoutFocusHelper> layout;
		LayoutHandler(LayoutFocusHelper layout) {
			this.layout = new WeakReference<LayoutFocusHelper>(layout);
		}

    	public void handleMessage(android.os.Message msg) {
    		if (msg.what == MESSAGE_REDRAW) {
    			removeMessages(MESSAGE_REDRAW);
    			layout.get().layout.postInvalidate();
    		}
    	};
    };
}