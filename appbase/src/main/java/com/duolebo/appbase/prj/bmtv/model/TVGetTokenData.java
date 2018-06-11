/**
 * 
 */
package com.duolebo.appbase.prj.bmtv.model;

import org.json.JSONObject;

/**
 * @author zlhl
 * @date 2014年2月17日
 */
public class TVGetTokenData extends ModelBase {

    private String userid = "";
    private String tvid = "";
    private String user_token = "";

	@Override
	public boolean from(JSONObject json) {
	    if (super.from(json)) {
	        json = json.optJSONObject("response").optJSONObject("body");
	        userid = json.optString("userid");
	        tvid = json.optString("tvid");
	        user_token = json.optString("user_token");
	        return true;
	    }
	    return false;
	}

	public String getUserid() {
		return userid;
	}

	public String getTvid() {
		return tvid;
	}

	public String getUserToken() {
		return user_token;
	}

}
