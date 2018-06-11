package com.duolebo.appbase.prj.bmtv.protocol;

import android.content.Context;

import com.duolebo.appbase.IModel;
import com.duolebo.appbase.prj.bmtv.model.GetSkinVersionData;

import java.util.Map;


public class GetSkinBg extends ProtocolBase {

    private GetSkinVersionData data = null;

    private String skin_version;

    public GetSkinBg(Context context, IProtocolConfig config) {
        super(context, config);
    }

    @Override
    public IModel getData() {
        return data;
    }

    public GetSkinBg with(String skin_version ){
        this.skin_version = skin_version;
        data = new GetSkinVersionData();
        return this;
    }

    @Override
    protected void prepareProtocolBody(Map<String, String> body) {
        body.put("skin_version",skin_version);
    }

    @Override
    public long getExpire() {
         return -1;
    }

    @Override
    protected String prepareProtocolRequestKey() {
        return "CheckIndexBGUpdate";
    }
}
