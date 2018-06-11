/**
 * 
 */
package com.duolebo.appbase.prj.bmtv.protocol;

import android.content.Context;

import com.duolebo.appbase.IModel;
import com.duolebo.appbase.prj.bmtv.model.GetSaleDetailData;

import java.util.Map;

/**
 * @author zlhl
 * @date 2014年6月6日
 */
public class GetSaleDetail extends ProtocolBase {

    private GetSaleDetailData data = new GetSaleDetailData();
    
    private String contentid = "";

    public GetSaleDetail(Context context, IProtocolConfig config) {
        super(context, config);
    }

    @Override
    public IModel getData() {
        return data;
    }
    
    public GetSaleDetail withContentId(String contentid) {
        this.contentid = contentid;
        return this;
    }

    @Override
    protected void prepareProtocolBody(Map<String, String> body) {
        body.put("contentid", contentid);
    }

    @Override
    protected String prepareProtocolRequestKey() {
        return "GetSaleDetail";
    }

    @Override
    public long getExpire() {
        return 1000 * 60 * 60 * 24 * 7 + System.currentTimeMillis();
    }
}
