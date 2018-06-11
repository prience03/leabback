package com.duolebo.tvui.volley;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.LruCache;

import com.duolebo.tvui.utils.Log;
import com.duolebo.tvui.volley.ForceCachedImageLoader.BitmapDrawableCache;

/**
 * @author zlhl
 * @date 2014年6月24日
 */
@SuppressLint("NewApi")
public class ForceCachedImageCache implements BitmapDrawableCache {
    private final static String TAG = "ForceCachedImageCache";

    private int cacheSize;
    //TODO: chanage lru cache to fifo cache.
    private LruCache<String, BitmapDrawable> bitmapDrawableCache;
    private static final int maxSize = 4 * 1024;
    
    private ForceCachedImageCache() {
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        cacheSize = maxMemory / 8;
        cacheSize = Math.min(cacheSize, maxSize);

        Log.i(TAG, "cacheSize:" + cacheSize);

        bitmapDrawableCache = new LruCache<String, BitmapDrawable>(cacheSize) {
            @Override
            protected int sizeOf(String key, BitmapDrawable drawable) {
                Bitmap bitmap = drawable.getBitmap();
                if (null == bitmap) return 1;

                int bitmapSize = bitmap.getByteCount() / 1024;
                return 0 == bitmapSize ? 1 : bitmapSize;
            }

            @Override
            protected void entryRemoved(boolean evicted, String key, BitmapDrawable oldValue,
                    BitmapDrawable newValue) {
                if (oldValue instanceof RecyclingBitmapDrawable) {
                    ((RecyclingBitmapDrawable)oldValue).setIsCached(false);
                }

                Log.i(TAG, "cache --- size:" + bitmapDrawableCache.size() + ", key:" + key);
            }
        };
    }

    private static ForceCachedImageCache instance = new ForceCachedImageCache();
    public static ForceCachedImageCache getInstance() {
        return instance;
    }

    @Override
    public BitmapDrawable getBitmapDrawable(String url) {
        return bitmapDrawableCache.get(url);
    }

    @Override
    public void putBitmapDrawable(String url, BitmapDrawable drawable) {
        if (drawable instanceof RecyclingBitmapDrawable) {
            ((RecyclingBitmapDrawable) drawable).setIsCached(true);
        }
        bitmapDrawableCache.put(url, drawable);

        Log.i(TAG, "cache +++ size:" + bitmapDrawableCache.size() + ", key:" + url);
    }
    
}
