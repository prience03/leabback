/**
 * 
 */
package com.duolebo.appbase.prj.bmtv.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.duolebo.appbase.prj.Model;

/**
 * @author zlhl
 * @date 2014年2月17日
 */
public class GetAppDetailData extends ModelBase {
	
	private Content content;
	
	private List<GetAppDetailData.Content> recommend_list = new ArrayList<GetAppDetailData.Content>();
	
	public Content getContent() {
		return content;
	}
	
	public List<GetAppDetailData.Content> getRecommend_list() {
		return recommend_list;
	}



	@Override
	public boolean from(JSONObject json) {
		if (super.from(json)) {
	        json = json.optJSONObject("response").optJSONObject("body");
			JSONObject jso = json.optJSONObject("content");
			content = new Content();
			content.from(jso);
			
			JSONArray ja = json.optJSONArray("recommend_list");
	        if (null != ja) {
	            for (int i = 0; i < ja.length(); i++) {
	                JSONObject jo = ja.optJSONObject(i);
	                GetAppDetailData.Content content = new GetAppDetailData.Content();
	                content.from(jo);
	                recommend_list.add(content);
	            }
	        }
	        return true;
		}
		return false;
	}
	
	public static class Content extends Model {
		
        public static final String TAGS_MOUSE = "鼠标";
        public static final String TAGS_JOYSTICK = "手柄";
        public static final String TAGS_AIRMOUSE = "空鼠";
        public static final String TAGS_BODYSENSOR = "体感";
        public static final String TAGS_NINEKEY = "九键";
        
		private String contentid = "";
		private String desc = "";
		private String portrait_url = "";
		private String package_name = "";
		private int download_count = 0; 
		private String developers = "";
		private String size = "";
		private String version = "";
		private String category = "";
		private String price = "";
		private String download_url = "";
		private String uploadtime = "";
		private String grade = "";
		private String min_sdk_version = "";
		private String contentname = "";
		private String landscape_url = "";
		private String tags = "";
		
		private ArrayList<Pic> pics = new ArrayList<Pic>();
		private ArrayList<GetContentListData.Content> recommends = new ArrayList<GetContentListData.Content>();
		
		@Override
		public boolean from(JSONObject json) {
			if (super.from(json)) {
				contentid = json.optString("contentid");
				contentname = json.optString("contentname");
				desc = json.optString("desc");
				developers = json.optString("developers");
				version = json.optString("version");
				size = json.optString("size");
				category = json.optString("category");
				package_name = json.optString("package_name");
				landscape_url = json.optString("landscape_url");
				portrait_url = json.optString("portrait_url");
				min_sdk_version = json.optString("min_sdk_version");
				grade = json.optString("grade");
				price = json.optString("price");
				uploadtime = json.optString("uploadtime");
				download_url = json.optString("download_url");
				download_count = json.optInt("download_count");
				tags = json.optString("tags");

				JSONArray jsa = json.optJSONArray("pics");
				int length = 0;
				JSONObject jso = null;
				if (null != jsa) {
					length = jsa.length();
					Pic pic = null;
					for (int i = 0; i < length; i++) {
						jso = jsa.optJSONObject(i);
						pic = new Pic();
						pic.from(jso);
						pics.add(pic);
					}
				}
				
				jsa = json.optJSONArray("recommend_list");
				if (null != jsa) {
					length = jsa.length();
					GetContentListData.Content recommendList = null;
					for (int i = 0; i < length; i++) {
						jso = jsa.optJSONObject(i);
						recommendList = new GetContentListData.Content();
						recommendList.from(jso);
						recommends.add(recommendList);
					}
				}
				return true;
			}
			return false;
		}

		public String getContentId() {
			return contentid;
		}

		public String getDesc() {
			return desc;
		}

		public String getPortraitUrl() {
			return portrait_url;
		}

		public String getPackageName() {
			return package_name;
		}

		public int getDownloadCount() {
			return download_count;
		}

		public String getDevelopers() {
			return developers;
		}

		public String getSize() {
			return size;
		}

		public String getVersion() {
			return version;
		}

		public String getCategory() {
			return category;
		}

		public String getPrice() {
			return price;
		}

		public String getDownloadUrl() {
			return download_url;
		}

		public String getUploadtime() {
			return uploadtime;
		}

		public String getGrade() {
			return grade;
		}

		public String getMinSdkVersion() {
			return min_sdk_version;
		}

		public String getContentName() {
			return contentname;
		}

		public String getLandscapeUrl() {
			return landscape_url;
		}

		public ArrayList<Pic> getPics() {
			return pics;
		}
		
		public String getTags() {
		    return tags;
		}

		public ArrayList<GetContentListData.Content> getRecommends() {
			return recommends;
		}
	}
	
	public static class Pic extends Model {
		
		private String url = "";

		@Override
		public boolean from(JSONObject json) {
		    if (super.from(json)) {
		        url = json.optString("url");
		        return true;
		    }
		    return false;
		}

		public String getUrl() {
			return url;
		}
		
	}
}
