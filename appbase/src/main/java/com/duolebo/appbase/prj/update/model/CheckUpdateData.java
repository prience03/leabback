package com.duolebo.appbase.prj.update.model;

import android.content.Context;

import com.duolebo.appbase.prj.Model;
import com.duolebo.appbase.prj.XMLHelper;
import com.duolebo.appbase.update.IUpdateModel;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;

public class CheckUpdateData extends Model implements IUpdateModel {
    
	private static final int TYPE_NOT_UPGRADE = 0;
	private static final int TYPE_OPTIONAL_UPGRADE = 1;
	private static final int TYPE_MANDATORY_UPGRADE = 2;

	private int code = -1;
	private String desc = "";

	private int needUpdate = TYPE_NOT_UPGRADE;
	private String updateVersion;
	private String updateInfo;
	private String updateUrl;
	private String md5;

    @Override
    public boolean from(JSONObject json) {
        if (super.from(json)) {
			JSONObject response = json.optJSONObject("response");
			if (null == response) return false;
			
			JSONObject header = response.optJSONObject("header");
			if (null == header) return false;

			JSONObject result = header.optJSONObject("result");
			if (null == result) return false;
			code = result.optInt("code");
			desc = result.optString("desc");

			JSONObject body = response.optJSONObject("body");
			if (null == body) return false;
			
			JSONObject clientinfo = body.optJSONObject("clientinfo");
			if (null == clientinfo) return false;
			needUpdate = clientinfo.optInt("needupdate", 0);
			updateVersion = clientinfo.optString("updateversion");
			updateInfo = clientinfo.optString("updateinfo");
			updateUrl = clientinfo.optString("updateurl");
			md5 = clientinfo.optString("md5");

            return true;
        }
        return false;
    }
    
    @Override
    public boolean from(XMLHelper parser) {
        if (super.from(parser)) {
            try {
                int et = parser.getEventType();

                String tn = null;
                while (et != XmlPullParser.END_DOCUMENT) {
                    switch (et) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        tn = parser.getName();
                        if ("response".equals(tn)) {
                        } else if ("header".equals(tn)) {
                        } else if ("result".equals(tn)) {
                        } else if ("code".equals(tn)) {
                            code = Integer.valueOf(parser.nextText());
                        } else if ("desc".equals(tn)) {
                            desc = parser.nextText();
                        } else if ("sequence".equals(tn)) {
                        } else if ("plat".equals(tn)) {
                        } else if ("client-version".equals(tn)) {
                        } else if ("user-agent".equals(tn)) {
                        } else if ("format".equals(tn)) {
                        } else if ("client-ip".equals(tn)) {
                        } else if ("phone-number".equals(tn)) {
                        } else if ("body".equals(tn)) {
                        } else if ("clientinfo".equals(tn)) {
                        } else if ("needupdate".equals(tn)) {
                            needUpdate = Integer.valueOf(parser.nextText());
                        } else if ("updateversion".equals(tn)) {
                            updateVersion = parser.nextText();
                        } else if ("updateinfo".equals(tn)) {
                            updateInfo = parser.nextText();
                        } else if ("updateurl".equals(tn)) {
                            updateUrl = parser.nextText();
                        } else if ("md5".equals(tn)) {
                            md5 = parser.nextText();
                        }

                        break;
                    case XmlPullParser.END_TAG:
                        tn = parser.getName();
                        break;

                    default:
                        break;
                    }

                    et = parser.next();
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean hasUpdate(Context context) {
        return TYPE_NOT_UPGRADE < needUpdate;
    }

    @Override
    public boolean isMandatory() {
        return TYPE_MANDATORY_UPGRADE <= needUpdate;
    }

    @Override
    public String getMessage() {
        return updateInfo;
    }

    @Override
    public String getVersion() {
        return updateVersion;
    }

    @Override
    public String getDownloadUrl() {
        return updateUrl;
    }

    @Override
    public String getMd5() {
        return md5;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}
