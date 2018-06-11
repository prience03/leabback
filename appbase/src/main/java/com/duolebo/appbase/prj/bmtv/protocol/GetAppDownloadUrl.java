/**
 * 
 */
package com.duolebo.appbase.prj.bmtv.protocol;

import android.content.Context;

import com.duolebo.appbase.prj.bmtv.model.GetAppDownloadUrlData;

import java.util.Map;

/**
 * @author zlhl
 * @date 2014年2月17日
 */
public class GetAppDownloadUrl extends ProtocolBase {
	
	private String contentid = "";
	
	private GetAppDownloadUrlData data = new GetAppDownloadUrlData();

	public GetAppDownloadUrl(Context context, IProtocolConfig config) {
		super(context, config);
	}

	public GetAppDownloadUrl withContentID(String id) {
		contentid = id;
		return this;
	}
	
	@Override
	protected void prepareProtocolBody(Map<String, String> body) {
		body.put("contentid", contentid);
	}

	@Override
	protected String prepareProtocolRequestKey() {
		return "GetAppDownloadUrl";
	}

	@Override
	public GetAppDownloadUrlData getData() {
		return data;
	}

	@Override
	public long getExpire() {
	    return -1;
	}

}
