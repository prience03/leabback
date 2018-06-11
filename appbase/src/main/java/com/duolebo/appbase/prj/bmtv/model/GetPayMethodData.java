package com.duolebo.appbase.prj.bmtv.model;

import com.duolebo.appbase.prj.Model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @author advu
 * @date 2016年11月16日
 *       获取支付类型
 */
public class GetPayMethodData extends ModelBase {

    private ArrayList<GetPayMethodData.Content> contentlist = new ArrayList<GetPayMethodData.Content>();

    private String order_id = "";
    private String order_code = "";
    private String order_status = "";


    @Override
    public boolean from(JSONObject json) {
        if (super.from(json)) {
	        json = json.optJSONObject("response").optJSONObject("body");
            order_id = json.optString("order_id");
            order_code = json.optString("order_code");
            order_status = json.optString("order_status");
            JSONArray ja = json.optJSONArray("methods");
            if (null != ja) {
                for (int i=0; i<ja.length(); i++) {
                    JSONObject jo = ja.optJSONObject(i);
                    GetPayMethodData.Content content = new GetPayMethodData.Content();
                    content.from(jo);
                    contentlist.add(content);
                }
            }
            return true;
        }
        return false;
    }

    public String getOrder_id() {
        return order_id;
    }

    public String getOrder_code() {
        return order_code;
    }

    public String getOrder_status() {
        return order_status;
    }

    public ArrayList<GetPayMethodData.Content> getContentList() {
        return contentlist;
    }

    public static class Content extends Model {
        private String _type = "";
        private String _desc = "";
        private String _url = "";

        @Override
        public boolean from(JSONObject json) {
            if (super.from(json)) {
                _type = json.optString("type");
                _desc = json.optString("desc");
                _url = json.optString("url");

                return true;
            }
            return false;
        }

        public String get_type() {
            return _type;
        }

        public void set_type(String _type) {
            this._type = _type;
        }

        public String get_desc() {
            return _desc;
        }

        public void set_desc(String _desc) {
            this._desc = _desc;
        }

        public String get_url() {
            return _url;
        }

        public void set_url(String _url) {
            this._url = _url;
        }

        @Override
        public String toString() {
            return "Content{" +
                    "_type='" + _type + '\'' +
                    ", _desc='" + _desc + '\'' +
                    ", _url='" + _url + '\'' +
                    '}';
        }
    }


}
