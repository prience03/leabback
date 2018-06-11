/**
 * 
 */
package com.duolebo.appbase.prj.bmtv.protocol;

import android.content.Context;

import com.duolebo.appbase.IModel;
import com.duolebo.appbase.prj.bmtv.model.GetSaleQRCodeData;

import java.util.Map;

/**
 * @author zlhl
 * @date 2014年6月6日
 */
public class GetSaleQRCode extends ProtocolBase {
    
    private GetSaleQRCodeData data = new GetSaleQRCodeData();

    private String contentid = "";
    private String pay_type = "";

    public GetSaleQRCode(Context context, IProtocolConfig config) {
        super(context, config);
    }

    @Override
    public IModel getData() {
        return data;
    }

    public GetSaleQRCode withContentId(String contentid) {
        this.contentid = contentid;
        return this;
    }
    
    public GetSaleQRCode withPayType(String pay_type) {
        this.pay_type = pay_type;
        return this;
    }

    @Override
    protected void prepareProtocolBody(Map<String, String> body) {
        body.put("contentid", contentid);
        body.put("pay_type", pay_type);
    }

    @Override
    protected String prepareProtocolRequestKey() {
        return "GetSaleQRCode";
    }

}
