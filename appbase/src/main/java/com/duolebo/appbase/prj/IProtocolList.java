package com.duolebo.appbase.prj;

import com.duolebo.appbase.AppBaseHandler;

/**
 * @author zlhl
 * @date 2014年12月16日
 */
public interface IProtocolList {

    void firstPage(AppBaseHandler handler);
    void nextPage(AppBaseHandler handler);
    void gotoPage(AppBaseHandler handler, int pageno);
    boolean hasNextPage();

}
