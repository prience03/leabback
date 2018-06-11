/**
 * 
 */
package com.duolebo.appbase.prj.bmtv.model;

import android.util.Log;

import org.json.JSONObject;

/**
 * @author advu
 * @date 2016年11月14日
 *
 *  *    注册 登录 公用一个 ，后端去判断
 */
public class GetRegisterData extends ModelBase {
	
	private  String  mStatus = "";

	@Override
	public boolean from(JSONObject json) {
	    if (super.from(json)) {
			Log.d("GetRegisterData" , json.toString());
	        json = json.optJSONObject("response").optJSONObject("body");
			mStatus = json.optString("status");
	        return true;
	    }
	    return false;
	}


	public String getmStatus() {
		return mStatus;
	}

	public void setmStatus(String mStatus) {
		this.mStatus = mStatus;
	}
}
