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
    DBHelper(Context aContext) {
        super(aContext, DBContract.DATABASE_NAME, null, DBContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase aDatabase) {
        try {
            aDatabase.execSQL(DBContract.FIRST_TABLE_CREATE_ENTRIES);
            aDatabase.execSQL(DBContract.SECOND_TABLE_CREATE_ENTRIES);
        } catch (SQLiteException aE) {
            aE.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase aDatabase, int aOldVersion, int aNewVersion) {
        System.out.println(aDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
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
