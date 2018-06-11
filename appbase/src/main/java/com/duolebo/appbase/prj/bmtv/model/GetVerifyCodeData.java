/**
 * 
 */
package com.duolebo.appbase.prj.bmtv.model;

import org.json.JSONObject;

/**
 * @author advu
 * @date 2016年11月14日
 */
public class GetVerifyCodeData extends ModelBase {
	
	private  String verify_status = "";

	@Override
	public boolean from(JSONObject json) {
	    if (super.from(json)) {
	        json = json.optJSONObject("response").optJSONObject("body");
			verify_status = json.optString("status");
	        return true;
	    }
	    return false;
	}

	public String getVerify_code() {
		return verify_status;
	}

	public void setVerify_code(String verify_code) {
		this.verify_status = verify_code;
	}
}
