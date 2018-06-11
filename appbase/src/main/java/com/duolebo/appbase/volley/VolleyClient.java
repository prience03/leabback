package com.duolebo.appbase.volley;

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
import com.duolebo.appbase.R;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;

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
            File cacheDir = new File(context.getCacheDir(), "AppBase");

            String userAgent = "AppBase/0";
            try {
                String packageName = context.getPackageName();
                PackageInfo info = context.getPackageManager().getPackageInfo(packageName, 0);
                userAgent = packageName + "/" + info.versionCode;
            } catch (NameNotFoundException e) {
                e.printStackTrace();
            }

            HttpStack stack = null;
            if (Build.VERSION.SDK_INT >= 9) {
                try {
                    InputStream in = context.getResources().openRawResource(R.raw.mystore);
                    KeyStore ks = KeyStore.getInstance("BKS");
                    ks.load(in, "ez24get".toCharArray());
                    in.close();

                    String algorithm = TrustManagerFactory.getDefaultAlgorithm();
                    TrustManagerFactory tmf = TrustManagerFactory.getInstance(algorithm);
                    tmf.init(ks);

                    SSLContext sc = SSLContext.getInstance("TLS");
                    sc.init(null, tmf.getTrustManagers(), null);

                    HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            return true;
                        }
                    });

                    stack = new HurlStack(null, sc.getSocketFactory());
                } catch (KeyStoreException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (CertificateException e) {
                    e.printStackTrace();
                } catch (KeyManagementException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (null == stack) {
                        stack = new HurlStack();
                    }
                }
            } else {
                // Prior to Gingerbread, HttpUrlConnection was unreliable.
                // See:
                // http://android-developers.blogspot.com/2011/09/androids-http-clients.html
                stack = new HttpClientStack(AndroidHttpClient.newInstance(userAgent));
            }

            Network network = new BasicNetwork(stack);
            requestQueue = new RequestQueue(new DiskBasedCache(cacheDir, 10 * 1024 * 1024), network, 2);
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
    
    public void clearCache(String key) {
        if (null != requestQueue) {
            Cache cache = requestQueue.getCache();
            if (null != cache) {
                cache.remove(key);
            }
        }
    }

    public boolean hasCache(String key) {
        if (null != requestQueue) {
            Cache cache = requestQueue.getCache();
            if (null != cache) {
                Cache.Entry entry = cache.get(key);
                if (null != entry) {
                    return System.currentTimeMillis() < entry.ttl;
                }
            }
        }
        return false;
    }

    public boolean cancleTag(Object tag) {
        if (null != requestQueue) {
            requestQueue.cancelAll(tag);
        }
        return false;
    }
    
    private void clearCacheIfNecessary(Context context) {
    	SharedPreferences sp = context.getSharedPreferences("AppBase", 0);
    	long timeStamp = sp.getLong("timeStamp", 0l);
    	if (System.currentTimeMillis() < timeStamp) {
    		clearCache();
    	}
    	
    	SharedPreferences.Editor e = sp.edit();
    	e.putLong("timeStamp", System.currentTimeMillis());
    	e.commit();
    }

}
