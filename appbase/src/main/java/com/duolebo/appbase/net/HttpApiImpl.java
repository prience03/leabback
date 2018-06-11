package com.duolebo.appbase.net;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.AbstractHttpMessage;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;

import android.text.TextUtils;

import com.duolebo.appbase.utils.Log;

public class HttpApiImpl {
   private static final String TAG = HttpApiImpl.class.getName();

	public static final int CONNECTION_TIMEOUT = 3000;
	public static final int RETRY_COUNT = 3;
	public static final int SOCKET_BUFFER_SIZE = 8192;
	public static final int SO_TIMEOUT = 10000;

	private List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	private Map<String, String> headers = null;
	
	private String requestData = "";

	public void setRequestData(String requestData) {
		this.requestData = requestData;
	}
	
	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}
	
	private void prepareHeaders(AbstractHttpMessage message) {
		if (null != headers) {
			for (Map.Entry<String, String> header : headers.entrySet()) {
				message.setHeader(header.getKey(), header.getValue());
			}
		}
	}

	private HttpParams getHttpParams() {
		BasicHttpParams baseHttp = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(baseHttp, CONNECTION_TIMEOUT);
		HttpConnectionParams.setSoTimeout(baseHttp, SO_TIMEOUT);
		HttpConnectionParams.setSocketBufferSize(baseHttp, SOCKET_BUFFER_SIZE);
		HttpClientParams.setRedirecting(baseHttp, true);
		HttpProtocolParams.setVersion(baseHttp, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(baseHttp, "UTF-8");
		return baseHttp;
	}

	public void addParams(String name, String value ) {
		if (isBlank(name))
			throw new IllegalArgumentException("addParams(String key, String value)");
		this.nameValuePairs.add(new BasicNameValuePair(name,value));
	}

	public boolean isBlank(String paramString) {
		boolean flag = true;
		if (paramString != null) {
			int length = paramString.length();
			if (length != 0) {
				for (int i = 0; i < length; i++) {
					if (!Character.isWhitespace(paramString.charAt(i))) {
						flag = false;
						break;
					}
				}
			}
		}

		return flag;
	}
	
	private static String getParams(List<?> list) {
	       if(list != null) {
	    	   StringBuffer sb = new StringBuffer();
	           int size = list.size();
	           for (int i = 0; i < size; i++) {
	               if (i == 0) {
	                   sb.append("?" + list.get(i) + "&");
	               } else {
	                   sb.append(list.get(i) + "&");
	               }
	           }
	           return sb.toString();
	       }
	       return null;
	    }

	public String doPost(String requestUrl) {
		DefaultHttpClient client = null;
		boolean success = false;
		/* FIXME:
		if (Zhilink.getUiThreadID() == Thread.currentThread().getId())
			throw new RuntimeException(
					"In the main thread is not allowed to perform network IO.");
		*/
		String result = "";
		int retry = 0;
		while (!success && retry < RETRY_COUNT) {
			HttpParams httpparams = getHttpParams();
			try {
				client = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(requestUrl);
				httppost.setParams(httpparams);
				prepareHeaders(httppost);
				if (nameValuePairs != null && nameValuePairs.size() > 0){
					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
				}
				if(!TextUtils.isEmpty(requestData)){
					httppost.setEntity(new StringEntity(requestData, "UTF-8"));
				}
				HttpResponse httpresponse = client.execute(httppost);
				/* 若状态码为200 ok */
				int code = httpresponse.getStatusLine().getStatusCode();
				if (code == 200) {
					result = EntityUtils.toString(httpresponse.getEntity(), "UTF-8");
					success = true;
				} else {
					httppost.abort();
					success = false;
					retry++;
				}
				if (success)
					break;
			} catch (Exception e) {
				success = false;
				retry++;
				Log.w(TAG, "doPost() " + e.toString());
			}finally{
				client.getConnectionManager().shutdown();
			}
		}
		return result;

	}

	public String doGet(String requestUrl){
		HttpGet httpGet = null;
		DefaultHttpClient client = null;
		boolean success = false;
		/* FIXME:
		if (Zhilink.getUiThreadID() == Thread.currentThread().getId())
			throw new RuntimeException(
					"In the main thread is not allowed to perform network IO.");
		*/
		int retry = 0;
		String result = "";
		while (!success && retry < RETRY_COUNT) {
			HttpParams httpparams = getHttpParams();
			try {
				client = new DefaultHttpClient();
				httpGet = new HttpGet(requestUrl);
				httpGet.setParams(httpparams);
				prepareHeaders(httpGet);
				HttpResponse httpresponse = client.execute(httpGet);
				/* 若状态码为200 ok */
				int code = httpresponse.getStatusLine().getStatusCode();
				if (code == 200) {
					result = EntityUtils.toString(httpresponse.getEntity(), "UTF-8");
					success = true;
				} else {
					httpGet.abort();
					success = false;
					retry++;
				}
				if (success)
					break;
			} catch (Exception e) {
				success = false;
				retry++;
				Log.w(TAG, "doGet() " + e.toString());
			} finally {
				client.getConnectionManager().shutdown();
			}
		}
		return result;

	}
	
	public boolean isnet(String requestUrl){
		HttpGet httpGet = null;
		DefaultHttpClient client = null;
		boolean success = false;
		int retry = 0;
		while (!success && retry < RETRY_COUNT) {
			HttpParams httpparams = getHttpParams();
			try {
				client = new DefaultHttpClient();
				httpGet = new HttpGet(requestUrl);
				httpGet.setParams(httpparams);
				prepareHeaders(httpGet);
				HttpResponse httpresponse = client.execute(httpGet);
				/* 若状态码为200 ok */
				int code = httpresponse.getStatusLine().getStatusCode();
				if (code == 200) {
					success = true;
				} else {
					httpGet.abort();
					success = false;
				}
				if (success)
					break;
			} catch (Exception e) {
				success = false;
				retry++;
				Log.w(TAG, "isnet() " + e.toString());
			} finally {
				client.getConnectionManager().shutdown();
			}
		}
		return success;

	}

	public HttpClient getNewHttpClient() {
		DefaultHttpClient defaulthttpclient;
		try {
			KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
			keystore.load(null, null);
			MySSLSocketFactory mysslsocketfactory = new MySSLSocketFactory(
					keystore);
			mysslsocketfactory
					.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			BasicHttpParams basichttpparams = new BasicHttpParams();
			HttpProtocolParams
					.setVersion(basichttpparams, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(basichttpparams, "UTF-8");
			HttpConnectionParams.setConnectionTimeout(basichttpparams, CONNECTION_TIMEOUT);
			HttpConnectionParams.setSoTimeout(basichttpparams, SO_TIMEOUT);
			HttpConnectionParams.setSocketBufferSize(basichttpparams, SOCKET_BUFFER_SIZE);
			HttpClientParams.setRedirecting(basichttpparams, true);
			SchemeRegistry schemeregistry = new SchemeRegistry();
			schemeregistry.register(new Scheme("http", PlainSocketFactory
					.getSocketFactory(), 80));
			schemeregistry
					.register(new Scheme("https", mysslsocketfactory, 443));
			defaulthttpclient = new DefaultHttpClient(
					new ThreadSafeClientConnManager(basichttpparams,
							schemeregistry), basichttpparams);
		} catch (Exception exception) {
			defaulthttpclient = new DefaultHttpClient();
		}
		return defaulthttpclient;
	}

	public class MySSLSocketFactory extends
			org.apache.http.conn.ssl.SSLSocketFactory {
		SSLContext sslContext = SSLContext.getInstance("TLS");

		public MySSLSocketFactory(KeyStore keyStore)
				throws NoSuchAlgorithmException, KeyManagementException,
				KeyStoreException, UnrecoverableKeyException {
			super(keyStore);
			X509TrustManager x509TrustManager = new X509TrustManager() {
				public void checkClientTrusted(
						X509Certificate[] paramArrayOfX509Certificate,
						String paramString) throws CertificateException {
				}

				public void checkServerTrusted(
						X509Certificate[] paramArrayOfX509Certificate,
						String paramString) throws CertificateException {
				}

				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			};
			SSLContext localSSLContext = this.sslContext;
			TrustManager[] arrayOfTrustManager = new TrustManager[1];
			arrayOfTrustManager[0] = x509TrustManager;
			localSSLContext.init(null, arrayOfTrustManager, null);
		}

		public Socket createSocket() throws IOException {
			return this.sslContext.getSocketFactory().createSocket();
		}

		public Socket createSocket(Socket paramSocket, String paramString,
				int paramInt, boolean paramBoolean) throws IOException,
				UnknownHostException {
			return this.sslContext.getSocketFactory().createSocket(paramSocket,
					paramString, paramInt, paramBoolean);
		}
	}
}