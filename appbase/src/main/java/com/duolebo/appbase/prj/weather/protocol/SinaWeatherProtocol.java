package com.duolebo.appbase.prj.weather.protocol;

import java.net.URLEncoder;
import java.util.Map;

import android.content.Context;

import com.duolebo.appbase.IModel;
import com.duolebo.appbase.prj.Protocol;
import com.duolebo.appbase.prj.weather.model.SinaWeatherData;

public class SinaWeatherProtocol extends Protocol {
	
	private SinaWeatherData weatherData = new SinaWeatherData();
	private String citys;

	public SinaWeatherProtocol(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public SinaWeatherProtocol withCitys(String citys) {
		try {
			this.citys = URLEncoder.encode(citys, "utf-8");
		} catch(Exception e) {
			e.printStackTrace();
		}
		return this;
	}

	@Override
	public IModel getData() {
		// TODO Auto-generated method stub
		return weatherData;
	}

	@Override
	public int resultFormat() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String prepareHttpRequestUrl() {
		
		String url = "http://platform.sina.com.cn/weather/forecast?app_key=2872801998&city="
				+ citys + "&startday=0&lenday=3&format=json";
		return url;
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
