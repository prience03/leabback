/**
 *
 */
package com.duolebo.appbase.prj.bmtv.protocol;

import android.content.Context;

import com.duolebo.appbase.prj.bmtv.model.GetContentListData;
import com.duolebo.appbase.prj.bmtv.model.GetContentListData.Content.ContentType;

import java.util.Map;

/**
 * @author zlhl
 * @date 2014年2月17日
 */
public class GetContentList extends ProtocolListBase {

    private String menuid = null;
    private ContentType contenttype = ContentType.Unknown;
    private int needcells = 0;
    private String idtype;

    private GetContentListData data = new GetContentListData();
    private String region = null;
    private String year = null;
    private Object tag;

    public GetContentList(Context context, IProtocolConfig config) {
        super(context, config);
    }

    public GetContentList(GetContentList getContentList) {
        super(getContentList);
        this.menuid = getContentList.menuid;
        this.contenttype = getContentList.contenttype;
        this.needcells = getContentList.needcells;
        this.data = getContentList.data;
        this.idtype = getContentList.idtype;
    }

    @Override
    public void clear() {
        super.clear();
        data = new GetContentListData();
        menuid = null;
        contenttype = ContentType.Unknown;
        needcells = 0;
        idtype = null;
    }

    public GetContentList withMenu(String menuid) {
        this.menuid = menuid;
        this.idtype = null;
        return this;
    }

    public GetContentList withContentId(String contentid) {
        this.menuid = contentid;
        this.idtype = "content";
        return this;
    }

    public GetContentList withContentType(ContentType contenttype) {
        this.contenttype = contenttype;
        return this;
    }

    public GetContentList withCells(boolean need) {
        this.needcells = need ? 1 : 0;
        return this;
    }

    public GetContentList withRegion(String regionstext) {
        this.region = regionstext;
        return this;
    }

    public GetContentList withYear(String year) {
        this.year = year;
        return this;
    }


    public GetContentList setMyTag(Object tag) {
        this.tag = tag;
        return this;
    }

    public GetContentList cancleWithTag(Object tag) {
        cancleTag(tag);
        return this;
    }


    @Override
    protected void prepareProtocolBody(Map<String, String> body) {
        super.prepareProtocolBody(body);
        if (null != menuid)
            body.put("menuid", String.valueOf(menuid));
        if (null != contenttype && ContentType.Unknown != contenttype)
            body.put("contenttype", contenttype.toString());
        if (null != idtype && !"".equals(idtype))
            body.put("idtype", idtype);
        if (null != region && !"".equals(region))
            body.put("region", region);
        if (null != year && !"".equals(year))
            body.put("year", year);
        body.put("needcells", String.valueOf(needcells));
    }

    @Override
    protected String prepareProtocolRequestKey() {
        return "GetContentList";
    }

    @Override
    public GetContentListData getData() {
        return data;
    }

    public String getMenuId() {
        return menuid;
    }

    @Override
    public int getTotalSize() {
        return data.getTotalSize();
    }

    @Override
    public ProtocolListBase createStableProtocol() {
        return new GetContentList(this);
    }

    public String getIdtype() {
        return idtype;
    }


    @Override
    public long getExpire() {
        return 1000 * 60 * 10 + System.currentTimeMillis();
    }


    @Override
    public Object setRequestTag() {
        if (null != tag) {
            return tag;
        }
        return super.setRequestTag();
    }


    public void cancleTag(Object tag) {
        super.cancleTag(tag);
    }
}
