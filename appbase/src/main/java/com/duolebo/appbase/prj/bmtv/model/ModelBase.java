/**
 *
 */
package com.duolebo.appbase.prj.bmtv.model;

import com.duolebo.appbase.prj.Model;

import org.json.JSONObject;

/**
 * @author zlhl
 * @date 2014年2月17日
 */
public class ModelBase extends Model {

    private int code = -1;
    private String desc = "";
    private static String clientIp = "";
    private long timestamp = -1;

    @Override
    public String toString() {
        if (null != getJSON())
            return getJSON().toString();

        return super.toString();
    }

    public ModelBase() {
    }

    public ModelBase(ModelBase base) {
        super(base);
        code = base.code;
        desc = base.desc;
        timestamp = base.timestamp;
    }

    @Override
    public boolean from(JSONObject json) {
        if (super.from(json)) {

            JSONObject response = json.optJSONObject("response");
            if (null == response) return false;

            JSONObject header = response.optJSONObject("header");
            if (null == header) return false;

            JSONObject result = header.optJSONObject("result");
            if (null == result) return false;

            JSONObject body = response.optJSONObject("body");
            if (null == body) return false;

            code = result.optInt("code");
            desc = result.optString("desc");
            if (clientIp == null || "".equals(clientIp)) {
                clientIp = header.optString("client-ip");
            }

            timestamp = header.optLong("timestamp", -1);

            return true;
        }
        return false;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static String getClientIp() {
        return clientIp;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
