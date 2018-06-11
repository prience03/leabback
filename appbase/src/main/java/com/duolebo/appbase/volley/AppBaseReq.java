package com.duolebo.appbase.volley;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.duolebo.appbase.AppBaseHandler;
import com.duolebo.appbase.net.Parser.ParserClient.HttpMethod;
import com.duolebo.appbase.utils.Log;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

abstract public class AppBaseReq extends Request<String> {

    private static final String TAG = "AppBaseReq";

    private static final int HTTP_TIMEOUT_MS = 1000 * 10;
    private static final int HTTP_MAX_RETRIES = 3;
    private static final float HTTP_BACKOFF_MULT = 2f;

	abstract public HttpMethod			requestHttpMethod();
	abstract public String 				prepareHttpRequestUrl();
	abstract public Map<String, String> prepareHttpParamters();
	abstract public byte[] 				prepareHttpBody();
	abstract public void 				parseHttpResult(String res);
	abstract public Map<String, String> prepareHttpHeaders();
	
	private Context context = null;
	private Handler handler = null;
    protected int statusCode = -1;
    private AppBaseRetryPolicy retryPolicy;
	
	@Override
	public int getMethod() {
		if (HttpMethod.POST == requestHttpMethod())
			return Method.POST;
		return Method.GET;
	}


	@Override
	public String getUrl() {
		return prepareHttpRequestUrl();
	}
	
	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		Map<String, String> headers = prepareHttpHeaders();
		headers = (null == headers ? new HashMap<String, String>() : headers);
		if (Build.VERSION.SDK != null && Integer.parseInt(Build.VERSION.SDK) > 13) {
		    headers.put("Connection", "close");
		}
		return headers;
	}
	
	@Override
	public byte[] getBody() throws AuthFailureError {
	    byte[] body = prepareHttpBody();
	    if (null != body) {
	        return body;
	    }
		return super.getBody();
	}
	
	@Override
	protected Map<String, String> getParams() throws AuthFailureError {
	    Map<String, String> params = prepareHttpParamters();
	    Log.i(TAG, params);
	    return params;
	}
	
	public void execute(Handler handler) {
        this.statusCode = -1;
		this.handler = handler;
        retryPolicy.resetRetryCount();
        setTag(setRequestTag());

		android.util.Log.e("TAG", "-------tag---------"+setRequestTag().toString());
		VolleyClient.getInstance().createRequestQueue(context).add(this);
		Log.i(TAG, getUrl());
	}

	public Object setRequestTag() {
		return "";
	}

	public AppBaseReq(Context context) {
		super(Method.GET, null, null);
		this.context = context;
        retryPolicy = new AppBaseRetryPolicy(HTTP_TIMEOUT_MS, HTTP_MAX_RETRIES, HTTP_BACKOFF_MULT);
        setRetryPolicy(retryPolicy);
	}
	
	@Override
	public void deliverError(VolleyError error) {
		super.deliverError(error);
        if (null == error || null == handler) return;

		if (null != error.getMessage()) 
		    Log.i(TAG, error.getMessage());

		error.printStackTrace();

        if (null != error.networkResponse)
            this.statusCode = error.networkResponse.statusCode;

		Message msg = new Message();
		msg.what = AppBaseHandler.FAIL;
		msg.obj = this;
		handler.sendMessage(msg);
	}

	@Override
	protected void deliverResponse(String response) {
		parseHttpResult(response);
		if (null == handler) return;
		Message msg = new Message();
		msg.what = AppBaseHandler.SUCCESS;
		msg.obj = this;
		handler.sendMessage(msg);
	}

	@Override
	protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, getParamsEncoding());
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        this.statusCode = response.statusCode;
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
	}

    protected int getStatusCode() {
        return statusCode;
    }

	protected void deleteCache(String stableKey) {
		VolleyClient.getInstance().createRequestQueue(context);
		VolleyClient.getInstance().clearCache(stableKey);
	}

	protected boolean hasCache(String stableKey) {
		VolleyClient.getInstance().createRequestQueue(context);
		return VolleyClient.getInstance().hasCache(stableKey);
	}

	protected void cancleTag(Object tag) {
		VolleyClient.getInstance().cancleTag(tag);
		android.util.Log.e("TAG", "--------cancleTag-------"+tag.toString());
	}


}
