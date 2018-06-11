package com.duolebo.appbase.prj.bmtv.protocol;

import android.content.Context;

import com.duolebo.appbase.AppBaseHandler;
import com.duolebo.appbase.prj.IProtocolList;

import java.util.Map;

/**
 * @author zlhl
 * @date 2014年3月3日
 */
abstract public class ProtocolListBase extends ProtocolBase implements IProtocolList {

    protected int pageno = 1;
    protected int pagesize = 20;

    abstract public ProtocolListBase createStableProtocol();

    public ProtocolListBase(Context context, IProtocolConfig config) {
        super(context, config);
    }

    public ProtocolListBase(ProtocolListBase list) {
        super(list);
        this.pageno = list.pageno;
        this.pagesize = list.pagesize;
    }

    public void clear() {
        // starting page's number is 1.
        // set 0 is unnecessary and would get empty list from server.
        pageno = 1;
        pagesize = 20;
    }

    public ProtocolListBase withPageNo(int pageno) {
        this.pageno = pageno;
        return this;
    }

    public ProtocolListBase withPageSize(int pagesize) {
        this.pagesize = pagesize;
        return this;
    }

    @Override
    public void firstPage(AppBaseHandler handler) {
        gotoPage(handler, 1);
    }

    @Override
    public void nextPage(AppBaseHandler handler) {
        gotoPage(handler, pageno + 1);
    }

    @Override
    public void gotoPage(AppBaseHandler handler, int pageno) {
        this.pageno = pageno;

        // protocol was put into a task queue, and is not executed right away.
        // creating a stable object can prevent its parameters from changing.
        ProtocolListBase stableProtocol = createStableProtocol();
        stableProtocol.execute(handler);
    }

    @Override
    public boolean hasNextPage() {
        // maybe total size is unknown yet, let assume it has page.
        if (getTotalSize() < 0) return true;

        // see if pageno has passed end.
        return (pagesize * pageno) < getTotalSize();
    }

    @Override
    protected void prepareProtocolBody(Map<String, String> body) {
        if (-1 != pageno)
            body.put("pageno", String.valueOf(pageno));
        if (-1 != pagesize)
            body.put("pagesize", String.valueOf(pagesize));
    }

    public int getPageNo() {
        return pageno;
    }

    public int getPageSize() {
        return pagesize;
    }

    abstract public int getTotalSize();
}
