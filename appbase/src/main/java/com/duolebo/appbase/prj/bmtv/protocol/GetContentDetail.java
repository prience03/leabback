/**
 * 
 */
package com.duolebo.appbase.prj.bmtv.protocol;

import java.util.Map;

import android.content.Context;
import android.text.TextUtils;

import com.duolebo.appbase.prj.bmtv.model.GetContentDetailData;

/**
 * @author zlhl
 * @date 2014年2月17日
 */
public class GetContentDetail extends ProtocolBase {
	
	private String c_ids = "";
	private String isp_menu_codes = "";
	
	private GetContentDetailData data = new GetContentDetailData();

	public GetContentDetail(Context context, IProtocolConfig config) {
		super(context, config);
	}

	public GetContentDetail withContentIds(String... c_ids) {
		this.c_ids = TextUtils.join(",", c_ids);
		return this;
	}
	
	public GetContentDetail withIspMenuCodes(String... isp_menu_codes) {
		this.isp_menu_codes = TextUtils.join(",", isp_menu_codes);
		return this;
	}
	
	@Override
	public GetContentDetailData getData() {
		return data;
	}

	@Override
	protected void prepareProtocolBody(Map<String, String> body) {
		body.put("c_ids", c_ids);
		body.put("isp_menu_codes", isp_menu_codes);
		body.put("isrecommend" ,"1");
	}

	@Override
	protected String prepareProtocolRequestKey() {
		return "GetContentDetail";
	}

	@Override
	public long getExpire() {
		return  -1 ;
//		return 1000 * 60 * 60 * 24 * 7 + System.currentTimeMillis();
	}

}
