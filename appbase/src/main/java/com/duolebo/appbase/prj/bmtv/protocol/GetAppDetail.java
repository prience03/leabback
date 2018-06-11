/**
 * 
 */
package com.duolebo.appbase.prj.bmtv.protocol;

import android.content.Context;

import com.duolebo.appbase.prj.bmtv.model.GetAppDetailData;

import java.util.Map;

/**
 * @author zlhl
 * @date 2014年2月17日
 */
public class GetAppDetail extends ProtocolBase {
	
	private String contentid = "";
	
	private GetAppDetailData data = new GetAppDetailData();

	public GetAppDetail(Context context, IProtocolConfig config) {
		super(context, config);
	}

	public GetAppDetail withContentId(String id) {
		contentid = id;
		return this;
	}

	@Override
	protected void prepareProtocolBody(Map<String, String> body) {
		body.put("contentid", contentid);
	}

	@Override
	protected String prepareProtocolRequestKey() {
		return "GetAppDetail";
	}

	@Override
	public GetAppDetailData getData() {
		return data;
	}

	@Override
	public long getExpire() {
		return 1000 * 60 * 60 * 24 * 7 + System.currentTimeMillis();
	}

}
