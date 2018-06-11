package com.duolebo.appbase.prj.bmtv.protocol;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.TextUtils;
import android.util.Log;

import com.duolebo.appbase.log.KLog;
import com.duolebo.appbase.net.Parser.ParserClient.HttpMethod;
import com.duolebo.appbase.prj.Protocol;
import com.duolebo.appbase.prj.XMLHelper;
import com.duolebo.appbase.prj.baidu.model.LocationData;
import com.duolebo.appbase.prj.bmtv.model.ModelBase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

abstract public class ProtocolBase extends Protocol {

    private static final String TAG = ProtocolBase.class.getName();
    private static Random random = new Random();
    private static long randomNum = random.nextLong()%1000 + 100000;

    private final int SUCCESS = 0;
    abstract protected void prepareProtocolBody(Map<String, String> body);
    protected String prepareProtocolBody() { return null; }
    abstract protected String prepareProtocolRequestKey();

    private static String version = null;
    private IProtocolConfig config = null;

    public ProtocolBase(Context context, IProtocolConfig config) {
        super(context);
        this.config = config;
        checkVersion(context);
    }

    public ProtocolBase(ProtocolBase base) {
        super(base);
        this.config = base.config;
    }

    private void checkVersion(Context context) {
        if (null == version) {
            try {
                version = context.getPackageManager()
                        .getPackageInfo(context.getPackageName(), 0).versionName;
            } catch (NameNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    protected String prepareProtocolUserToken() {
        return GetToken.getUserToken(getContext());
    }

    protected void prepareProtocolHeader(Map<String, String> header) {
        randomNum++;
        String stamp = random.nextLong()%1000000 + "_" + randomNum;
        header.put("client-version", version);
        header.put("user-agent", "0");
        header.put("sequence", stamp);
        header.put("plat", "android");
        header.put("user_token", prepareProtocolUserToken());
        header.put("format", "json");
        header.put("model", android.os.Build.MODEL);
        header.put("zone", LocationData.getZone());
        header.put("channel", config.getChannel());
    }

    @Override
    public HttpMethod requestHttpMethod() {
        return HttpMethod.POST;
    }

    @Override
    public Map<String, String> prepareHttpParamters() {
        return null;
    }

    @Override
    public byte[] prepareHttpBody() {
        StringBuilder sb = new StringBuilder();

        sb.append("<request key=\"").append(prepareProtocolRequestKey()).append("\">");

        /// protocol header
        HashMap<String, String> header = new HashMap<String, String>();
        prepareProtocolHeader(header);
        sb.append("<header>");
        for (Map.Entry<String, String> item : header.entrySet()) {
            sb.append("<").append(item.getKey()).append(">");
            sb.append(item.getValue());
            sb.append("</").append(item.getKey()).append(">");
        }
        sb.append("</header>");

        /// protocol body
        HashMap<String, String> body = new HashMap<String, String>();
        prepareProtocolBody(body);
        sb.append("<body>");
        for (Map.Entry<String, String> item : body.entrySet()) {
            sb.append("<").append(item.getKey()).append(">");
            sb.append(item.getValue());
            sb.append("</").append(item.getKey()).append(">");
        }
        String bodyString = prepareProtocolBody();
        if (null != bodyString && !TextUtils.isEmpty(bodyString)) {
            sb.append(bodyString);
        }
        sb.append("</body>");

        sb.append("</request>");

        String string = sb.toString();
        KLog.d(TAG, string);

        Log.d("TAG", string);
        return string.getBytes();
    }

    @Override
    public Map<String, String> prepareHttpHeaders() {
        return null;
    }

    @Override
    public String prepareHttpRequestUrl() {
        return config.getProtocolUrl();
    }

    @Override
    public boolean succeed() {
        return SUCCESS == ((ModelBase)getData()).getCode();
    }

    @Override
    public int resultFormat() {
        return PROTOCOL_RESULT_FORMAT_JSON;
    }

    public String getKey() {
        return prepareProtocolRequestKey();
    }

    @Override
    public String getStableKey() {
        StringBuilder sb = new StringBuilder();
        sb.append(prepareProtocolRequestKey());
        HashMap<String, String> body = new HashMap<String, String>();
        prepareProtocolBody(body);
        for (Map.Entry<String, String> item : body.entrySet()) {
            sb.append(item.getKey()).append(item.getValue());
        }
        return sb.toString();
    }

    @Override
    public long getExpire() {
        return 1000 * 60 * 60  + System.currentTimeMillis();
    }

    @Override
    public JSONArray toJSONArray(String json) throws JSONException {
        if (config instanceof IDataConfig) {
            return ((IDataConfig) config).getJsonArray(json);
        }
        return super.toJSONArray(json);
    }

    @Override
    public JSONObject toJSONObject(String json) throws JSONException {
        if (config instanceof IDataConfig) {
            return ((IDataConfig) config).getJsonObject(json);
        }
        return super.toJSONObject(json);
    }

    @Override
    public XMLHelper toXMLHelper(String json) throws XmlPullParserException {
        if (config instanceof IDataConfig) {
            return ((IDataConfig) config).getXMLHelper(json);
        }
        return super.toXMLHelper(json);
    }

    protected IProtocolConfig getConfig() {
        return config;
    }

}
