package com.duolebo.appbase;

import com.duolebo.appbase.prj.XMLHelper;

import org.json.JSONArray;
import org.json.JSONObject;

public interface IModel {
	public boolean from(JSONObject json);
	public boolean from(JSONArray jsonArray);
	public boolean from(XMLHelper parser);
}
