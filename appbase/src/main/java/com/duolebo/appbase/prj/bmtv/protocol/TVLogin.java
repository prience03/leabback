/**
 * 
 */
package com.duolebo.appbase.prj.bmtv.protocol;

import java.util.Map;

import android.content.Context;

import com.duolebo.appbase.prj.bmtv.model.GetTokenData;
import com.duolebo.appbase.prj.bmtv.model.TVLoginData;
import com.duolebo.appbase.utils.ThreeDESCode;

/**
 * @author zlhl
 * @date 2014年2月17日
 */
public class TVLogin extends ProtocolBase {
	
	private TVLoginData data = new TVLoginData();
	
	public TVLogin(Context context, IProtocolConfig config) {
		super(context, config);
	}

	@Override
	protected void prepareProtocolBody(Map<String, String> body) {
		body.put("tvid", GetToken.getTvid(getContext()));
		body.put("userid", GetToken.getUserid(getContext()));
		body.put("encrypt_data", getEncryptData());
	}

	@Override
	protected String prepareProtocolRequestKey() {
		return "TVLogin";
	}

	private String getEncryptData() {
		String src = GetToken.getTvid(getContext()) + "$"
				+ GetToken.getUserid(getContext()) + "$"
				+ GetToken.getUserToken(getContext());
		String key = GetTokenData.getEncrypt_key();
		String data = "";
		try {
			data = ThreeDESCode.encryptThreeDESECB(src, key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	@Override
	public TVLoginData getData() {
		return data;
	}

	@Override
	public long getExpire() {
	    return -1;
	}

}
