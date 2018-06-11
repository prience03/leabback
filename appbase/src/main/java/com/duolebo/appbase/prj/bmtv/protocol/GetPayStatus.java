/**
 * 
 */
package com.duolebo.appbase.prj.bmtv.protocol;

import android.content.Context;

import com.duolebo.appbase.prj.bmtv.model.GetPayStatusData;

import org.json.JSONObject;

import java.util.Map;

/**
 * @author advu
 * @date 2016年11月16日
 *
 *   获取支付 status
 */
public class GetPayStatus extends ProtocolBase {

	private String pfuser_id = "";
	private String order_id = "";
	private String order_code = "";

	private GetPayStatusData data = new GetPayStatusData();

	public GetPayStatus(Context context, IProtocolConfig config) {
		super(context, config);
	}

	public GetPayStatus withData(JSONObject jsonObject) {
		pfuser_id = jsonObject.optString("pfuser_id");
		order_id = jsonObject.optString("order_id");
		order_code = jsonObject.optString("order_code");
		return this;
	}


	@Override
	protected void prepareProtocolBody(Map<String, String> body) {
		body.put("pfuser_id", pfuser_id);
		body.put("order_id", order_id);
		body.put("order_code", order_code);
	}


	@Override
	protected String prepareProtocolRequestKey() {
		return "GETPFPayStatus";
	}

	@Override
	public GetPayStatusData getData() {
		return data;
	}

	@Override
	public long getExpire() {
	    return -1;
//		return 1000 * 60 * 60  + System.currentTimeMillis();
	}

}
