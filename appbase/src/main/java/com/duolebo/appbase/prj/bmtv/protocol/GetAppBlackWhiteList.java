package com.duolebo.appbase.prj.bmtv.protocol;

import android.content.Context;

import com.duolebo.appbase.IModel;
import com.duolebo.appbase.prj.bmtv.model.GetAppBlackWhiteListData;

import java.util.Map;

/**
 * Created by ottauto on 16-1-18.
 */
public class GetAppBlackWhiteList extends ProtocolBase {

    private GetAppBlackWhiteListData data = new GetAppBlackWhiteListData();

    public GetAppBlackWhiteList(Context context, IProtocolConfig config) {
        super(context, config);
    }

    public GetAppBlackWhiteList(ProtocolBase base) {
        super(base);
    }

    @Override
    protected void prepareProtocolBody(Map<String, String> body) {

    }

    @Override
    protected String prepareProtocolRequestKey() {
        return "GetAppBlackWhiteList";
    }

    @Override
    public IModel getData() {
        return data;
    }
}
