package com.duolebo.appbase.db;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;

public interface IRecord {
	
	public static final String FIELD_TYPE_PRIMARY = " INTEGER PRIMARY KEY";
	public static final String FIELD_TYPE_TEXT = " TEXT";
	public static final String FIELD_TYPE_NUMERIC = " NUMERIC";
	public static final String FIELD_TYPE_INTEGER = " INTEGER";
	public static final String FIELD_TYPE_REAL = " REAL";
	public static final String FIELD_TYPE_NONE = " NONE";
	
	public static final String CONSTRAINT_UNIQUE = " UNIQUE";
	
	public static final int NO_PKID = -1;

	public long getPKIdValue();
	public String getPKIdKey();
	public void onModifyFieldValues();
	public void writeFieldValues(ContentValues values);
	public void readFieldValues(Cursor c);
	public void prepareFieldDefs(ArrayList<String> fieldDefs);

}
