/**
 * 
 */
package com.duolebo.appbase.prj.bmtv.protocol;

import android.content.Context;

import com.duolebo.appbase.prj.bmtv.model.GetOrderListData;

import java.util.Map;

/**
 * @author advu
 * @date 2016年11月14日
 */
public class GetOrderList extends ProtocolBase {

	private String phone_num = "";


	private GetOrderListData data = new GetOrderListData();

	public GetOrderList(Context context, IProtocolConfig config) {
		super(context, config);
	}


	@Override
	protected void prepareProtocolBody(Map<String, String> body) {
	}

	@Override
	protected String prepareProtocolRequestKey() {
		return "PFOrder";
	}

	@Override
	public GetOrderListData getData() {
		return data;
	}

	@Override
	public long getExpire() {
	    return -1;
	}

}
