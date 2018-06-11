/**
 *
 */
package com.duolebo.appbase.prj.bmtv.model;

import com.duolebo.appbase.prj.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author lixiangen
 * @date 2017年11月30日
 */
public class GetContentTVPlayUrlListData extends ModelBase {

    private ArrayList<PlayMessage> playMessages = new ArrayList<PlayMessage>();

    public ArrayList<PlayMessage> getPlayMessages() {
        return playMessages;
    }

    @Override
    public boolean from(JSONObject json) {
        if (super.from(json)) {
            json = json.optJSONObject("response").optJSONObject("body");
            try {
                JSONArray tvPlayUrls = json.optJSONArray("tvPlayUrls");
                for (int i = 0; i < tvPlayUrls.length(); i++) {
                    JSONObject jsonobject = tvPlayUrls.optJSONObject(i);
                    String tv_play_url = jsonobject.optString("tv_play_url");
                    PlayMessage playMessage = new PlayMessage();
                    playMessage.from(new JSONObject(tv_play_url));
                    playMessages.add(playMessage);
                }
            } catch (Exception e) {
                return false;
            }
            return true;
        }
        return false;
    }

    public static class PlayMessage extends Model implements Serializable {
        private ArrayList<Extra> extraslist = new ArrayList<>();

        public ArrayList<Extra> getExtraslist() {
            return extraslist;
        }

        @Override
        public boolean from(JSONObject json) {
            if (super.from(json)) {
                JSONArray extras = json.optJSONArray("extras");
                for (int i = 0; extras != null && i < extras.length(); i++) {
                    try {
                        Extra extra = new Extra();
                        extra.from(extras.optJSONObject(i));
                        extraslist.add(extra);
                    } catch (Exception e) {
                        return false;
                    }
                }
                return true;
            }

            return false;
        }
        public static class Extra extends Model implements Serializable {
            private String key_name = "";
            private String key_value = "";
            private String contentId = "";
            private String contentType = "";
            private String contentTitle = "";
            private String playUrl = "";
            private String rateTag = "";

            @Override
            public boolean from(JSONObject json) {
                if (super.from(json)) {
                    key_name = json.optString("key_name");
                    key_value = json.optString("key_value");

                    try {
                        JSONObject jsonObject = new JSONObject(key_value);
                        contentId = jsonObject.optString("contentId");
                        contentType = jsonObject.optString("contentType");
                        contentTitle = jsonObject.optString("contentTitle");
                        playUrl = jsonObject.optString("playUrl");
                        rateTag = jsonObject.optString("rateTag");
                    } catch (JSONException e) {
                        return false;
                    }
                    return true;
                }
                return false;
            }


            public String getKey_name() {
                return key_name;
            }

            public String getKey_value() {
                return key_value;
            }

            public String getContentId() {
                return contentId;
            }

            public String getContentType() {
                return contentType;
            }

            public String getContentTitle() {
                return contentTitle;
            }

            public String getPlayUrl() {
                return playUrl;
            }

            public String getRateTag() {
                return rateTag;
            }
        }
    }
}
