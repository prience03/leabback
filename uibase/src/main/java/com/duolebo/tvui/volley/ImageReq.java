/**
 * 
 */

package com.duolebo.tvui.volley;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.duolebo.tvui.utils.Log;
import com.duolebo.tvui.volley.ForceCachedImageLoader.ImageListener;

/**
 * @author zlhl
 * @date 2014年6月20日
 */
public class ImageReq {
    
    private static ForceCachedImageLoader imageLoader = null;
    
    public static void get(Context context, String url, ImageListener listener) {
    	get(context, url, listener, 0, 0);
    }

    public static void get(Context context, String url, ImageListener listener, int maxWidth, int maxHeight) {
        Log.i("ImageReq", "ImageReq.get(" + url + ", maxWidth:" + maxWidth + ", maxHeight:" + maxHeight);
        if (null == imageLoader) {
            RequestQueue requestQueue = VolleyClient.getInstance().createRequestQueue(context);
            imageLoader = new ForceCachedImageLoader(context, requestQueue, ForceCachedImageCache.getInstance());
        }
        imageLoader.get(url, listener, maxWidth, maxHeight);
    }
    
}
