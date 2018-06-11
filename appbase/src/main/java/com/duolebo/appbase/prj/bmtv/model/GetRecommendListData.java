package com.duolebo.appbase.prj.bmtv.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetRecommendListData extends ModelBase {

    private int totalsize = -1;
    private ArrayList<GetContentListData.Content> recommendlist = new ArrayList<GetContentListData.Content>();

    @Override
    public boolean from(JSONObject json) {
        if (super.from(json)) {
            json = json.optJSONObject("response").optJSONObject("body");
            totalsize = json.optInt("totalsize");
            JSONArray ja = json.optJSONArray("recommend_list");
            for(int i=0; null!=ja && i<ja.length(); i++) {
                JSONObject jo = ja.optJSONObject(i);
                GetContentListData.Content c = new GetContentListData.Content();
                if(c.from(jo)) recommendlist.add(c);
            }
            return true;
        }
        return false;
    }



    public int getTotalsize() {
        return totalsize;
    }

    public ArrayList<GetContentListData.Content> getRecommendlist() {
        return recommendlist;
    }

}
