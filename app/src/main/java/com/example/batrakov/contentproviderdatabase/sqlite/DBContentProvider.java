package com.example.batrakov.contentproviderdatabase.sqlite;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Content provider for accessing Database safely and allow to work with
 * database by remote queries from another apps.
 */
public class DBContentProvider extends ContentProvider {

    private static final UriMatcher URI_MATCHER;

    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

        URI_MATCHER.addURI(DBContract.CONTENT_AUTHORITY,
                DBContract.Entry.FIRST_TABLE_NAME, DBContract.URI_FIRST_TABLE_ALL);

        URI_MATCHER.addURI(DBContract.CONTENT_AUTHORITY,
                DBContract.Entry.SECOND_TABLE_NAME, DBContract.URI_SECOND_TABLE_ALL);

        URI_MATCHER.addURI(DBContract.CONTENT_AUTHORITY, DBContract.Entry.FIRST_TABLE_NAME
                + "/#", DBContract.URI_FIRST_TABLE_SINGLE_ID);

        URI_MATCHER.addURI(DBContract.CONTENT_AUTHORITY, DBContract.Entry.SECOND_TABLE_NAME
                + "/#", DBContract.URI_SECOND_TABLE_SINGLE_ID);
    }

    private DBHelper mDBHelper;
    private SQLiteDatabase mDatabase;

    @Override
    public boolean onCreate() {
        mDBHelper = new DBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri aUri, @Nullable String[] aProjection, @Nullable String aSelection,
                        @Nullable String[] aSelectionArgs, @Nullable String aSortOrder) {
        if (aSelection != null && !aSelection.matches("(_id = \\d+\\s*(AND)*\\s*)+")) {
            Log.d(TAG, "query: Ow, u little piece of bastard, u wanna rape me?");
            return null;
        }

        String tableName;
        String id = aUri.getLastPathSegment();
        Uri contentUri;

        Cursor cursor;
        switch (URI_MATCHER.match(aUri)) {
            case DBContract.URI_FIRST_TABLE_ALL:
                tableName = DBContract.Entry.FIRST_TABLE_NAME;
                contentUri = DBContract.FIRST_TABLE_CONTENT_URI;

                mDatabase = mDBHelper.getReadableDatabase();
                cursor = mDatabase.rawQuery("select * from " + tableName, null);
                break;
            case DBContract.URI_SECOND_TABLE_ALL:
                contentUri = DBContract.SECOND_TABLE_CONTENT_URI;
                tableName = DBContract.Entry.SECOND_TABLE_NAME;

                mDatabase = mDBHelper.getReadableDatabase();
                cursor = mDatabase.rawQuery("select * from " + tableName, null);
                break;
            case DBContract.URI_FIRST_TABLE_SINGLE_ID:
                contentUri = DBContract.FIRST_TABLE_CONTENT_URI;
                tableName = DBContract.Entry.FIRST_TABLE_NAME;

                mDatabase = mDBHelper.getReadableDatabase();
                cursor = mDatabase.rawQuery("select * from " + tableName + " where " + "_id = ?", new String[]{id});
                break;
            case DBContract.URI_SECOND_TABLE_SINGLE_ID:
                contentUri = DBContract.SECOND_TABLE_CONTENT_URI;
                tableName = DBContract.Entry.SECOND_TABLE_NAME;

                mDatabase = mDBHelper.getReadableDatabase();
                cursor = mDatabase.rawQuery("select * from " + tableName + " where " + "_id = ?", new String[]{id});
                break;
            default:
                return null;
        }
        if (getContext() != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), contentUri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri aUri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri aUri, @Nullable ContentValues aValues) {
        mDatabase = mDBHelper.getWritableDatabase();
        switch (URI_MATCHER.match(aUri)) {
            case DBContract.URI_FIRST_TABLE_ALL:
                try {
                    mDatabase.insert(DBContract.Entry.FIRST_TABLE_NAME, null, aValues);
                } catch (SQLiteException aE) {
                    aE.printStackTrace();
                    return null;
                }
                break;
            case DBContract.URI_SECOND_TABLE_ALL:
                try {
                    mDatabase.insert(DBContract.Entry.SECOND_TABLE_NAME, null, aValues);
                } catch (SQLiteException aE) {
                    aE.printStackTrace();
                    return null;
                }
                break;
            default:
                return null;
        }
        if (getContext() != null) {
            getContext().getContentResolver().notifyChange(aUri, null);
        }

        return aUri;
    }

    @Override
    public int delete(@NonNull Uri aUri, @Nullable String aSelection, @Nullable String[] aSelectionArgs) {
        mDBHelper.clearTables();
        if (getContext() != null) {
            getContext().getContentResolver().notifyChange(aUri, null);
        }
        return 0;
    }

    @Override
    public int update(@NonNull Uri aUri, @Nullable ContentValues aValues,
                      @Nullable String aSelection, @Nullable String[] aSelectionArgs) {
        return 0;
    }
}
