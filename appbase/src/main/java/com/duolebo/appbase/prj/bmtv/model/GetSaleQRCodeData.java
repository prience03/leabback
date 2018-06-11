/**
 * 
 */
package com.duolebo.appbase.prj.bmtv.model;

import com.duolebo.appbase.prj.Model;

import org.json.JSONObject;

/**
 * @author zlhl
 * @date 2014年6月6日
 */
public class GetSaleQRCodeData extends ModelBase {

    private Content content = new Content();

    @Override
    public boolean from(JSONObject json) {
        if (super.from(json)) {
	        json = json.optJSONObject("response").optJSONObject("body");
            return content.from(json.optJSONObject("content"));
        }
        return false;
    }
    
    public Content getContent() {
    	return content;
    }

    public static class Content extends Model {
        
        private String qrcode_url = "";
        
        @Override
        public boolean from(JSONObject json) {
            if (super.from(json)) {
                qrcode_url = json.optString("qrcode_url");
                return true;
            }
            return false;
        }

        public String getQrcodeUrl() {
            return qrcode_url;
        }

    }
}
