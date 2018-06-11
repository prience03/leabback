package com.duolebo.appbase.log;

import com.duolebo.appbase.BuildConfig;

public class DefaultFactory implements ILogFactory {
    
    private static final Log log = new Log();
	private static final String TAG = "AppBase";
//    private static final boolean DEBUG = true;
	private static final boolean DEBUG = BuildConfig.DEBUG;

    @Override
    public ILog createLog() {
        return log;
    }

    
    public static class Log implements ILog {

        @Override
        public void i(String tag, String message) {
            if (DEBUG)
                android.util.Log.i(TAG, tag + " " + message);
        }

        @Override
        public void w(String tag, String message) {
            if (DEBUG)
                android.util.Log.w(TAG, tag + " " + message);
        }

        @Override
        public void d(String tag, String message) {
            if (DEBUG)
                android.util.Log.d(TAG, tag + " " + message);
        }

        @Override
        public void v(String tag, String message) {
            if (DEBUG)
                android.util.Log.v(TAG, tag + " " + message);
        }

        @Override
        public void e(String tag, String message) {
            if (DEBUG)
                android.util.Log.e(TAG, tag + " " + message);
        }
        
    }
}
