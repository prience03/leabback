/**
 * 
 */
package com.duolebo.appbase.cache;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

import com.duolebo.appbase.db.IRecord;
import com.duolebo.appbase.db.Table;
import com.duolebo.appbase.utils.Log;

/**
 * @author zlhl
 * @date 2014年4月25日
 */
public class CacheEntity implements IRecord {
	private static String TAG = "CacheEntity";
	
	private long pkId = NO_PKID;
	private ICacheable cacheable;
	
	private String stableKey;
	private String data;
	private long expire;
	
	public CacheEntity() {
	}
	
	public CacheEntity(ICacheable cacheable) {
		this.cacheable = cacheable;
	}
	
	public void set(ICacheable cacheable) {
		this.cacheable = cacheable;
	}

	@Override
	public long getPKIdValue() {
		return pkId;
	}

	@Override
	public String getPKIdKey() {
		return BaseColumns._ID;
	}

	@Override
	public void writeFieldValues(ContentValues values) {
		values.put(Fields.FIELD_DATA, data);
		if (null != cacheable) {
			values.put(Fields.FIELD_EXPIRE, cacheable.getExpire());
			values.put(Fields.FIELD_STABLEKEY, cacheable.getStableKey());
		}
	}

	@Override
	public void readFieldValues(Cursor c) {
		pkId = c.getLong(c.getColumnIndex(BaseColumns._ID));
		stableKey = c.getString(c.getColumnIndex(Fields.FIELD_STABLEKEY));
		data = c.getString(c.getColumnIndex(Fields.FIELD_DATA));
		expire = c.getLong(c.getColumnIndex(Fields.FIELD_EXPIRE));
	}

	@Override
	public void prepareFieldDefs(ArrayList<String> fieldDefs) {
		fieldDefs.add(BaseColumns._ID + FIELD_TYPE_PRIMARY);
		fieldDefs.add(Fields.FIELD_STABLEKEY + FIELD_TYPE_TEXT + CONSTRAINT_UNIQUE);
		fieldDefs.add(Fields.FIELD_DATA + FIELD_TYPE_TEXT);
		fieldDefs.add(Fields.FIELD_EXPIRE + FIELD_TYPE_NUMERIC);
	}
	
	public void setData(String data) {
		if (null == cacheable) return;
		this.data = data;
		Table t = CacheDB.getSelf(cacheable.getContext()).getTable(CacheDB.TABLE_CACHE);
		if (null != t) {
			String whereClause = Fields.FIELD_STABLEKEY + "=?";
			String[] whereArgs = new String[]{
					cacheable.getStableKey()
			};
			t.insertOrUpdate(this, whereClause, whereArgs);
		}
	}
	
	@SuppressWarnings("unchecked")
	public String getData() {
		if (null == cacheable) return null;
		Table t = CacheDB.getSelf(cacheable.getContext()).getTable(CacheDB.TABLE_CACHE);
		if (null != t) {
			String stableKey = cacheable.getStableKey();
			String whereClause = Fields.FIELD_STABLEKEY + "=?";
			String[] whereArgs = new String[] {
					stableKey
			};
			List<CacheEntity> entities = (List<CacheEntity>) t.query(whereClause, whereArgs);
			if (0 < entities.size()) {
				if (System.currentTimeMillis() < entities.get(0).expire) {
					Log.d(TAG, "for " + stableKey + ", we have a local copy, so use it to save some time");
					Log.d(TAG, "and the expired time of the local copy would be (UTC) " + DateFormat.getInstance().format(entities.get(0).expire));
					return entities.get(0).data;
				} else {
					whereClause = Fields.FIELD_EXPIRE + "<?";
					whereArgs = new String[] {
							String.valueOf(System.currentTimeMillis())
					};
					t.delete(whereClause, whereArgs);
				}
			}
		}
		return null;
	}
	
	interface Fields {
		public static String FIELD_STABLEKEY = "stableKey"; 
		public static String FIELD_DATA = "cachedData";
		public static String FIELD_EXPIRE = "expire";
	}

    @Override
    public void onModifyFieldValues() {
    }

}
