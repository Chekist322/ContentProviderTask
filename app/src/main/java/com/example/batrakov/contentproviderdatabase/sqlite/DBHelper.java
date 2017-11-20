package com.example.batrakov.contentproviderdatabase.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by batrakov on 16.11.17.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String TAG = DBHelper.class.getSimpleName();

    public DBHelper(Context context) {
        super(context, DBContract.DATABASE_NAME, null, DBContract.DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            db.execSQL(DBContract.FIRST_TABLE_CREATE_ENTRIES);
            db.execSQL(DBContract.SECOND_TABLE_CREATE_ENTRIES);
        } catch (SQLiteException aE) {
            Log.d(TAG, "DBHelper: tables already exist");
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void deleteFirstTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DBContract.Entry.FIRST_TABLE_NAME, null, null);
    }

    public void deleteSecondTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DBContract.Entry.SECOND_TABLE_NAME, null, null);
    }

    public void deleteTables() {
        deleteFirstTable();
        deleteSecondTable();
    }
}
