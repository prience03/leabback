/**
 * 
 */
package com.duolebo.appbase.prj.bmtv.model;

import org.json.JSONObject;

/**
 * @author zlhl
 * @date 2014年2月17日
 */
public class GetAppDownloadUrlData extends ModelBase {
	
	private String download_url = "";
	private String md5="";
	
	@Override
	public boolean from(JSONObject json) {
	    if (super.from(json)) {
	        json = json.optJSONObject("response").optJSONObject("body");
	        JSONObject jo = json.optJSONObject("content");
	        if (null == jo) return false;
	        download_url = jo.optString("download_url");
	        md5 = jo.optString("md5");
	        return true;
	    }
	    return false;
	}

	public String getDownloadUrl() {
		return download_url;
	}
	
	public String getMd5(){
		return md5;
	}
}
