/**
 * 
 */
package com.duolebo.appbase.prj.bmtv.protocol;

import android.content.Context;

import com.duolebo.appbase.prj.bmtv.model.GetUserAuthData;
import com.duolebo.appbase.prj.bmtv.model.GetVerifyCodeData;

import java.util.Map;

/**
 * @author advu
 * @date 2016年11月14日
 *  获取个人信息
 */
public class GetUserAuth extends ProtocolBase {

	private String phone_num = "";


	private GetUserAuthData data = new GetUserAuthData();

	public GetUserAuth(Context context, IProtocolConfig config) {
		super(context, config);
	}

	public GetUserAuth withPhoneNum(String phone_num) {
		this.phone_num = phone_num;
		return this;
	}
	
	@Override
	protected void prepareProtocolBody(Map<String, String> body) {
//		body.put("mobile_phone", phone_num);
	}

	@Override
	protected String prepareProtocolRequestKey() {
		return "PFUserAuth";
	}

	@Override
	public GetUserAuthData getData() {
		return data;
	}

	@Override
	public long getExpire() {
	    return -1;
	}

}
