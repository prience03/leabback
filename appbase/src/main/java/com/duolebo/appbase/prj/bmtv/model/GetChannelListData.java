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
public class GetChannelListData extends ModelBase {
	
	private ArrayList<Channel> channellist = new ArrayList<Channel>();

	@Override
	public boolean from(JSONObject json) {
	    if (super.from(json)) {
	        json = json.optJSONObject("response").optJSONObject("body");
	        JSONArray ja = json.optJSONArray("channellist");
	        if (null == ja)
	            return false;

	        for (int i = 0; i < ja.length(); i++) {
	            JSONObject jo = ja.optJSONObject(i);
	            if (null != jo) {
	                Channel channel = new Channel();
	                channel.from(jo);
	                channellist.add(channel);
	            }
	        }
	        return true;
	    }
	    return false;
	}
	
	public static class Channel extends Model {

		private String id = "";
		private String name_cn = "";
		private String name_en = "";
		private String desc = "";
		private String area = "";
		private String provider = "";
		private String icon_url = "";
		private String landscape_url = "";
		private String portrait_url = "";
		private String play_url = "";

		@Override
		public boolean from(JSONObject json) {
			id = json.optString("id");
			name_cn = json.optString("name_cn");
			name_en = json.optString("name_en");
			desc = json.optString("desc");
			area = json.optString("area");
			provider = json.optString("provider");
			icon_url = json.optString("icon_url");
			landscape_url = json.optString("landscape_url");
			portrait_url = json.optString("portrait_url");
			play_url = json.optString("play_url");
			return super.from(json);
		}

		public String getId() {
			return id;
		}

		public String getNameCn() {
			return name_cn;
		}

		public String getNameEn() {
			return name_en;
		}

		public String getDesc() {
			return desc;
		}

		public String getArea() {
			return area;
		}

		public String getProvider() {
			return provider;
		}

		public String getIconUrl() {
			return icon_url;
		}

		public String getLandscapeUrl() {
			return landscape_url;
		}

		public String getPortraitUrl() {
			return portrait_url;
		}

		public String getPlayUrl() {
			return play_url;
		}
		
	}

	public ArrayList<Channel> getChannelList() {
		return channellist;
	}

}
