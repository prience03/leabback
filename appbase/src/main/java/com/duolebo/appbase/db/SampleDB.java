package com.duolebo.appbase.db;

import java.util.HashMap;
import java.util.Map;

import com.duolebo.appbase.utils.Log;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * subclass this class to create your own database,
 * 
 * @author zlhl
 * @date 2014年4月8日
 */
public class SampleDB extends SQLiteOpenHelper implements IDatabase {

	private Map<String, Table> tables = new HashMap<String, Table>();
	private int openedConnections = 0;

	public SampleDB(Context context, String name, int version) {
		super(context, name, null, version);
	}

	public void putTable(String name, Table table) {
		if (!tables.containsKey(name)) {
			tables.put(name, table);
		}
	}

	public Table getTable(String name) {
		return tables.get(name);
	}

	@Override
	public synchronized IDatabase open() {
		openedConnections++;
		return this;
	}

	@Override
	public synchronized void close() {
		if (0 == --openedConnections)
			super.close();
	}
	
	@Override
	public SQLiteDatabase getWritableDatabase() {
		return super.getWritableDatabase();
	}

	@Override
	public SQLiteDatabase getReadableDatabase() {
		return super.getReadableDatabase();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		for (Map.Entry<String, Table> entry : tables.entrySet()) {
			Table table = entry.getValue();
			String createClause = table.getTableCreateClause();
			if (null == createClause) {
				Log.d("SampleDB", "Empty CREATE clause.");
				continue;
			}
			db.execSQL(createClause);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// override this method when database schema has changed.
	}

}
