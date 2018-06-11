package com.duolebo.appbase.prj.baidu.protocol;

import java.net.URLDecoder;
import java.util.Map;

import android.content.Context;

import com.duolebo.appbase.IModel;
import com.duolebo.appbase.prj.Protocol;
import com.duolebo.appbase.prj.baidu.model.LocationData;
import com.duolebo.appbase.prj.bmtv.model.ModelBase;

public class LocationProtocol extends Protocol {
	private LocationData data = new LocationData();
    private String ipAddress ;
    public LocationProtocol(Context context) {
        super(context);
        // Auto-generated constructor stub
    }

    public LocationProtocol(Protocol protocol) {
        super(protocol);
        // Auto-generated constructor stub
    }
    public  LocationProtocol withData(String ipAddress){
        this.ipAddress = ipAddress ;
        return this ;
    }

    @Override
    public IModel getData() {
        return data;
    }

    @Override
    public int resultFormat() {
        return 0;
    }

    @Override
    public String prepareHttpRequestUrl() {
    	String locationUrl = "http://opendata.baidu.com/api.php?query="
                + ipAddress
                + "&co=&resource_id=6006&t=1392605558255&ie=utf8&oe=utf8&cb=op_aladdin_callback&format=json&tn=baidu&cb=";
        return locationUrl;
    }

    @Override
    public Map<String, String> prepareHttpParamters() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public byte[] prepareHttpBody() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, String> prepareHttpHeaders() {
        // TODO Auto-generated method stub
        return null;
    }

}
