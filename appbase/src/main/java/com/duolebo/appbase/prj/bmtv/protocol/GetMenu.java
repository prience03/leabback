/**
 * 
 */
package com.duolebo.appbase.prj.bmtv.protocol;

import java.util.Map;

import android.content.Context;

import com.duolebo.appbase.prj.bmtv.model.GetMenuData;

/**
 * @author zlhl
 * @date 2014年2月17日
 *
 *     后台协议中menuid暂不用
 *
 */
public class GetMenu extends ProtocolBase {
	
	private String menuid = null;
	
	private GetMenuData data = new GetMenuData();

	public GetMenu(Context context, IProtocolConfig config) {
		super(context, config);
	}

	public GetMenu with(String menuid) {
		this.menuid = menuid;
		return this;
	}

	@Override
	protected void prepareProtocolBody(Map<String, String> body) {
		if(menuid != null) {
			body.put("menuid", menuid);
		}


//		body.put("parentid", "0");
	}

	@Override
	protected String prepareProtocolRequestKey() {
		return "GetMenu";
	}

	@Override
	public GetMenuData getData() {
		return data;
	}

	@Override
	public long getExpire() {
		return -1;
	}
}
