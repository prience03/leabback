package com.duolebo.appbase.prj.weather.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.duolebo.appbase.prj.Model;

public class SinaWeatherData extends Model {

	public String code;
	public List<WeatherCityData> cityDatas = new ArrayList<WeatherCityData>();

	@Override
	public boolean from(JSONObject json) {
		boolean res = false;
		if (json != null) {
			try {
				cityDatas.clear();
				;
				this.code = json.optJSONObject("status").optString("code");
				JSONArray cityArray = json.optJSONObject("data").optJSONArray(
						"city");

				cityDatas = new ArrayList<WeatherCityData>();

				if (cityArray != null) {
					for (int i = 0; i < cityArray.length(); i++) {
						JSONObject cityObject = cityArray.optJSONObject(i);

						WeatherCityData cityData = new WeatherCityData();
						cityData.from(cityObject);

						cityDatas.add(cityData);
					}
				}

				res = true;

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return res;
	}

	public String getCode() {
		return code;
	}

	public List<WeatherCityData> getCityDatas() {
		return cityDatas;
	}

	public WeatherCityData getFirstCityData() {
		WeatherCityData cityData = null;
		if (cityDatas.size() > 0) {
			cityData = cityDatas.get(0);
		}
		return cityData;
	}

	public WeatherDayData getFirstDayData() {
		WeatherDayData res = null;
		if (getFirstCityData() != null
				&& getFirstCityData().getWeatherDays().size() > 0) {
			res = getFirstCityData().getWeatherDays().get(0);
		}
		return res;
	}

	public static class WeatherDayData extends Model {

		// s1 s2 为天气描述，如晴转多云
		private String s1;
		private String s2;

		// f1 f2 天气图片
		private String f1;
		private String f2;

		// t1 t2 表示温度
		private String t1;
		private String t2;
		private String p1;
		private String p2;
		private String d1;
		private String d2;

		@Override
		public boolean from(JSONObject json) {
			boolean res = false;
			if (json != null) {
				this.s1 = json.optString("s1");
				this.s2 = json.optString("s2");
				this.f1 = json.optString("f1");
				this.f2 = json.optString("f2");
				this.t1 = json.optString("t1");
				this.t2 = json.optString("t2");
				this.p1 = json.optString("p1");
				this.p2 = json.optString("p2");
				this.d1 = json.optString("d1");
				this.d2 = json.optString("d2");

				res = true;
			}
			return res;
		}

		public String getS1() {
			return s1;
		}

		public String getS2() {
			return s2;
		}

		public String getF1() {
			return f1;
		}

		public String getF2() {
			return f2;
		}

		public String getT1() {
			return t1;
		}

		public String getT2() {
			return t2;
		}

		public String getP1() {
			return p1;
		}

		public String getP2() {
			return p2;
		}

		public String getD1() {
			return d1;
		}

		public String getD2() {
			return d2;
		}
	}

	public static class WeatherCityInfo extends Model {

		private String cityId;
		private String cityName;

		public String getCityId() {
			return cityId;
		}

		public String getCityName() {
			return cityName;
		}
	}

	public static class WeatherCityData extends Model {

		private WeatherCityInfo cityInfo;
		private List<WeatherDayData> weatherDays = new ArrayList<WeatherDayData>();
		private int index = -1;

		@Override
		public boolean from(JSONObject json) {

			boolean res = false;

			if (json != null) {
				try {
					weatherDays.clear();

					WeatherCityInfo info = new WeatherCityInfo();
					info.from(json.optJSONObject("info"));

					this.cityInfo = info;

					JSONArray dayArray = json.optJSONObject("days")
							.optJSONArray("day");
					if (dayArray != null) {
						for (int j = 0; j < dayArray.length(); j++) {
							JSONObject object = dayArray.optJSONObject(j);
							WeatherDayData dayEnity = new WeatherDayData();
							dayEnity.from(object);
							weatherDays.add(dayEnity);
						}
					}

					res = true;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			return res;
		}

		public WeatherCityInfo getCityInfo() {
			return cityInfo;
		}

		public List<WeatherDayData> getWeatherDays() {
			return weatherDays;
		}

		public WeatherDayData getNextDayData() {
			WeatherDayData dayData = null;
			if (weatherDays.size() > 0) {
				index ++ ;
				int i = index % weatherDays.size();
				dayData = weatherDays.get(i);
				index = i;
			}
			return dayData;
		}
		
		public boolean isFirstDay(){
			return index == 0;
		}
		
		public String getDayName(){
			String dayStr = "";
            switch (index) {
                case 1:
                    dayStr = "明天";
                    break;
                case 2:
                    dayStr = "后天";
                    break;
                case 0:
                default:
                    dayStr = "今天";
            }
            return dayStr;
		}
	}

}
