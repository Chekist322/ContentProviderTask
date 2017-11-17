package com.example.batrakov.contentproviderdatabase.sqlite;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

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

    DBHelper mDBHelper;
    SQLiteDatabase mDatabase;

    @Override
    public boolean onCreate() {
        mDBHelper = new DBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        String tableName = null;
        String id = uri.getLastPathSegment();
        Uri contentUri = null;
        switch (URI_MATCHER.match(uri)) {
            case DBContract.URI_FIRST_TABLE_ALL:
                tableName = DBContract.Entry.FIRST_TABLE_NAME;
                contentUri = DBContract.FIRST_TABLE_CONTENT_URI;
                if (TextUtils.isEmpty(sortOrder)) {
                    sortOrder = DBContract.Entry._ID + " ASC";
                }
                break;
            case DBContract.URI_SECOND_TABLE_ALL:
                contentUri = DBContract.SECOND_TABLE_CONTENT_URI;
                tableName = DBContract.Entry.SECOND_TABLE_NAME;
                if (TextUtils.isEmpty(sortOrder)) {
                    sortOrder = DBContract.Entry._ID + " ASC";
                }
                break;
            case DBContract.URI_FIRST_TABLE_SINGLE_ID:
                contentUri = DBContract.FIRST_TABLE_CONTENT_URI;
                tableName = DBContract.Entry.FIRST_TABLE_NAME;
                if (TextUtils.isEmpty(selection)) {
                    selection = DBContract.Entry._ID + " = " + id;
                } else {
                    selection = selection + " AND " + DBContract.Entry._ID + " = " + id;
                }
                break;
            case DBContract.URI_SECOND_TABLE_SINGLE_ID:
                contentUri = DBContract.SECOND_TABLE_CONTENT_URI;
                tableName = DBContract.Entry.SECOND_TABLE_NAME;
                if (TextUtils.isEmpty(selection)) {
                    selection = DBContract.Entry._ID + " = " + id;
                } else {
                    selection = selection + " AND " + DBContract.Entry._ID + " = " + id;
                }
                break;
        }
        mDatabase = mDBHelper.getWritableDatabase();
        Cursor cursor = mDatabase.query(tableName, projection, selection,
                selectionArgs, null, null, sortOrder);
        if (getContext() != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), contentUri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        mDBHelper.deleteTables(getContext());
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
