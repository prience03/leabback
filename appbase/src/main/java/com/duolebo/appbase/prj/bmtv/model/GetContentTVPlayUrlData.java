/**
 * 
 */
package com.duolebo.appbase.prj.bmtv.model;

import com.duolebo.appbase.prj.bmtv.model.GetContentListData.Content.DetailInfo;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author zlhl
 * @date 2014年2月17日
 */
public class GetContentTVPlayUrlData extends ModelBase {
	
	private DetailInfo tv_play_url = null;
    private String hits;

    @Override
	public boolean from(JSONObject json) {
	    if (super.from(json)) {
			json = json.optJSONObject("response").optJSONObject("body");
	        String tv_play_url_str = json.optString("tv_play_url");
            hits = json.optString("hits");
	        try {
	            DetailInfo tmp = new DetailInfo();
                tmp.from(new JSONObject(tv_play_url_str));
                tv_play_url = tmp;
            } catch (JSONException e) {
                e.printStackTrace();
            }
	        return true;
	    }
	    return false;
	}

	public DetailInfo getTvPlayUrl() {
		return tv_play_url;
	}

    public String getHits() {
        return hits;
    }
}
