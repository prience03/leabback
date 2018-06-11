/**
 * 
 */
package com.duolebo.appbase.prj.bmtv.protocol;

import java.util.Map;

import android.content.Context;

import com.duolebo.appbase.prj.bmtv.model.GetChannelListData;

/**
 * @author zlhl
 * @date 2014年2月17日
 */
public class GetChannelList extends ProtocolBase {
	
	private String menuid = "";
	private String area_code = "";

	private GetChannelListData data = new GetChannelListData();

	public GetChannelList(Context context, IProtocolConfig config) {
		super(context, config);
	}

	@Override
	protected void prepareProtocolBody(Map<String, String> body) {
		body.put("menuid", menuid);
		body.put("area_code", area_code);
	}

	@Override
	protected String prepareProtocolRequestKey() {
		return "GetChannelList";
	}

	@Override
	public GetChannelListData getData() {
		return data;
	}

}
