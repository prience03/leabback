package com.duolebo.appbase.prj.bmtv.model;

import org.json.JSONObject;

/**
 * @author advu
 * @date 2016年11月16日
 *       获取订单状态
 *
*/
public class GetPayStatusData extends ModelBase {

    private String status = "";

    @Override
    public boolean from(JSONObject json) {
        if (super.from(json)) {
	        json = json.optJSONObject("response").optJSONObject("body");
            status = json.optString("status");
            return true;
        }
        return false;
    }

    public String getStatus() {
        return status;
    }
}
