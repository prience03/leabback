package com.duolebo.appbase.prj;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.duolebo.appbase.IModel;
import com.duolebo.appbase.IProtocol;
import com.duolebo.appbase.cache.ICacheable;
import com.duolebo.appbase.log.KLog;
import com.duolebo.appbase.net.Parser.ParserClient.HttpMethod;
import com.duolebo.appbase.volley.AppBaseReq;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

/*
 * An easy code base to get you started.
 */
abstract public class Protocol extends AppBaseReq implements IProtocol, ICacheable {

    private Object obj = null;
    private Context context = null;
    private boolean succeed = false;
    private boolean cacheEnabled = true;

    private long protocolTimeStamp = 0;

    public Protocol setTag(Object obj) {
        this.obj = obj;
        return this;
    }

    public Object getTag() {
        return obj;
    }

    public Protocol(Context context) {
        super(context);
        this.context = context;
    }

    public Protocol(Protocol protocol) {
        super(protocol.context);
        this.obj = protocol.obj;

        this.context = protocol.context;
        this.succeed = protocol.succeed;
        this.cacheEnabled = protocol.cacheEnabled;
    }

    @Override
    public void execute(Handler handler) {
        protocolTimeStamp = System.currentTimeMillis();
        setShouldCache(cacheEnabled && !TextUtils.isEmpty(getStableKey()) && 0 < getExpire());
        super.execute(handler);
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public String getStableKey() {
        return null;
    }

    @Override
    public long getExpire() {
        return -1;
    }

    @Override
    public boolean succeed() {
        return succeed;
    }

    @Override
    public HttpMethod requestHttpMethod() {
        return HttpMethod.GET;
    }

    @Override
    public void parseHttpResult(String res) {
        KLog.d("Response", String.format("time cost:%dms, %s",
                (System.currentTimeMillis() - protocolTimeStamp), res));
        succeed = false;

        IModel data = getData();
        if (null == data) return;

        if (data instanceof Model) {
            ((Model)data).setRawData(res);
        }

        switch (resultFormat()) {
            case PROTOCOL_RESULT_FORMAT_JSON: {
                try {
//                    JSONObject json = new JSONObject(res);
                    JSONObject json = toJSONObject(res);
                    succeed = data.from(json);
                } catch (JSONException e) {
                    e.printStackTrace();
                    succeed = false;
                }

                break;
            }

            case PROTOCOL_RESULT_FORMAT_XML: {
                try {
//                    XmlPullParser parser = Xml.newPullParser();
//                    parser.setInput(new StringReader(res));
                    XMLHelper xmlHelper = toXMLHelper(res);
                    succeed = data.from(xmlHelper);
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                    succeed = false;
                }

                break;
            }

            case PROTOCOL_RESULT_FORMAT_JSON_ARRAY: {
                try {
//                    JSONArray jsonArray = new JSONArray(res);
                    JSONArray jsonArray = toJSONArray(res);
                    succeed = data.from(jsonArray);
                } catch (JSONException e) {
                    e.printStackTrace();
                    succeed = false;
                }

                break;
            }

            default:
                break;
        }
    }

    public JSONObject toJSONObject(String json) throws JSONException {
        return new JSONObject(json);
    }

    public JSONArray toJSONArray(String json) throws JSONException {
        return new JSONArray(json);
    }

    public XMLHelper toXMLHelper(String json) throws XmlPullParserException {
        return new XMLHelper(json);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        Response<String> res =  super.parseNetworkResponse(response);
        if (null != res.cacheEntry) {
            res.cacheEntry.softTtl = res.cacheEntry.ttl = getExpire();
        } else {
            // if server has explicitly declared no-cache,
            // no cache will be saved.
        }
        return res;
    }

    @Override
    public void setCacheEnabled(boolean enable) {
        cacheEnabled = enable;
    }

    @Override
    public String getCacheKey() {
        return getStableKey();
    }

    @Override
    public int statusCode() {
        return getStatusCode();
    }

    @Override
    public void expunge() {
        deleteCache(getStableKey());
    }

    @Override
    public boolean hasCached() {
        return hasCache(getCacheKey());
    }
}
