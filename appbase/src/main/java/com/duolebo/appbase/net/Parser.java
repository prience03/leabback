package com.duolebo.appbase.net;

import java.util.Map;

import android.os.Handler;

public class Parser {
	
	private static final int SUCCESS = HttpRequestTask.SUCCESS;
	private static final int FAIL = HttpRequestTask.FAIL;
	
	private static Parser instance = null;
	private Parser() {}
	
	public static Parser getInstance() {
		if(instance == null) {
			instance = new Parser();
		}
		return instance;
	}
	
	public static void doRequest(ParserClient httpReq, Handler handler) {
		HttpRequestTask.execute(httpReq, getInstance().new Callback(httpReq, handler));
	}
	
	private void success(ParserClient httpReq, String content, Handler handler, int what) {
		httpReq.parseHttpResult(content);
		handler.sendMessage(handler.obtainMessage(what, httpReq));
	}
	
	private void failure(ParserClient httpReq, Handler handler, int what) {
		handler.sendMessage(handler.obtainMessage(what, httpReq));
	}
	
	public class Callback {
		private int what;
		private int errWhat;
		private Handler handler;
		private ParserClient httpReq;
		
		public Callback(ParserClient httpReq, Handler handler) {
			this.handler = handler;
			this.httpReq = httpReq;
			this.what = SUCCESS;
			this.errWhat = FAIL;
		}
		
		public void exe(int tag, String content) {
			if(handler != null && null != httpReq) {
				switch(tag) {
				case SUCCESS:
					getInstance().success(httpReq, content, handler, what);
					break;
				case FAIL:
					getInstance().failure(httpReq, handler, errWhat);
					break;
				}
			}
		}
	}

	abstract static public class ParserClient {

		abstract public HttpMethod			requestHttpMethod();
		abstract public String 				prepareHttpRequestUrl();
		abstract public Map<String, String> prepareHttpParamters();
		abstract public String 				prepareHttpBody();
		abstract public void 				parseHttpResult(String res);
		abstract public Map<String, String> prepareHttpHeaders();

		public enum HttpMethod {
			POST, GET
		}

		public void execute(Handler handler) {
			Parser.doRequest(this, handler);
		}

	}
}
