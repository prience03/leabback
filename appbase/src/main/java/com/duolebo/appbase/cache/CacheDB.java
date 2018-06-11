/**
 * 
 */
package com.duolebo.appbase.cache;

import android.content.Context;

import com.duolebo.appbase.db.SampleDB;
import com.duolebo.appbase.db.Table;

/**
 * @author zlhl
 * @date 2014年4月25日
 */
public class CacheDB extends SampleDB {
	
	static final String DATABASE_CACHE = "cacheDB";
	static public final String TABLE_CACHE = "cacheTable";
	static final int VERSION = 1;

	private static CacheDB db = null;
	
	public static CacheDB getSelf(Context context) {
		if (null == db) {
			db = new CacheDB(context, DATABASE_CACHE, VERSION);
		}
		return db;
	}
	
	public CacheDB(Context context, String name, int version) {
		super(context, name, version);
		
		Table cacheTable = new Table(TABLE_CACHE, CacheEntity.class, this);
		putTable(TABLE_CACHE, cacheTable);
	}

}
