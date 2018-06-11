package com.duolebo.appbase.prj.bmtv.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

import com.duolebo.appbase.db.IRecord;
import com.duolebo.appbase.prj.Model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetAreaListData extends ModelBase {

    List<Area> arealist = new ArrayList<Area>();

    public List<Area> getArealist() {
        return arealist;
    }

    public void setArealist(List<Area> arealist) {
        this.arealist = arealist;
    }

    @Override
    public boolean from(JSONObject json) {
        if(super.from(json)){
            json = json.optJSONObject("response").optJSONObject("body");
            JSONArray ja = json.optJSONArray("arealist");
            if (null == ja)
                return false;

            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo = ja.optJSONObject(i);
                if (null != jo) {
                    Area area = new Area();
                    area.from(jo);
                    arealist.add(area);
                }
            }
            return true;
        }
        return false;
    }

    public static class Area extends Model{
        public static final int AUTO = -1000;

        private int id=0;
        private int parent_id=0;
        private String code;
        private String area_name;
        private int level;

        public int getId() {
            return id;
        }
        public void setId(int id) {
            this.id = id;
        }
        public int getParent_id() {
            return parent_id;
        }
        public void setParent_id(int parent_id) {
            this.parent_id = parent_id;
        }
        public String getCode() {
            return code;
        }
        public void setCode(String code) {
            this.code = code;
        }
        public String getArea_name() {
            return area_name;
        }
        public void setArea_name(String area_name) {
            this.area_name = area_name;
        }
        public int getLevel() {
            return level;
        }
        public void setLevel(int level) {
            this.level = level;
        }
        @Override
        public boolean from(JSONObject json) {
            if( super.from(json)){
                id=json.optInt(Fields.ID);
                parent_id = json.optInt(Fields.PARENT_ID);
                code = json.optString(Fields.CODE);
                area_name = json.optString(Fields.ARAE_NAME);
                level = json.optInt(Fields.LEVEL);
                return true;
            }
            return false;
        }

        public static interface Fields extends BaseColumns{
            public static final String ID ="id";
            public static final String PARENT_ID="parent_id";
            public static final String CODE="code";
            public static final String ARAE_NAME="area_name";
            public static final String LEVEL="level";
        }

        @Override
        public void prepareFieldDefs(ArrayList<String> fieldDefs) {
            // TODO Auto-generated method stub
            super.prepareFieldDefs(fieldDefs);
            fieldDefs.add(Fields.ID+IRecord.FIELD_TYPE_INTEGER);
            fieldDefs.add(Fields.PARENT_ID+IRecord.FIELD_TYPE_INTEGER);
            fieldDefs.add(Fields.CODE+IRecord.FIELD_TYPE_TEXT);
            fieldDefs.add(Fields.ARAE_NAME+IRecord.FIELD_TYPE_TEXT);
            fieldDefs.add(Fields.LEVEL+IRecord.FIELD_TYPE_INTEGER);
        }
        @Override
        public void readFieldValues(Cursor c) {
            super.readFieldValues(c);
            id =c.getInt(c.getColumnIndex(Fields.ID));
            parent_id =c.getInt(c.getColumnIndex(Fields.PARENT_ID));
            code= c.getString(c.getColumnIndex(Fields.CODE));
            area_name = c.getString(c.getColumnIndex(Fields.ARAE_NAME));
            level =c.getInt(c.getColumnIndex(Fields.LEVEL));
        }
        @Override
        public void writeFieldValues(ContentValues values) {
            super.writeFieldValues(values);
            values.put(Fields.ID, id);
            values.put(Fields.PARENT_ID, parent_id);
            values.put(Fields.CODE, code);
            values.put(Fields.ARAE_NAME, area_name);
            values.put(Fields.LEVEL, level);
        }

        public boolean isCity(){
            return level==2 ? true: false;
        }
        public boolean isProvince(){
            return level==1 ? true: false;
        }

        public boolean isAuto(){
            return id == AUTO;
        }

        public  static int getAutoId(){
            return AUTO;
        }

        @Override
        public String toString() {
            return area_name;
        }
        @Override
        public boolean equals(Object o) {
            return this.id == ((Area)o).getId();
        }

    }
}
