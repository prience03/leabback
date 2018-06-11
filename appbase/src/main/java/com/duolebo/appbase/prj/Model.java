package com.duolebo.appbase.prj;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.view.View;

import com.duolebo.appbase.IModel;
import com.duolebo.appbase.IView;
import com.duolebo.appbase.db.IRecord;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/*
 * An easy code base to get you started.
 */
abstract public class Model implements IModel, IRecord, IView {

	private long pkId = NO_PKID;
	private long dateCreated = 0l;
	private long dateModified = 0l;

	private JSONObject json = null;
	private String rawData = "";
	
	private boolean isModifying = false;
	
	public Model() {
	    // empty constructor
	}
	
	public Model(Model model) {
	    // copy constructor
	    pkId = model.pkId;
	    dateCreated = model.dateCreated;
	    dateModified = model.dateCreated;
	    json = model.json;
	    rawData = model.rawData;
	}

	@Override
	public boolean from(JSONObject json) {
		if (null != json) {
			this.json = json;
			return true;
		}
		return false;
	}

	@Override
	public boolean from(XMLHelper parser) {
		return true;
	}

	@Override
	public boolean from(JSONArray jsonArray) {
		return false;
	}

	public JSONObject getJSON() {
		return json;
	}
	
	/* public */ void setRawData(String rawData) {
	    this.rawData = rawData;
	}
	
	public String getRawData() {
	    return rawData;
	}

	@Override
	public long getPKIdValue() {
		return pkId;
	}

	@Override
	public String getPKIdKey() {
		return Fields._ID;
	}
	
	@Override
	public void onModifyFieldValues() {
	    isModifying = true;
	}

	@Override
	public void writeFieldValues(ContentValues values) {
	    // don't update pkId, as it'd be updated automatically.
	    if (!isModifying) {
	        // create new record.
	        dateCreated = System.currentTimeMillis();
	        values.put(Fields.DATE_CREATED, dateCreated);
	        dateModified = dateCreated;
	        values.put(Fields.DATE_MODIFIED, dateModified);
	    } else {
	        // update existing record.
	        dateModified = System.currentTimeMillis();
	        values.put(Fields.DATE_MODIFIED, dateModified);
	        isModifying = false;
	    }
	}

	@Override
	public void readFieldValues(Cursor c) {
		pkId = c.getLong(c.getColumnIndex(getPKIdKey()));
		dateCreated = c.getLong(c.getColumnIndex(Fields.DATE_CREATED));
		dateModified = c.getLong(c.getColumnIndex(Fields.DATE_MODIFIED));
	}

	@Override
	public void prepareFieldDefs(ArrayList<String> fieldDefs) {
		fieldDefs.add(getPKIdKey() + FIELD_TYPE_PRIMARY);
		fieldDefs.add(Fields.DATE_CREATED + FIELD_TYPE_NUMERIC);
		fieldDefs.add(Fields.DATE_MODIFIED + FIELD_TYPE_NUMERIC);
	}

	public interface Fields extends BaseColumns {
	    public static final String DATE_CREATED = "date_created";
	    public static final String DATE_MODIFIED = "date_modified";
	}

    public long getDateCreated() {
        return dateCreated;
    }

    public long getDateModified() {
        return dateModified;
    }
	
    /*
     * Subclasses should (eventually) override this function
     * to supply corresponding view.
     */
    @Override
    public View getView(int type, View convertView) {
        return null;
    }
    
    /*
     * for view to process onclick event.
     */
    @Override
    public void onViewClick(View view) {
    }
}
