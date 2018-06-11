package com.duolebo.appbase.prj.bmtv.protocol;

import android.content.Context;

import com.duolebo.appbase.IModel;
import com.duolebo.appbase.prj.bmtv.model.GetRecommendListData;

/**
 * Created by zlhl on 15/6/22.
 */
public class GetRecommendList extends ProtocolListBase {

    private GetRecommendListData data = new GetRecommendListData();

    public GetRecommendList(Context context, IProtocolConfig config) {
        super(context, config);
    }

    public GetRecommendList(GetRecommendList list) {
        super(list);
    }

    @Override
    public ProtocolListBase createStableProtocol() {
        return new GetRecommendList(this);
    }

    @Override
    public int getTotalSize() {
        return 0;
    }

    @Override
    protected String prepareProtocolRequestKey() {
        return "GetRecommendList";
    }

    @Override
    public long getExpire() {
        return -1;
    }

    @Override
    public IModel getData() {
        return data;
    }

}
