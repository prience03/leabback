/**
 * 
 */
package com.duolebo.appbase.prj.bmtv.protocol;

import android.content.Context;

import com.duolebo.appbase.prj.bmtv.model.GetPayMethodData;

import org.json.JSONObject;

import java.util.Map;

/**
 * @author advu
 * @date 2016年11月14日
 *
 *   获取支付  wx alipay
 */
public class GetPayMethods extends ProtocolBase {

	private String pfuser_id = "";
	private String pfuser_name = "";
	private String type = "";
	private String tariff_id = "";
	private String tariff_name = "";
	private String price = "";
	private String num = "";
	private String total_fee = "";

	private GetPayMethodData data = new GetPayMethodData();

	public GetPayMethods(Context context, IProtocolConfig config) {
		super(context, config);
	}

	public GetPayMethods withData(JSONObject jsonObject) {
		pfuser_id = jsonObject.optString("pfuser_id");
		pfuser_name = jsonObject.optString("pfuser_name");
		type = jsonObject.optString("type");
		tariff_id = jsonObject.optString("tariff_id");
		tariff_name = jsonObject.optString("tariff_name");
		price = jsonObject.optString("price");
		num = jsonObject.optString("num");
		total_fee = jsonObject.optString("total_fee");
		return this;
	}


	@Override
	protected void prepareProtocolBody(Map<String, String> body) {
		body.put("pfuser_id", pfuser_id);
		body.put("pfuser_name", pfuser_name);
		body.put("type", type);
		body.put("tariff_id", tariff_id);
		body.put("tariff_name", tariff_name);
		body.put("price", price);
		body.put("num", num);
		body.put("total_fee", total_fee);
	}


	@Override
	protected String prepareProtocolRequestKey() {
		return "GETPFPayMethods";
	}

	@Override
	public GetPayMethodData getData() {
		return data;
	}

	@Override
	public long getExpire() {
	    return -1;
//		return 1000 * 60 * 60  + System.currentTimeMillis();
	}

}
