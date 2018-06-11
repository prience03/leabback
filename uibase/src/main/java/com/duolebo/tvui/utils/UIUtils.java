/**
 * 
 */
package com.duolebo.tvui.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

/**
 * @author zlhl
 * @date 2014年4月2日
 */
public class UIUtils {

    static public int getPixelFromDpi(Context context, int dpi) {
        Resources res = context.getResources();
        int id = res.getIdentifier("d_" + dpi + "dp", "dimen", context.getPackageName());
        if (0 != id) {
            return res.getDimensionPixelSize(id); 
        }
        return dpi;
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    // via http://developer.android.com/intl/zh-cn/training/displaying-bitmaps/load-bitmap.html
    //
    // This method makes it easy to load a bitmap of arbitrarily large size into an ImageView
    // that displays a 100x100 pixel thumbnail, as shown in the following example code:
    //
    //        mImageView.setImageBitmap(
    //              decodeSampledBitmapFromResource(getResources(), R.id.myimage, 100, 100));
    //
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static boolean isDescentantOf(View parent, View child) {
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
}
