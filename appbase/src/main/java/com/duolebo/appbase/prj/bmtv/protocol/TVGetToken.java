/**
 * 
 */
package com.duolebo.appbase.prj.bmtv.protocol;

import java.util.Map;

import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils;

import com.duolebo.appbase.prj.bmtv.model.TVGetTokenData;
import com.duolebo.appbase.prj.bmtv.model.TVRegistrationData;
import com.duolebo.appbase.utils.Preference;

/**
 * @author zlhl
 * @date 2014年2月17日
 */
public class TVGetToken extends ProtocolBase {

	private TVGetTokenData data = new TVGetTokenData();

    private static String userid = "";
    private static String tvid = "";
    private static String user_token = "";

    private static Preference pref;
    private static final String PREF_USERID = "userid";
    private static final String PREF_TVID = "tvid";
    private static final String PREF_TOKEN = "token";

	public TVGetToken(Context context, IProtocolConfig config) {
		super(context, config);
	}

	@Override
	protected void prepareProtocolBody(Map<String, String> body) {
		body.put("tvid", TVRegistrationData.getTvid());
		body.put("userid", TVRegistrationData.getUserid());
	}

	@Override
	protected String prepareProtocolRequestKey() {
		return "TVGetToken";
	}
	
	@Override
	protected String prepareProtocolUserToken() {
	    return "";
	}

	@Override
	public TVGetTokenData getData() {
		return data;
	}

	@Override
	public long getExpire() {
	    return -1;
	}

    @Override
    public void parseHttpResult(String res) {
        super.parseHttpResult(res);
        setTvid(getContext(), data.getTvid());
        setUserid(getContext(), data.getUserid());
        setUserToken(getContext(), data.getUserToken());
    }

    private static Preference getPref(Context context) {
        if (null == pref) {
            pref = new Preference(context, "bmtv");
        }
        return pref;
    }

    public static String getUserid(Context context) {
        if (TextUtils.isEmpty(userid)) {
            userid = getPref(context).load(PREF_USERID);
        }
        return userid;
    }

    public static void setUserid(Context context, String userid) {
        TVGetToken.userid = userid;
        getPref(context).save(PREF_USERID, userid);
    }

    public static String getTvid(Context context) {
        if (TextUtils.isEmpty(tvid)) {
            tvid = getPref(context).load(PREF_TVID);
        }
        return tvid;
    }

    public static void setTvid(Context context, String tvid) {
        TVGetToken.tvid = tvid;
        getPref(context).save(PREF_TVID, tvid);
    }

    public static String getUserToken(Context context) {
        if (TextUtils.isEmpty(user_token)) {
            user_token = getPref(context).load(PREF_TOKEN);
        }
        return user_token;
    }

    public static void setUserToken(Context context, String user_token) {
        TVGetToken.user_token = user_token;
        getPref(context).save(PREF_TOKEN, user_token);
    }
}
