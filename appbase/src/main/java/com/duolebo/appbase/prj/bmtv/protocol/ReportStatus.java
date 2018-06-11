package com.duolebo.appbase.prj.bmtv.protocol;

import java.util.Map;

import android.content.Context;

import com.duolebo.appbase.prj.bmtv.model.ReportStatusData;
import com.duolebo.appbase.prj.bmtv.model.ReportStatusData.OperType;

public class ReportStatus extends ProtocolBase {

    private String contentid;
    private OperType oper_type = OperType.Unknown;
    private ReportStatusData data = new ReportStatusData();

    public ReportStatus(Context context, IProtocolConfig config) {
        super(context, config);
    }

    public ReportStatus with(String contentid, OperType oper_type) {
        this.contentid = contentid;
        this.oper_type = oper_type;
        return this;
    }

    @Override
    public ReportStatusData getData() {
        return data;
    }

    @Override
    protected void prepareProtocolBody(Map<String, String> body) {
        body.put("contentid", contentid);
        body.put("oper_type", oper_type.toString());
    }

    @Override
    protected String prepareProtocolRequestKey() {
        return "ReportStatus";
    }

}
