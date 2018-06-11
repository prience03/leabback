/**
 * 
 */
package com.duolebo.appbase.prj.bmtv.model;

import org.json.JSONObject;

/**
 * @author zlhl
 * @date 2014年2月17日
 */
public class TVLoginData extends ModelBase {
	
	private static String tvid = "";
	private static String userid = "";

	@Override
	public boolean from(JSONObject json) {
	    if (super.from(json)) {
	        json = json.optJSONObject("response").optJSONObject("body");
	        tvid = json.optString("tvid");
	        userid = json.optString("userid");
	        return true;
	    }
	    return false;
	}

	public static String getTvid() {
		return tvid;
	}

	public static String getUserid() {
		return userid;
	}

}
