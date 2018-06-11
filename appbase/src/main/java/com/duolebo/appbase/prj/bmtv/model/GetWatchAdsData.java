package com.duolebo.appbase.prj.bmtv.model;


import org.json.JSONObject;

/**
 * @author advu
 * @date 2016年11月30日
 */
public class GetWatchAdsData extends ModelBase {
	

	@Override
	public boolean from(JSONObject json) {
	    if (super.from(json)) {
	        json = json.optJSONObject("response").optJSONObject("body");
	        return true;
	    }
	    return false;
	}




}
