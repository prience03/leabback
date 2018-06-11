/**
 * 
 */
package com.duolebo.appbase.prj.bmtv.protocol;

import java.util.Map;

import android.content.Context;

import com.duolebo.appbase.prj.bmtv.model.GetHotWordsData;

/**
 * @author zlhl
 * @date 2014年2月17日
 */
public class GetHotWords extends ProtocolBase {
	
	private String keyword = "";

	private GetHotWordsData data = new GetHotWordsData();

	public GetHotWords(Context context, IProtocolConfig config) {
		super(context, config);
	}

	@Override
	protected void prepareProtocolBody(Map<String, String> body) {
		body.put("searchwords", keyword);
	}

	@Override
	protected String prepareProtocolRequestKey() {
		return "GetHotWords";
	}

	@Override
	public GetHotWordsData getData() {
		return data;
	}
	
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

}
