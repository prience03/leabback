package com.duolebo.appbase.prj.bmtv.protocol;

import android.content.Context;

import com.duolebo.appbase.IModel;

import java.util.Map;

/**
 * Created by lxn on 2017/9/3.
 */

public class GetExitRecommend extends ProtocolBase {

    private GetExitRecommendData data = null;
    private String columntype;

    public GetExitRecommend(Context context, IProtocolConfig config) {
        super(context, config);
    }

    @Override
    public IModel getData() {
        return data;
    }

    public GetExitRecommend with(String columntype ){
        this.columntype = columntype;
        data = new GetExitRecommendData();
        return this;
    }

    @Override
    protected void prepareProtocolBody(Map<String, String> body) {
        body.put("columntype",columntype);

    }

    @Override
    public long getExpire() {
//        return  1000 * 60 * 10  + System.currentTimeMillis();
    return -1;
    }

    @Override
    protected String prepareProtocolRequestKey() {
        return "GetRecommendList";
    }
}
