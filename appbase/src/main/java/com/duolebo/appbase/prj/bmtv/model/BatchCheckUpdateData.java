/**
 * 
 */
package com.duolebo.appbase.prj.bmtv.model;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;

import com.duolebo.appbase.app.AppManager;
import com.duolebo.appbase.prj.Model;
import com.duolebo.appbase.utils.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zlhl
 * @date 2014年5月12日
 */
public class BatchCheckUpdateData extends ModelBase {
    
    private List<Content> contentlist = new ArrayList<Content>();

    @Override
    public boolean from(JSONObject json) {
        if (super.from(json)) {
            contentlist.clear();
	        json = json.optJSONObject("response").optJSONObject("body");
            JSONArray jArray = json.optJSONArray("contentlist");
            for (int i=0; null!=jArray && i<jArray.length(); i++) {
                Content content = new Content();
                content.from(jArray.optJSONObject(i));
                contentlist.add(content);
            }
            return true;
        }
        return false;
    }
    
    public List<Content> getContentList() {
        return contentlist;
    }

    public boolean hasUpdates(Context context) {
        List<ApplicationInfo> apps = AppManager.getAllAppInfo(context, false);
        for (Content content : contentlist) {
            for (ApplicationInfo app : apps) {
                if (app.packageName.equalsIgnoreCase(content.package_name)) {
                    if (content.compareVersionWith(context, app)) {
                        Log.i("BatchCheckUpdate", "hasUpdate return true, " + content.package_name);
                        return true;
                    }
                }
            }
        }
        Log.i("BatchCheckUpdate", "hasUpdate return false.");
        return false;
    }
        
    public static class Content extends Model {
        
        public interface Fields extends Model.Fields {
            public static final String CONTENTID = "contentid";
            public static final String PACKAGE_NAME = "package_name";
            public static final String VERSION = "version";
            public static final String VERSION_CODE = "versioncode";
            public static final String TAGS = "tags";
        }

        private String contentid = "";
        private String package_name = "";
        private String version = "";
        private int version_code = -1;
        private String tags = "";

        public Content() {}

        public Content(Content content) {
            super(content);
            contentid = content.contentid;
            package_name = content.package_name;
            version = content.version;
            version_code = content.version_code;
            tags = content.tags;
        }

        public Content(Model model) {
            super(model);
        }

        @Override
        public boolean from(JSONObject json) {
            if (super.from(json)) {
                contentid = json.optString(Fields.CONTENTID);
                package_name = json.optString(Fields.PACKAGE_NAME);
                version = json.optString(Fields.VERSION);
                version_code = json.optInt(Fields.VERSION_CODE);
                tags = json.optString(Fields.TAGS);
                return true;
            }
            return false;
        }

        @Override
        public void prepareFieldDefs(ArrayList<String> fieldDefs) {
            super.prepareFieldDefs(fieldDefs);
            fieldDefs.add(Fields.CONTENTID + FIELD_TYPE_TEXT);
            fieldDefs.add(Fields.PACKAGE_NAME + FIELD_TYPE_TEXT);
            fieldDefs.add(Fields.VERSION + FIELD_TYPE_TEXT);
            fieldDefs.add(Fields.VERSION_CODE + FIELD_TYPE_NUMERIC);
            fieldDefs.add(Fields.TAGS + FIELD_TYPE_TEXT);
        }

        @Override
        public void writeFieldValues(ContentValues values) {
            super.writeFieldValues(values);
            values.put(Fields.CONTENTID, contentid);
            values.put(Fields.PACKAGE_NAME, package_name);
            values.put(Fields.VERSION, version);
            values.put(Fields.VERSION_CODE, version_code);
            values.put(Fields.TAGS, tags);
        }

        @Override
        public void readFieldValues(Cursor c) {
            super.readFieldValues(c);
            contentid = c.getString(c.getColumnIndex(Fields.CONTENTID));
            package_name = c.getString(c.getColumnIndex(Fields.PACKAGE_NAME));
            version = c.getString(c.getColumnIndex(Fields.VERSION));
            version_code = c.getInt(c.getColumnIndex(Fields.VERSION_CODE));
            tags = c.getString(c.getColumnIndex(Fields.TAGS));
        }

        public String getContentId() {
            return contentid;
        }

        public void setContentId(String contentid) {
            this.contentid = contentid;
        }

        public String getPackageName() {
            return package_name;
        }

        public void setPackageName(String package_name) {
            this.package_name = package_name;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public int getVersionCode() {
            return version_code;
        }

        public void setVersionCode(int version_code) {
            this.version_code = version_code;
        }

        public String getTags() {
            return tags;
        }

        public void setTags(String tags) {
            this.tags = tags;
        }
        
        public boolean compareVersionWith(Content content) {
            if (null == content)
                return false;
            
            if (!package_name.equalsIgnoreCase(content.package_name))
                return false;
            
            if (0 < content.version_code && 0 < version_code) {
                return content.version_code < version_code;
            }
            
            return compareVersions(content.version, version);
        }
        
        public boolean compareVersionWith(Context context, ApplicationInfo appInfo) {
            if (null == appInfo)
                return false;
            
            if (!package_name.equalsIgnoreCase(appInfo.packageName))
                return false;

            PackageManager pm = context.getPackageManager();
            PackageInfo pInfo = null;
            try {
                pInfo = pm.getPackageInfo(appInfo.packageName, 0);
            } catch (NameNotFoundException e) {
                e.printStackTrace();
            }
            
            if (null == pInfo)
                return false;
            
            Log.i("BatchCheckUpdate", "comparing " + appInfo.packageName 
                    + " local:" + pInfo.versionCode + " vs remote:" + getVersionCode());

            boolean ret = false;
            if (0 < getVersionCode()) {
                ret = pInfo.versionCode < getVersionCode();
                Log.i("BatchCheckUpdate", "ret: " + ret);
                return ret;
            }
            
            ret = compareVersions(pInfo.versionName, getVersion());
            Log.i("BatchCheckUpdate", "ret: " + ret);
            return ret;
        }
        
        private boolean compareVersions(String v1, String v2) {
            String[] Ver1NameArr = v1.split("\\.");
            String[] Ver2NameArr = v2.split("\\.");

            Log.i("BatchCheckUpdate", "comparing versionString" 
                    + " local:" + v1 + " vs remote:" + v2);

            try {
                for (int i = 0, len = Math.min(Ver1NameArr.length, Ver2NameArr.length); i < len; i++) {
                    int ver1Int = Integer.valueOf(Ver1NameArr[i]);
                    int ver2Int = Integer.valueOf(Ver2NameArr[i]);
                    if (ver2Int != ver1Int) {
                        return ver2Int > ver1Int;
                    }
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            return false;
        }
    }

}
