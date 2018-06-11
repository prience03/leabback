/**
 *
 */
package com.duolebo.appbase.prj.bmtv.protocol;

import android.content.Context;

import com.duolebo.appbase.prj.bmtv.model.SearchContentListData;

import java.util.Map;

/**
 * @author zlhl
 * @date 2014年2月17日
 */
public class SearchContentList extends ProtocolListBase {

    /**
     * 搜索类型：拼音首字母
     */
    public static final int ARG_PIN_YIN = 1;
    /**
     * 搜索类型：关键字(默认)
     */
    public static final int ARG_KEYWORD = 0;

    private String keyword = "";
    private int is_pinyin = ARG_KEYWORD;

    private SearchContentListData data = new SearchContentListData();
    private String region = "";
    private String year = "";

    public SearchContentList(Context context, IProtocolConfig config) {
        super(context, config);
    }

    public SearchContentList(SearchContentList searchContentList) {
        super(searchContentList);
        this.keyword = searchContentList.keyword;
        this.is_pinyin = searchContentList.is_pinyin;
        this.data = searchContentList.data;
    }

    public SearchContentList withKeyword(String keyword) {
        this.keyword = keyword;
        return this;
    }

    public SearchContentList withPinYin(int isPinYin) {
        this.is_pinyin = isPinYin;
        return this;
    }


    public SearchContentList withRegion(String regionstext) {
        this.region = regionstext;
        return this;
    }

    public SearchContentList withYear(String year) {
        this.year = year;
        return this;
    }

    @Override
    protected void prepareProtocolBody(Map<String, String> body) {
        super.prepareProtocolBody(body);

        body.put("is_pinyin", String.valueOf(is_pinyin));
        body.put("keyword", keyword);
        if(null != region && !"".equals(region))
            body.put("region", region);
        if(null != year && !"".equals(year))
        body.put("year", year);

    }

    @Override
    protected String prepareProtocolRequestKey() {
        return "SearchContentList";
    }

    @Override
    public SearchContentListData getData() {
        return data;
    }

    public String getKeyword() {
        return keyword;
    }

    @Override
    public int getTotalSize() {
        return data.getTotalsize();
    }

    @Override
    public ProtocolListBase createStableProtocol() {
        return new SearchContentList(this);
    }

}
