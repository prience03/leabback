package com.duolebo.appbase.prj.bmtv.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

import com.duolebo.appbase.db.IRecord;
import com.duolebo.appbase.prj.Model;

import org.json.JSONObject;

import java.util.ArrayList;


/**
 *   统计开机 数据
 */
public class TjOpenData extends ModelBase {


    @Override
    public boolean from(JSONObject json) {
        if(super.from(json)){
            return true;
        }
        return false;
    }

    public static class Content extends Model{
        public static final int AUTO = -1000;

        private String app_version;
        private String user_id ;
        private String machine_model; // 设备型号
        private String device ;       //芯片类型
        private String facturer;      //厂商
        private String mac ;
        private String ip ;
        private String screen_display;
        private String start_timer;
        private String close_timer ;

        @Override
        public boolean from(JSONObject json) {
            if( super.from(json)){
                return true;
            }
            return false;
        }

        public static interface Fields extends BaseColumns{
            public static final String APP_VERSION ="APP_VERSION";
            public static final String USER_ID ="USER_ID";
            public static final String MACHINE_MODEL ="MACHINE_MODEL";
            public static final String DEVICE ="DEVICE";
            public static final String FACTURER ="FACTURER";
            public static final String MAC ="MAC";
            public static final String IP ="IP";
            public static final String SCREEN_DISPLAY ="SCREEN_DISPLAY";
            public static final String START_TIME ="START_TIME";
            public static final String CLOSE_TIME ="CLOSE_TIME";

        }

        @Override
        public void prepareFieldDefs(ArrayList<String> fieldDefs) {
            super.prepareFieldDefs(fieldDefs);
            fieldDefs.add(Fields.APP_VERSION+IRecord.FIELD_TYPE_TEXT);
            fieldDefs.add(Fields.USER_ID+IRecord.FIELD_TYPE_TEXT);
            fieldDefs.add(Fields.MACHINE_MODEL+IRecord.FIELD_TYPE_TEXT);
            fieldDefs.add(Fields.DEVICE+IRecord.FIELD_TYPE_TEXT);
            fieldDefs.add(Fields.MAC+IRecord.FIELD_TYPE_TEXT);
            fieldDefs.add(Fields.IP+IRecord.FIELD_TYPE_TEXT);
            fieldDefs.add(Fields.SCREEN_DISPLAY+IRecord.FIELD_TYPE_TEXT);
            fieldDefs.add(Fields.START_TIME+IRecord.FIELD_TYPE_TEXT);
            fieldDefs.add(Fields.CLOSE_TIME+IRecord.FIELD_TYPE_TEXT);
        }
        @Override
        public void readFieldValues(Cursor c) {
            super.readFieldValues(c);
            app_version =c.getString(c.getColumnIndex(Fields.APP_VERSION));
            user_id =c.getString(c.getColumnIndex(Fields.USER_ID));
            machine_model =c.getString(c.getColumnIndex(Fields.MACHINE_MODEL));
            device =c.getString(c.getColumnIndex(Fields.DEVICE));
            mac =c.getString(c.getColumnIndex(Fields.MAC));
            ip =c.getString(c.getColumnIndex(Fields.IP));
            screen_display =c.getString(c.getColumnIndex(Fields.SCREEN_DISPLAY));
            start_timer =c.getString(c.getColumnIndex(Fields.START_TIME));
            close_timer =c.getString(c.getColumnIndex(Fields.CLOSE_TIME));

        }
        @Override
        public void writeFieldValues(ContentValues values) {
            super.writeFieldValues(values);
            values.put(Fields.APP_VERSION, app_version);
            values.put(Fields.USER_ID, user_id);
            values.put(Fields.MACHINE_MODEL, machine_model);
            values.put(Fields.DEVICE, device);
            values.put(Fields.MAC, mac);
            values.put(Fields.IP, ip);
            values.put(Fields.SCREEN_DISPLAY, screen_display);
            values.put(Fields.START_TIME, start_timer);
            values.put(Fields.CLOSE_TIME, close_timer);

        }

        public String getApp_version() {
            return app_version;
        }

        public void setApp_version(String app_version) {
            this.app_version = app_version;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getMachine_model() {
            return machine_model;
        }

        public void setMachine_model(String machine_model) {
            this.machine_model = machine_model;
        }

        public String getDevice() {
            return device;
        }

        public void setDevice(String device) {
            this.device = device;
        }

        public String getFacturer() {
            return facturer;
        }

        public void setFacturer(String facturer) {
            this.facturer = facturer;
        }

        public String getMac() {
            return mac;
        }

        public void setMac(String mac) {
            this.mac = mac;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getScreen_display() {
            return screen_display;
        }

        public void setScreen_display(String screen_display) {
            this.screen_display = screen_display;
        }

        public String getStart_timer() {
            return start_timer;
        }

        public void setStart_timer(String start_timer) {
            this.start_timer = start_timer;
        }

        public String getClose_timer() {
            return close_timer;
        }

        public void setClose_timer(String close_timer) {
            this.close_timer = close_timer;
        }

        @Override
        public String toString() {
            return "Content{" +
                    "app_version='" + app_version + '\'' +
                    ", user_id='" + user_id + '\'' +
                    ", machine_model='" + machine_model + '\'' +
                    ", device='" + device + '\'' +
                    ", facturer='" + facturer + '\'' +
                    ", mac='" + mac + '\'' +
                    ", ip='" + ip + '\'' +
                    ", screen_display='" + screen_display + '\'' +
                    ", start_timer='" + start_timer + '\'' +
                    ", close_timer='" + close_timer + '\'' +
                    '}';
        }
    }
}
