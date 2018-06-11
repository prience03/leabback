package com.duolebo.appbase.prj.bmtv.protocol;

import com.duolebo.appbase.prj.XMLHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

/**
 * Created by dengln on 16/4/21.
 */
public interface IDataConfig extends IProtocolConfig {

    public JSONArray getJsonArray(String res) throws JSONException;

    public JSONObject getJsonObject(String res) throws JSONException;

    public XMLHelper getXMLHelper(String res) throws XmlPullParserException;
}
