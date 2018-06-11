/**
 * 
 */
package com.duolebo.appbase.prj.bmtv.model;

import org.json.JSONObject;

/**
 *
 * @author wang
 * created at 2017/3/29 18:09
 */
public class GetTokenData extends ModelBase {

    private String user_token = "";
	private static String tvid = "";
	private static String userid = "";
	private static String encrypt_key = "";

	@Override
	public boolean from(JSONObject json) {
	    if (super.from(json)) {
	        json = json.optJSONObject("response").optJSONObject("body");
	        userid = json.optString("userid");
	        tvid = json.optString("tvid");
	        user_token = json.optString("user_token");
			encrypt_key = json.optString("encrypt_key");

	        return true;
	    }
	    return false;
	}

	public String getUser_token() {
		return user_token;
	}

	public static String getTvid() {
		return tvid;
	}

	public static String getUserid() {
		return userid;
	}

	public static String getEncrypt_key() {
		return encrypt_key;
	}
}
