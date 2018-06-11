/**
 * 
 */
package com.duolebo.appbase.prj.bmtv.protocol;

import android.content.Context;

import com.duolebo.appbase.prj.bmtv.model.GetVerifyCodeData;

import java.util.Map;

/**
 * @author advu
 * @date 2016年11月14日
 */
public class GetVerifyCode extends ProtocolBase {

	private String phone_num = "";


	private GetVerifyCodeData data = new GetVerifyCodeData();

	public GetVerifyCode(Context context, IProtocolConfig config) {
		super(context, config);
	}

	public GetVerifyCode withPhoneNum(String phone_num) {
		this.phone_num = phone_num;
		return this;
	}
	
	@Override
	protected void prepareProtocolBody(Map<String, String> body) {
		body.put("mobile_phone", phone_num);
	}

	@Override
	protected String prepareProtocolRequestKey() {
		return "PFRegistLogin";
	}

	@Override
	public GetVerifyCodeData getData() {
		return data;
	}

	@Override
	public long getExpire() {
	    return -1;
	}

}
