package com.example.batrakov.contentproviderdatabase.sqlite;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by batrakov on 17.11.17.
 */

public final class DBContract {
    DBContract() {
    }

    public static final String DATABASE_NAME = "db";
    public static final int DATABASE_VERSION = 1;

    public static final String TYPE_TEXT = " TEXT";
    public static final String TYPE_INTEGER = " INTEGER";


    public static final String FIRST_TABLE_CREATE_ENTRIES =
            "CREATE TABLE " + Entry.FIRST_TABLE_NAME + " (" +
                    Entry._ID + " INTEGER PRIMARY KEY," +
                    Entry.COLUMN_NAME_NAME + TYPE_TEXT + "," +
                    Entry.COLUMN_NAME_AGE + TYPE_INTEGER + ")";


    public static final String SECOND_TABLE_CREATE_ENTRIES =
            "CREATE TABLE " + Entry.SECOND_TABLE_NAME + " (" +
                    Entry._ID + " INTEGER PRIMARY KEY," +
                    Entry.COLUMN_NAME_NAME + TYPE_TEXT + "," +
                    Entry.COLUMN_NAME_AGE + TYPE_INTEGER + ")";

    public static final String DELETE_FIRST_TABLE_ENTRIES =
            "DROP TABLE IF EXISTS " + Entry.FIRST_TABLE_NAME;

    public static final String DELETE_SECOND_TABLE_ENTRIES =
            "DROP TABLE IF EXISTS " + Entry.SECOND_TABLE_NAME;

    public static final String CONTENT_AUTHORITY = "com.example.batrakov.contentproviderdatabase.authority";

    public static final int URI_FIRST_TABLE_ALL = 1;
    public static final int URI_SECOND_TABLE_ALL = 2;
    public static final int URI_FIRST_TABLE_SINGLE_ID = 3;
    public static final int URI_SECOND_TABLE_SINGLE_ID = 4;

    public static final Uri FIRST_TABLE_CONTENT_URI = Uri.parse("content://"
            + CONTENT_AUTHORITY + "/" + Entry.FIRST_TABLE_NAME);

    public static final Uri SECOND_TABLE_CONTENT_URI = Uri.parse("content://"
            + CONTENT_AUTHORITY + "/" + Entry.SECOND_TABLE_NAME);
//
//    /**
//     * Base URI. (content://com.example.batrakov.contentproviderdatabase.authority)
//     */
//    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
//
//    /**
//     * Path component for "entry"-type resources..
//     */
//    private static final String PATH_ENTRIES = "entries";

    /**
     * Columns supported by "entries" records.
     */
    public static class Entry implements BaseColumns {
//        /**
//         * MIME type for lists of entries.
//         */
//        public static final String CONTENT_TYPE =
//                ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.basicsyncadapter.entries";
//        /**
//         * MIME type for individual entries.
//         */
//        public static final String CONTENT_ITEM_TYPE =
//                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.basicsyncadapter.entry";
//
//        /**
//         * Fully qualified URI for "entry" resources.
//         */
//        public static final Uri CONTENT_URI =
//                BASE_CONTENT_URI.buildUpon().appendPath(PATH_ENTRIES).build();


        public static final String FIRST_TABLE_NAME = "foxes";
        public static final String SECOND_TABLE_NAME = "badgers";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_AGE = "age";

    }
}
