/**
 * 
 */

package com.duolebo.appbase.prj.bmtv.model;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.text.TextUtils;

import com.duolebo.appbase.prj.Model;
import com.duolebo.appbase.prj.bmtv.model.GetMenuData.Menu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zlhl
 * @date 2014年2月17日
 */
public class GetContentListData extends ModelBase implements Serializable {

    private int totalsize = -1;
    private ArrayList<Content> contentlist = new ArrayList<Content>();
    private JSONObject display_cells = null;

    @Override
    public boolean from(JSONObject json) {
        if (super.from(json)) {
            json = json.optJSONObject("response").optJSONObject("body");
            totalsize = json.optInt("totalsize");
            display_cells = json.optJSONObject("display_cells");
            JSONArray ja = json.optJSONArray("contentlist");

            if (null == ja)
                return false;

            JSONArray cells = null;
            int version = 1;
            if (null != display_cells) {
                cells = display_cells.optJSONArray("cells");
                version = display_cells.optInt("version", 1);
            }

            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo = ja.optJSONObject(i);

                if (null != jo) {
                  if (null != cells && 1 < version) {
                        int cellsSize = cells.length();
                        for(int j=0; j<cellsSize; j++) {
                            JSONObject cell = cells.optJSONObject(j);
                            String imgurl = cell.optString("imgurl");
                            String id = cell.optString("id");
                            if(id.equalsIgnoreCase(jo.optString("contentid"))) {
                                try {
                                    jo.put("display_image", imgurl);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                break;
                            }
                        }
                    }
                    Content content = new Content();
                    content.from(jo);

//                    if (Content.ContentType.Unknown == content.getContentType())
//                        continue;

                    contentlist.add(content);
                }
            }



            return true;
        }
        return false;
    }

    public int getTotalSize() {
        return totalsize;
    }

    public JSONObject getDisplayCells() {
        return display_cells;
    }

    public ArrayList<Content> getContentList() {
        return contentlist;
    }

    public static class Content extends Model implements Serializable {

        public static enum ContentType {

            ARTICLE("1"),
            MUSIC("2"),
            VIDEO("3"),
            URL("4"),
            PIC("5"),
            FOOD("6"),
            APP("7"),
            SHOP("8"),
            SHOW("9"),
            MOVIE("10"),
            TV("11"),
            NEWS("12"),
            AD("13"),
            LIVE("15"),
            HTML("16"),
            SUBMENU("17"),
            TOPIC("18"),
            INTERFIX("99"),
            ADs("90"), // 前插广告

            FOCUS("101"), // 焦点
            ZHUANQU("102"), // 专区

            Unknown("0");

            private String val;

            ContentType(String val) {
                this.val = val;
            }

            public String toString() {
                return this.val;
            }

            public static ContentType fromString(String val) {
                for (ContentType at : ContentType.values()) {
                    if (at.val.equalsIgnoreCase(val)) {
                        return at;
                    }
                }
                return Unknown;
            }
        }

        public interface Fields extends Model.Fields {
            String CONTENTID = "contentid";
            String CONTENTNAME = "contentname";
            String ISP_MENU_CODE = "isp_menu_code";
            String CONTENTTYPE = "contenttype";
            String ICON_URL = "icon_url";
            String LANDSCAPE_URL = "landscape_url";
            String PORTRAIT_URL = "portrait_url";
            String BACKGROUND_URL = "background_url";
            String BACKGROUND_THUMB_URL = "background_thumb_url";
            String PROMPT = "prompt";
            String IS_HD = "is_HD";
            String IS_HOT = "is_hot";
            String IS_NEW = "is_new";
            String PACKAGE_NAME = "package_name";
            String LINK_URL = "link_url";
            String SUPERSCRIPT_URL = "superscript_url";
            String DISPLAY_IMAGE = "display_image";
            String DETAIL_INFOS = "detail_infos";
            String IS_AUTOPLAY = "is_autoplay";
            String FAKE_PRICE = "fake_price";
            String SALE_PRICE = "sale_price";
            String POST_PRICE = "post_price";
            String PLAY_URL = "play_url";
            String MENU = "menu";
            String PROVIDER = "provider";
            String CHANGE_PRICE = "change_price";
            String IS_FLASH_SALE = "is_flashsale";
            String FLASHSALE_STARTTIME = "flashsale_starttime";
            String FLASHSALE_ENDTIME = "flashsale_endtime";
//            String BACKGROUND_URL = "background_url";
//            String BACKGROUND_THUMB_URL = "background_thumb_url";
        }

        private String contentid = "";
        private String contentname = "";
        private String isp_menu_code = "";
        private ContentType contenttype = ContentType.Unknown;
        private String icon_url = "";
        private String landscape_url = "";
        private String portrait_url = "";
        private String background_url = "";
        private String background_thumb_url = "";
        private String prompt = "";
        private int is_HD = 0;
        private int is_hot = 0;
        private int is_new = 0;
        private String package_name = "";
        private String link_url = "";
        private String superscript_url = "";
        private String display_image = "";
        private DetailInfo detail_infos = null;
        private int is_autoplay = 0;
        private double fake_price = 0.f;
        private double sale_price = 0.f;
        private double post_price = 0.f;
        private String play_url = "";
        private String is_flash_sale = "0";
        private long flashsale_starttime = 0;
        private long flashsale_endtime = 0;

        private String isMore ;


        private Menu menu = null;

        private Provider provider = null;
        private ChangePrice change_price = null;

        public Content() {
        }

        public Content(Content content) {
            super(content);
            contentid = content.contentid;
            contentname = content.contentname;
            isp_menu_code = content.isp_menu_code;
            contenttype = content.contenttype;
            icon_url = content.icon_url;
            landscape_url = content.landscape_url;
            portrait_url = content.portrait_url;
            background_url = content.background_url;
            background_thumb_url = content.background_thumb_url;
            prompt = content.prompt;
            is_HD = content.is_HD;
            is_hot = content.is_hot;
            is_new = content.is_new;
            package_name = content.package_name;
            link_url = content.link_url;
            superscript_url = content.superscript_url;
            display_image = content.display_image;
            detail_infos = content.detail_infos;
            is_autoplay = content.is_autoplay;
            fake_price = content.fake_price;
            sale_price = content.sale_price;
            post_price = content.post_price;
            play_url = content.play_url;
            menu = content.menu;
            provider = content.provider;
            change_price = content.change_price;
            is_flash_sale = content.is_flash_sale;
            flashsale_starttime = content.flashsale_starttime;
            flashsale_endtime = content.flashsale_endtime;
            background_url = content.background_url;
            background_thumb_url = content.background_thumb_url;
        }

        public Content(Model model) {
            super(model);
        }

        @Override
        public boolean from(JSONObject json) {
            if (super.from(json)) {
                contentid = json.optString(Fields.CONTENTID);
                contentname = json.optString(Fields.CONTENTNAME);
                isp_menu_code = json.optString(Fields.ISP_MENU_CODE);
                contenttype = ContentType.fromString(json.optString(Fields.CONTENTTYPE));
                icon_url = json.optString(Fields.ICON_URL);
                landscape_url = json.optString(Fields.LANDSCAPE_URL);
                portrait_url = json.optString(Fields.PORTRAIT_URL);
                background_url = json.optString(Fields.BACKGROUND_URL);
                background_thumb_url = json.optString(Fields.BACKGROUND_THUMB_URL);
                prompt = json.optString(Fields.PROMPT);
                is_HD = json.optInt(Fields.IS_HD);
                is_hot = json.optInt(Fields.IS_HOT);
                is_new = json.optInt(Fields.IS_NEW);
                package_name = json.optString(Fields.PACKAGE_NAME);
                link_url = json.optString(Fields.LINK_URL);
                superscript_url = json.optString(Fields.SUPERSCRIPT_URL);
                display_image = json.optString(Fields.DISPLAY_IMAGE);
                is_autoplay = json.optInt(Fields.IS_AUTOPLAY);
                fake_price = json.optDouble(Fields.FAKE_PRICE);
                fake_price = fake_price != fake_price ? 0.0f : fake_price;
                sale_price = json.optDouble(Fields.SALE_PRICE);
                sale_price = sale_price != sale_price ? 0.0f : sale_price;
                post_price = json.optDouble(Fields.POST_PRICE);
                post_price = post_price != post_price ? 0.0f : post_price;
                play_url = json.optString(Fields.PLAY_URL);
                is_flash_sale = json.optString(Fields.IS_FLASH_SALE);
                flashsale_starttime = json.optLong(Fields.FLASHSALE_STARTTIME);
                flashsale_endtime = json.optLong(Fields.FLASHSALE_ENDTIME);
                background_url = json.optString("background_url");
                background_thumb_url = json.optString("background_thumb_url");
//                background_url = json.optString(Fields.BACKGROUND_URL);
//                background_thumb_url = json.optString(Fields.BACKGROUND_THUMB_URL);

                if (json.has(Fields.MENU)) {
                    menu = new Menu();
                    menu.from(json.optJSONObject(Fields.MENU));
                }

                if (json.has(Fields.DETAIL_INFOS)) {
                    detail_infos = new DetailInfo();
                    detail_infos.from(json.optJSONObject(Fields.DETAIL_INFOS));
                }

                if (json.has(Fields.PROVIDER)) {
                    provider = new Provider();
                    provider.from(json.optJSONObject(Fields.PROVIDER));
                }

                if (json.has(Fields.CHANGE_PRICE)) {
                    change_price = new ChangePrice();
                    change_price.from(json.optJSONObject(Fields.CHANGE_PRICE));
                }

                return true;
            }
            return false;
        }

        @Override
        public void prepareFieldDefs(ArrayList<String> fieldDefs) {
            super.prepareFieldDefs(fieldDefs);
            fieldDefs.add(Fields.CONTENTID + FIELD_TYPE_TEXT);
            fieldDefs.add(Fields.CONTENTNAME + FIELD_TYPE_TEXT);
            fieldDefs.add(Fields.ISP_MENU_CODE + FIELD_TYPE_TEXT);
            fieldDefs.add(Fields.CONTENTTYPE + FIELD_TYPE_TEXT);
            fieldDefs.add(Fields.ICON_URL + FIELD_TYPE_TEXT);
            fieldDefs.add(Fields.LANDSCAPE_URL + FIELD_TYPE_TEXT);
            fieldDefs.add(Fields.PORTRAIT_URL + FIELD_TYPE_TEXT);
            fieldDefs.add(Fields.BACKGROUND_URL + FIELD_TYPE_TEXT);
            fieldDefs.add(Fields.BACKGROUND_THUMB_URL + FIELD_TYPE_TEXT);
            fieldDefs.add(Fields.PROMPT + FIELD_TYPE_TEXT);
            fieldDefs.add(Fields.IS_HD + FIELD_TYPE_INTEGER);
            fieldDefs.add(Fields.IS_HOT + FIELD_TYPE_INTEGER);
            fieldDefs.add(Fields.IS_NEW + FIELD_TYPE_INTEGER);
            fieldDefs.add(Fields.PACKAGE_NAME + FIELD_TYPE_TEXT);
            fieldDefs.add(Fields.LINK_URL + FIELD_TYPE_TEXT);
            fieldDefs.add(Fields.SUPERSCRIPT_URL + FIELD_TYPE_TEXT);
            fieldDefs.add(Fields.DISPLAY_IMAGE + FIELD_TYPE_TEXT);
            fieldDefs.add(Fields.DETAIL_INFOS + FIELD_TYPE_TEXT);
            fieldDefs.add(Fields.IS_AUTOPLAY + FIELD_TYPE_INTEGER);
            fieldDefs.add(Fields.FAKE_PRICE + FIELD_TYPE_NUMERIC);
            fieldDefs.add(Fields.SALE_PRICE + FIELD_TYPE_NUMERIC);
            fieldDefs.add(Fields.POST_PRICE + FIELD_TYPE_NUMERIC);
            fieldDefs.add(Fields.PLAY_URL + FIELD_TYPE_TEXT);
            fieldDefs.add(Fields.MENU + FIELD_TYPE_TEXT);
            fieldDefs.add(Fields.IS_FLASH_SALE + FIELD_TYPE_TEXT);
            fieldDefs.add(Fields.FLASHSALE_STARTTIME + FIELD_TYPE_NUMERIC);
            fieldDefs.add(Fields.FLASHSALE_ENDTIME + FIELD_TYPE_NUMERIC);
//            fieldDefs.add(Fields.BACKGROUND_URL + FIELD_TYPE_TEXT);
//            fieldDefs.add(Fields.BACKGROUND_THUMB_URL + FIELD_TYPE_TEXT);
        }

        @Override
        public void writeFieldValues(ContentValues values) {
            super.writeFieldValues(values);
            values.put(Fields.CONTENTID, contentid);
            values.put(Fields.CONTENTNAME, contentname);
            values.put(Fields.ISP_MENU_CODE, isp_menu_code);
            values.put(Fields.CONTENTTYPE, contenttype.toString());
            values.put(Fields.ICON_URL, icon_url);
            values.put(Fields.LANDSCAPE_URL, landscape_url);
            values.put(Fields.PORTRAIT_URL, portrait_url);
            values.put(Fields.BACKGROUND_URL, background_url);
            values.put(Fields.BACKGROUND_THUMB_URL, background_url);
            values.put(Fields.PROMPT, prompt);
            values.put(Fields.IS_HD, is_HD);
            values.put(Fields.IS_HOT, is_hot);
            values.put(Fields.IS_NEW, is_new);
            values.put(Fields.PACKAGE_NAME, package_name);
            values.put(Fields.LINK_URL, link_url);
            values.put(Fields.SUPERSCRIPT_URL, superscript_url);
            values.put(Fields.DISPLAY_IMAGE, display_image);
            values.put(Fields.DETAIL_INFOS, null != detail_infos ? detail_infos.getJSON().toString() : "");
            values.put(Fields.IS_AUTOPLAY, is_autoplay);
            values.put(Fields.FAKE_PRICE, fake_price);
            values.put(Fields.SALE_PRICE, sale_price);
            values.put(Fields.POST_PRICE, post_price);
            values.put(Fields.PLAY_URL, play_url);
            values.put(Fields.MENU, null != menu ? menu.getJSON().toString() : "");
            values.put(Fields.IS_FLASH_SALE, is_flash_sale);
            values.put(Fields.FLASHSALE_STARTTIME, flashsale_starttime);
            values.put(Fields.FLASHSALE_ENDTIME, flashsale_endtime);
//            values.put(Fields.BACKGROUND_URL, background_url);
//            values.put(Fields.BACKGROUND_THUMB_URL,background_thumb_url);
        }

        @Override
        public void readFieldValues(Cursor c) {
            super.readFieldValues(c);
            contentid = c.getString(c.getColumnIndex(Fields.CONTENTID));
            contentname = c.getString(c.getColumnIndex(Fields.CONTENTNAME));
            isp_menu_code = c.getString(c.getColumnIndex(Fields.ISP_MENU_CODE));
            contenttype = ContentType.fromString(c.getString(c.getColumnIndex(Fields.CONTENTTYPE)));
            icon_url = c.getString(c.getColumnIndex(Fields.ICON_URL));
            landscape_url = c.getString(c.getColumnIndex(Fields.LANDSCAPE_URL));
            portrait_url = c.getString(c.getColumnIndex(Fields.PORTRAIT_URL));
            background_url = c.getString(c.getColumnIndex(Fields.BACKGROUND_URL));
            background_thumb_url = c.getString(c.getColumnIndex(Fields.BACKGROUND_THUMB_URL));
            prompt = c.getString(c.getColumnIndex(Fields.PROMPT));
            is_HD = c.getInt(c.getColumnIndex(Fields.IS_HD));
            is_hot = c.getInt(c.getColumnIndex(Fields.IS_HOT));
            is_new = c.getInt(c.getColumnIndex(Fields.IS_NEW));
            package_name = c.getString(c.getColumnIndex(Fields.PACKAGE_NAME));
            link_url = c.getString(c.getColumnIndex(Fields.LINK_URL));
            superscript_url = c.getString(c.getColumnIndex(Fields.SUPERSCRIPT_URL));
            display_image = c.getString(c.getColumnIndex(Fields.DISPLAY_IMAGE));
            String detail_infosString = c.getString(c.getColumnIndex(Fields.DETAIL_INFOS));
            if (!TextUtils.isEmpty(detail_infosString)) {
                try {
                    JSONObject jo = new JSONObject(detail_infosString);
                    detail_infos = new DetailInfo();
                    detail_infos.from(jo);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            is_autoplay = c.getInt(c.getColumnIndex(Fields.IS_AUTOPLAY));
            fake_price = c.getDouble(c.getColumnIndex(Fields.FAKE_PRICE));
            sale_price = c.getDouble(c.getColumnIndex(Fields.SALE_PRICE));
            post_price = c.getDouble(c.getColumnIndex(Fields.POST_PRICE));
            play_url = c.getString(c.getColumnIndex(Fields.PLAY_URL));
            String menuString = c.getString(c.getColumnIndex(Fields.MENU));
            if (!TextUtils.isEmpty(menuString)) {
                try {
                    JSONObject jo = new JSONObject(menuString);
                    menu = new Menu();
                    menu.from(jo);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            is_flash_sale = c.getString(c.getColumnIndex(Fields.IS_FLASH_SALE));
            flashsale_starttime = c.getLong(c.getColumnIndex(Fields.FLASHSALE_STARTTIME));
            flashsale_endtime = c.getLong(c.getColumnIndex(Fields.FLASHSALE_ENDTIME));

//            background_url = c.getString(c.getColumnIndex(Fields.BACKGROUND_URL));
//            background_thumb_url = c.getString(c.getColumnIndex(Fields.BACKGROUND_THUMB_URL));
        }

        public String getContentId() {
            return contentid;
        }

        public void setContentId(String contentid) {
            this.contentid = contentid;
        }

        public String getContentName() {
            return contentname;
        }

        public void setContentName(String contentname) {
            this.contentname = contentname;
        }

        public String getIspMenuCode() {
            return isp_menu_code;
        }

        public void setIspMenuCode(String isp_menu_code) {
            this.isp_menu_code = isp_menu_code;
        }

        public ContentType getContentType() {
            return contenttype;
        }

        public void setContentType(ContentType contenttype) {
            this.contenttype = contenttype;
        }

        public String getIconUrl() {
            return icon_url;
        }

        public void setIconUrl(String icon_url) {
            this.icon_url = icon_url;
        }

        public String getLandscapeUrl() {
            return landscape_url;
        }

        public void setLandscapeUrl(String landscape_url) {
            this.landscape_url = landscape_url;
        }

        public String getPortraitUrl() {
            return portrait_url;
        }

        public void setPortraitUrl(String portrait_url) {
            this.portrait_url = portrait_url;
        }

        public String getBackgroundUrl() {
            return background_url;
        }

        public void setBackgroundUrl(String background_url) {
            this.background_url = background_url;
        }

        public String getBackgroundThumbUrl() {
            return background_thumb_url;
        }

        public void setBackgroundThumbUrl(String background_thumb_url) {
            this.background_thumb_url = background_thumb_url;
        }

        public String getPrompt() {
            return prompt;
        }

        public void setPrompt(String prompt) {
            this.prompt = prompt;
        }

        public boolean getIsHD() {
            return 1 == is_HD;
        }

        public void setIsHD(boolean is_HD) {
            this.is_HD = is_HD ? 1 : 0;
        }

        public boolean getIsHot() {
            return 1 == is_hot;
        }

        public void setIsHot(boolean is_hot) {
            this.is_hot = is_hot ? 1 : 0;
        }

        public boolean getIsNew() {
            return 1 == is_new;
        }

        public void setIsNew(boolean is_new) {
            this.is_new = is_new ? 1 : 0;
        }

        public String getPackageName() {
            return package_name;
        }

        public void setPackageName(String package_name) {
            this.package_name = package_name;
        }

        public String getLinkUrl() {
            return link_url;
        }

        public void setLinkUrl(String link_url) {
            this.link_url = link_url;
        }

        public String getSuperscriptUrl() {
            return superscript_url;
        }

        public void setSuperscriptUrl(String superscript_url) {
            this.superscript_url = superscript_url;
        }

        public String getDisplayImage() {
            return display_image;
        }

        public void setDisplayImage(String display_image) {
            this.display_image = display_image;
        }

        public DetailInfo getDetailInfos() {
            return detail_infos;
        }

        public void setDetailInfos(DetailInfo detail_infos) {
            this.detail_infos = detail_infos;
        }

        public boolean getIsAutoplay() {
            return 1 == is_autoplay;
        }

        public void setIsAutoplay(boolean is_autoplay) {
            this.is_autoplay = is_autoplay ? 1 : 0;
        }

        public double getFakePrice() {
            return fake_price;
        }

        public String getIsMore() {
            return isMore;
        }

        public void setIsMore(String isMore) {
            this.isMore = isMore;
        }

        public void setFakePrice(double fake_price) {
            this.fake_price = fake_price;
        }

        public double getSalePrice() {
            return sale_price;
        }

        public void setSalePrice(double sale_price) {
            this.sale_price = sale_price;
        }

        public double getPostPrice() {
            return post_price;
        }

        public void setPostPrice(double post_price) {
            this.post_price = post_price;
        }

        public String getPlayUrl() {
            return play_url;
        }

        public void setPlayUrl(String play_url) {
            this.play_url = play_url;
        }

        public Menu getMenu() {
            return menu;
        }

        public Provider getProvider() {
            return provider;
        }

        public ChangePrice getChangePrice() {
            return change_price;
        }

        public long getFlashsaleEndtime() {
            return flashsale_endtime;
        }

        public void setFlashsaleEndtime(long flashsale_endtime) {
            this.flashsale_endtime = flashsale_endtime;
        }

        public long getFlashsaleStarttime() {
            return flashsale_starttime;
        }

        public void setFlashsaleStarttime(long flashsale_starttime) {
            this.flashsale_starttime = flashsale_starttime;
        }

        public boolean getIsFlashSale() {
            return "1".equalsIgnoreCase(is_flash_sale);
        }

        public void setIsFlashSale(boolean is_flash_sale) {
            this.is_flash_sale = is_flash_sale ? "1" : "0";
        }

        public void setBackground_url(String background_url) {
            this.background_url = background_url;
        }

        public String getBackground_url() {
            return background_url;
        }

        public void setBackground_thumb_url(String background_thumb_url) {
            this.background_thumb_url = background_thumb_url;
        }

        public String getBackground_thumb_url() {
            return background_thumb_url;
        }

        public static class Provider extends Model {

            private String id = "";
            private String name = "";
            private String icon_url = "";
            private String hot_info = "";

            @Override
            public boolean from(JSONObject json) {
                if(super.from(json)) {
                    id = json.optString("id");
                    name = json.optString("name");
                    icon_url = json.optString("icon_url");
                    hot_info = json.optString("hot_info");
                    return true;
                }
                return false;
            }

            public String getId() {
                return id;
            }

            public String getName() {
                return name;
            }

            public String getIconUrl() {
                return icon_url;
           }

            public String getHotInfo() {
                return hot_info;
            }
        }

        public static class ChangePrice extends Model {

            private String is_support = "";
            private String icon_url = "";
            private String description = "";

            @Override
            public boolean from(JSONObject json) {
                if(super.from(json)) {
                    is_support = json.optString("is_support");
                    icon_url = json.optString("icon_url");
                    description = json.optString("description");
                    return true;
                }
                return false;
            }

            public boolean isSupport() {
                return "1".equalsIgnoreCase(is_support);
            }

            public String getIcon_url() {
                return icon_url;
            }

            public String getDescription() {
                return description;
            }
        }

        public static class DetailInfo extends Model {
            
            public static enum StartType {

                START_ACTIVITY("start_activity"),
                INTENT_URI("intent"),
                START_SERVICE("start_service"),
                BROADCAST("broadcast"),
                Unknown("");

                private String val;

                StartType(String val) {
                    this.val = val;
                }

                public String toString() {
                    return this.val;
                }

                public static StartType fromString(String val) {
                    for (StartType st : StartType.values()) {
                        if (st.val.equalsIgnoreCase(val)) {
                            return st;
                        }
                    }
                    return Unknown;
                }
            }

            private List<Extra> extras = new ArrayList<Extra>();
            private List<Flag> flags = new ArrayList<Flag>();

            public interface Fields extends Model.Fields {
                public static final String START_TYPE = "start_type";
                public static final String INTENT_URI = "intent_uri";
                public static final String ACTION_NAME = "action_name";
                public static final String PACKAGE_NAME = "package_name";
                public static final String CLASS_NAME = "class_name";
                public static final String EXTRAS = "extras";
                public static final String FLAGS = "flags";
            }

            private StartType start_type = StartType.Unknown;
            private String intent_uri = "";
            private String action_name = "";
            private String package_name = "";
            private String class_name = "";

            public DetailInfo() {
            }

            public DetailInfo(DetailInfo detailInfo) {
                super(detailInfo);
                start_type = detailInfo.start_type;
                intent_uri = detailInfo.intent_uri;
                action_name = detailInfo.action_name;
                package_name = detailInfo.package_name;
                class_name = detailInfo.class_name;
                extras = detailInfo.extras;
                flags = detailInfo.flags;
            }

            public DetailInfo(Model model) {
                super(model);
            }

            @Override
            public boolean from(JSONObject json) {
                if (super.from(json)) {
                    start_type = StartType.fromString(json.optString(Fields.START_TYPE));
                    intent_uri = json.optString(Fields.INTENT_URI);
                    action_name = json.optString(Fields.ACTION_NAME);
                    package_name = json.optString(Fields.PACKAGE_NAME);
                    class_name = json.optString(Fields.CLASS_NAME);
                    JSONArray jarr = json.optJSONArray(Fields.EXTRAS);
                    for (int i = 0; null != jarr && i < jarr.length(); i++) {
                        Extra extra = new Extra();
                        extra.from(jarr.optJSONObject(i));
                        extras.add(extra);
                    }
                    jarr = json.optJSONArray(Fields.FLAGS);
                    for (int i = 0; null != jarr && i < jarr.length(); i++) {
                        Flag flag = new Flag();
                        flag.from(jarr.optJSONObject(i));
                        flags.add(flag);
                    }
                    return true;
                }
                return false;
            }

            @Override
            public void prepareFieldDefs(ArrayList<String> fieldDefs) {
                super.prepareFieldDefs(fieldDefs);
                fieldDefs.add(Fields.START_TYPE + FIELD_TYPE_TEXT);
                fieldDefs.add(Fields.INTENT_URI + FIELD_TYPE_TEXT);
                fieldDefs.add(Fields.ACTION_NAME + FIELD_TYPE_TEXT);
                fieldDefs.add(Fields.PACKAGE_NAME + FIELD_TYPE_TEXT);
                fieldDefs.add(Fields.CLASS_NAME + FIELD_TYPE_TEXT);
                fieldDefs.add(Fields.EXTRAS + FIELD_TYPE_TEXT);
                fieldDefs.add(Fields.FLAGS + FIELD_TYPE_TEXT);
            }

            @Override
            public void writeFieldValues(ContentValues values) {
                super.writeFieldValues(values);
                values.put(Fields.START_TYPE, start_type.toString());
                values.put(Fields.INTENT_URI, intent_uri);
                values.put(Fields.ACTION_NAME, action_name);
                values.put(Fields.PACKAGE_NAME, package_name);
                values.put(Fields.CLASS_NAME, class_name);
                JSONArray jarr;

                jarr = new JSONArray();
                for (Extra extra : extras) {
                    jarr.put(extra.getJSON());
                }
                values.put(Fields.EXTRAS, jarr.toString());

                jarr = new JSONArray();
                for (Flag flag : flags) {
                    jarr.put(flag.toString());
                }
                values.put(Fields.FLAGS, jarr.toString());
            }

            @Override
            public void readFieldValues(Cursor c) {
                super.readFieldValues(c);
                start_type = StartType.fromString(c.getString(c.getColumnIndex(Fields.START_TYPE)));
                intent_uri = c.getString(c.getColumnIndex(Fields.INTENT_URI));
                action_name = c.getString(c.getColumnIndex(Fields.ACTION_NAME));
                package_name = c.getString(c.getColumnIndex(Fields.PACKAGE_NAME));
                class_name = c.getString(c.getColumnIndex(Fields.CLASS_NAME));
                
                JSONArray jarr;

                String extrasString = c.getString(c.getColumnIndex(Fields.EXTRAS));
                if (!TextUtils.isEmpty(extrasString)) {
                    try {
                        jarr = new JSONArray(extrasString);
                        for (int i = 0; null != jarr && i < jarr.length(); i++) {
                            Extra extra = new Extra();
                            extra.from(jarr.optJSONObject(i));
                            extras.add(extra);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                String flagsString = c.getString(c.getColumnIndex(Fields.FLAGS));
                if (!TextUtils.isEmpty(flagsString)) {
                    try {
                        jarr = new JSONArray(flagsString);
                        for (int i = 0; null != jarr && i < jarr.length(); i++) {
                            Flag flag = new Flag();
                            flag.from(jarr.optJSONObject(i));
                            flags.add(flag);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            public StartType getStartType() {
                return start_type;
            }

            public void setStartType(StartType start_type) {
                this.start_type = start_type;
            }

            public String getIntentUri() {
                return intent_uri;
            }

            public void setIntentUri(String intent_uri) {
                this.intent_uri = intent_uri;
            }

            public String getActionName() {
                return action_name;
            }

            public void setActionName(String action_name) {
                this.action_name = action_name;
            }

            public String getPackageName() {
                return package_name;
            }

            public void setPackageName(String package_name) {
                this.package_name = package_name;
            }

            public String getClassName() {
                return class_name;
            }

            public void setClassName(String class_name) {
                this.class_name = class_name;
            }

            public List<Extra> getExtras() {
                return extras;
            }
            
            public String findExtra(String key) {
                for (Extra extra : extras) {
                    if (extra.getKeyName().equalsIgnoreCase(key)) {
                        return extra.getKeyValue();
                    }
                }

                return null;
            }

            public List<Flag> getFlags() {
                return flags;
            }
            
            public String findRealPlayUrl() {
                for (Extra extra : extras) {
                    try {
                        JSONObject jobj = new JSONObject(extra.getKeyValue());
                        if (jobj.has("playUrl"))
                            return jobj.optString("playUrl");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                return null;
            }

            @TargetApi(Build.VERSION_CODES.DONUT)
            public boolean start(Context context) {
                if (null == context) return false;

                Intent intent = new Intent();

                if (!TextUtils.isEmpty(intent_uri)) {
                    try {
                        intent = Intent.parseUri(intent_uri, 0);
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                        return false;
                    }
                }

                if (!TextUtils.isEmpty(action_name)) {
                    intent.setAction(action_name);
                }

                if (!TextUtils.isEmpty(package_name) && !TextUtils.isEmpty(class_name)) {
                    intent.setClassName(package_name, class_name);
                }

                for (Extra extra : extras) {
                    intent.putExtra(extra.getKeyName(), extra.getKeyValue());
                }

                for (Flag flag : flags) {
                    intent.addFlags(flag.getKeyValue());
                }

                try {
                    switch (start_type) {
                        case START_ACTIVITY:
                        case INTENT_URI:
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                            return true;

                        case START_SERVICE:
                            context.startService(intent);
                            return true;

                        case BROADCAST:
                            context.sendBroadcast(intent);
                            return true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return false;
            }

            public static class Extra extends Model {

                public interface Fields extends Model.Fields {
                    public static final String KEY_NAME = "key_name";
                    public static final String KEY_VALUE = "key_value";
                }

                private String key_name = "";
                private String key_value = "";

                public Extra() {
                }

                public Extra(Extra extra) {
                    super(extra);
                    key_name = extra.key_name;
                    key_value = extra.key_value;
                }

                public Extra(Model model) {
                    super(model);
                }

                @Override
                public boolean from(JSONObject json) {
                    if (super.from(json)) {
                        key_name = json.optString(Fields.KEY_NAME);
                        key_value = json.optString(Fields.KEY_VALUE);
                        return true;
                    }
                    return false;
                }

                @Override
                public void prepareFieldDefs(ArrayList<String> fieldDefs) {
                    super.prepareFieldDefs(fieldDefs);
                    fieldDefs.add(Fields.KEY_NAME + FIELD_TYPE_TEXT);
                    fieldDefs.add(Fields.KEY_VALUE + FIELD_TYPE_TEXT);
                }

                @Override
                public void writeFieldValues(ContentValues values) {
                    super.writeFieldValues(values);
                    values.put(Fields.KEY_NAME, key_name);
                    values.put(Fields.KEY_VALUE, key_value);
                }

                @Override
                public void readFieldValues(Cursor c) {
                    super.readFieldValues(c);
                    key_name = c.getString(c.getColumnIndex(Fields.KEY_NAME));
                    key_value = c.getString(c.getColumnIndex(Fields.KEY_VALUE));
                }

                public String getKeyName() {
                    return key_name;
                }

                public void setKeyName(String key_name) {
                    this.key_name = key_name;
                }

                public String getKeyValue() {
                    return key_value;
                }

                public void setKeyValue(String key_value) {
                    this.key_value = key_value;
                }

            }

            public static class Flag extends Model {

                public interface Fields extends Model.Fields {
                    public static final String KEY_VALUE = "key_value";
                }

                private int key_value = -1;

                public Flag() {
                }

                public Flag(Flag flag) {
                    super(flag);
                    key_value = flag.key_value;
                }

                public Flag(Model model) {
                    super(model);
                }

                @Override
                public boolean from(JSONObject json) {
                    if (super.from(json)) {
                        key_value = json.optInt(Fields.KEY_VALUE);
                        return true;
                    }
                    return false;
                }

                @Override
                public void prepareFieldDefs(ArrayList<String> fieldDefs) {
                    super.prepareFieldDefs(fieldDefs);
                    fieldDefs.add(Fields.KEY_VALUE + FIELD_TYPE_NUMERIC);
                }

                @Override
                public void writeFieldValues(ContentValues values) {
                    super.writeFieldValues(values);
                    values.put(Fields.KEY_VALUE, key_value);
                }

                @Override
                public void readFieldValues(Cursor c) {
                    super.readFieldValues(c);
                    key_value = c.getInt(c.getColumnIndex(Fields.KEY_VALUE));
                }

                public int getKeyValue() {
                    return key_value;
                }

                public void setKeyValue(int key_value) {
                    this.key_value = key_value;
                }

            }
        }

    }

}
