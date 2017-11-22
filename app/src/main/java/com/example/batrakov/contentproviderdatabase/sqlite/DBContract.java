package com.example.batrakov.contentproviderdatabase.sqlite;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Utility class for Database tables constants and static queries.
 */
public final class DBContract {

    /**
     * Blocking constructor.
     */
    private DBContract(){
    }

    /**
     * Content provider authority.
     */
    public static final String CONTENT_AUTHORITY = "com.example.batrakov.contentproviderdatabase.authority";

    /**
     * First table SQLite entries.
     */
    public static final Uri FIRST_TABLE_CONTENT_URI = Uri.parse("content://"
            + CONTENT_AUTHORITY + "/" + Entry.FIRST_TABLE_NAME);

    /**
     * Second table SQLite entries.
     */
    public static final Uri SECOND_TABLE_CONTENT_URI = Uri.parse("content://"
            + CONTENT_AUTHORITY + "/" + Entry.SECOND_TABLE_NAME);

    private static final String TYPE_TEXT = " TEXT";
    private static final String TYPE_INTEGER = " INTEGER";

    static final String DATABASE_NAME = "db";
    static final int DATABASE_VERSION = 2;

    static final String FIRST_TABLE_CREATE_ENTRIES =
            "CREATE TABLE " + Entry.FIRST_TABLE_NAME + " ("
                    + Entry._ID + " INTEGER PRIMARY KEY,"
                    + Entry.COLUMN_NAME_NAME + TYPE_TEXT + ","
                    + Entry.COLUMN_NAME_AGE + TYPE_INTEGER + ")";


    static final String SECOND_TABLE_CREATE_ENTRIES =
            "CREATE TABLE " + Entry.SECOND_TABLE_NAME + " ("
                    + Entry._ID + " INTEGER PRIMARY KEY,"
                    + Entry.COLUMN_NAME_NAME + TYPE_TEXT + ","
                    + Entry.COLUMN_NAME_AGE + TYPE_INTEGER + ")";


    static final int URI_FIRST_TABLE_ALL = 1;
    static final int URI_SECOND_TABLE_ALL = 2;
    static final int URI_FIRST_TABLE_SINGLE_ID = 3;
    static final int URI_SECOND_TABLE_SINGLE_ID = 4;


    /**
     * Columns supported by "entries" records.
     */
    public static class Entry implements BaseColumns {
        /**
         * Name column name.
         */
        public static final String COLUMN_NAME_NAME = "name";

        /**
         * Age column name.
         */
        public static final String COLUMN_NAME_AGE = "age";

        /**
         * Color column name.
         */
        public static final String COLUMN_NAME_COLOR = "color";

        static final String FIRST_TABLE_NAME = "foxes";
        static final String SECOND_TABLE_NAME = "badgers";
    }
}
