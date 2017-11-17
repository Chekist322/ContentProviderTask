package com.example.batrakov.contentproviderdatabase.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by batrakov on 16.11.17.
 */

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, DBContract.DATABASE_NAME, null, DBContract.DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();

        createFox("Vasya", 13);
        createFox("Petya", 12);
        createFox("Kolya", 11);

        createBadger("Tolyan", 20);
        createBadger("Mamon", 5);
        createBadger("Torvald", 8);
        createBadger("Gustav", 11);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(DBContract.FIRST_TABLE_CREATE_ENTRIES);
            db.execSQL(DBContract.SECOND_TABLE_CREATE_ENTRIES);
        } catch (SQLiteException aE) {
            aE.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void deleteFirstTable(Context aContext) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.execSQL(DBContract.DELETE_FIRST_TABLE_ENTRIES);
        } catch (SQLiteException aE) {
         aE.printStackTrace();
            Toast.makeText(aContext, "Table already deleted", Toast.LENGTH_LONG).show();
        }
    }

    public void deleteSecondTable(Context aContext) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.execSQL(DBContract.DELETE_SECOND_TABLE_ENTRIES);
        } catch (SQLiteException aE) {
            aE.printStackTrace();
            Toast.makeText(aContext, "Table already deleted", Toast.LENGTH_LONG).show();
        }
    }

    public void deleteTables(Context aContext) {
        deleteFirstTable(aContext);
        deleteSecondTable(aContext);
    }

    public void createFox(String aName, int aAge) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBContract.Entry.COLUMN_NAME_NAME, aName);
        values.put(DBContract.Entry.COLUMN_NAME_AGE, aAge);

        db.insert(DBContract.Entry.FIRST_TABLE_NAME, null, values);
    }

    public void createBadger(String aName, int aAge) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBContract.Entry.COLUMN_NAME_NAME, aName);
        values.put(DBContract.Entry.COLUMN_NAME_AGE, aAge);

        db.insert(DBContract.Entry.SECOND_TABLE_NAME, null, values);
    }
}
