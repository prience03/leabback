package com.duolebo.appbase.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Table {

    public interface WhereClauseCallback {
        Map<String, String> getWhereClause(IRecord record);
    }

    public static final String ORDERBY_DESC = " DESC";
    public static final String ORDERBY_ASC = " ASC";

    private String name;
    private Class<? extends IRecord> cls;
    private IDatabase db;

    /**
     * 创建数据表
     * 
     * @param name 表名。注意避免使用 class.getSimpleName()之类的函数生成表名，以免代码混淆后，表名发生改变。
     * @param cls
     * @param db
     */
    public Table(String name, Class<? extends IRecord> cls, IDatabase db) {
        this.name = name;
        this.cls = cls;
        this.db = db;
    }

    public String getTableName() {
        return name;
    }

    public String getTableCreateClause() {
        try {
            IRecord r = cls.newInstance();
            ArrayList<String> fieldDefs = new ArrayList<String>();
            r.prepareFieldDefs(fieldDefs);
            StringBuilder sb = new StringBuilder();
            sb.append("CREATE TABLE ").append(name);
            sb.append("(");
            for (String def : fieldDefs) {
                sb.append(def).append(",");
            }
            int idx = sb.lastIndexOf(",");
            if (-1 != idx) {
                sb.deleteCharAt(idx);
            }
            sb.append(");");
            return sb.toString();

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<? extends IRecord> query() {
        return query(null, null, null, null, null);
    }

    /*
     * i.e. table.query(Fields.DATE_MODIFIED + Table.ORDERBY_DESC);
     */
    public List<? extends IRecord> query(String orderBy) {
        return query(null, null, null, null, orderBy);
    }

    public List<? extends IRecord> query(String whereClause, String whereArgs[]) {
        return query(whereClause, whereArgs, null, null, null);
    }

    public List<? extends IRecord> query(String whereClause, String whereArgs[], String orderBy) {
        return query(whereClause, whereArgs, null, null, orderBy);
    }

    public List<? extends IRecord> query(String whereClause, String whereArgs[], String groupBy,
            String having, String orderBy) {
        List<IRecord> records = new ArrayList<IRecord>();
        SQLiteDatabase sqlite = db.open().getReadableDatabase();
        Cursor c = sqlite.query(name, null, whereClause, whereArgs, groupBy, having, orderBy);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            try {
                IRecord record = cls.newInstance();
                record.readFieldValues(c);
                records.add(record);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            c.moveToNext();
        }
        c.close();
        db.close();
        return records;
    }

    public List<? extends IRecord> query(Map<String, String> whereMap) {
        whereClauseHelper helper = new whereClauseHelper(whereMap);
        return query(helper.getWhereCluase(), helper.getWhereArgs(), null, null, null);
    }

    public List<? extends IRecord> query(Map<String, String> whereMap, String orderBy) {
        whereClauseHelper helper = new whereClauseHelper(whereMap);
        return query(helper.getWhereCluase(), helper.getWhereArgs(), null, null, orderBy);
    }

    public List<? extends IRecord> query(Map<String, String> whereMap, String groupBy,
            String having, String orderBy) {
        whereClauseHelper helper = new whereClauseHelper(whereMap);
        return query(helper.getWhereCluase(), helper.getWhereArgs(), groupBy, having, orderBy);
    }

    public long insert(IRecord record) {
        long count = 0l;
        if (null != db && null != record) {
            SQLiteDatabase sqlite = db.open().getWritableDatabase();
            ContentValues values = new ContentValues();
            record.writeFieldValues(values);
            count = sqlite.insert(name, null, values);
            db.close();
        }
        return count;
    }

    public long insert(List<? extends IRecord> records) {
        long count = 0l;
        if (null != db && null != records) {
            SQLiteDatabase sqlite = db.open().getWritableDatabase();
            sqlite.beginTransaction();
            for (IRecord record : records) {
                ContentValues values = new ContentValues();
                record.writeFieldValues(values);
                count += sqlite.insert(name, null, values);
            }
            sqlite.setTransactionSuccessful();
            sqlite.endTransaction();
            db.close();
        }
        return count;
    }

    public long insertOrUpdate(IRecord record, String fieldName, String fieldValue) {
        String whereClause = fieldName + "=?";
        String whereArgs[] = new String[] {
            fieldValue
        };
        return insertOrUpdate(record, whereClause, whereArgs);
    }

    public long insertOrUpdate(IRecord record, WhereClauseCallback callback) {
        return insertOrUpdate(record, callback.getWhereClause(record));
    }

    public long insertOrUpdate(IRecord record, Map<String, String> whereMap) {
        whereClauseHelper helper = new whereClauseHelper(whereMap);
        return insertOrUpdate(record, helper.getWhereCluase(), helper.getWhereArgs());
    }

    public long insertOrUpdate(IRecord record, String whereClause, String whereArgs[]) {
        long count = 0l;
        if (null != db && null != record) {
            SQLiteDatabase sqlite = db.open().getWritableDatabase();
            Cursor c = sqlite.query(name, null, whereClause, whereArgs, null, null, null);
            if (0 < c.getCount()) {
                count = update(record, whereClause, whereArgs);
            } else {
                count = insert(record);
            }
            c.close();
            db.close();
        }
        return count;
    }

    public long insertOrUpdate(List<? extends IRecord> records, WhereClauseCallback callback) {
        List<Map<String, String>> whereMaps = new ArrayList<Map<String, String>>();
        for (int i = 0; i < records.size(); i++) {
            IRecord record = records.get(i);
            Map<String, String> whereMap = callback.getWhereClause(record);
            whereMaps.add(whereMap);
        }
        return insertOrUpdate(records, whereMaps);
    }

    public long insertOrUpdate(List<? extends IRecord> records, List<Map<String, String>> whereMaps) {
        long count = 0l;
        if (null == db || null == records || null == whereMaps)
            return count;
        if (records.size() != whereMaps.size())
            return count;

        SQLiteDatabase sqlite = db.open().getWritableDatabase();
        sqlite.beginTransaction();
        for (int i = 0; i < records.size(); i++) {
            IRecord record = records.get(i);
            ContentValues values = new ContentValues();

            Map<String, String> whereMap = whereMaps.get(i);
            whereClauseHelper helper = new whereClauseHelper(whereMap);

            Cursor c = sqlite.query(name, null, helper.getWhereCluase(), helper.getWhereArgs(),
                    null, null, null);
            if (0 < c.getCount()) {
                record.onModifyFieldValues();
                record.writeFieldValues(values);
                count += sqlite
                        .update(name, values, helper.getWhereCluase(), helper.getWhereArgs());
            } else {
                record.writeFieldValues(values);
                count += sqlite.insert(name, null, values);
            }
            c.close();
        }
        sqlite.setTransactionSuccessful();
        sqlite.endTransaction();
        db.close();

        return count;
    }

    public long update(IRecord record) {
        if (null != record) {
            return update(record, record.getPKIdKey(), Long.toString(record.getPKIdValue()));
        }
        return 0l;
    }

    public long update(IRecord record, String fieldName, String fieldValue) {
        String whereClause = fieldName + "=?";
        String whereArgs[] = new String[] {
            fieldValue
        };
        return update(record, whereClause, whereArgs);
    }

    public long update(IRecord record, Map<String, String> whereMap) {
        whereClauseHelper helper = new whereClauseHelper(whereMap);
        return update(record, helper.getWhereCluase(), helper.getWhereArgs());
    }

    public long update(IRecord record, WhereClauseCallback callback) {
        whereClauseHelper helper = new whereClauseHelper(callback.getWhereClause(record));
        return update(record, helper.getWhereCluase(), helper.getWhereArgs());
    }

    public long update(IRecord record, String whereClause, String whereArgs[]) {
        long count = 0l;
        if (null != db && null != record) {
            SQLiteDatabase sqlite = db.open().getWritableDatabase();
            ContentValues values = new ContentValues();
            record.onModifyFieldValues();
            record.writeFieldValues(values);
            count = sqlite.update(name, values, whereClause, whereArgs);
            db.close();
        }
        return count;
    }

    public long updateRawValues(ContentValues values, Map<String, String> whereMap) {
        whereClauseHelper helper = new whereClauseHelper(whereMap);
        return updateRawValues(values, helper.getWhereCluase(), helper.getWhereArgs());
    }
    
    public long updateRawValues(ContentValues values, String whereClause, String whereArgs[]) {
        long count = 0l;
        if (null != db) {
            SQLiteDatabase sqlite = db.open().getWritableDatabase();
            count = sqlite.update(name, values, whereClause, whereArgs);
            db.close();
        }
        return count;
    }

    public long delete(IRecord record) {
        long count = 0l;
        if (null != record) {
            String whereClause = record.getPKIdKey() + "=?";
            String whereArgs[] = new String[] {
                Long.toString(record.getPKIdValue())
            };
            return delete(whereClause, whereArgs);
        }
        return count;
    }

    public long delete(Map<String, String> whereMap) {
        whereClauseHelper helper = new whereClauseHelper(whereMap);
        return delete(helper.getWhereCluase(), helper.getWhereArgs());
    }

    public long delete(String whereClause, String whereArgs[]) {
        long count = 0l;
        if (null != db) {
            SQLiteDatabase sqlite = db.open().getWritableDatabase();
            count = sqlite.delete(name, whereClause, whereArgs);
            db.close();
        }
        return count;
    }

    public long deleteAll() {
        return delete(null, null);
    }

    class whereClauseHelper {
        String whereClause = null;
        String whereArgs[] = null;

        public whereClauseHelper(Map<String, String> whereMap) {
            if (null != whereMap && 0 < whereMap.size()) {
                StringBuilder sb = new StringBuilder();
                whereArgs = new String[whereMap.size()];
                int idx = 0;
                for (Map.Entry<String, String> where : whereMap.entrySet()) {
                    if (0 < sb.length()) {
                        sb.append(" AND ");
                    }
                    sb.append(where.getKey()).append("=?");
                    whereArgs[idx++] = where.getValue();
                }
                whereClause = sb.toString();
            }
        }

        String getWhereCluase() {
            return whereClause;
        }

        String[] getWhereArgs() {
            return whereArgs;
        }
    }

}
