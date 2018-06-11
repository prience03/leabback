package com.duolebo.appbase.db;

import android.database.sqlite.SQLiteDatabase;

/**
 * in most cases, you don't need this interface, instead,
 * subclassing the SampleDB is a better way to go.
 * 
 * @author zlhl
 * @date 2014年4月8日
 */
interface IDatabase {
	public IDatabase open();
	public void close();
	public SQLiteDatabase getWritableDatabase();
	public SQLiteDatabase getReadableDatabase();
}
