/**
 * 
 */
package com.duolebo.appbase.prj.bmtv.model;

import org.json.JSONObject;

/**
 * @author zlhl
 * @date 2014年2月17日
 */
public class TVRegistrationData extends ModelBase {
	
	private static String tvid = "";
	private static String userid = "";
	private static String encrypt_key = "";

	@Override
	public boolean from(JSONObject json) {
	    if (super.from(json)) {
	        json = json.optJSONObject("response").optJSONObject("body");
	        tvid = json.optString("tvid");
	        userid = json.optString("userid");
	        encrypt_key = json.optString("encrypt_key");
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

	public static String getEncryptKey() {
		return encrypt_key;
	}

}
