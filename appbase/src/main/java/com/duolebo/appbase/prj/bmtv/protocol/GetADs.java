/**
 * 
 */
package com.duolebo.appbase.prj.bmtv.protocol;

import java.util.Map;

import android.content.Context;

import com.duolebo.appbase.prj.bmtv.model.GetADsData;

/**
 * @author zlhl
 * @date 2014年2月17日
 */
public class GetADs extends ProtocolBase {
	
	private GetADsData data = new GetADsData();

	public GetADs(Context context, IProtocolConfig config) {
		super(context, config);
	}

	@Override
	protected void prepareProtocolBody(Map<String, String> body) {
	}

	@Override
	protected String prepareProtocolRequestKey() {
		return "GetADs";
	}

	@Override
	public GetADsData getData() {
		return data;
	}

}
