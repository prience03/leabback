package com.duolebo.appbase.prj.bmtv.protocol;

import android.content.Context;

import com.duolebo.appbase.prj.bmtv.model.GetVipPacksData;

import java.util.Map;

/**
 * @author advu  @date 2016/11/11.
 *
 *  获取 VIP产品包
 */

public class GetVipPacks extends ProtocolBase {

    private GetVipPacksData getVipPacksData = new GetVipPacksData();

    public GetVipPacks(Context context, IProtocolConfig config) {
        super(context, config);
    }

    @Override
    protected void prepareProtocolBody(Map<String, String> body) {

    }

    @Override
    protected String prepareProtocolRequestKey() {
        return "GETPFTariff";
    }

    @Override
    public GetVipPacksData getData() {
        return getVipPacksData;
    }

    @Override
    public long getExpire() {
        return  -1;
    }
}
