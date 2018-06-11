package com.duolebo.appbase.prj.bmtv.model;

import com.duolebo.appbase.prj.Model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @author advu
 * @date 2016年11月11日
 */
public class GetVipPacksData extends ModelBase {
    
    private ArrayList<GetVipPacksData.Content> contentlist = new ArrayList<GetVipPacksData.Content>();

    @Override
    public boolean from(JSONObject json) {
        if (super.from(json)) {
	        json = json.optJSONObject("response").optJSONObject("body");
            JSONArray ja = json.optJSONArray("tarifflist");
            if (null != ja) {
                for (int i=0; i<ja.length(); i++) {
                    JSONObject jo = ja.optJSONObject(i);
                    GetVipPacksData.Content content = new GetVipPacksData.Content();
                    content.from(jo);
                    contentlist.add(content);
                }
            }
            return true;
        }
        return false;
    }

    public ArrayList<GetVipPacksData.Content> getContentList() {
        return contentlist;
    }

    public static class Content extends Model {

        private String contentid = "";
        private String contentname = "";
        private String contentlogo = "";
        private String price = "";
        private String type = "";

        @Override
        public boolean from(JSONObject json) {
            if (super.from(json)) {
                contentid = json.optString("id");
                contentname = json.optString("name");
                contentlogo = json.optString("logo");
                price = json.optString("price");
                type = json.optString("type");
                return true;
            }
            return false;
        }


        public String getContentid() {
            return contentid;
        }

        public String getType() {
            return type;
        }

        public void setContentid(String contentid) {
            this.contentid = contentid;
        }

        public String getContentname() {
            return contentname;
        }

        public void setContentname(String contentname) {
            this.contentname = contentname;
        }

        public String getContentlogo() {
            return contentlogo;
        }

        public void setContentlogo(String contentlogo) {
            this.contentlogo = contentlogo;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        @Override
        public String toString() {
            return "Content{" +
                    "contentid='" + contentid + '\'' +
                    ", contentname='" + contentname + '\'' +
                    ", contentlogo='" + contentlogo + '\'' +
                    ", price='" + price + '\'' +
                    '}';
        }
    }


}
