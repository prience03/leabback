/**
 * 
 */
package com.duolebo.appbase.prj.bmtv.protocol;

import android.content.Context;

import com.duolebo.appbase.prj.bmtv.model.GetLoginMethodData;

import java.util.Map;

/**
 * @author advu
 * @date 2016年11月14日
 *
 *   获取登录方法 手机 wx alipay
 */
public class GetLoginMethods extends ProtocolBase {

	private String phone_num = "";


	private GetLoginMethodData data = new GetLoginMethodData();

	public GetLoginMethods(Context context, IProtocolConfig config) {
		super(context, config);
	}


	@Override
	protected void prepareProtocolBody(Map<String, String> body) {
	}

	@Override
	protected String prepareProtocolRequestKey() {
		return "GETPFLoginMethods";
	}

	@Override
	public GetLoginMethodData getData() {
		return data;
	}

	@Override
	public long getExpire() {
	    return -1;
//		return 1000 * 60 * 60  + System.currentTimeMillis();
	}

}
