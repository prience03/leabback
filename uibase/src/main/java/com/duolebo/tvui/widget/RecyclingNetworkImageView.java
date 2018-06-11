package com.duolebo.tvui.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.duolebo.tvui.volley.ForceCachedImageCache;
import com.duolebo.tvui.volley.ForceCachedImageLoader;
import com.duolebo.tvui.volley.RecyclingImageView;
import com.duolebo.tvui.volley.VolleyClient;

/**
 * Created by zlhl on 2015/4/1.
 */
public class RecyclingNetworkImageView extends RecyclingImageView {

    private static ForceCachedImageLoader imageLoader = null;
    private ForceCachedImageLoader.ImageContainer imageContainer;
    private int defaultImageId;
    private int errorImageId;
    private Observer observer;
    private String url;
    private boolean smoothTransitionBetweenImages = false;

    public void setSmoothTransitionBetweenImages(boolean smooth) {
        smoothTransitionBetweenImages = smooth;
    }

    public interface Observer {
        void onNetworkImageLoaded(View view, boolean succeed, Drawable drawable);
    }

    public RecyclingNetworkImageView(Context context) {
        super(context);
        init(context);
    }

    public RecyclingNetworkImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RecyclingNetworkImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    synchronized private void init(Context context) {
        if (null == imageLoader) {
            RequestQueue requestQueue = VolleyClient.getInstance().createRequestQueue(context);
            imageLoader = new ForceCachedImageLoader(context, requestQueue, ForceCachedImageCache.getInstance());
        }
    }

    @Override
    public void setImageURI(Uri uri) {
        //super.setImageURI(uri);
        setImageUrl(uri.toString());
    }

    public void setImageUrl(String url) {
        this.url = url;
        // The URL has potentially changed. See if we need to load it.
        loadImageIfNecessary(false);
    }

    public void setImageUrl(String url, Observer observer) {
        this.observer = observer;
        setImageUrl(url);
    }

    public void setDefaultImageResId(int defaultImage) {
        this.defaultImageId = defaultImage;
    }

    public void setErrorImageResId(int errorImage) {
        this.errorImageId = errorImage;
    }

    void loadImageIfNecessary(final boolean isInLayoutPass) {
        int width = getWidth();
        int height = getHeight();
        ScaleType scaleType = getScaleType();

        boolean wrapWidth = false, wrapHeight = false;
        if (getLayoutParams() != null) {
            wrapWidth = getLayoutParams().width == LayoutParams.WRAP_CONTENT;
            wrapHeight = getLayoutParams().height == LayoutParams.WRAP_CONTENT;
        }

        // if the view's bounds aren't known yet, and this is not a wrap-content/wrap-content
        // view, hold off on loading the image.
        boolean isFullyWrapContent = wrapWidth && wrapHeight;
        if (width == 0 && height == 0 && !isFullyWrapContent) {
            return;
        }

        // if the URL to be loaded in this view is empty, cancel any old requests and clear the
        // currently loaded image.
        if (TextUtils.isEmpty(url)) {
            if (imageContainer != null) {
                imageContainer.cancelRequest();
                imageContainer = null;
            }
            setDefaultImageOrNull();
            return;
        }

//        // if there was an old request in this view, check if it needs to be canceled.
        if (imageContainer != null && imageContainer.getRequestUrl() != null) {
            if (imageContainer.getRequestUrl().equals(url)) {
                // if the request is from the same URL, return.
                return;
            } else {
                // if there is a pre-existing request, cancel it if it's fetching a different URL.
                imageContainer.cancelRequest();
                if (!smoothTransitionBetweenImages) {
                    setDefaultImageOrNull();
                }
            }
        }

        // Calculate the max image width / height to use while ignoring WRAP_CONTENT dimens.
        int maxWidth = wrapWidth ? 0 : width;
        int maxHeight = wrapHeight ? 0 : height;

        // The pre-existing content of this view didn't match the current URL. Load the new image
        // from the network.
        ForceCachedImageLoader.ImageContainer newContainer = imageLoader.get(url,
                new ForceCachedImageLoader.ImageListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (errorImageId != 0) {
                            setImageResource(errorImageId);
                            if (null != observer) {
                                observer.onNetworkImageLoaded(
                                        RecyclingNetworkImageView.this,
                                        false, null);
                            }
                        }
                    }

                    @Override
                    public void onResponse(final ForceCachedImageLoader.ImageContainer response, boolean isImmediate) {
                        // If this was an immediate response that was delivered inside of a layout
                        // pass do not set the image immediately as it will trigger a requestLayout
                        // inside of a layout. Instead, defer setting the image by posting back to
                        // the main thread.
//                        if (isImmediate && isInLayoutPass) {
//                            post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    onResponse(response, false);
//                                }
//                            });
//                            return;
//                        }

                        if (response.getBitmapDrawable() != null) {
                            setImageDrawable(response.getBitmapDrawable());
                        } else if (defaultImageId != 0) {
                            setImageResource(defaultImageId);
                        }

//                        if (null != observer) {
//                            observer.onNetworkImageLoaded(
//                                    RecyclingNetworkImageView.this,
//                                    null != response.getBitmapDrawable() && null != response.getBitmapDrawable().getBitmap(),
//                                    response.getBitmapDrawable());
//                        }
                    }
                }, maxWidth, maxHeight, scaleType);

        // update the ImageContainer to be the new bitmap container.
        imageContainer = newContainer;
    }

    private void setDefaultImageOrNull() {
        if(defaultImageId != 0) {
            setImageResource(defaultImageId);
        } else {
            //setImageBitmap(null);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        loadImageIfNecessary(true);
    }

    @Override
    protected void onDetachedFromWindow() {
        if (imageContainer != null) {
            // If the view was bound to an image request, cancel it and clear
            // out the image from the view.
            imageContainer.cancelRequest();
            setImageDrawable(null);
            // also clear out the container so we can reload the image if necessary.
            imageContainer = null;
        }
        super.onDetachedFromWindow();
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        invalidate();
    }

}
