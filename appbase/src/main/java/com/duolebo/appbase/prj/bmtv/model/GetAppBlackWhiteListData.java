package com.duolebo.appbase.prj.bmtv.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.text.TextUtils;

import com.duolebo.appbase.prj.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ottauto on 16-1-18.
 */
public class GetAppBlackWhiteListData extends ModelBase {

    public interface Fields extends Model.Fields {
        String WHITESIZE = "whitesize";
        String WHITELIST = "whitelist";
        String BLACKSIZE = "blacksize";
        String BLACKLIST = "blacklist";
    }

    private int whitesize = -1;
    private List<App> whitelist = new ArrayList<App>();

    private int blacksize = -1;
    private List<App> blacklist = new ArrayList<App>();

    public GetAppBlackWhiteListData() {}

    public GetAppBlackWhiteListData(GetAppBlackWhiteListData getappblackwhitelistdata) {
        super(getappblackwhitelistdata);
        whitesize = getappblackwhitelistdata.whitesize;
        whitelist = getappblackwhitelistdata.whitelist;
        blacksize = getappblackwhitelistdata.blacksize;
        blacklist = getappblackwhitelistdata.blacklist;
    }

    @Override
    public boolean from(JSONObject json) {
        if (super.from(json)) {
            json = json.optJSONObject("response").optJSONObject("body");
            whitesize = json.optInt(Fields.WHITESIZE);
            JSONArray jarr = json.optJSONArray(Fields.WHITELIST);
            for (int i=0; null!=jarr&&i<jarr.length(); i++) {
                App app = new App();
                app.from(jarr.optJSONArray(i));
                whitelist.add(app);
            }
            blacksize = json.optInt(Fields.BLACKSIZE);
            jarr = json.optJSONArray(Fields.BLACKLIST);
            for (int i = 0; null != jarr && i < jarr.length(); i++) {
                App app = new App();
                app.from(jarr.optJSONArray(i));
                blacklist.add(app);
            }
            return true;
        }
        return false;
    }

    @Override
    public void prepareFieldDefs(ArrayList<String> fieldDefs) {
        super.prepareFieldDefs(fieldDefs);
        fieldDefs.add(Fields.WHITESIZE + FIELD_TYPE_NUMERIC);
        fieldDefs.add(Fields.WHITELIST + FIELD_TYPE_TEXT);
        fieldDefs.add(Fields.BLACKSIZE + FIELD_TYPE_NUMERIC);
        fieldDefs.add(Fields.BLACKLIST + FIELD_TYPE_TEXT);
    }

    @Override
    public void writeFieldValues(ContentValues values) {
        super.writeFieldValues(values);
        values.put(Fields.WHITESIZE, whitesize);
        JSONArray jarr = new JSONArray();
        for (App app : whitelist) {
            jarr.put(app.getJSON());
        }
        values.put(Fields.WHITELIST, jarr.toString());
        values.put(Fields.BLACKSIZE, blacksize);
        jarr = new JSONArray();
        for (App app : blacklist) {
            jarr.put(app.getJSON());
        }
        values.put(Fields.BLACKLIST, jarr.toString());
    }

    @Override
    public void readFieldValues(Cursor c) {
        super.readFieldValues(c);
        whitesize = c.getInt(c.getColumnIndex(Fields.WHITESIZE));
        String whitelistStr = c.getString(c.getColumnIndex(Fields.WHITELIST));
        blacksize = c.getInt(c.getColumnIndex(Fields.BLACKSIZE));
        String blacklistStr = c.getString(c.getColumnIndex(Fields.BLACKLIST));
        try {
            if (!TextUtils.isEmpty(whitelistStr)) {
                JSONArray jarr = new JSONArray(whitelistStr);
                for (int i=0; null!=jarr&&i<jarr.length(); i++) {
                    App app = new App();
                    app.from(jarr.optJSONArray(i));
                    whitelist.add(app);
                }
            }
            if (!TextUtils.isEmpty(blacklistStr)) {
                JSONArray jarr = new JSONArray(blacklistStr);
                for (int i=0; null!=jarr&&i<jarr.length(); i++) {
                    App app = new App();
                    app.from(jarr.optJSONArray(i));
                    blacklist.add(app);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public boolean isValidPackage(String packageName) {
        if (TextUtils.isEmpty(packageName)) return false;
        if (0 < whitelist.size()) {
            for (App app : whitelist) {
                if (packageName.equals(app.packagename)) return true;
            }
            return false;
        } else {
            for (App app : blacklist) {
                if (packageName.equals(app.packagename)) return false;
            }
            return true;
        }
    }

    public int getWhiteSize() {
        return whitesize;
    }

    public void setWhiteSize(int whitesize) {
        this.whitesize = whitesize;
    }

    public List<App> getWhiteList() {
        return whitelist;
    }

    public void setWhiteList(List<App> whitelist) {
        this.whitelist = whitelist;
    }

    public int getBlackSize() {
        return blacksize;
    }

    public void setBlackSize(int blacksize) {
        this.blacksize = blacksize;
    }

    public List<App> getBlackList() {
        return blacklist;
    }

    public void setBlackList(List<App> blacklist) {
        this.blacklist = blacklist;
    }

    public static class App extends Model {

        public interface Fields extends Model.Fields {
            String PACKAGENAME = "packagename";
            String NAME = "name";
        }

        private String packagename = "";
        private String name = "";

        public App() {}

        public App(App app) {
            super(app);
            packagename = app.packagename;
            name = app.name;
        }

        public App(Model model) {
            super(model);
        }

        @Override
        public boolean from(JSONObject json) {
            if (super.from(json)) {
                packagename = json.optString(Fields.PACKAGENAME);
                name = json.optString(Fields.NAME);
                return true;
            }
            return false;
        }

        @Override
        public void prepareFieldDefs(ArrayList<String> fieldDefs) {
            super.prepareFieldDefs(fieldDefs);
            fieldDefs.add(Fields.PACKAGENAME + FIELD_TYPE_TEXT);
            fieldDefs.add(Fields.NAME + FIELD_TYPE_TEXT);
        }

        @Override
        public void writeFieldValues(ContentValues values) {
            super.writeFieldValues(values);
            values.put(Fields.PACKAGENAME, packagename);
            values.put(Fields.NAME, name);
        }

        @Override
        public void readFieldValues(Cursor c) {
            super.readFieldValues(c);
            packagename = c.getString(c.getColumnIndex(Fields.PACKAGENAME));
            name = c.getString(c.getColumnIndex(Fields.NAME));
        }

        public String getPackageName() {
            return packagename;
        }

        public void setPackageName(String packagename) {
            this.packagename = packagename;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }
}
