package com.duolebo.appbase.prj.bmtv.protocol;

import java.util.Map;

import android.content.Context;

import com.duolebo.appbase.prj.bmtv.model.GetAreaListData;

public class GetAreaList extends ProtocolBase {
	private int areaid = 0;
	private GetAreaListData data = new GetAreaListData() ;
	public GetAreaList(Context context, IProtocolConfig config) {
		super(context, config);
	}
	
	public GetAreaList with(int areaid){
		this.areaid=areaid;
		return this;
	}
	
	
	@Override
	public GetAreaListData getData() {
		return data;
	}

	@Override
	protected void prepareProtocolBody(Map<String, String> body) {
		body.put("areaid", String.valueOf(areaid));
	}

	@Override
	protected String prepareProtocolRequestKey() {
		return "GetAreaList";
	}
	
}
