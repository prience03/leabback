/**
 * 
 */
package com.duolebo.appbase.utils;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import android.content.Intent;
import android.os.Bundle;


/**
 * @author zlhl
 * @date 2014年3月14日
 */
public class Log {
	
	public static void i(String tag, String message) {
		com.duolebo.appbase.log.Log.getInstance().i(tag, message);
	}

	public static void w(String tag, String message) {
		com.duolebo.appbase.log.Log.getInstance().w(tag, message);
	}

	public static void d(String tag, String message) {
		com.duolebo.appbase.log.Log.getInstance().d(tag, message);
	}
	
	public static void i(String tag, Map<String, String> map) {
	    for (Map.Entry<String, String> item : map.entrySet()) {
	        i(tag, item.getKey() + "=" + item.getValue());
	    }
	}
	
	public static void dumpIntent(String tag, Intent i){
	    Bundle bundle = i.getExtras();
	    if (bundle != null) {
	        Set<String> keys = bundle.keySet();
	        Iterator<String> it = keys.iterator();
	        i(tag, "Dumping Intent start");
	        while (it.hasNext()) {
	            String key = it.next();
	            i(tag,"[" + key + "=" + bundle.get(key)+"]");
	        }
	        i(tag,"Dumping Intent end");
	    }
	}

}
