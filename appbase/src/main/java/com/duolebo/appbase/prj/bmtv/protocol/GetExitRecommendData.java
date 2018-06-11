package com.duolebo.appbase.prj.bmtv.protocol;

import com.duolebo.appbase.prj.bmtv.model.GetContentListData;
import com.duolebo.appbase.prj.bmtv.model.ModelBase;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by lxn on 2017/9/3.
 */

public class GetExitRecommendData extends ModelBase implements Serializable {
    private ArrayList<GetContentListData.Content> recommendlist = new ArrayList<GetContentListData.Content>();
    private int totalsize = -1;
    public GetExitRecommendData() {
    }

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
