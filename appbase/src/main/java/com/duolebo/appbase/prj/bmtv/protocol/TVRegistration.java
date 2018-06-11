/**
 *
 */
package com.duolebo.appbase.prj.bmtv.protocol;

import java.util.Map;

import org.json.JSONObject;

import android.content.Context;

import com.duolebo.appbase.prj.bmtv.model.TVRegistrationData;

/**
 * @author zlhl
 * @date 2014年2月17日
 */
public class TVRegistration extends ProtocolBase {

    /// query conditions
    private String tvid = "";

    /// retrieved data
    private TVRegistrationData data = new TVRegistrationData();

    public TVRegistration(Context context, IProtocolConfig config) {
        super(context, config);
    }

    @Override
    protected String prepareProtocolRequestKey() {
        return "TVRegistration";
    }

    @Override
    protected void prepareProtocolBody(Map<String, String> body) {
        body.put("tvid", getConfig().getTvid());
    }

    @Override
    protected String prepareProtocolUserToken() {
        return "";
    }

    @Override
    public TVRegistrationData getData() {
        return data;
    }

    @Override
    public long getExpire() {
        return -1;
    }

}
