package com.lzy.okgo.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lzy.okgo.utils.OkLogger;


public class DBUtils {


    public static boolean isNeedUpgradeTable(SQLiteDatabase db, TableEntity table) {
        if (!isTableExists(db, table.tableName)) return true;

        Cursor cursor = db.rawQuery("select * from " + table.tableName, null);
        if (cursor == null) return false;
        try {
            int columnCount = table.getColumnCount();
            if (columnCount == cursor.getColumnCount()) {
                for (int i = 0; i < columnCount; i++) {
                    if (table.getColumnIndex(cursor.getColumnName(i)) == -1) {
                        return true;
                    }
                }
            } else {
                return true;
            }
            return false;
        } finally {
            cursor.close();
        }
    }


    public static boolean isTableExists(SQLiteDatabase db, String tableName) {
        if (tableName == null || db == null || !db.isOpen()) return false;

        Cursor cursor = null;
        int count = 0;
        try {
            cursor = db.rawQuery("SELECT COUNT(*) FROM sqlite_master WHERE type = ? AND name = ?", new String[]{"table", tableName});
            if (!cursor.moveToFirst()) {
                return false;
            }
            count = cursor.getInt(0);
        } catch (Exception e) {
            OkLogger.printStackTrace(e);
        } finally {
            if (cursor != null) cursor.close();
        }
        return count > 0;
    }

    public static boolean isFieldExists(SQLiteDatabase db, String tableName, String fieldName) {
        if (tableName == null || db == null || fieldName == null || !db.isOpen()) return false;

        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM " + tableName + " LIMIT 0", null);
            return cursor != null && cursor.getColumnIndex(fieldName) != -1;
        } catch (Exception e) {
            OkLogger.printStackTrace(e);
            return false;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}
