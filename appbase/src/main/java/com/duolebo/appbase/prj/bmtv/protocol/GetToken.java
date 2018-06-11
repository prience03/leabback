package com.duolebo.appbase.prj.bmtv.protocol;

import android.content.Context;
import android.text.TextUtils;

import com.duolebo.appbase.prj.bmtv.model.GetTokenData;
import com.duolebo.appbase.utils.Preference;

import java.util.Map;

/**
 *
 * @author wang
 * created at 2017/3/29 18:08
 */
public class GetToken extends ProtocolBase {

	private GetTokenData data = new GetTokenData();

    private static String userid = "";
    private static String tvid = "";
    private static String user_token = "";

    private static Preference pref;
    private static final String PREF_USERID = "userid";
    private static final String PREF_TVID = "tvid";
    private static final String PREF_TOKEN = "token";

	public GetToken(Context context, IProtocolConfig config) {
		super(context, config);
	}

	@Override
	protected void prepareProtocolBody(Map<String, String> body) {
        body.put("tvid", getConfig().getTvid());
	}

	@Override
	protected String prepareProtocolRequestKey() {
		return "GetToken";
	}
	
	@Override
	protected String prepareProtocolUserToken() {
	    return "";
	}

	@Override
	public GetTokenData getData() {
		return data;
	}

	@Override
	public long getExpire() {
	    return -1;
//	    return  1000 * 60 * 10  + System.currentTimeMillis();
	}

    @Override
    public void parseHttpResult(String res) {
        super.parseHttpResult(res);
        setTvid(getContext(), data.getTvid());
        setUserid(getContext(), data.getUserid());
        setUserToken(getContext(), data.getUser_token());
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
        GetToken.userid = userid;
        getPref(context).save(PREF_USERID, userid);
    }

    public static String getTvid(Context context) {
        if (TextUtils.isEmpty(tvid)) {
            tvid = getPref(context).load(PREF_TVID);
        }
        return tvid;
    }

    public static void setTvid(Context context, String tvid) {
        GetToken.tvid = tvid;
        getPref(context).save(PREF_TVID, tvid);
    }

    public static String getUserToken(Context context) {
        if (TextUtils.isEmpty(user_token)) {
            user_token = getPref(context).load(PREF_TOKEN);
        }
        return user_token;
    }

    public static void setUserToken(Context context, String user_token) {
        GetToken.user_token = user_token;
        getPref(context).save(PREF_TOKEN, user_token);
    }
}
