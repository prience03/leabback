package com.duolebo.appbase.cache;

import android.content.Context;

/**
 * @author zlhl
 * @date 2014年4月25日
 */
public interface ICacheable {

    void setCacheEnabled(boolean enable);

    Context getContext();

    /**
     * provide a stable key stirng.
     *
     * @return
     */
    String getStableKey();

    /**
     * set when to expire.
     *
     * @return milliseconds since January 1, 1970 00:00:00.0 UTC.
     */
    long getExpire();


    /**
     * delete the cached data.
     */
    void expunge();


    /**
     * check if it has cached.
     *
     * @return
     */
    boolean hasCached();
}
