package com.duolebo.tvui.volley;

import java.io.File;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.http.AndroidHttpClient;
import android.os.Build;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.HurlStack;

/**
 * @author zlhl
 * @date 2014年6月20日
 */
public class VolleyClient {

    private RequestQueue requestQueue = null;
    private static VolleyClient instance = new VolleyClient();
    private VolleyClient() {}

    public static VolleyClient getInstance() {
        return instance;
    }

    @SuppressLint("NewApi")
    public synchronized RequestQueue createRequestQueue(Context context) {
        if (null == requestQueue) {
            File cacheDir = new File(context.getCacheDir(), "TVUI");

            String userAgent = "TVUI/0";
            try {
                String packageName = context.getPackageName();
                PackageInfo info = context.getPackageManager().getPackageInfo(packageName, 0);
                userAgent = packageName + "/" + info.versionCode;
            } catch (NameNotFoundException e) {
                e.printStackTrace();
            }

            HttpStack stack = null;
            if (Build.VERSION.SDK_INT >= 9) {
                stack = new HurlStack(null, null);
            } else {
                // Prior to Gingerbread, HttpUrlConnection was unreliable.
                // See:
                // http://android-developers.blogspot.com/2011/09/androids-http-clients.html
                stack = new HttpClientStack(AndroidHttpClient.newInstance(userAgent));
            }

            Network network = new BasicNetwork(stack);
            requestQueue = new RequestQueue(new DiskBasedCache(cacheDir, 30 * 1024 * 1024), network, 2);
            requestQueue.start();
            
            clearCacheIfNecessary(context);
        }

        return requestQueue;
    }
    
    public void clearCache() {
        if (null != requestQueue) {
            Cache cache = requestQueue.getCache();
            if (null != cache) {
                cache.clear();
            }
        }
    }

    private void clearCacheIfNecessary(Context context) {
    	SharedPreferences sp = context.getSharedPreferences("TVUI", 0);
    	long timeStamp = sp.getLong("timeStamp", 0l);
    	if (System.currentTimeMillis() < timeStamp) {
    		clearCache();
    	}
    	
    	SharedPreferences.Editor e = sp.edit();
    	e.putLong("timeStamp", System.currentTimeMillis());
    	e.commit();
    }

}
