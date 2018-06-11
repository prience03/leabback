/**
 * 
 */
package com.duolebo.appbase.prj.bmtv.protocol;

import android.content.Context;

import com.duolebo.appbase.prj.bmtv.model.GetContentTVPlayUrlData;

import java.util.Map;

/**
 * @author zlhl
 * @date 2014年2月17日
 */
public class GetContentTVPlayUrl extends ProtocolBase {

	private String contentid = "";
	private boolean is_HD = false;
	private String tag = "";
	private String episodeid = "";
	private String isp_menu_code = "";
	
	private GetContentTVPlayUrlData data = new GetContentTVPlayUrlData();

	public GetContentTVPlayUrl(Context context, IProtocolConfig config) {
		super(context, config);
	}

	@Override
	protected void prepareProtocolBody(Map<String, String> body) {
		body.put("contentid", contentid);
		body.put("is_HD", is_HD ? "1" : "0");
		body.put("tag", tag);
		body.put("episodeid", episodeid);
		body.put("isp_menu_code", isp_menu_code);
	}

	@Override
	protected String prepareProtocolRequestKey() {
		return "GetContentTVPlayUrl";
	}

	public GetContentTVPlayUrl withContentId(String contentid) {
		this.contentid = contentid;
		return this;
	}
	
	public GetContentTVPlayUrl withIsHD(boolean is_HD) {
		this.is_HD = is_HD;
		return this;
	}
	
	public GetContentTVPlayUrl withTag(String tag) {
		this.tag = tag;
		return this;
	}
	
	public GetContentTVPlayUrl withEpisodeId(String episodeid) {
		this.episodeid = episodeid;
		return this;
	}
	
	public GetContentTVPlayUrl withIspMenuCode(String isp_menu_code) {
		this.isp_menu_code = isp_menu_code;
		return this;
	}

	@Override
	public GetContentTVPlayUrlData getData() {
		return data;
	}

	@Override
	public long getExpire() {
//		return 1000 * 60 * 60 * 24 * 7 + System.currentTimeMillis();
		return  -1;
	}

}
