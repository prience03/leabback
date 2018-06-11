package com.duolebo.appbase.prj.baidu.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.duolebo.appbase.prj.Model;

public class LocationData extends Model {
	public static final String TAG = LocationData.class.getName();
	public String zone;
	public String city;
	
	//根据IP从百度接口获取的城市
	private static String weatherCityFromBaidu;
	//从百度获取的地区 省|市|区
	private static String zoneFromBaidu;
	//人工设置的地区 省|市|区
	private static String zoneMenual;
	
	
	@Override
	public boolean from(JSONObject json) {
		boolean res = false;
		if(json != null ){
			try {
				String status = json.getString("status");
				if("0".equals(status)){
					JSONArray datas = json.getJSONArray("data");
					if(datas!=null  && datas.length()>=1){
						JSONObject data = datas.getJSONObject(0);
						String location= data.getString("location");
						this.city = parseCity(location);
						this.zone = locationFormat(location);
						weatherCityFromBaidu = this.city;
						zoneFromBaidu = this.zone;
						return true;
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return res;
	}

	private String parseCity(String location){
		String city ="";
		String key = "";
		if(location!=null && !"".equals(location)){
			if(location.contains("香港")){
				city = "香港";
			}else if(location.contains("澳门")){
				city = "澳门";
			}else{
				if(location.contains("自治区")){
					key = "自治区";
				}else if(location.contains("省")){
					key = "省";
				}
				try{
					Pattern pt = Pattern.compile(key+"(.+?)市");
					Matcher mh = pt.matcher(location);
					mh.find();
					city = mh.group(1);
				}catch(Exception e){
					city="";
				}
			}
		}
		return city;
	}
	
	private String locationFormat(String location){
		StringBuffer buffer = new StringBuffer();
		int emptyIndex = location.indexOf(" ");
		if(emptyIndex !=-1)
			location = location.substring(0, emptyIndex);
		if( location!=null && !"".equals(location) ){
			int index = location.indexOf("省");
			if(index !=-1){
				buffer.append(location.substring(0,index+1));
				buffer.append("|");
				buffer.append(location.substring(index+1));
			}else{
				index = location.indexOf("自治区");
				if(index!=-1){
					buffer.append(location.substring(0,index+3));
					buffer.append("|");
					buffer.append(location.substring(index+3));
				}else{
					buffer.append(location.substring(0,location.indexOf("")));
					buffer.append("|");
					buffer.append(location);
				}
			}
		}
		return buffer.toString();
	}

	public static String getWeatherCityFromBaidu() {
		return weatherCityFromBaidu;
	}
	
	public static void setZoneMenual(String zoneMenu) {
		LocationData.zoneMenual = zoneMenu;
	}

	public static String getZone(){
		if(zoneMenual!=null && !"".equals(zoneMenual)){
			return zoneMenual;
		}else{
			return zoneFromBaidu;
		}
	}
	
}
