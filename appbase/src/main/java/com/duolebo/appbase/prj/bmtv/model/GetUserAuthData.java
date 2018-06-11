package com.duolebo.appbase.prj.bmtv.model;

import org.json.JSONObject;

/**
 * @author advu
 * @date 2016年11月14日
 *       获取 个人信息
 *
*/
public class GetUserAuthData extends ModelBase {

    private String status = "";
    private String userid = "";
    private String username = "";
    private String user_type = "";
    private String vip_end_time = "";

    @Override
    public boolean from(JSONObject json) {
        if (super.from(json)) {
	        json = json.optJSONObject("response").optJSONObject("body");
            status = json.optString("status");
            userid = json.optString("userid");
            username = json.optString("username");
            user_type = json.optString("user_type");
            vip_end_time = json.optString("vip_end_time");
            return true;
        }
        return false;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getVip_end_time() {
        return vip_end_time;
    }

    public void setVip_end_time(String vip_end_time) {
        this.vip_end_time = vip_end_time;
    }

    @Override
    public String toString() {
        return "GetUserAuthData{" +
                "status='" + status + '\'' +
                ", userid='" + userid + '\'' +
                ", username='" + username + '\'' +
                ", user_type='" + user_type + '\'' +
                ", vip_end_time='" + vip_end_time + '\'' +
                '}';
    }
}
