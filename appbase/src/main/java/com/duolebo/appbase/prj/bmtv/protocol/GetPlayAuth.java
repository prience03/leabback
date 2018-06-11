package com.duolebo.appbase.prj.bmtv.protocol;

import android.content.Context;

import com.duolebo.appbase.prj.bmtv.model.GetPlayAuthData;

import java.util.Map;

/**
 * @author zlhl
 * @date 2014年2月17日
 *
 *  *       播放鉴权
 *
 */
public class GetPlayAuth extends ProtocolBase {

	private String c_id = "";

	private GetPlayAuthData data = new GetPlayAuthData();

	public GetPlayAuth(Context context, IProtocolConfig config) {
		super(context, config);
	}

	public GetPlayAuth withContentIds(String  c_id) {
		this.c_id =  c_id;
		return this;
	}

	@Override
	public GetPlayAuthData getData() {
		return data;
	}

	@Override
	protected void prepareProtocolBody(Map<String, String> body) {
		body.put("c_id", c_id);
	}

	@Override
	protected String prepareProtocolRequestKey() {
		return "PFAuthPlay";
	}

	@Override
	public long getExpire() {
		return -1;
	}

}
