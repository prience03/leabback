/**
 * 
 */
package com.duolebo.appbase.prj.bmtv.protocol;

import android.content.Context;

import com.duolebo.appbase.prj.Protocol;
import com.duolebo.appbase.prj.bmtv.model.GetAboutHtmlData;

import java.util.Map;

/**
 * @author zlhl
 * @date 2014年6月6日
 */
public class GetAboutHtml extends ProtocolBase {
    
    private GetAboutHtmlData data = new GetAboutHtmlData();

    public GetAboutHtml(Context context, IProtocolConfig config) {
        super(context, config);
    }

    @Override
    public GetAboutHtmlData getData() {
        return data;
    }

    @Override
    public Protocol setTag(Object obj) {
        return super.setTag(obj);
    }

    @Override
    protected void prepareProtocolBody(Map<String, String> body) {
    }

    @Override
    protected String prepareProtocolRequestKey() {
        return "GetAboutHtml";
    }

}
