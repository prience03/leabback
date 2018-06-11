/**
 * 
 */
package com.duolebo.appbase.prj.bmtv.model;

import org.json.JSONObject;

/**
 * @author zlhl
 * @date 2014年6月6日
 */
public class GetAboutHtmlData extends ModelBase {
    
    private String about_url = "";

    @Override
    public boolean from(JSONObject json) {
        if (super.from(json)) {
	        json = json.optJSONObject("response").optJSONObject("body");
            about_url = json.optString("about_url");
            return true;
        }
        return false;
    }

    public String getAboutUrl() {
        return about_url;
    }
    
    

}
