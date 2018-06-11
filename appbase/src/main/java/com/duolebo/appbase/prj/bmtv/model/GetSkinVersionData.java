package com.duolebo.appbase.prj.bmtv.model;

import android.text.TextUtils;

import com.duolebo.appbase.prj.Model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class GetSkinVersionData extends ModelBase implements Serializable {

    private static final String TAG = "GetSkinVersionData";

    private SkinModel skinModel = new SkinModel();

    public GetSkinVersionData() {
    }

    @Override
    public boolean from(JSONObject json) {
        if (super.from(json)) {
            json = json.optJSONObject("response").optJSONObject("body").optJSONObject("IndexBGImg");
            skinModel.from(json);
            return true;
        }
        return false;
    }

    public SkinModel getSkinModel() {
        return skinModel;
    }

    public static class SkinModel extends Model implements Serializable{
        private int index_id;
        private String home_bg;
        private String second_bg;
        private String top_icon_color;
        private String skin_version;
        private HomeTopBean home_top;

        @Override
        public boolean from(JSONObject json) {
            if (super.from(json)) {
                skin_version = json.optString("skin_version");
                if(TextUtils.isEmpty(skin_version)){
                    return true;
                }
                index_id = json.optInt("index_id");
                home_bg = json.optString("home_bg");
                second_bg = json.optString("second_bg");
                top_icon_color = json.optString("top_icon_color");
                JSONObject js_icon = json.optJSONObject("home_top");
                home_top = new HomeTopBean();
                home_top.setTop_icon_color(js_icon.optString("top_icon_color"));
                JSONArray jsonArray = js_icon.optJSONArray("bg_icon");
                List<HomeTopBean.BgIconBean> bgIconBeanList = new ArrayList<>();
                for (int i=0 ;i < jsonArray.length() ; i++){
                    JSONObject jsonObject = jsonArray.optJSONObject(i);
                    HomeTopBean.BgIconBean bgIconBean = new HomeTopBean.BgIconBean();
                    bgIconBean.setIcon_normal(jsonObject.optString("icon_normal"));
                    bgIconBean.setIcon_select(js_icon.optString("icon_select"));
                    bgIconBean.setIcon_text(js_icon.optString("icon_text"));
                    bgIconBeanList.add(bgIconBean);
                }
                home_top.setBg_icon(bgIconBeanList);
                return  true ;
            }
            return false;
        }

        public int getIndex_id() {
            return index_id;
        }

        public void setIndex_id(int index_id) {
            this.index_id = index_id;
        }

        public String getHome_bg() {
            return home_bg;
        }

        public void setHome_bg(String home_bg) {
            this.home_bg = home_bg;
        }

        public String getSecond_bg() {
            return second_bg;
        }

        public void setSecond_bg(String second_bg) {
            this.second_bg = second_bg;
        }

        public String getTop_icon_color() {
            return top_icon_color;
        }

        public void setTop_icon_color(String top_icon_color) {
            this.top_icon_color = top_icon_color;
        }

        public String getSkin_version() {
            return skin_version;
        }

        public void setSkin_version(String skin_version) {
            this.skin_version = skin_version;
        }

        public HomeTopBean getHome_top() {
            return home_top;
        }

        public void setHome_top(HomeTopBean home_top) {
            this.home_top = home_top;
        }

        public static class HomeTopBean {
            private String top_icon_color;
            private List<BgIconBean> bg_icon;

            public String getTop_icon_color() {
                return top_icon_color;
            }

            public void setTop_icon_color(String top_icon_color) {
                this.top_icon_color = top_icon_color;
            }

            public List<BgIconBean> getBg_icon() {
                return bg_icon;
            }

            public void setBg_icon(List<BgIconBean> bg_icon) {
                this.bg_icon = bg_icon;
            }

            public static class BgIconBean {
                /**
                 * icon_normal : http://192.168.1.8:8080/apkimg/pic/index/icon_normal_file20180308130835.png
                 * icon_select : http://192.168.1.8:8080/apkimg/pic/index/icon_select_file20180308130835.png
                 * icon_text : 搜索
                 */

                private String icon_normal;
                private String icon_select;
                private String icon_text;

                public String getIcon_normal() {
                    return icon_normal;
                }

                public void setIcon_normal(String icon_normal) {
                    this.icon_normal = icon_normal;
                }

                public String getIcon_select() {
                    return icon_select;
                }

                public void setIcon_select(String icon_select) {
                    this.icon_select = icon_select;
                }

                public String getIcon_text() {
                    return icon_text;
                }

                public void setIcon_text(String icon_text) {
                    this.icon_text = icon_text;
                }
            }
        }
    }




}
