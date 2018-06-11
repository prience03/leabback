package com.duolebo.tvui.utils;

import com.duolebo.tvui.BuildConfig;

public class Log {
	
	public final static boolean DEBUG = BuildConfig.DEBUG;
	public final static String TAG = "TVUI";
	
	public static void i(String tag, String message) {
		if (DEBUG)
			android.util.Log.i(TAG, tag + " " + message);
	}
	
	public static void i(String message) {
		i(TAG, message);
	}
	
	public static void d(String tag, String message) {
		if (DEBUG)
			android.util.Log.d(TAG, tag + " " + message);
	}
	
	public static void d(String message) {
		d(TAG, message);
	}
}
