package com.example.batrakov.contentproviderdatabase.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Database helper optimize and rule work with SQLiteDatabase.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String TAG = DBHelper.class.getSimpleName();

    /**
     * Constructor.
     *
     * @param aContext aContext from Activity for super method.
     */
    public DBHelper(Context aContext) {
        super(aContext, DBContract.DATABASE_NAME, null, DBContract.DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            db.execSQL(DBContract.FIRST_TABLE_CREATE_ENTRIES);
            db.execSQL(DBContract.SECOND_TABLE_CREATE_ENTRIES);
        } catch (SQLiteException aE) {
            Log.d(TAG, "DBHelper: tables already exist");
        }
    }

    @Override
    public void onCreate(SQLiteDatabase aDatabase) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase aDatabase, int aOldVersion, int aNewVersion) {

    }

    /**
     * Delete first table from Database.
     */
    private void deleteFirstTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DBContract.Entry.FIRST_TABLE_NAME, null, null);
    }

    /**
     * Delete second table from Database.
     */
    private void deleteSecondTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DBContract.Entry.SECOND_TABLE_NAME, null, null);
    }

    /**
     * Delete both tables from Database.
     */
    void deleteTables() {
        deleteFirstTable();
        deleteSecondTable();
    }
}
