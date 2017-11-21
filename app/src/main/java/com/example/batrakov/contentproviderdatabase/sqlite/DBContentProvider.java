package com.example.batrakov.contentproviderdatabase.sqlite;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

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
        String tableName = null;
        String id = aUri.getLastPathSegment();
        Uri contentUri = null;
        switch (URI_MATCHER.match(aUri)) {
            case DBContract.URI_FIRST_TABLE_ALL:
                tableName = DBContract.Entry.FIRST_TABLE_NAME;
                contentUri = DBContract.FIRST_TABLE_CONTENT_URI;
                if (TextUtils.isEmpty(aSortOrder)) {
                    aSortOrder = DBContract.Entry._ID + " ASC";
                }
                break;
            case DBContract.URI_SECOND_TABLE_ALL:
                contentUri = DBContract.SECOND_TABLE_CONTENT_URI;
                tableName = DBContract.Entry.SECOND_TABLE_NAME;
                if (TextUtils.isEmpty(aSortOrder)) {
                    aSortOrder = DBContract.Entry._ID + " ASC";
                }
                break;
            case DBContract.URI_FIRST_TABLE_SINGLE_ID:
                contentUri = DBContract.FIRST_TABLE_CONTENT_URI;
                tableName = DBContract.Entry.FIRST_TABLE_NAME;
                if (TextUtils.isEmpty(aSelection)) {
                    aSelection = DBContract.Entry._ID + " = " + id;
                } else {
                    aSelection = aSelection + " AND " + DBContract.Entry._ID + " = " + id;
                }
                break;
            case DBContract.URI_SECOND_TABLE_SINGLE_ID:
                contentUri = DBContract.SECOND_TABLE_CONTENT_URI;
                tableName = DBContract.Entry.SECOND_TABLE_NAME;
                if (TextUtils.isEmpty(aSelection)) {
                    aSelection = DBContract.Entry._ID + " = " + id;
                } else {
                    aSelection = aSelection + " AND " + DBContract.Entry._ID + " = " + id;
                }
                break;
            default:
                return null;
        }
        mDatabase = mDBHelper.getReadableDatabase();
        Cursor cursor = mDatabase.query(tableName, aProjection, aSelection,
                aSelectionArgs, null, null, aSortOrder);
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
                mDatabase.insert(DBContract.Entry.FIRST_TABLE_NAME, null, aValues);
                break;
            case DBContract.URI_SECOND_TABLE_ALL:
                mDatabase.insert(DBContract.Entry.SECOND_TABLE_NAME, null, aValues);
                break;
            default:
                return null;
        }
        return null;
    }

    @Override
    public int delete(@NonNull Uri aUri, @Nullable String aSelection, @Nullable String[] aSelectionArgs) {
        mDBHelper.deleteTables();
        return 0;
    }

    @Override
    public int update(@NonNull Uri aUri, @Nullable ContentValues aValues,
                      @Nullable String aSelection, @Nullable String[] aSelectionArgs) {
        return 0;
    }
}
