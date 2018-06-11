package com.duolebo.appbase.prj.bmtv.protocol;

import android.content.Context;

import com.duolebo.appbase.prj.bmtv.model.GetScreenData;

import java.util.Map;

/**
 * Created by Administrator on 2017/5/2.
 */

public class GetScreenToken extends ProtocolBase {
    private String year = "";
    private String region = "";

    GetScreenData data = new GetScreenData();


    public GetScreenToken(Context context, IProtocolConfig config) {
        super(context, config);
    }

    @Override
    public GetScreenData getData() {
        return data;
    }


    public GetScreenToken with(String year,String region) {
        this.year = year;
        this.region = region ;
        return this;
    }

    @Override
    protected void prepareProtocolBody(Map<String, String> body) {

    }

    @Override
    protected String prepareProtocolRequestKey() {
        return "SearchYearRegion";
    }

    @Override
    public long getExpire() {
        return -1;
    }
}
