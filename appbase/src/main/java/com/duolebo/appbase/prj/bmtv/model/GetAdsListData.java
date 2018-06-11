package com.duolebo.appbase.prj.bmtv.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

import com.duolebo.appbase.db.IRecord;
import com.duolebo.appbase.prj.Model;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetAdsListData extends ModelBase {

    List<ADS> arealist = new ArrayList<ADS>();

    public List<ADS> getArealist() {
        return arealist;
    }

    public void setArealist(List<ADS> arealist) {
        this.arealist = arealist;
    }

    @Override
    public boolean from(JSONObject json) {
        if(super.from(json)){
            return true;
        }
        return false;
    }

    public static class ADS extends Model{
        public static final int AUTO = -1000;

        private String mac_id;
        private String machine_id;
        private String machine_type;
        private String timer;

        public String getMac_id() {
            return mac_id;
        }
        public void setMac_id(String mac_id) {
            this.mac_id = mac_id;
        }
        public String getMachine_id() {
            return machine_id;
        }
        public void setMachine_id(String machine_id) {
            this.machine_id = machine_id;
        }
        public String getMachine_type() {
            return machine_type;
        }
        public void setMachine_type(String machine_type) {
            this.machine_type = machine_type;
        }
        public String getTimer() {
            return timer;
        }
        public void setTimer(String timer) {
            this.timer = timer;
        }

        @Override
        public boolean from(JSONObject json) {
            if( super.from(json)){
                mac_id=json.optString(Fields.MAC_ID);
                machine_id = json.optString(Fields.MACHINE_ID);
                machine_type = json.optString(Fields.MACHINE_TYPE);
                timer = json.optString(Fields.TIMER);
                return true;
            }
            return false;
        }

        public static interface Fields extends BaseColumns{
            public static final String MAC_ID ="mac_id";
            public static final String MACHINE_ID="machine_id";
            public static final String MACHINE_TYPE="machine_type";
            public static final String TIMER="timer";
        }

        @Override
        public void prepareFieldDefs(ArrayList<String> fieldDefs) {
            super.prepareFieldDefs(fieldDefs);
            fieldDefs.add(Fields.MAC_ID+IRecord.FIELD_TYPE_TEXT);
            fieldDefs.add(Fields.MACHINE_ID+IRecord.FIELD_TYPE_TEXT);
            fieldDefs.add(Fields.MACHINE_TYPE+IRecord.FIELD_TYPE_TEXT);
            fieldDefs.add(Fields.TIMER+IRecord.FIELD_TYPE_TEXT);
        }
        @Override
        public void readFieldValues(Cursor c) {
            super.readFieldValues(c);
            mac_id =c.getString(c.getColumnIndex(Fields.MAC_ID));
            machine_id =c.getString(c.getColumnIndex(Fields.MACHINE_ID));
            machine_type= c.getString(c.getColumnIndex(Fields.MACHINE_TYPE));
            timer = c.getString(c.getColumnIndex(Fields.TIMER));
        }
        @Override
        public void writeFieldValues(ContentValues values) {
            super.writeFieldValues(values);
            values.put(Fields.MAC_ID, mac_id);
            values.put(Fields.MACHINE_ID, machine_id);
            values.put(Fields.MACHINE_TYPE, machine_id);
            values.put(Fields.TIMER, timer);
        }

        @Override
        public String toString() {
            return "ADS{" +
                    "mac_id='" + mac_id + '\'' +
                    ", machine_id='" + machine_id + '\'' +
                    ", machine_type='" + machine_type + '\'' +
                    ", timer='" + timer + '\'' +
                    '}';
        }
    }
}
