package com.duolebo.appbase.net;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.text.TextUtils;

import com.duolebo.appbase.cache.CacheEntity;
import com.duolebo.appbase.cache.ICacheable;
import com.duolebo.appbase.net.Parser.Callback;
import com.duolebo.appbase.net.Parser.ParserClient;

public class HttpRequestTask extends HttpAsyncTask<Callback, Object, Callback> {
   private static final String TAG = HttpRequestTask.class.getName();

	public static final String REQ_GET = "GET";
	public static final String REQ_POST = "POST";
	public static final String UTF_8 = "UTF-8";
	public static int connectTimes = 3;

	public static final int SUCCESS = 200;
	public static final int FAIL = 404;
	public static final int SERVER_ERR = 500;
	public static final int ADDRESS_NOT_FOUNT = 404;

	private String requestUrl;
	private String requestData;
	private Map<String, String> requestFields;
	private Map<String, String> requestHeaders;

	private int result;
	private String resultString;
	
	private ParserClient httpReq;
	private CacheEntity cacheEntity;

	public HttpRequestTask(ParserClient httpReq) {
		this.httpReq = httpReq;
		this.requestUrl = httpReq.prepareHttpRequestUrl();
		this.requestData = httpReq.prepareHttpBody();
		this.requestFields = httpReq.prepareHttpParamters();
		this.requestHeaders = httpReq.prepareHttpHeaders();
		this.cacheEntity = new CacheEntity();
		if (null == this.requestHeaders)
			this.requestHeaders = getDefaultHeaders();
	}
	
	private Map<String, String> getDefaultHeaders() {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/x-www-form-urlencoded");
		headers.put("Accept", "application/json");
		return headers;
	}
	
	public void doHttpGet() {
		resultString = null;

		if (httpReq instanceof ICacheable && 0 < ((ICacheable)httpReq).getExpire()) {
			ICacheable cacheable = (ICacheable)httpReq;
			cacheEntity.set(cacheable);
			resultString = cacheEntity.getData();
		} 
		
		if (TextUtils.isEmpty(resultString)) {
			doRealHttpGet();
		} else {
			result = SUCCESS;
		}
	}
	
	public void doRealHttpGet() {
		HttpApiImpl httpApiImpl = new HttpApiImpl();
		httpApiImpl.setHeaders(requestHeaders);
		Map<String, String> fields = httpReq.prepareHttpParamters();
		String getUrl = requestUrl;
		
		if(fields != null && fields.size() > 0) {
			String params = toURLParams(fields);
			if(getUrl.contains("?")) {
				getUrl += "&" + params;
			} else {
				getUrl += "?" + params;
			}
		}

		String resultString = httpApiImpl.doGet(getUrl);
		if (TextUtils.isEmpty(resultString)) {
			result = FAIL;
		} else {
			this.resultString = resultString;
			result = SUCCESS;
			
			if (httpReq instanceof ICacheable && 0 < ((ICacheable)httpReq).getExpire()) {
				ICacheable cacheable = (ICacheable)httpReq;
				cacheEntity.set(cacheable);
				cacheEntity.setData(resultString);
			} 
		}
	}
	
	private String toURLParams(Map<String, String> params) {
		StringBuffer strBuf = new StringBuffer();
		String param = "";
		
		Iterator<String> it = params.keySet().iterator();
		String key, value;
		while(it.hasNext()) {
			key = (String) it.next();
			value = params.get(key);
			try {
				strBuf.append(key).append("=").append(URLEncoder.encode(value, UTF_8)).append("&");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		param = strBuf.toString();
		if(param.length() > 0) {
			param = param.substring(0, param.length()-1);
		}
		
		return param;
	}

	public void doHttpPost(){
		resultString = null;

		if (httpReq instanceof ICacheable && 0 < ((ICacheable)httpReq).getExpire()) {
			ICacheable cacheable = (ICacheable)httpReq;
			cacheEntity.set(cacheable);
			resultString = cacheEntity.getData();
		} 
		
		if (TextUtils.isEmpty(resultString)) {
			doRealHttpPost();
		} else {
			result = SUCCESS;
		}
	}

	public void doRealHttpPost(){
		HttpApiImpl httpApiImpl = new HttpApiImpl();
		httpApiImpl.setHeaders(requestHeaders);

		if (TextUtils.isEmpty(requestData)) {
			String key, value;
			Iterator<String> it = requestFields.keySet().iterator();
			while(it.hasNext()) {
				key = (String) it.next();
				value = requestFields.get(key);
				httpApiImpl.addParams(key, value);
			}
		} else {
			httpApiImpl.setRequestData(requestData);
		}

		String resultString = httpApiImpl.doPost(requestUrl);
		if (TextUtils.isEmpty(resultString)) {
			result = FAIL;
		} else {
			this.resultString = resultString;
			result = SUCCESS;
			
			if (httpReq instanceof ICacheable && 0 < ((ICacheable)httpReq).getExpire()) {
				ICacheable cacheable = (ICacheable)httpReq;
				cacheEntity.set(cacheable);
				cacheEntity.setData(resultString);
			} 
		}
	}

	protected Callback doInBackground(Callback callbacks[]) {
		switch (httpReq.requestHttpMethod()) {
		case POST:
			doHttpPost();
			break;
		case GET:
			doHttpGet();
			break;
		default:
			break;
		}
		return callbacks[0];
	}

	protected void onCancelled() {
		if (getStatus() != HttpAsyncTask.Status.RUNNING)
			cancel(true);
		super.onCancelled();
	}

	protected void onPostExecute(Callback callback) {
		callback.exe(result, resultString);
		super.onPostExecute(callback);
	}
	
	public static void execute(ParserClient httpReq, Callback callback){
		new HttpRequestTask(httpReq).execute(callback);
	}
	
}
