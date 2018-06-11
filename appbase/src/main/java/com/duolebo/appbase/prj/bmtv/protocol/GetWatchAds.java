/**
 * 
 */
package com.duolebo.appbase.prj.bmtv.protocol;

import android.content.Context;

import com.duolebo.appbase.prj.bmtv.model.GetWatchAdsData;

import org.json.JSONObject;

import java.util.Map;

/**
 * @author zlhl
 * @date 2014年2月17日
 */
public class GetWatchAds extends ProtocolBase {

	private GetWatchAdsData data = new GetWatchAdsData();

	private JSONObject values ;

	public GetWatchAds(Context context, IProtocolConfig config) {
		super(context, config);
	}

	public GetWatchAds with(JSONObject values) {
		this.values = values;
		return this;
	}

	@Override
	protected void prepareProtocolBody(Map<String, String> body) {
		body.put("c_id",values.optString("c_id"));
		body.put("ad_id",values.optString("ad_id"));
		body.put("pfuser_id",values.optString("pfuser_id"));
		body.put("monitor_type",values.optString("monitor_type"));//监测链接类型0：开机广告，1：贴片广告，2：暂停广告，3其他
		body.put("macaddr",values.optString("macaddr"));
		body.put("exposure_time",values.optString("exposure_time"));
	}

	@Override
	protected String prepareProtocolRequestKey() {
		return "Monitor";
	}

	@Override
	public GetWatchAdsData getData() {
		return data;
	}

	@Override
	public long getExpire() {
		return  -1 ;
	}
}
