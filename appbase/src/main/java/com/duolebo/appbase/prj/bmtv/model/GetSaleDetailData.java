/**
 * 
 */

package com.duolebo.appbase.prj.bmtv.model;

import android.text.TextUtils;

import com.duolebo.appbase.prj.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zlhl
 * @date 2014年6月6日
 */
public class GetSaleDetailData extends ModelBase {

    private Content content = new Content();
    private ArrayList<GetContentListData.Content> recommend_list = new ArrayList<GetContentListData.Content>();

    @Override
    public boolean from(JSONObject json) {
        if (super.from(json)) {
            json = json.optJSONObject("response").optJSONObject("body");
            JSONArray jarr = json.optJSONArray("recommend_list");
            for (int i=0; null!=jarr && i<jarr.length(); i++) {
                JSONObject jo = jarr.optJSONObject(i);
                GetContentListData.Content content = new GetContentListData.Content();
                content.from(jo);
                recommend_list.add(content);
            }
            return content.from(json.optJSONObject("content"));
        }
        return false;
    }

    public Content getContent() {
        return content;
    }

    public ArrayList<GetContentListData.Content> getRecommendList() {
        return recommend_list;
    }

    public static class Content extends Model {

        private String contentid = "";
        private String contentname = "";
        private String hot_info = "";
        private float fake_price = 0.f;
        private float sale_price = 0.f;
        private float price_desc = 0.f;
        private float real_price = 0.f;
        private float post_price = 0.f;
        private String disaccount = "";
        private String key_words = "";
        private String cp_name = "";
        private String sales_no = "";
        private String sub_cp_name = "";
        private String qrcode_url = "";
        private String sales_detail_url = "";
        private String sales_comment_url = "";
        //new
        private String icon_url = "";
        private String landscape_url = "";
        private String portrait_url = "";
        private String is_flash_sale = "0";
        private long flashsale_starttime = 0;
        private long flashsale_endtime = 0;

        private List<PayType> pay_types = new ArrayList<PayType>();
        
        private TvPlayUrlTags tv_play_url_tags = new TvPlayUrlTags();

        private List<Pic> pics = new ArrayList<Pic>();
        private String video_seg_time = "";
        private GetContentListData.Content.ChangePrice change_price = new GetContentListData.Content.ChangePrice();
        private String multi_hot_info = "";
        private String service_info = "";
        private GetContentListData.Content.Provider provider = new GetContentListData.Content.Provider();
        private Shop shop = new Shop();

        private String float_layer_url = "";

        @Override
        public boolean from(JSONObject json) {
            if (super.from(json)) {
                contentid = json.optString("contentid");
                contentname = json.optString("contentname");
                hot_info = json.optString("hot_info");
                icon_url = json.optString("icon_url");
                landscape_url = json.optString("landscape_url");
                portrait_url = json.optString("portrait_url");
                is_flash_sale = json.optString("is_flashsale");
                flashsale_starttime = json.optLong("flashsale_starttime");
                flashsale_endtime = json.optLong("flashsale_endtime");
                
                try {
                    fake_price = Float.valueOf(json.optString("fake_price"));
                } catch (NumberFormatException e) {
                }
                try {
                    sale_price = Float.valueOf(json.optString("sale_price"));
                } catch (NumberFormatException e) {
                }
                try {
                    price_desc = Float.valueOf(json.optString("price_desc"));
                } catch (NumberFormatException e){
                }
                try {
                    real_price = Float.valueOf(json.optString("real_price"));
                } catch (NumberFormatException e) {
                }
                try {
                    post_price = Float.valueOf(json.optString("post_price"));
                } catch (NumberFormatException e) {
                }
                disaccount = json.optString("disaccount");
                key_words = json.optString("key_words");
                cp_name = json.optString("cp_name");
                sales_no = json.optString("sales_no");
                sub_cp_name = json.optString("sub_cp_name");
                
                qrcode_url = json.optString("qrcode_url");
                sales_detail_url = json.optString("sales_detail_url");
                sales_comment_url = json.optString("sales_comment_url");

                JSONArray jsonArr = json.optJSONArray("pay_types");
                for (int i = 0; null != jsonArr && i < jsonArr.length(); i++) {
                    PayType payType = new PayType();
                    payType.from(jsonArr.optJSONObject(i));
                    pay_types.add(payType);
                }

                try {
                    String tagsString = json.optString("tv_play_url_tags");
                    if (!TextUtils.isEmpty(tagsString)) {
                        JSONObject tagsJson = new JSONObject(tagsString);
                        tv_play_url_tags.from(tagsJson);
                    }
                } catch (JSONException e) {
                }

                jsonArr = json.optJSONArray("pics");
                for (int i = 0; null != jsonArr && i < jsonArr.length(); i++) {
                    Pic pic = new Pic();
                    pic.from(jsonArr.optJSONObject(i));
                    pics.add(pic);
                }

                video_seg_time = json.optString("video_seg_time");
                change_price.from(json.optJSONObject("change_price"));
                multi_hot_info = json.optString("multi_hot_info");
                service_info = json.optString("service_info");
                provider.from(json.optJSONObject("provider"));
                shop.from(json.optJSONObject("shop"));

                float_layer_url = json.optString("float_layer_url");

                return true;
            }
            return false;
        }

        public String getContentId() {
            return contentid;
        }

        public String getContentName() {
            return contentname;
        }

        public String getHotInfo() {
            return hot_info;
        }

        /*
         * 市场价格
         */
        public float getFakePrice() {
            return fake_price;
        }

        /*
         * 销售价格
         */
        public float getSalePrice() {
            return sale_price;
        }

        /**
         * 价格区间
         * 不为空时，替换销售价格字段，优先显示此字段
         */
        public float getPriceDesc() {
            return price_desc;
        }

        /*
         * 实际卖出价格 暂不用
         */
        public float getRealPrice() {
            return real_price;
        }

        /*
         * 邮寄费用
         */
        public float getPostPrice() {
            return post_price;
        }

        public String getDisaccount() {
            return disaccount;
        }

        public String getKeywords() {
            return key_words;
        }

        public String getCpName() {
            return cp_name;
        }

        public String getSalesNo() {
            return sales_no;
        }

        public String getSubCpName() {
            return sub_cp_name;
        }

        public String getQrcodeUrl() {
            return qrcode_url;
        }

        public String getSalesDetailUrl() {
            return sales_detail_url;
        }

        public String getSalesCommentUrl() {
            return sales_comment_url;
        }

        public List<PayType> getPayTypes() {
            return pay_types;
        }
        
        public String getPortraitUrl() {
            return portrait_url;
        }
        
        public String getIconUrl() {
            return icon_url;
        }
        
        public String getLandscapeUrl() {
            return landscape_url;
        }

        public List<Pic> getPics() {
            return pics;
        }

        public String getVideoSegTime() {
            return video_seg_time;
        }

        public GetContentListData.Content.ChangePrice getChangePrice() {
            return change_price;
        }

        public String getMultiHotInfo() {
            return multi_hot_info;
        }

        public String getServiceInfo() {
            return service_info;
        }

        public GetContentListData.Content.Provider getProvider() {
            return provider;
        }

        public long getFlashsale_starttime() {
            return flashsale_starttime;
        }

        public long getFlashsale_endtime() {
            return flashsale_endtime;
        }

        public boolean getIsFlashSale() {
            return "1".equalsIgnoreCase(is_flash_sale);
        }

        public Shop getShop() {
            return shop;
        }

        public String getFloat_layer_url() {
            return float_layer_url;
        }

        public static class PayType extends Model {

            public static final int PAY_BY_PHONE = 0;
            public static final int PAY_BY_ALIPAY = 1;
            public static final int PAY_BY_WEIXIN = 2;
            public static final int PAY_BY_OTHER = 4;

            private int id = 0;
            private String qrcode_url = "";
            private int pay_type = 0;
            private int has_qrcode = 0;
            private String service_hotline = "";
            private String description = "";

            @Override
            public boolean from(JSONObject json) {
                if (super.from(json)) {
                    id = json.optInt("id");
                    qrcode_url = json.optString("qrcode_url");
                    pay_type = json.optInt("pay_type");
                    has_qrcode = json.optInt("has_qrcode");
                    service_hotline = json.optString("service_hotline");
                    description = json.optString("description");
                    return true;
                }
                return false;
            }

            public int getId() {
                return id;
            }

            public String getQrcdeUrl() {
                return qrcode_url;
            }

            public int getPayType() {
                return pay_type;
            }

            public int getHasQrcode() {
                return has_qrcode;
            }

            public String getServiceHotline() {
                return service_hotline;
            }

            public String getDescription() {
                return description;
            }

        }

        public String generateDetailInfo() {
            StringBuilder sb = new StringBuilder();
            List<GetSaleDetailData.Content.PayType> payTypes = getPayTypes();
            for (final GetSaleDetailData.Content.PayType payType : payTypes) {
                sb.append(payType.getDescription()).append("\n");
            }

            if (!TextUtils.isEmpty(getMultiHotInfo().trim())) {
                sb.append(getMultiHotInfo().trim()).append("\n");
            }

            if (!TextUtils.isEmpty(getHotInfo().trim())) {
                sb.append(getHotInfo().trim());
            }

            return sb.toString();
        }

        public TvPlayUrlTags getTvPlayUrlTags() {
            return tv_play_url_tags;
        }

        public static class TvPlayUrlTags extends Model implements Serializable {
            
            private int current_index = 0;
            private int current_tag = 0;
            private List<Tag> tags = new ArrayList<Tag>();
            
            @Override
            public boolean from(JSONObject json) {
                if (super.from(json)) {
                    current_index = json.optInt("current_index");
                    current_tag = json.optInt("current_tag");
                    JSONArray jarr = json.optJSONArray("tags");
                    for (int i=0; null!=jarr && i<jarr.length(); i++) {
                        Tag tag = new Tag();
                        tag.from(jarr.optJSONObject(i));
                        tags.add(tag);
                    }
                    return true;
                }
                return false;
            }
            
            public int getCurrentIndex() {
                return current_index;
            }

            public int getCurrentTag() {
                return current_tag;
            }

            public List<Tag> getTags() {
                return tags;
            }
            
            public Tag findTag(int rate) {
                return findTag(tags, rate);
            }
            
            public static Tag findTag(List<Tag> tagList, int rate) {
                for (Tag t : tagList) {
                    if (rate == t.tag) {
                        return t;
                    }
                }

                // fail back to highest rated tag if rate >= 0;
                // or fail back to lowest rated tag if rate < 0;
                Tag tag = null;
                for (Tag t : tagList) {
                    if (null == tag) {
                        tag = t;
                    } else {
                        if (0 <= rate && tag.tag < t.tag) {
                            tag = t;
                        } else if (rate < 0 && t.tag < tag.tag) {
                            tag = t;
                        }
                    }
                }
                return tag;
            }

            public static class Tag extends Model {
                
                private int tag = 0;
                private String tag_name = "";
                private List<Serie> series = new ArrayList<Serie>();
                    
                @Override
                public boolean from(JSONObject json) {
                    if (super.from(json)) {
                        tag = json.optInt("tag");
                        tag_name = json.optString("tag_name");
                        JSONArray jarr = json.optJSONArray("series");
                        for (int i=0; null!=jarr && i<jarr.length(); i++) {
                            Serie serie = new Serie();
                            serie.from(jarr.optJSONObject(i));
                            series.add(serie);
                        }
                        return true;
                    }
                    return false;
                }
                
                public int getTag() {
                    return tag;
                }
                
                public String getTagName() {
                    return tag_name;
                }

                public List<Serie> getSeries() {
                    return series;
                }

                public static class Serie extends Model {
                    
                    private int index = 0;
                    private String play_url = "";
                    private String title = "";
                    
                    @Override
                    public boolean from(JSONObject json) {
                        if (super.from(json)) {
                            index = json.optInt("index");
                            play_url = json.optString("play_url");
                            title = json.optString("title");
                            return true;
                        }
                        return false;
                    }

                    public int getIndex() {
                        return index;
                    }

                    public String getPlayUrl() {
                        return play_url;
                    }

                    public String getTitle() {
                        return title;
                    }
                }
            }
        }

        public static class Pic extends Model {

            private String url = "";

            @Override
            public boolean from(JSONObject json) {
                if (super.from(json)) {
                    url = json.optString("url");
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
        }

        public static class Shop extends GetContentListData.Content.Provider {
            private String credit = "";

            @Override
            public boolean from(JSONObject json) {
                if (super.from(json)) {
                    credit = json.optString("credit");
                    return true;
                }
                return false;
            }

            public String getCredit() {
                return credit;
            }
        }
    }

}
