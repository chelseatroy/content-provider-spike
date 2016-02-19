package com.chelseatroy.android.contentproviderspike;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.Nullable;

public class ProjectContentProvider extends ContentProvider {

    private ProjectsOpenHelper projectsOpenHelper;

    public static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    private static final int PROJECTS = 1;
    private static final int PROJECT_ID = 2;

    static {
        URI_MATCHER.addURI("com.chelseatroy.android.contentproviderspike", "projects", PROJECTS);
        URI_MATCHER.addURI("com.chelseatroy.android.contentproviderspike", "projects/#", PROJECT_ID);
    }

    @Override
    public boolean onCreate() {
        projectsOpenHelper = new ProjectsOpenHelper(getContext(), "projects.sql", null, 1);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor = null;

        switch (URI_MATCHER.match(uri)) {
            case PROJECTS:
                cursor = projectsOpenHelper
                        .getReadableDatabase()
                        .query(
                                "projects",
                                projection,
                                null,
                                null,
                                null,
                                null,
                                sortOrder
                        );
                break;
            case PROJECT_ID:
                cursor = projectsOpenHelper
                        .getReadableDatabase()
                        .query(
                                "projects",
                                projection,
                                "_id = ?",
                                new String[]{uri.getLastPathSegment()},
                                null,
                                null,
                                sortOrder,
                                "1"
                        );
                break;
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = projectsOpenHelper.getWritableDatabase();
        long id = db.insertWithOnConflict("projects", null, values, SQLiteDatabase.CONFLICT_REPLACE);
        Uri uri1 = ContentUris.withAppendedId(Uri.parse("content://com.chelseatroy.android.contentproviderspike/projects"), id);
        getContext().getContentResolver().notifyChange(uri1, null);
        return uri1;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    public class ProjectsOpenHelper extends SQLiteOpenHelper {
        public ProjectsOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table projects (_id integer primary key autoincrement, display_name varchar(255) unique)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
