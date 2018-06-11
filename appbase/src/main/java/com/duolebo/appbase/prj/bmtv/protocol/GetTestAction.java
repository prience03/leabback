package com.duolebo.appbase.prj.bmtv.protocol;

import android.content.Context;

import com.duolebo.appbase.prj.bmtv.model.GetTestActionData;

import java.util.Map;

/**
 * @author zlhl
 * @date 2017年9月25日
 */
public class GetTestAction extends ProtocolBase {

	private GetTestActionData data = new GetTestActionData();

	public GetTestAction(Context context, IProtocolConfig config) {
		super(context, config);
	}

	@Override
	protected void prepareProtocolBody(Map<String, String> body) {
	}

	@Override
	protected String prepareProtocolRequestKey() {
		return "TestAction";
	}

	@Override
	public long getExpire() {
		return  -1 ;
	}

	@Override
	public GetTestActionData getData() {
		return data;
	}

}
