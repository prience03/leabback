package com.duolebo.tvui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * Created by Nicky on 16/5/30.
 */
public class RoundedImageView extends ImageView implements UIRounded.RoundedView {

    public RoundedImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    public RoundedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public RoundedImageView(Context context) {
        super(context);
        init(null);

    }

    UIRounded UIRounded;
    private void init(AttributeSet attrs){
        UIRounded = new UIRounded(this, attrs);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // we have to remove the hardware acceleration if we want the clip
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        UIRounded.draw(canvas);
    }

    @Override
    public void setRoundSize(int size) {
        UIRounded.setRoundSize(size);
    }

    @Override
    public void drawSuper(Canvas canvas) {
        super.draw(canvas);
    }
    public UIRounded getUIRounded() {
        return UIRounded;
    }


//    @Override
//    public ViewParent invalidateChildInParent(int[] location, Rect dirty) {
//        invalidate();
//        return super.invalidateChildInParent(location, dirty);
//    }
//    @Override
//    public void childDrawableStateChanged(View child) {
//        invalidate();
//        super.childDrawableStateChanged(child);
//    }
    @Override
    public void refreshDrawableState() {
        super.refreshDrawableState();
        invalidate();
    }
}