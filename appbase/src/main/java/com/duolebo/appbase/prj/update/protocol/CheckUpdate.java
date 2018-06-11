package com.duolebo.appbase.prj.update.protocol;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;

import com.duolebo.appbase.IModel;
import com.duolebo.appbase.IProtocol;
import com.duolebo.appbase.net.Parser.ParserClient.HttpMethod;
import com.duolebo.appbase.prj.Protocol;
import com.duolebo.appbase.prj.update.model.CheckUpdateData;
import com.duolebo.appbase.utils.Log;
import com.duolebo.appbase.utils.NetUtils;

public class CheckUpdate extends Protocol {
    
    private IProtocolConfig config = null;
    private CheckUpdateData data = new CheckUpdateData();
	private long startTime = 0l;
	private long lastCloseTime = 0l;
	private long onlineDuration = 0l;
	private String plat = null;
	private String version = null;
    private String channel = null;

	private final static int CONTENTTYPE_WIRED = 0;
	private final static int CONTENTTYPE_WIFI = 1;
	private final static int CONTENTTYPE_OTHRE = 2;

    private boolean noBody = false;

    public CheckUpdate(Context context, IProtocolConfig config) {
        super(context);
        this.config = config;
    }

    public CheckUpdate withNoBody(boolean noBody) {
        this.noBody = noBody;
        return this;
    }

    @Override
    public IModel getData() {
        return data;
    }
    
    @Override
    public boolean succeed() {
        if (super.succeed()) {
            return 0 == data.getCode();
        }
        return false;
    }

    @Override
    public int resultFormat() {
        if (null != config)
            return config.getResultFormat();

        return IProtocol.PROTOCOL_RESULT_FORMAT_JSON;
    }

    @Override
    public HttpMethod requestHttpMethod() {
        return HttpMethod.POST;
    }

    @Override
    public String prepareHttpRequestUrl() {
        if (null != config)
            return config.getCheckUpdateProtocolUrl();

        return null;
    }

    @Override
    public Map<String, String> prepareHttpParamters() {
        return null;
    }

    @Override
    public byte[] prepareHttpBody() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("<request key=\"CheckUpdate\">");

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
        if(!noBody) {
            for (Map.Entry<String, String> item : body.entrySet()) {
                sb.append("<").append(item.getKey()).append(">");
                sb.append(item.getValue());
                sb.append("</").append(item.getKey()).append(">");
            }
        }
		sb.append("</body>");

		sb.append("</request>");
		
		/// full protocol
		String string = sb.toString();
		Log.i("CheckUpdate", string);
		return string.getBytes();
    }

    @Override
    public Map<String, String> prepareHttpHeaders() {
        return null;
    }

	private void prepareProtocolHeader(Map<String, String> header) {
	    PackageManager pm = getContext().getPackageManager();
	    
	    if(null == version || "".equals(version.trim())) {
		    PackageInfo pInfo = null;
	        try {
	            pInfo = pm.getPackageInfo(getContext().getPackageName(), 0);
	        } catch (NameNotFoundException e) {
	            e.printStackTrace();
	        }
	        
	        version = (null == pInfo ? "0.0.0.0" : pInfo.versionName);
	    }

		header.put("client-version", version);
		header.put("user-agent", "AppBase");
		header.put("plat", (null == plat || TextUtils.isEmpty(plat)) ? "android" : plat);

        header.put("model", Build.MODEL);
        header.put("channel", channel);
		
		if (null != config && IProtocol.PROTOCOL_RESULT_FORMAT_XML == config.getResultFormat()) {
		    header.put("format", "xml");
		} else {
		    header.put("format", "json");
		}
	}

    @SuppressLint("NewApi")
    private void prepareProtocolBody(Map<String, String> body) {
		JSONObject systemInfo = new JSONObject();
		try {
            //业务账号，可与serialno序列号同 
            systemInfo.put("accountId", android.os.Build.SERIAL);        

            //设备ID，对应ro.product.device             
            systemInfo.put("deviceId", android.os.Build.DEVICE);        

            //本次启动时间，可设置为当前系统时间
            systemInfo.put("startTime", getStartTime());        

            //上次关闭时间，可空            
            systemInfo.put("lastCloseTime", getLastCloseTime());    

            //上次在线时长（秒），可空                       
            systemInfo.put("onlineDuration", getOnlineDuration());                     

            //网络接入状态（0:有线，1:无线，2:其他）           
            systemInfo.put("connectionType", getConnectionType());                       

            //网络连接方式（0:DHCP,1:PPPOE,2:STATIC IP）       
            systemInfo.put("connectionMode", getConnectionMode());                       

            //操作系统版本号，对应ro.build.version.release  
            systemInfo.put("systemVersion", android.os.Build.VERSION.RELEASE);                    

            //软件版本号，对应ro.build.version.incremental   
            systemInfo.put("softVersionNum", android.os.Build.VERSION.INCREMENTAL);                     

            //电视机分辨率（宽*高）                 
            DisplayMetrics dm = new DisplayMetrics();
            dm = getContext().getApplicationContext().getResources().getDisplayMetrics();
            systemInfo.put("TVResolutionFactor", dm.widthPixels + "*" + dm.heightPixels);

            //芯片型号，对应ro.product.model      
            systemInfo.put("chipModelNum", android.os.Build.MODEL);          

            //厂商,对应ro.product.manufacturer                  
            systemInfo.put("company", android.os.Build.MANUFACTURER);                        

        } catch (JSONException e) {
            e.printStackTrace();
        }

		body.put("serialno", "<![CDATA[" + android.os.Build.SERIAL + "]]>");
		body.put("macaddr", "<![CDATA[" + getMacAddress() + "]]>");
		body.put("uploadinfo", "<![CDATA[" + systemInfo.toString() + "]]>");
    }

    public CheckUpdate withStartTime(long startTime) {
        this.startTime = startTime;
        return this;
    }

    public CheckUpdate withLastCloseTime(long lastCloseTime) {
        this.lastCloseTime = lastCloseTime;
        return this;
    }

    public CheckUpdate withOnlineDuration(long onlineDuration) {
        this.onlineDuration = onlineDuration;
        return this;
    }

    public CheckUpdate withChannel(String channel) {
        this.channel = channel;
        return this;
    }
    
    public CheckUpdate withPlat(String plat) {
        this.plat = plat;
        return this;
    }
    
    public CheckUpdate withVersion(String version) {
        this.version = version;
        return this;
    }

    @SuppressLint("NewApi")
    String getStartTime() {
        if (0l < startTime)
            return DateFormat.format("yyyy-MM-dd hh:mm:ss", startTime).toString();
        
        return "";
    }

    @SuppressLint("NewApi")
    String getLastCloseTime() {
        if (0l < lastCloseTime)
            return DateFormat.format("yyyy-MM-dd hh:mm:ss", lastCloseTime).toString();
        
        return "";
    }

    String getOnlineDuration() {
        return String.valueOf(onlineDuration / 1000);
    }

    int getConnectionType() {
        int connectionType = CONTENTTYPE_OTHRE;
		ConnectivityManager connectivityManager = (ConnectivityManager)
		        getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

		if (null != connectivityManager) {
		    NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
		    for (int i = 0; null != info && i < info.length; i++) {
		        if (info[i].isConnected()) {
		            if(info[i].getTypeName().equalsIgnoreCase("wifi")) {
		                connectionType = CONTENTTYPE_WIFI;
		            } else {
		                connectionType = CONTENTTYPE_WIRED;
		            }
		        }
		    }
		}

        return connectionType;
    }

    int getConnectionMode() {
        return 0;
    }

    String getMacAddress() {
        String mac = NetUtils.getMACAddress("eth0");

        if (null == mac || TextUtils.isEmpty(mac))
            mac = NetUtils.getMACAddress("wlan0");

        return mac;
    }
}
