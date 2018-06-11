/**
 * 
 */
package com.duolebo.appbase.prj.bmtv.model;

import com.duolebo.appbase.prj.ContrastList;
import com.duolebo.appbase.prj.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author wang
 * created at 2017/9/25
 */
public class GetTestActionData extends ModelBase {

	private static final String TAG = "GetTestActionData";
	private ArrayList<GetTestActionData.Content> contentlist = new ArrayList<GetTestActionData.Content>();

	@Override
	public boolean from(JSONObject json) {
	    if (super.from(json)) {
			json = json.optJSONObject("response").optJSONObject("body");
			String str= json.optString("test");
			try {
				JSONObject jO = new JSONObject(Decryption(str).toString());
				JSONArray ja = jO.getJSONArray("testLPlist");
				if (null != ja) {
					for (int i=0; i<ja.length(); i++) {
						JSONObject jo = ja.optJSONObject(i);
						GetTestActionData.Content content = new GetTestActionData.Content();
						content.from(jo);
						contentlist.add(content);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return true;
	    }
	    return false;
	}

	ContrastList C = new ContrastList();
	Map<Character, String> maplist = C.maplist;
	/**
	 * 加密
	 */
	public StringBuffer Encryption(String str) {
		StringBuffer cipherStr = new StringBuffer(" "); // 密文字符串
		char plaintext[] = str.toCharArray(); // 明文
		for (int i = 0; i < plaintext.length; i++) {
			char tmp = plaintext[i];
			/* 字母 */
			if (maplist.containsKey(tmp))
				cipherStr.append(maplist.get(tmp)).append(" "); // 追加空格
		}
		return cipherStr;
	}

	/**
	 * 解密
	 */
	public  StringBuffer Decryption(String cipherStr) {
		StringBuffer plainStr = new StringBuffer(" "); // 明文字符串
		String ciphertext[] = cipherStr.toString().split(" "); // 密文
		for (int i = 1; i < ciphertext.length; i++) {
			String tmp = ciphertext[i];
			/* 字母 */
			if (maplist.containsValue(tmp)) {
				for (Map.Entry<Character, String> s : maplist.entrySet()) {
					if (tmp.equals(s.getValue())) {
						plainStr.append(s.getKey().toString());
					}
				}
			}
		}
		return plainStr;
	}






	public ArrayList<Content> getContentlist() {
		return contentlist;
	}

	public static class Content extends Model {

		private String url = "";
		private String frequency = "";
		private String activity = "";

		@Override
		public boolean from(JSONObject json) {
			if (super.from(json)) {
				url = json.optString("url");
				frequency = json.optString("frequency");
				activity = json.optString("activity");
				return true;
			}
			return false;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getFrequency() {
			return frequency;
		}

		public void setFrequency(String frequency) {
			this.frequency = frequency;
		}

		public String getActivity() {
			return activity;
		}

		public void setActivity(String activity) {
			this.activity = activity;
		}

		@Override
		public String toString() {
			return "Content{" +
					"url='" + url + '\'' +
					", frequency='" + frequency + '\'' +
					", activity='" + activity + '\'' +
					'}';
		}
	}



}
