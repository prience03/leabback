package com.duolebo.appbase.prj.bmtv.model;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/2.
 */

public class GetScreenData extends ModelBase {

    private List<String> type = new ArrayList<>();
    private List<List<String>> datas = new ArrayList<>();

    @Override
    public boolean from(JSONObject json) {
        if (super.from(json)) {
            json = json.optJSONObject("response").optJSONObject("body");
            JSONArray searchType = json.optJSONArray("searchType");
            for (int i = 0; i < searchType.length(); i++) {
                try {
                    JSONArray jsonArray = searchType.getJSONArray(i);
                    String strKey = jsonArray.getString(0);
                    type.add(strKey);
                    JSONArray rserData = jsonArray.getJSONArray(1);
                    List<String> values = new ArrayList<>();
                    for (int j = 0; j < rserData.length(); j++) {
                        String string = rserData.getString(j);
                        //地区转换
                        string = getString(strKey, string);
                        values.add(string);
                    }
                    datas.add(values);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            return true;
        }
        return false;
    }

    private String getString(String strKey, String string) {
        if("regions".equals(strKey)) {
            if("0".equals(string)) {
                string = "中国";
            }
            if("1".equals(string)) {
                string = "日韩";
            }
            if("2".equals(string)) {
                string = "欧美";
            }
//            if("3".equals(string)) {
//                string = "美国";
//            }
//            if("4".equals(string)) {
//                string = "日本";
//            }
//            if("5".equals(string)) {
//                string = "韩国";
//            }
//            if("6".equals(string)) {
//                string = "英国";
//            }
//            if("7".equals(string)) {
//                string = "法国";
//            }
//            if("8".equals(string)) {
//                string = "泰国";
//            }
//            if("9".equals(string)) {
//                string = "印度";
//            }
        }
        return string;
    }

    public List<String> getType() {
        return type;
    }

    public List<List<String>> getDatas() {
        return datas;
    }

}
