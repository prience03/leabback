package com.duolebo.tvui.widget;

import java.util.List;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Property;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

public class ViewAnimatorEx extends FrameLayout {

    private static final int DURATION_ANIM = 300;

    private static AccelerateInterpolator sAccelerator = new AccelerateInterpolator();
    private static DecelerateInterpolator sDecelerator = new DecelerateInterpolator();

    public enum AnimationDirection {
        LEFT, RIGHT, UP, DOWN, CENTER
    }

    public ViewAnimatorEx(Context context) {
        super(context);
        init(context);
    }

    public ViewAnimatorEx(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ViewAnimatorEx(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        setFocusable(false);
        setFocusableInTouchMode(false);
    }

    public boolean addViewWithAnimInfo(View view, int gravity, AnimationDirection dirction) {
        return addViewWithAnimInfo(view, gravity, dirction, -1);
    }

    public boolean addViewWithAnimInfo(View view, int gravity, AnimationDirection dirction, int index) {
        /// Quick check
        if (NO_ID == view.getId()) {
            return false;
        }

        /// Ensure no identical ids.
        View existing = findViewById(view.getId());
        if (null != existing) {
            return false;
        }

        /// Initialize animation information.
        AnimInfo info = new AnimInfo();
        info.direction = dirction;
        info.gravity = gravity;
        view.setTag(info);
        view.setVisibility(View.INVISIBLE);

        /// Add View
        android.view.ViewGroup.LayoutParams lp = view.getLayoutParams();
        if (null != lp) {
            lp = new LayoutParams(lp.width, lp.height, gravity);
        } else {
            if ((gravity & Gravity.TOP) == Gravity.TOP || (gravity & Gravity.BOTTOM) == Gravity.BOTTOM) {
                lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT, gravity);
            } else {
                lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.MATCH_PARENT, gravity);
            }
        }

        if (index < 0) {
            addView(view, lp);
        } else {
            addView(view, index, lp);
        }

        return true;
    }

    private void prepareViewAnimation(View view) {
        AnimInfo info = (AnimInfo) view.getTag();

        /// Quick check.
        if (null == info) {
            return;
        }

        /// Animations have all setup.
        if (null != info.animIn && null != info.animOut) {
            return;
        }

        switch (info.direction) {
            case CENTER:
                setupViewAnimationCenter(view);
                break;
            case LEFT:
                setupViewAnimationLeft(view);
                break;
            case RIGHT:
                setupViewAnimationRight(view);
                break;
            case DOWN:
                setupViewAnimationDown(view);
                break;
            case UP:
                setupViewAnimationUp(view);
                break;
        }
    }

    @SuppressLint("NewApi")
    private void setupViewAnimationCenter(View view) {
        AnimInfo info = (AnimInfo) view.getTag();

        /// Quick check
        if (null == info) {
            return;
        }

        view.setAlpha(0f);
        setViewAnimationIn(view, View.ALPHA, 1f, 0l);
        setViewAnimationOut(view, View.ALPHA, 0f, 0l);
    }

    @SuppressLint("NewApi")
    private void setupViewAnimationLeft(View view) {
        AnimInfo info = (AnimInfo) view.getTag();

        /// Quick check
        if (null == info) {
            return;
        }

        if ((info.gravity & Gravity.LEFT) == Gravity.LEFT) {

            view.setTranslationX(getMeasuredWidth());
            setViewAnimationIn(view, View.TRANSLATION_X, 0);
            setViewAnimationOut(view, View.TRANSLATION_X, getMeasuredWidth());

        } else if ((info.gravity & Gravity.RIGHT) == Gravity.RIGHT) {

            view.setTranslationX(view.getMeasuredWidth());
            setViewAnimationIn(view, View.TRANSLATION_X, 0);
            setViewAnimationOut(view, View.TRANSLATION_X, view.getMeasuredWidth());

        }
    }

    @SuppressLint("NewApi")
    private void setupViewAnimationRight(View view) {
        AnimInfo info = (AnimInfo) view.getTag();

        /// Quick check
        if (null == info) {
            return;
        }

        if ((info.gravity & Gravity.LEFT) == Gravity.LEFT) {

            view.setTranslationX(-view.getMeasuredWidth());
            setViewAnimationIn(view, View.TRANSLATION_X, 0);
            setViewAnimationOut(view, View.TRANSLATION_X, -view.getMeasuredWidth());

        } else if ((info.gravity & Gravity.RIGHT) == Gravity.RIGHT) {

            view.setTranslationX(-getMeasuredWidth());
            setViewAnimationIn(view, View.TRANSLATION_X, 0);
            setViewAnimationOut(view, View.TRANSLATION_X, -getMeasuredWidth());

        }
    }

    @SuppressLint("NewApi")
    private void setupViewAnimationUp(View view) {
        AnimInfo info = (AnimInfo) view.getTag();

        /// Quick check
        if (null == info) {
            return;
        }

        if ((info.gravity & Gravity.TOP) == Gravity.TOP) {

            view.setTranslationY(getMeasuredHeight());
            setViewAnimationIn(view, View.TRANSLATION_Y, 0);
            setViewAnimationOut(view, View.TRANSLATION_Y, getMeasuredHeight());

        } else if ((info.gravity & Gravity.BOTTOM) == Gravity.BOTTOM) {

            view.setTranslationY(view.getMeasuredHeight());
            setViewAnimationIn(view, View.TRANSLATION_Y, 0);
            setViewAnimationOut(view, View.TRANSLATION_Y, view.getMeasuredHeight());

        }

    }

    @SuppressLint("NewApi")
    private void setupViewAnimationDown(View view) {
        AnimInfo info = (AnimInfo) view.getTag();

        /// Quick check
        if (null == info) {
            return;
        }

        if ((info.gravity & Gravity.TOP) == Gravity.TOP) {

            view.setTranslationY(-view.getMeasuredHeight());
            setViewAnimationIn(view, View.TRANSLATION_Y, 0);
            setViewAnimationOut(view, View.TRANSLATION_Y, -view.getMeasuredHeight());

        } else if ((info.gravity & Gravity.BOTTOM) == Gravity.BOTTOM) {

            view.setTranslationY(-getMeasuredHeight());
            setViewAnimationIn(view, View.TRANSLATION_Y, 0);
            setViewAnimationOut(view, View.TRANSLATION_Y, -getMeasuredHeight());

        }
    }

    private void setViewAnimationIn(final View view, Property<?, Float> property, float value) {
        setViewAnimationIn(view, property, value, DURATION_ANIM);
    }

    @SuppressLint("NewApi")
    private void setViewAnimationIn(final View view, Property<?, Float> property, float value, long duration) {
        AnimInfo info = (AnimInfo) view.getTag();

        /// Quick check
        if (null == info) {
            return;
        }

        PropertyValuesHolder pvh = PropertyValuesHolder.ofFloat(property, value);
        info.animIn = ObjectAnimator.ofPropertyValuesHolder(view, pvh);
        info.animIn.setDuration(duration);
        info.animIn.setInterpolator(sDecelerator);
        info.animIn.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                view.setVisibility(View.VISIBLE);
                if (view instanceof IViewAnimatorExChildView) {
                    IViewAnimatorExChildView child =
                            (IViewAnimatorExChildView) view;
                    child.onShow(ViewAnimatorEx.this);
                }
            }

        });
    }

    private void setViewAnimationOut(final View view, Property<?, Float> property, float value) {
        setViewAnimationOut(view, property, value, DURATION_ANIM);
    }

    @SuppressLint("NewApi")
    private void setViewAnimationOut(final View view, Property<?, Float> property, float value, long duration) {
        AnimInfo info = (AnimInfo) view.getTag();

        /// Quick check
        if (null == info) {
            return;
        }

        PropertyValuesHolder pvh = PropertyValuesHolder.ofFloat(property, value);
        info.animOut = ObjectAnimator.ofPropertyValuesHolder(view, pvh);
        info.animOut.setDuration(DURATION_ANIM);
        info.animOut.setInterpolator(sAccelerator);
        info.animOut.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (view instanceof IViewAnimatorExChildView) {
                    IViewAnimatorExChildView child =
                            (IViewAnimatorExChildView) view;
                    child.onHide(ViewAnimatorEx.this);
                }
                view.setVisibility(View.INVISIBLE);
            }

        });
    }

    @SuppressLint("NewApi")
    public void showViews(int... viewIds) {
        View view;
        for (int id : viewIds) {
            view = findViewById(id);
            if (null != view) {
                prepareViewAnimation(view);
                AnimInfo info = (AnimInfo) view.getTag();
                view.setVisibility(View.VISIBLE);
                if (null != info && null != info.animIn) {
                    info.animOut.cancel();
                    info.animIn.start();
                }
            }
        }
    }

    public void showViews(List<Integer> ids) {
        View view;
        for (int id : ids) {
            view = findViewById(id);
            if (null != view) {
                prepareViewAnimation(view);
                AnimInfo info = (AnimInfo) view.getTag();
                view.setVisibility(View.VISIBLE);
                if (null != info && null != info.animIn) {
                    info.animOut.cancel();
                    info.animIn.start();
                }
            }
        }
    }

    public void setVisibility(boolean show, int... viewIds) {
        if (show) {
            showViews(viewIds);
        } else {
            hideViews(viewIds);
        }
    }

    @SuppressLint("NewApi")
    public void hideViews(int... viewIds) {
        View view;
        for (int id : viewIds) {
            view = findViewById(id);
            if (null != view) {
                prepareViewAnimation(view);
                AnimInfo info = (AnimInfo) view.getTag();
                if (null != info && null != info.animIn) {
                    info.animIn.cancel();
                    info.animOut.start();
                } else {
                    view.setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    @SuppressLint("NewApi")
    public void hideAll() {
        View view;
        for (int i = 0; i < getChildCount(); i++) {
            view = getChildAt(i);
            if (view.getVisibility() == View.VISIBLE) {
                prepareViewAnimation(view);
                AnimInfo info = (AnimInfo) view.getTag();
                if (null != info && null != info.animIn) {
                    info.animIn.cancel();
                    info.animOut.start();
                } else {
                    view.setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    @SuppressLint("NewApi")
    public void hideAllExcept(int viewId) {
        View view;
        for (int i = 0; i < getChildCount(); i++) {
            view = getChildAt(i);
            if (view.getId() == viewId)
                continue;

            if (view.getVisibility() == View.VISIBLE) {
                prepareViewAnimation(view);
                AnimInfo info = (AnimInfo) view.getTag();
                if (null != info && null != info.animIn) {
                    info.animIn.cancel();
                    info.animOut.start();
                } else {
                    view.setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    @SuppressLint("NewApi")
    public void hideAllExcept(int... viewIds) {
        View view;
        int childCount = getChildCount();
        int length = viewIds.length;

        boolean same;

        for (int i = 0; i < childCount; i++) {
            same = false;

            view = getChildAt(i);
            for (int j = 0; j < length; j++) {
                if (view.getId() == viewIds[j]) {
                    same = true;
                    break;
                }
            }
            if (same) {
                continue;
            }

            if (view.getVisibility() == View.VISIBLE) {
                prepareViewAnimation(view);
                AnimInfo info = (AnimInfo) view.getTag();
                if (null != info && null != info.animIn) {
                    info.animIn.cancel();
                    info.animOut.start();
                } else {
                    view.setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    @SuppressLint("NewApi")
    public void hideAllExcept(List<Integer> viewIds) {
        View view;
        int childCount = getChildCount();
        int length = viewIds.size();

        boolean same;

        for (int i = 0; i < childCount; i++) {
            same = false;

            view = getChildAt(i);
            for (int j = 0; j < length; j++) {
                if (view.getId() == viewIds.get(j)) {
                    same = true;
                    break;
                }
            }
            if (same) {
                continue;
            }

            if (view.getVisibility() == View.VISIBLE) {
                prepareViewAnimation(view);
                AnimInfo info = (AnimInfo) view.getTag();
                if (null != info && null != info.animIn) {
                    info.animIn.cancel();
                    info.animOut.start();
                } else {
                    view.setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    private class AnimInfo {
        public int gravity;
        public AnimationDirection direction;
        public ObjectAnimator animIn;
        public ObjectAnimator animOut;
    }


    public void clean() {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            view.setTag(null);
        }
    }

    public interface IViewAnimatorExChildView {
        void onShow(ViewAnimatorEx mask);

        void onHide(ViewAnimatorEx mask);
    }
}