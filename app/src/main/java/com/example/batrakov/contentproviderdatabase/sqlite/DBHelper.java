package com.example.batrakov.contentproviderdatabase.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

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
        System.out.println(aDatabase.isReadOnly());
        try {
            aDatabase.execSQL(DBContract.FIRST_TABLE_CREATE_ENTRIES);
            aDatabase.execSQL(DBContract.SECOND_TABLE_CREATE_ENTRIES);
        } catch (SQLiteException aE) {
            aE.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase aDatabase, int aOldVersion, int aNewVersion) {
        if (aOldVersion == 1 && aNewVersion == 2) {
            aDatabase.execSQL("ALTER TABLE " + DBContract.Entry.FIRST_TABLE_NAME
                    + " ADD COLUMN " + DBContract.Entry.COLUMN_NAME_COLOR + " TEXT DEFAULT " + "'red'");
        }
    }

    /**
     * Delete first table from Database.
     */
    private void clearFirstTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DBContract.Entry.FIRST_TABLE_NAME, null, null);
    }

    /**
     * Delete second table from Database.
     */
    private void clearSecondTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DBContract.Entry.SECOND_TABLE_NAME, null, null);
    }

    /**
     * Clear both tables from Database.
     */
    void clearTables() {
        clearFirstTable();
        clearSecondTable();
    }
}
