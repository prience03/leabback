package com.duolebo.appbase.prj.bmtv.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

import com.duolebo.appbase.db.IRecord;
import com.duolebo.appbase.prj.Model;

import org.json.JSONObject;

import java.util.ArrayList;


/**
 *   统计用户行为
 */
public class TjCustomEventData extends Model {

        public static final int AUTO = -1000;

    private String pageStartTime ;
    private String pageCloseTime ;
    private String pageStayTime ;
    private String pageName ;
    private String adshop_id;
    private String userid;
    private String logintype; // 0 手机  1 微信  2 支付宝  3未登录
    private String searchContent ;
    private String videoId;
    private String videoName ; // 观看影片名
    private String videoTime ; // 观看影片时间


    @Override
    public boolean from(JSONObject json) {
        if( super.from(json)){
            return true;
        }
        return false;
    }

    public static interface Fields extends BaseColumns{
        public static final String PAGE_STARTTIME ="PAGE_STARTTIME";
        public static final String PAGE_CLOSETIME ="PAGE_CLOSETIME";
        public static final String PAGE_STAYTIME ="PAGE_STAYTIME";
        public static final String PAGE_NAME ="PAGE_NAME";
        public static final String ADSHOP_ID ="ADSHOP_ID";
        public static final String USER_ID ="USER_ID";
        public static final String LOGIN_TYPE ="LOGIN_TYPE";
        public static final String SEARCH_CONTENT ="SEARCH_CONTENT";
        public static final String VIDEO_ID ="VIDEO_ID";
        public static final String VIDEO_NAME ="VIDEO_NAME";
        public static final String VIDEO_TIME ="VIDEO_TIME";
    }

    @Override
    public void prepareFieldDefs(ArrayList<String> fieldDefs) {
        super.prepareFieldDefs(fieldDefs);
        fieldDefs.add(Fields.PAGE_STARTTIME+IRecord.FIELD_TYPE_TEXT);
        fieldDefs.add(Fields.PAGE_CLOSETIME+IRecord.FIELD_TYPE_TEXT);
        fieldDefs.add(Fields.PAGE_STAYTIME+IRecord.FIELD_TYPE_TEXT);
        fieldDefs.add(Fields.PAGE_NAME+IRecord.FIELD_TYPE_TEXT);
        fieldDefs.add(Fields.ADSHOP_ID+IRecord.FIELD_TYPE_TEXT);
        fieldDefs.add(Fields.USER_ID+IRecord.FIELD_TYPE_TEXT);
        fieldDefs.add(Fields.LOGIN_TYPE+IRecord.FIELD_TYPE_TEXT);
        fieldDefs.add(Fields.SEARCH_CONTENT+IRecord.FIELD_TYPE_TEXT);
        fieldDefs.add(Fields.VIDEO_ID+IRecord.FIELD_TYPE_TEXT);
        fieldDefs.add(Fields.VIDEO_NAME+IRecord.FIELD_TYPE_TEXT);
        fieldDefs.add(Fields.VIDEO_TIME+IRecord.FIELD_TYPE_TEXT);

    }
    @Override
    public void readFieldValues(Cursor c) {
        super.readFieldValues(c);
        pageStartTime =c.getString(c.getColumnIndex(Fields.PAGE_STARTTIME));
        pageCloseTime =c.getString(c.getColumnIndex(Fields.PAGE_STARTTIME));
        pageStayTime =c.getString(c.getColumnIndex(Fields.PAGE_STAYTIME));
        pageName =c.getString(c.getColumnIndex(Fields.PAGE_NAME));
        adshop_id =c.getString(c.getColumnIndex(Fields.ADSHOP_ID));
        userid =c.getString(c.getColumnIndex(Fields.USER_ID));
        logintype =c.getString(c.getColumnIndex(Fields.LOGIN_TYPE));
        searchContent =c.getString(c.getColumnIndex(Fields.SEARCH_CONTENT));
        videoId =c.getString(c.getColumnIndex(Fields.VIDEO_ID));
        videoName =c.getString(c.getColumnIndex(Fields.VIDEO_NAME));
        videoTime=c.getString(c.getColumnIndex(Fields.VIDEO_TIME));
    }
    @Override
    public void writeFieldValues(ContentValues values) {
        super.writeFieldValues(values);
        values.put(Fields.PAGE_STARTTIME, pageStartTime);
        values.put(Fields.PAGE_CLOSETIME, pageCloseTime);
        values.put(Fields.PAGE_STAYTIME,  pageStayTime);
        values.put(Fields.PAGE_NAME, pageName);
        values.put(Fields.ADSHOP_ID, adshop_id);
        values.put(Fields.USER_ID, userid);
        values.put(Fields.LOGIN_TYPE, logintype);
        values.put(Fields.SEARCH_CONTENT, searchContent);
        values.put(Fields.VIDEO_ID, videoId);
        values.put(Fields.VIDEO_NAME, videoName);
        values.put(Fields.VIDEO_TIME, videoTime);
    }

    public String getPageStartTime() {
        return pageStartTime;
    }

    public void setPageStartTime(String pageStartTime) {
        this.pageStartTime = pageStartTime;
    }

    public String getPageCloseTime() {
        return pageCloseTime;
    }

    public void setPageCloseTime(String pageCloseTime) {
        this.pageCloseTime = pageCloseTime;
    }

    public String getPageStayTime() {
        return pageStayTime;
    }

    public void setPageStayTime(String pageStayTime) {
        this.pageStayTime = pageStayTime;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getAdshop_id() {
        return adshop_id;
    }

    public void setAdshop_id(String adshop_id) {
        this.adshop_id = adshop_id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getLogintype() {
        return logintype;
    }

    public void setLogintype(String logintype) {
        this.logintype = logintype;
    }

    public String getSearchContent() {
        return searchContent;
    }

    public void setSearchContent(String searchContent) {
        this.searchContent = searchContent;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getVideoTime() {
        return videoTime;
    }

    public void setVideoTime(String videoTime) {
        this.videoTime = videoTime;
    }
}
