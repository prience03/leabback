/**
 *
 */
package com.duolebo.appbase.prj.bmtv.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.text.TextUtils;

import com.duolebo.appbase.db.IRecord;
import com.duolebo.appbase.prj.Model;
import com.duolebo.appbase.prj.bmtv.protocol.GetContentList;
import com.duolebo.appbase.prj.bmtv.protocol.IProtocolConfig;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zlhl
 * @date 2014年2月17日
 */
public class GetMenuData extends ModelBase {

    private ArrayList<Menu> menulist = new ArrayList<Menu>();
    private JSONObject display_cells = null;

    @Override
    public boolean from(JSONObject json) {
        if (super.from(json)) {
            json = json.optJSONObject("response").optJSONObject("body");
            display_cells = json.optJSONObject("display_cells");
            JSONArray ja = json.optJSONArray("menulist");
            if (null == ja)
                return false;

            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo = ja.optJSONObject(i);
                if (null != jo) {
                    Menu menu = new Menu();
                    menu.from(jo);
                    menulist.add(menu);
                }
            }

            for (Menu menu : menulist) {
                ArrayList<Menu> subs = new ArrayList<Menu>();
                for (Menu sub : menulist) {
                    if (menu.getMenuId().equalsIgnoreCase(sub.getParentId())) {
                        subs.add(sub);
                    }
                }
                menu.setSubMenus(subs);
            }

            return true;
        }
        return false;
    }

    public JSONObject getDisplayCells() {
        return display_cells;
    }

    public ArrayList<Menu> getMenulist() {
        return menulist;
    }

    @Deprecated
    public ArrayList<Menu> getRootMenuList() {
        return getSubMenuList("0");
    }

    public ArrayList<Menu> getSubMenuList(String menuid) {
        ArrayList<Menu> subMenuList = new ArrayList<Menu>();
        for (Menu menu : menulist) {
            if (menuid.equalsIgnoreCase(menu.getParentId()))
                subMenuList.add(menu);
        }
        return subMenuList;
    }

    public JSONArray getRootMenuJSONArray() {
        return getSubMenuJSONArray("0");
    }

    public JSONArray getSubMenuJSONArray(String menuid) {
        JSONObject json = getJSON();
        json = json.optJSONObject("response").optJSONObject("body");
        JSONArray ja = new JSONArray();
        JSONArray jm = json.optJSONArray("menulist");
        for (int i = 0; null != menuid && i < jm.length(); i++) {
            if (null != jm.optJSONObject(i) && menuid.equalsIgnoreCase(jm.optJSONObject(i).optString("parentid")))
                ja.put(jm.opt(i));
        }
        return ja;
    }

    public Menu findByMenuId(String menuId) {
        if (!TextUtils.isEmpty(menuId)) {
            for (Menu menu : menulist) {
                if (menuId.equalsIgnoreCase(menu.getMenuId()))
                    return menu;
            }
        }
        return null;
    }

    public static class Menu extends Model implements Serializable {

        public enum ActType {

            ShowEntries("ShowEntries"),
            getChannel("getChannel"),
            getMenu("getMenu"),
            getHot("getHot"),
            Search("Search"),
            MySpace("MySpace"),
            ShowApps("ShowApps"),
            LocalApps("LocalApps"),
            HyperLink("HyperLink"),
            Ali("Ali"),
            FlashSale("FlashSale"),
            Unknown("Unknown");

            private String val;

            ActType(String val) {
                this.val = val;
            }

            public String toString() {
                return this.val;
            }

            public static ActType fromString(String val) {
                for (ActType at : ActType.values()) {
                    if (at.val.equalsIgnoreCase(val)) {
                        return at;
                    }
                }
                return Unknown;
            }
        }


        public enum StructType {

            SubMenu("1"),
            SubContent("2"),
            SubMenuContentMix("3"),
            Unknown("Unknown");

            private String val;

            StructType(String val) {
                this.val = val;
            }

            public String toString() {
                return this.val;
            }

            public static StructType fromString(String val) {
                for (StructType st : StructType.values()) {
                    if (st.val.equalsIgnoreCase(val)) {
                        return st;
                    }
                }
                return Unknown;
            }
        }

        private String menuid = null;
        private String parentid = null;
        private String menutitle = "";
        private int order_no = 0;
        private StructType struct_type = StructType.Unknown;
        private ActType act_type = ActType.Unknown;
        private String link_ur =  "";
        private String icon_url = "";
        private String img_url = "";
        private String landscape_url = "";
        private String portrait_url = "";
        private String islocal = "";
        private String type = "";
        private int is_autoplay = 0;
        private int is_autoplay_pic = 0;
        private long flashsale_starttime = 0;
        private long flashsale_endtime = 0;
        private String background_url = "";
        private String columntemplate ="" ;
        private String intro = "";
        private GetContentListData.Content content = null;

        private long version = 0l;

        private GetContentList contentList = null;
        private List<Menu> subMenus;

        @Override
        public boolean from(JSONObject json) {
            if (super.from(json)) {
                menuid = json.optString(Fields.MENU_ID);
                parentid = json.optString(Fields.PARENT_ID);
                menutitle = json.optString(Fields.MENU_TITLE);
                link_ur = json.optString(Fields.LINK_URL);
                order_no = json.optInt(Fields.ORDER_NO);
                struct_type = StructType.fromString(json.optString(Fields.STRUCT_TYPE));
                act_type = ActType.fromString(json.optString(Fields.ACT_TYPE));
                icon_url = json.optString(Fields.ICON_URL);
                img_url = json.optString(Fields.IMG_URL);
                landscape_url = json.optString(Fields.LANDSCAPE_URL);
                portrait_url = json.optString(Fields.PORTRAIT_URL);
                version = json.optLong(Fields.VERSION);
                islocal = json.optString(Fields.IS_LOCAL);
                type = json.optString(Fields.TYPE);
                is_autoplay = json.optInt("is_autoplay", 0);
                is_autoplay_pic = json.optInt("is_autoplay_pic", 0);
                flashsale_starttime = json.optLong(Fields.FLASHSALE_STARTTIME);
                flashsale_endtime = json.optLong(Fields.FLASHSALE_ENDTIME);
                background_url = json.optString(Fields.BACKGROUND_URL);
                columntemplate = json.optString(Fields.columntemplate);
                intro = json.optString(Fields.INTRO);
                if (TextUtils.isEmpty(background_url))
                    // keep backward compatible.
                    background_url = json.optString(Fields.BACKGROUND);

                if (json.has("content")) {
                    content = new GetContentListData.Content();
                    content.from(json.optJSONObject("content"));
                }
                return true;
            }
            return false;
        }

        public interface Fields extends BaseColumns {
            String MENU_ID = "menuid";
            String PARENT_ID = "parentid";
            String MENU_TITLE = "menutitle";
            String ORDER_NO = "order_no";
            String LINK_URL = "link_url";
            String STRUCT_TYPE = "struct_type";
            String ACT_TYPE = "act_type";
            String ICON_URL = "icon_url";
            String IMG_URL = "img_url";
            String LANDSCAPE_URL = "landscape_url";
            String PORTRAIT_URL = "portrait_url";
            String VERSION = "version";
            String IS_LOCAL = "islocal";
            String TYPE = "type";
            String FLASHSALE_STARTTIME = "flashsale_starttime";
            String FLASHSALE_ENDTIME = "flashsale_endtime";
            @Deprecated String BACKGROUND = "background";
            String BACKGROUND_URL = "background_url";
            String INTRO = "intro";
            String columntemplate = "columntemplate";

        }

        @Override
        public void prepareFieldDefs(ArrayList<String> fieldDefs) {
            super.prepareFieldDefs(fieldDefs);
            fieldDefs.add(Fields.MENU_ID + IRecord.FIELD_TYPE_TEXT);
            fieldDefs.add(Fields.PARENT_ID + IRecord.FIELD_TYPE_TEXT);
            fieldDefs.add(Fields.LINK_URL + IRecord.FIELD_TYPE_TEXT);
            fieldDefs.add(Fields.MENU_TITLE + IRecord.FIELD_TYPE_TEXT);
            fieldDefs.add(Fields.ORDER_NO + IRecord.FIELD_TYPE_INTEGER);
            fieldDefs.add(Fields.STRUCT_TYPE + IRecord.FIELD_TYPE_TEXT);
            fieldDefs.add(Fields.ACT_TYPE + IRecord.FIELD_TYPE_TEXT);
            fieldDefs.add(Fields.ICON_URL + IRecord.FIELD_TYPE_TEXT);
            fieldDefs.add(Fields.IMG_URL + IRecord.FIELD_TYPE_TEXT);
            fieldDefs.add(Fields.LANDSCAPE_URL + IRecord.FIELD_TYPE_TEXT);
            fieldDefs.add(Fields.PORTRAIT_URL + IRecord.FIELD_TYPE_TEXT);
            fieldDefs.add(Fields.VERSION + IRecord.FIELD_TYPE_INTEGER);
            fieldDefs.add(Fields.IS_LOCAL + IRecord.FIELD_TYPE_TEXT);
            fieldDefs.add(Fields.TYPE+IRecord.FIELD_TYPE_TEXT);
            fieldDefs.add(Fields.FLASHSALE_STARTTIME + IRecord.FIELD_TYPE_NUMERIC);
            fieldDefs.add(Fields.FLASHSALE_ENDTIME + IRecord.FIELD_TYPE_NUMERIC);
            fieldDefs.add(Fields.BACKGROUND + IRecord.FIELD_TYPE_TEXT /* keep backward compatible */);
            fieldDefs.add(Fields.columntemplate + IRecord.FIELD_TYPE_TEXT);

        }

        @Override
        public void readFieldValues(Cursor c) {
            super.readFieldValues(c);
            menuid = c.getString(c.getColumnIndex(Fields.MENU_ID));
            parentid = c.getString(c.getColumnIndex(Fields.PARENT_ID));
            menutitle = c.getString(c.getColumnIndex(Fields.MENU_TITLE));
            link_ur = c.getString(c.getColumnIndex(Fields.LINK_URL));
            order_no = c.getInt(c.getColumnIndex(Fields.ORDER_NO));
            struct_type = StructType.fromString(c.getString(c.getColumnIndex(Fields.STRUCT_TYPE)));
            act_type = ActType.fromString(c.getString(c.getColumnIndex(Fields.ACT_TYPE)));
            icon_url = c.getString(c.getColumnIndex(Fields.ICON_URL));
            img_url = c.getString(c.getColumnIndex(Fields.IMG_URL));
            landscape_url = c.getString(c.getColumnIndex(Fields.LANDSCAPE_URL));
            portrait_url = c.getString(c.getColumnIndex(Fields.PORTRAIT_URL));
            version = c.getLong(c.getColumnIndex(Fields.VERSION));
            islocal = c.getString(c.getColumnIndex(Fields.IS_LOCAL));
            type = c.getString(c.getColumnIndex(Fields.TYPE));
            flashsale_starttime = c.getLong(c.getColumnIndex(Fields.FLASHSALE_STARTTIME));
            flashsale_endtime = c.getLong(c.getColumnIndex(Fields.FLASHSALE_ENDTIME));
            background_url = c.getString(c.getColumnIndex(Fields.BACKGROUND /* keep backward compatible */));
            intro = c.getString(c.getColumnIndex(Fields.INTRO));
            columntemplate = c.getString(c.getColumnIndex(Fields.columntemplate));
        }

        @Override
        public void writeFieldValues(ContentValues values) {
            super.writeFieldValues(values);
            values.put(Fields.MENU_ID, menuid);
            values.put(Fields.PARENT_ID, parentid);
            values.put(Fields.MENU_TITLE, menutitle);
            values.put(Fields.ORDER_NO, order_no);
            values.put(Fields.STRUCT_TYPE, struct_type.toString());
            values.put(Fields.ACT_TYPE, act_type.toString());
            values.put(Fields.LINK_URL, link_ur.toString());
            values.put(Fields.ICON_URL, icon_url);
            values.put(Fields.IMG_URL, img_url);
            values.put(Fields.LANDSCAPE_URL, landscape_url);
            values.put(Fields.PORTRAIT_URL, portrait_url);
            values.put(Fields.VERSION, version);
            values.put(Fields.IS_LOCAL, islocal);
            values.put(Fields.TYPE,  type);
            values.put(Fields.FLASHSALE_STARTTIME, flashsale_starttime);
            values.put(Fields.FLASHSALE_ENDTIME, flashsale_endtime);
            values.put(Fields.BACKGROUND /* keep backward compatible */, background_url);
            values.put(Fields.INTRO, intro);
            values.put(Fields.columntemplate, columntemplate);

        }

        public String getLink_ur() {
            return link_ur;
        }

        public void setLink_ur(String link_ur) {
            this.link_ur = link_ur;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getLandscapeUrl() {
            return landscape_url;
        }

        public String getPortraitUrl() {
            return portrait_url;
        }

        public String getMenuId() {
            return menuid;
        }

        public String getParentId() {
            return parentid;
        }

        public String getMenuTitle() {
            return menutitle;
        }

        public int getOrderNo() {
            return order_no;
        }

        public StructType getStructType() {
            return struct_type;
        }

        public ActType getActType() {
            return act_type;
        }

        public String getIconUrl() {
            return icon_url;
        }

        public String getImgUrl() {
            return img_url;
        }

        public String getIslocal() {
            return islocal;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public boolean getIsAutoPlay() {
            return is_autoplay == 1;
        }

        public boolean getIsAutoPlayPic() {
            return is_autoplay_pic == 1;
        }

        public GetContentListData.Content getContent() {
            return content;
        }

        public long getVersion() {
            return version;
        }

        public GetContentList getContentList(Context context, IProtocolConfig config) {
            if (null == contentList) {
                contentList = new GetContentList(context, config);
                contentList.withMenu(menuid);
                contentList.withPageSize(20);
                contentList.setCacheEnabled(ActType.FlashSale != getActType());
            }
            return contentList;
        }

        public List<Menu> getSubMenus() {
            return subMenus;
        }

        void setSubMenus(List<Menu> subMenus) {
            this.subMenus = subMenus;
        }

        public long getFlashsaleStarttime() {
            return flashsale_starttime;
        }

        public void setFlashsaleStarttime(long flashsale_starttime) {
            this.flashsale_starttime = flashsale_starttime;
        }

        public long getFlashsaleEndtime() {
            return flashsale_endtime;
        }

        public void setFlashsaleEndtime(long flashsale_endtime) {
            this.flashsale_endtime = flashsale_endtime;
        }

        @Deprecated
        public String getBackground() {
            return background_url;
        }

        @Deprecated
        public void setBackground(String background) {
            this.background_url = background;
        }

        public String getBackgroundUrl() {
            return background_url;
        }

        public void setBackgroundUrl(String background_url) {
            this.background_url = background_url;
        }

        public void setMenutitle(String menutitle) {
            this.menutitle = menutitle;
        }

        public void setParentid(String parentid) {
            this.parentid = parentid;
        }

        public void setMenuid(String menuid) {
            this.menuid = menuid;
        }

        public String getColumntemplate() {
            return columntemplate;
        }

        public void setColumntemplate(String columntemplate) {
            this.columntemplate = columntemplate;
        }
    }
}
