/**
 * 
 */
package com.duolebo.appbase.prj.bmtv.model;

import com.duolebo.appbase.prj.Model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @author zlhl
 * @date 2014年2月17日
 */
public class GetADsData extends ModelBase {
	
	private int totalsize = 0;
	
	private ArrayList<GetADsData.Content> contentlist = new ArrayList<GetADsData.Content>();

	@Override
	public boolean from(JSONObject json) {
	    if (super.from(json)) {
	        json = json.optJSONObject("response").optJSONObject("body");
	        totalsize = json.optInt("totalsize");
	        JSONArray ja = json.optJSONArray("contentlist");
	        if (null != ja) {
	            for (int i=0; i<ja.length(); i++) {
	                JSONObject jo = ja.optJSONObject(i);
	                GetADsData.Content content = new GetADsData.Content();
	                content.from(jo);
	                contentlist.add(content);
	            }
	        }
	        return true;
	    }
	    return false;
	}
	
	public int getTotalSize() {
		return totalsize;
	}

	public ArrayList<GetADsData.Content> getContentList() {
		return contentlist;
	}

	public Content getContentByShowType(String showType) {
		
		int size = contentlist.size();
		Content content = null;
		for (int i = 0; i < size; i++) {
			content = contentlist.get(i);
			if (showType.equals(content.getShowType())) {
				return content;
			}
		}
		return null;
	}

	public static class Content extends Model {
		
		public static final String SHOW_TYPE_APP = "7";
		public static final String SHOW_TYPE_SHOP = "8";
		public static final String SHOW_TYPE_SHOW = "9";
		public static final String SHOW_TYPE_MOVIE = "10";
		public static final String SHOW_TYPE_TV = "11";
		public static final String SHOW_TYPE_EXIT = "50";
		public static final String SHOW_TYPE_PLAY_PAUSE = "51";
		public static final String SHOW_TYPE_PLAYCONTROLLER = "52";
		public static final String SHOW_TYPE_WEB = "30";
		public static final String SHOW_TYPE_VIDEO_AT_START = "90";

		private String contentid = "";
		private String show_type = "";
		private String desc = "";
		private String img_url = "";
		private String link_url = "";

		@Override
		public boolean from(JSONObject json) {
		    if (super.from(json)) {
		        contentid = json.optString("contentid");
		        show_type = json.optString("show_type");
		        desc = json.optString("desc");
		        img_url = json.optString("img_url");
		        link_url = json.optString("link_url");
		        return true;
		    }
		    return false;
		}

		public String getContentId() {
			return contentid;
		}

		public String getShowType() {
			return show_type;
		}

		public String getDesc() {
			return desc;
		}

		public String getImgUrl() {
			return img_url;
		}
		
		public String getLinkUrl() {
		    return link_url;
		}
	}

}
