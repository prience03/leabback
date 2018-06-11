package com.duolebo.appbase.prj.bmtv.model;

import com.duolebo.appbase.prj.Model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 *   这个接口很乱 ，
 * @author wang
 * created at 2017/6/28 10:30
 */
public class GetInitIndexData extends ModelBase implements Serializable {

    private ArrayList<HomeEnity2> homeEnity2List = new ArrayList<HomeEnity2>();
    private ArrayList<GetContentListData.Content> contentlist = new ArrayList<GetContentListData.Content>();
    private ArrayList<HomeEnity2> homeAllChanelList = new ArrayList<HomeEnity2>();
    private boolean isNeedMore = true  ;
    private boolean isUseSLT;

    public GetInitIndexData (boolean isNeedMore ){
        this.isNeedMore = isNeedMore ;
    }

    public boolean isUseSLT() {
        return isUseSLT;
    }

    @Override
    public boolean from(JSONObject json) {
        if (super.from(json)) {

            json = json.optJSONObject("response").optJSONObject("body");

            //是否需要视联通
            isUseSLT = json.optBoolean("isUseSLT");
            JSONArray ja = json.optJSONArray("contentList");
            if (null == ja)
                return false;
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo = ja.optJSONObject(i);
                if (null != jo) {
                    HomeEnity2 home = new HomeEnity2();
                    home.parseJson(jo);
                    ArrayList<GetContentListData.Content> contentlistNew = new ArrayList<>();
                    JSONArray jsonArray = jo.optJSONArray("contentList");
                    for (int j = 0; j < jsonArray.length(); j++) {
                        JSONObject jo2 = jsonArray.optJSONObject(j);
                        GetContentListData.Content content = new GetContentListData.Content();
                        content.from(jo2);
//                        if (GetContentListData.Content.ContentType.Unknown == content.getContentType())
//                            continue;
                        contentlistNew.add(content);
                    }
                    if(isNeedMore)
                    {
                        if(Integer.parseInt(home.getType()) != 4){
                            GetContentListData.Content content = new GetContentListData.Content();
                            content.setIsMore("2");
                            contentlistNew.add(content);
                            homeAllChanelList.add(home);
                        }
                    }
                    home.setContentList(contentlistNew);
                    homeEnity2List.add(home);
                }
            }
            return true;
        }
        return false;
    }

    public ArrayList<HomeEnity2> getHomeAllChanelList() {
        return homeAllChanelList;
    }

    public ArrayList<HomeEnity2> getHomeEnity2List() {
        return homeEnity2List;
    }

    public static class HomeEnity2 extends Model{


        private String menuid;
        private String parentid;
        private String menutitle;
        private int order_no;
        private String is_autoplay;
        private String is_autoplay_pic;
        private String struct_type;
        private String type;
        private String act_type;
        private String show_type;
        private String icon_url;
        private String landscape_url;
        private String portrait_url;
        private long version;
        private String display_image;
        private long flashsale_starttime;
        private long flashsale_endtime;
        private String background_url;
        private String url_backgrounddetails;


        private List<GetContentListData.Content> contentList;


        public Model parseJson(JSONObject jSONObject) {
            if (jSONObject != null) {
                this.menuid = jSONObject.optString("menuid");
                this.parentid = jSONObject.optString("parentid");
                this.menutitle = jSONObject.optString("menutitle");
                this.order_no = jSONObject.optInt("order_no");
                this.is_autoplay = jSONObject.optString("is_autoplay");
                this.is_autoplay_pic = jSONObject.optString("is_autoplay_pic");
                this.struct_type = jSONObject.optString("struct_type");
                this.type = jSONObject.optString("type");
                this.act_type = jSONObject.optString("act_type");
                this.show_type = jSONObject.optString("show_type");
                this.icon_url = jSONObject.optString("icon_url");
                this.landscape_url = jSONObject.optString("landscape_url");
                this.portrait_url = jSONObject.optString("portrait_url");
                this.version = jSONObject.optLong("version");
                this.display_image = jSONObject.optString("display_image");
                this.flashsale_starttime = jSONObject.optLong("flashsale_starttime");
                this.flashsale_endtime = jSONObject.optLong("flashsale_endtime");
                this.background_url = jSONObject.optString("background_url");
                this.url_backgrounddetails = jSONObject.optString("url_backgrounddetails");
            }
            return this;
        }


        public String getMenuid() {
            return menuid;
        }

        public void setMenuid(String menuid) {
            this.menuid = menuid;
        }

        public String getParentid() {
            return parentid;
        }

        public void setParentid(String parentid) {
            this.parentid = parentid;
        }

        public String getMenutitle() {
            return menutitle;
        }

        public void setMenutitle(String menutitle) {
            this.menutitle = menutitle;
        }

        public int getOrder_no() {
            return order_no;
        }

        public void setOrder_no(int order_no) {
            this.order_no = order_no;
        }

        public String getIs_autoplay() {
            return is_autoplay;
        }

        public void setIs_autoplay(String is_autoplay) {
            this.is_autoplay = is_autoplay;
        }

        public String getIs_autoplay_pic() {
            return is_autoplay_pic;
        }

        public void setIs_autoplay_pic(String is_autoplay_pic) {
            this.is_autoplay_pic = is_autoplay_pic;
        }

        public String getStruct_type() {
            return struct_type;
        }

        public void setStruct_type(String struct_type) {
            this.struct_type = struct_type;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getAct_type() {
            return act_type;
        }

        public void setAct_type(String act_type) {
            this.act_type = act_type;
        }

        public String getShow_type() {
            return show_type;
        }

        public void setShow_type(String show_type) {
            this.show_type = show_type;
        }

        public String getIcon_url() {
            return icon_url;
        }

        public void setIcon_url(String icon_url) {
            this.icon_url = icon_url;
        }

        public String getLandscape_url() {
            return landscape_url;
        }

        public void setLandscape_url(String landscape_url) {
            this.landscape_url = landscape_url;
        }

        public String getPortrait_url() {
            return portrait_url;
        }

        public void setPortrait_url(String portrait_url) {
            this.portrait_url = portrait_url;
        }

        public long getVersion() {
            return version;
        }

        public void setVersion(long version) {
            this.version = version;
        }

        public String getDisplay_image() {
            return display_image;
        }

        public void setDisplay_image(String display_image) {
            this.display_image = display_image;
        }

        public long getFlashsale_starttime() {
            return flashsale_starttime;
        }

        public void setFlashsale_starttime(long flashsale_starttime) {
            this.flashsale_starttime = flashsale_starttime;
        }

        public long getFlashsale_endtime() {
            return flashsale_endtime;
        }

        public void setFlashsale_endtime(long flashsale_endtime) {
            this.flashsale_endtime = flashsale_endtime;
        }

        public String getBackground_url() {
            return background_url;
        }

        public void setBackground_url(String background_url) {
            this.background_url = background_url;
        }

        public String getUrl_backgrounddetails() {
            return url_backgrounddetails;
        }

        public void setUrl_backgrounddetails(String url_backgrounddetails) {
            this.url_backgrounddetails = url_backgrounddetails;
        }

        public List<GetContentListData.Content> getContentList() {
            return contentList;
        }

        public void setContentList(List<GetContentListData.Content> contentList) {
            this.contentList = contentList;
        }
    }
}
