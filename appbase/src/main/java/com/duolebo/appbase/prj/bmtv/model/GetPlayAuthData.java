package com.duolebo.appbase.prj.bmtv.model;

import org.json.JSONObject;

/**
 * @author advu
 * @date 2016年11月14日
 *       playauth
 *
*/
public class GetPlayAuthData extends ModelBase {

    private String auth_status = "";
    private String desc  = "";
    private String c_id = "";

    @Override
    public boolean from(JSONObject json) {
        if (super.from(json)) {
	        json = json.optJSONObject("response").optJSONObject("body");
            auth_status = json.optString("auth_status");
            desc = json.optString("desc");
            c_id = json.optString("c_id");
            return true;
        }
        return false;
    }

    public String getAuth_status() {
        return auth_status;
    }

    public void setAuth_status(String auth_status) {
        this.auth_status = auth_status;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getC_id() {
        return c_id;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }

    @Override
    public String toString() {
        return "GetPlayAuthData{" +
                "auth_status='" + auth_status + '\'' +
                ", desc='" + desc + '\'' +
                ", c_id='" + c_id + '\'' +
                '}';
    }
}
