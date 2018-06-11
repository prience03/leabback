/**
 * 
 */
package com.duolebo.appbase.prj.bmtv.model;

import android.text.TextUtils;

import com.duolebo.appbase.prj.Model;
import com.duolebo.appbase.prj.bmtv.model.GetContentListData.Content.ContentType;
import com.duolebo.appbase.prj.bmtv.model.GetSaleDetailData.Content.TvPlayUrlTags;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author zlhl
 * @date 2014年2月17日
 */
public class GetContentDetailData extends ModelBase implements Serializable {

	private ArrayList<Content> contentlist = new ArrayList<Content>();

	private ArrayList<EpisodeContent> episode_list = new ArrayList<EpisodeContent>();

	private ArrayList<GetContentListData.Content> recommend_list = new ArrayList<GetContentListData.Content>();

	@Override
	public boolean from(JSONObject json) {
		if (super.from(json)) {
	        json = json.optJSONObject("response").optJSONObject("body");
	        JSONArray ja = json.optJSONArray("contentlist");
	        for (int i = 0; null != ja && i < ja.length(); i++) {
	            JSONObject jo = ja.optJSONObject(i);
	            if (null != jo) {
	                Content content = new Content();
	                content.from(jo);
	                contentlist.add(content);
	            }
	        }
			try {
				JSONArray jsonArrayEpisode = json.optJSONArray("allEpisode").optJSONObject(0).optJSONArray("allMessage");
				for (int i = 0; null != jsonArrayEpisode && i < jsonArrayEpisode.length(); i++) {
					JSONObject jo = jsonArrayEpisode.optJSONObject(i);
					if (null != jo) {
						EpisodeContent content = new EpisodeContent();
						content.from(jo);
						episode_list.add(content);
					}
				}
			}catch (Exception e){

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
	
	public Content getContextByIndex(int idx) {
		if (idx < contentlist.size())
			return contentlist.get(idx);
		return null;
	}
	
	public Content getContentById(String id) {
		for (Content content : contentlist) {
			if (id.equalsIgnoreCase(content.getContentId()))
				return content;
		}
		return null;
	}
	
	public ArrayList<Content> getContentList() {
		return contentlist;
	}
	public ArrayList<EpisodeContent> getEpisode_list() {
		return episode_list;
	}


	public ArrayList<GetContentListData.Content> getRecommendList() {
		return recommend_list;
	}

	public static class Content extends Model implements Serializable{

		private String contentid = "";
		private String isp_cont_code = "";
		private String isp_menu_code = "";
		private String contentname = "";
		private ContentType contenttype = ContentType.Unknown;
		private String icon_url = "";
		private String landscape_url = "";
		private String portrait_url = "";
		private String desc = "";
		private String publishtime = "";
		private String region = "";
		private String cont_type = "";
		private String show_year = "";
		private String show_month = "";
		private String alias = "";
		private String duration = "";
		private String actors = "";
		private String director = "";
		private String screenwriter = "";
		private String language = "";
		private String has_volume = "";
		private int vol_total = 0;
		private int vol_current = 0;
		private String is_HD = "";
		private String is_hot = "";
		private String is_new = "";
		private String url_bgd = "";

		private ArrayList<Fee> fees = new ArrayList<Fee>();

		private TvPlayUrlTags tv_play_url_tags = new TvPlayUrlTags();
		private String hits= "";
		private String area= "";
		private String origin= "";
		private String car_type= "";
		private String level= "";
		private String priceSection= "";
		private String brand= "";
		private String carType = "";
		private String price= "";
		private String sellStatus= "";
		private String aspect = "";

		public String getAspect() {
			return aspect;
		}

		public void setAspect(String aspect) {
			this.aspect = aspect;
		}

		public String getContentid() {
			return contentid;
		}

		public void setContentid(String contentid) {
			this.contentid = contentid;
		}

		public String getArea() {
			return area;
		}

		public void setArea(String area) {
			this.area = area;
		}

		public String getOrigin() {
			return origin;
		}

		public void setOrigin(String origin) {
			this.origin = origin;
		}

		public String getCar_type() {
			return car_type;
		}

		public void setCar_type(String car_type) {
			this.car_type = car_type;
		}

		public String getLevel() {
			return level;
		}

		public void setLevel(String level) {
			this.level = level;
		}

		public String getPriceSection() {
			return priceSection;
		}

		public void setPriceSection(String priceSection) {
			this.priceSection = priceSection;
		}

		public String getBrand() {
			return brand;
		}

		public void setBrand(String brand) {
			this.brand = brand;
		}

		public String getCarType() {
			return carType;
		}

		public void setCarType(String carType) {
			this.carType = carType;
		}

		public String getPrice() {
			return price;
		}

		public void setPrice(String price) {
			this.price = price;
		}

		public String getSellStatus() {
			return sellStatus;
		}

		public void setSellStatus(String sellStatus) {
			this.sellStatus = sellStatus;
		}

		@Override
		public boolean from(JSONObject json) {
		    if (super.from(json)) {
		        contentid = json.optString("contentid");
		        isp_cont_code = json.optString("isp_cont_code");
		        isp_menu_code = json.optString("isp_menu_code");
		        contentname = json.optString("contentname");
		        contenttype = ContentType.fromString(json.optString("contenttype"));
		        icon_url = json.optString("icon_url");
		        landscape_url = json.optString("landscape_url");
		        portrait_url = json.optString("portrait_url");
		        desc = json.optString("desc");
		        publishtime = json.optString("publishtime");
		        region = json.optString("region");
		        cont_type = json.optString("cont_type");
		        show_year = json.optString("show_year");
		        show_month = json.optString("show_month");
		        alias = json.optString("alias");
		        duration = json.optString("duration");
		        actors = json.optString("actors");
		        director = json.optString("director");
		        screenwriter = json.optString("screenwriter");
		        language = json.optString("language");
		        has_volume = json.optString("has_volume");
		        vol_total = json.optInt("vol_total");
		        vol_current = json.optInt("vol_current");
		        is_HD = json.optString("is_HD");
		        is_hot = json.optString("is_hot");
		        is_new = json.optString("is_new");
				url_bgd = json.optString("url_bgd");
				hits = json.optString("hits");
				area = json.optString("area");
				origin = json.optString("origin");
				car_type = json.optString("car_type");
				level = json.optString("level");
				priceSection = json.optString("priceSection");
				brand = json.optString("brand");
				carType = json.optString("carType");
				aspect = json.optString("aspect");
				price = json.optString("price");
				sellStatus = json.optString("sellStatus");
				JSONArray ja = json.optJSONArray("fees");
		        if (null != ja) {
		            for (int i = 0; i < ja.length(); i++) {
		                JSONObject jo = ja.optJSONObject(i);
		                Fee fee = new Fee();
		                fee.from(jo);
		                fees.add(fee);
		            }
		        }

				try {
					String tagsString = json.optString("tv_play_url_tags");
					if (!TextUtils.isEmpty(tagsString)) {
						JSONObject tagsJson = new JSONObject(tagsString);
						tv_play_url_tags.from(tagsJson);
					}
				} catch (JSONException e) {
				}

		        return true;
		    }
		    return false;
		}

		public static class Fee extends Model implements Serializable {

			private String is_HD = "";
			private String tag = "";
			private String tag_name = "";
			private String price = "";

			@Override
			public boolean from(JSONObject json) {
			    if (super.from(json)) {
			        is_HD = json.optString("is_HD");
			        tag = json.optString("tag");
			        tag_name = json.optString("tag_name");
			        price = json.optString("price");
			        return true;
			    }
			    return false;
			}

			public boolean getIsHD() {
				return is_HD.equalsIgnoreCase("1");
			}

			public String getTag() {
				return tag;
			}

			public String getTagName() {
				return tag_name;
			}

			public String getPrice() {
				return price;
			}

		}

		public String getContentId() {
			return contentid;
		}

		public String getIspContCode() {
			return isp_cont_code;
		}

		public String getIspMenuCode() {
			return isp_menu_code;
		}

		public String getContentName() {
			return contentname;
		}

		public ContentType getContentType() {
			return contenttype;
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

		public String getDesc() {
			return desc;
		}

		public String getHits() {
			return hits;
		}

		public String getPublishTime() {
			return publishtime;
		}

		public String getRegion() {
			return region;
		}

		public String getContType() {
			return cont_type;
		}

		public String getShowYear() {
			return show_year;
		}

		public String getShowMonth() {
			return show_month;
		}

		public String getAlias() {
			return alias;
		}

		public String getDuration() {
			return duration;
		}

		public String getActors() {
			return actors;
		}

		public String getDirector() {
			return director;
		}

		public String getScreenWriter() {
			return screenwriter;
		}

		public String getLanguage() {
			return language;
		}

		public String getHasVolume() {
			return has_volume;
		}

		public int getVolTotal() {
			return vol_total;
		}

		public int getVolCurrent() {
			return vol_current;
		}

		public String getIsHD() {
			return is_HD;
		}

		public String getIsHot() {
			return is_hot;
		}

		public String getIsNew() {
			return is_new;
		}

		public String getUrl_bgd() {
			return url_bgd;
		}

		public ArrayList<Fee> getFees() {
			return fees;
		}

		public TvPlayUrlTags getTvPlayUrlTags() {
			return tv_play_url_tags;
		}


	}


	public static class EpisodeContent extends Model implements Serializable{
		private String id = "";
		private String bit_rate = "";
		private String c_id = "";
		private String order_num = "";
		private String rate_tag = "";
		private String rate_tag_eng = "";
		private String play_url = "";
		private String title = "";
		private String file_desc = "";
		private String url = "";
		private String url_little = "";
		private String url_background = "";

		@Override
		public boolean from(JSONObject json) {
			if (super.from(json)) {
				id = json.optString("id");
				bit_rate = json.optString("bit_rate");
				c_id = json.optString("c_id");
				order_num = json.optString("order_num");
				rate_tag = json.optString("rate_tag");
				rate_tag_eng = json.optString("rate_tag_eng");
				play_url = json.optString("play_url");
				title = json.optString("title");
				file_desc = json.optString("file_desc");
				url = json.optString("url");
				url_little = json.optString("url_little");
				url_background = json.optString("url_background");

				return true;
			}
			return false;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getBit_rate() {
			return bit_rate;
		}

		public void setBit_rate(String bit_rate) {
			this.bit_rate = bit_rate;
		}

		public String getC_id() {
			return c_id;
		}

		public void setC_id(String c_id) {
			this.c_id = c_id;
		}

		public String getOrder_num() {
			return order_num;
		}

		public void setOrder_num(String order_num) {
			this.order_num = order_num;
		}

		public String getRate_tag() {
			return rate_tag;
		}

		public void setRate_tag(String rate_tag) {
			this.rate_tag = rate_tag;
		}

		public String getRate_tag_eng() {
			return rate_tag_eng;
		}

		public void setRate_tag_eng(String rate_tag_eng) {
			this.rate_tag_eng = rate_tag_eng;
		}

		public String getPlay_url() {
			return play_url;
		}

		public void setPlay_url(String play_url) {
			this.play_url = play_url;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getFile_desc() {
			return file_desc;
		}

		public void setFile_desc(String file_desc) {
			this.file_desc = file_desc;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getUrl_little() {
			return url_little;
		}

		public void setUrl_little(String url_little) {
			this.url_little = url_little;
		}

		public String getUrl_background() {
			return url_background;
		}

		public void setUrl_background(String url_background) {
			this.url_background = url_background;
		}
	}


}
