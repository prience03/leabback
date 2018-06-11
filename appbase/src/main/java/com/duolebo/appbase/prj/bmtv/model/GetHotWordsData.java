/**
 * 
 */
package com.duolebo.appbase.prj.bmtv.model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.text.TextUtils;

import com.duolebo.appbase.prj.bmtv.model.GetContentListData.Content;

/**
 * @author zlhl
 * @date 2014年2月17日
 */
public class GetHotWordsData extends ModelBase {
	
	private String[] hotWordArr = null;

	private ArrayList<Content> recommend_list = new ArrayList<Content>();
	private ArrayList<Content> hot_list = new ArrayList<Content>();

	@Override
	public boolean from(JSONObject json) {
	    if (super.from(json)) {
	        json = json.optJSONObject("response").optJSONObject("body");
	        String hotwords = json.optString("hotwords");
	        if (!TextUtils.isEmpty(hotwords)) {
	            hotWordArr = hotwords.split(",");
	        }

	        JSONArray ja = json.optJSONArray("recommend_list");
			if (null != ja) {
				for (int i = 0; i < ja.length(); i++) {
					JSONObject jo = ja.optJSONObject(i);
					Content content = new Content();
					content.from(jo);
					recommend_list.add(content);
				}
			}

			JSONArray ja_hot = json.optJSONArray("first_List");
			if (null != ja_hot) {
				for (int i = 0; i < ja_hot.length(); i++) {
					JSONObject jo = ja_hot.optJSONObject(i);
					Content content = new Content();
					content.from(jo);
					hot_list.add(content);
				}
			}
	        return true;
	    }
	    return false;
	}

	public String[] getHotWordArr() {
		return hotWordArr;
	}

	public ArrayList<Content> getRecommendList() {
		return recommend_list;
	}

	public ArrayList<Content> getHot_list() {
		return hot_list;
	}
}
