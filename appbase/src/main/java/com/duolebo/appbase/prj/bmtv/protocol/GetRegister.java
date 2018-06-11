/**
 * 
 */
package com.duolebo.appbase.prj.bmtv.protocol;

import android.content.Context;

import com.duolebo.appbase.prj.bmtv.model.GetRegisterData;

import java.util.Map;

/**
 * @author advu
 * @date 2016年11月14日
 *
 *    注册 登录 公用一个 ，后端去判断
 *
 */
public class GetRegister extends ProtocolBase {

	private String phone_num = "";
	private String verify_code = "" ;

	private GetRegisterData data = new GetRegisterData();

	public GetRegister(Context context, IProtocolConfig config) {
		super(context, config);
	}

	public GetRegister withPhoneNumAndVerifyCode(String phone_num,String verify_code) {
		this.phone_num = phone_num;
		this.verify_code = verify_code ;
		return this;
	}
	
	@Override
	protected void prepareProtocolBody(Map<String, String> body) {
		body.put("mobile_phone", phone_num);
		body.put("code" , verify_code);
	}

	@Override
	protected String prepareProtocolRequestKey() {
		return "PFVerification";
	}

	@Override
	public GetRegisterData getData() {
		return data;
	}

	@Override
	public long getExpire() {
	    return -1;
	}

}
