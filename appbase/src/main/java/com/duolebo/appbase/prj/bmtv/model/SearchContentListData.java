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
public class SearchContentListData extends ModelBase {
	
	private int totalsize = 0;

	private String keyword;

	private ArrayList<SearchContentListData.Content> contentlist = new ArrayList<SearchContentListData.Content>();
	private ArrayList<GetContentListData.Content> recommend_list = new ArrayList<GetContentListData.Content>();

	@Override
	public boolean from(JSONObject json) {
	    if (super.from(json)) {
	        json = json.optJSONObject("response").optJSONObject("body");
	        totalsize = json.optInt("totalsize");
	        keyword = json.optString("keyword");

	        JSONArray ja = json.optJSONArray("contentlist");
	        if (null != ja) {
	            for (int i = 0; i < ja.length(); i++) {
	                JSONObject jo = ja.optJSONObject(i);
	                Content content = new Content();
	                content.from(jo);

	                contentlist.add(content);
	            }
	        }

	        ja = json.optJSONArray("recommend_list");
	        if (null != ja) {
	            for (int i = 0; i < ja.length(); i++) {
	                JSONObject jo = ja.optJSONObject(i);
	                GetContentListData.Content content = new GetContentListData.Content();
	                content.from(jo);
	                recommend_list.add(content);
	            }
	        }
	        return true;
	    }
	    return false;
	}
	
	public int getTotalsize() {
		return totalsize;
	}
	
	public String getKeyword() {
		return keyword;
	}
	
	public ArrayList<SearchContentListData.Content> getContentList() {
		return contentlist;
	}
	
	public ArrayList<GetContentListData.Content> getRecommendList() {
		return recommend_list;
	}
	
	public static class Content extends Model {
		private String contentid= ""; 
		private String contentname= "";
		private String contenttype= "";
		private String icon_url= ""; 
		private String img_url= "";
		private String display_image = "";

		private String play_url= "";
		private String desc= ""; 
		private String publishtime= "";
		private String package_name= "";
		private String link_url = "";
		private String superscript_url = "";
		private int has_video = 0;
		private GetContentListData.Content.DetailInfo detail_infos = null;

		@Override
		public boolean from(JSONObject json) {
		    if (super.from(json)) {
		        contentid = json.optString("contentid");
		        contentname = json.optString("contentname");
		        contenttype = json.optString("contenttype");
		        icon_url = json.optString("icon_url");
		        img_url = json.optString("img_url");
				display_image = json.optString("display_image");
		        play_url = json.optString("play_url");
		        desc = json.optString("desc");
		        publishtime = json.optString("publishtime");
		        package_name = json.optString("package_name");
		        link_url = json.optString("link_url");
		        superscript_url = json.optString("superscript_url");
		        has_video = json.optInt("has_video");

				if (json.has("detail_infos")) {
					detail_infos = new GetContentListData.Content.DetailInfo();
					detail_infos.from(json.optJSONObject("detail_infos"));
				}

		        return true;
		    }
		    return false;
		}

		public GetContentListData.Content.DetailInfo getDetailInfos() {
			return detail_infos;
		}

		public String getContentId() {
			return contentid;
		}

		public String getContentName() {
			return contentname;
		}

		public String getContentType() {
			return contenttype;
		}

		public String getIconUrl() {
			return icon_url;
		}

		public String getImgUrl() {
			return img_url;
		}

		public String getPlayUrl() {
			return play_url;
		}

		public String getDesc() {
			return desc;
		}

		public String getPublishTime() {
			return publishtime;
		}

		public String getPackageName() {
			return package_name;
		}

		public String getDisplay_image() {
			return display_image;
		}

		public void setDisplay_image(String display_image) {
			this.display_image = display_image;
		}

		public String getLinkUrl() {
			return link_url;
		}

		public String getSuperscriptUrl() {
			return superscript_url;
		}
		
		public int getHasVideo() {
		    return has_video;
		}


//		public class DetailInfo {
//			/**
//			 * "detail_infos":{
//			 * "flags":[],
//			 * "extras":[],
//			 * "intent_uri":"#Intent;action=com.wasu.openpage;S.widgetV=1.0;S.layoutCode=Detail_Movie;S.url=http%3A%2F%2Fshanxi-unicom-zw3.wasu.tv%2FXmlData%2FPlayData%3FassetId%3D7602179%26nodeId%3D92831;end",
//			 * "start_type":"INTENT"},"
//			 **/
//
//		}

	}

}
