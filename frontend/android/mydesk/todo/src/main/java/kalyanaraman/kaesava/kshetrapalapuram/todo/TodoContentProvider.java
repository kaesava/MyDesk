package kalyanaraman.kaesava.kshetrapalapuram.todo;

import android.app.Activity;
import android.app.Application;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import kalyanaraman.kaesava.kshetrapalapuram.MyDeskApp;

/**
 * Created by kaesava on 15/12/15.
 */
public class TodoContentProvider extends ContentProvider {

    public static final String AUTHORITY = "kalyanaraman.kaesava.kshetrapalapuram.provider.mydesk";
    public static final String ENTITY_NAME = "todos";
    public static final String CONTENT_URI_STRING = "content://" + AUTHORITY + "/" + ENTITY_NAME;
    private static final int URI_MATCH_CODE_ALL_TODOS = 1;
    private static final int URI_MATCH_CODE_SINGLE_TODO = 2;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AUTHORITY, ENTITY_NAME, URI_MATCH_CODE_ALL_TODOS);
        sUriMatcher.addURI(AUTHORITY, ENTITY_NAME + "/#", URI_MATCH_CODE_SINGLE_TODO);
    }

    private TodoDbHelper todoDbHelper;

    public TodoContentProvider() {
        super();
        onCreate();
    }

    @Override
    public boolean onCreate() {
        if (todoDbHelper == null) {
            todoDbHelper = TodoDbHelper.getInstance();
        }
        return false;
    }

    @Override
    public String getType(Uri uri) {

        switch (sUriMatcher.match(uri)) {
            case URI_MATCH_CODE_ALL_TODOS:
                return "vnd.android.cursor.dir/vnd." + AUTHORITY + "." + ENTITY_NAME;
            case URI_MATCH_CODE_SINGLE_TODO:
                return "vnd.android.cursor.item/vnd." + AUTHORITY + "." + ENTITY_NAME;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }


    // The query() method must return a Cursor object, or if it fails,
    // throw an Exception. If you are using an SQLite database as your data storage,
    // you can simply return the Cursor returned by one of the query() methods of the
    // SQLiteDatabase class. If the query does not match any rows, you should return a
    // Cursor instance whose getCount() method returns 0. You should return null only
    // if an internal error occurred during the query process.
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        SQLiteDatabase db = todoDbHelper.getReadableDatabase();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(TodoDbHelper.TABLE_NAME);

        switch (sUriMatcher.match(uri)) {
            case URI_MATCH_CODE_ALL_TODOS:
                //do nothing
                break;
            case URI_MATCH_CODE_SINGLE_TODO:
                String id = uri.getPathSegments().get(1);
                queryBuilder.appendWhere(Todo.ID + "=" + id);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }

        Cursor cursor = queryBuilder.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);
        return cursor;

    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        SQLiteDatabase db = todoDbHelper.getWritableDatabase();
        long _id = 0;
        switch (sUriMatcher.match(uri)) {
            case URI_MATCH_CODE_ALL_TODOS:
                _id = db.insert(TodoDbHelper.TABLE_NAME, null, values);
                getContext().getContentResolver().notifyChange(uri, null);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        return Uri.parse(Uri.parse(CONTENT_URI_STRING) + "/" + String.valueOf(_id));
    }

    // The delete() method deletes rows based on the seletion or if an id is
    // provided then it deleted a single row. The methods returns the numbers
    // of records delete from the database. If you choose not to delete the data
    // physically then just update a flag here.
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = todoDbHelper.getWritableDatabase();
        int deleteCount = -1;
        switch (sUriMatcher.match(uri)) {
            case URI_MATCH_CODE_ALL_TODOS:
                //do nothing
                deleteCount = 0;
                break;
            case URI_MATCH_CODE_SINGLE_TODO:
                // selection has already been made
                deleteCount = db.delete(TodoDbHelper.TABLE_NAME, selection, selectionArgs);
                getContext().getContentResolver().notifyChange(uri, null);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }

        return deleteCount;
    }

    // The update method() is same as delete() which updates multiple rows
    // based on the selection or a single row if the row id is provided. The
    // update method returns the number of updated rows.
    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase db = todoDbHelper.getWritableDatabase();
        int updateCount = -1;
        switch (sUriMatcher.match(uri)) {
            case URI_MATCH_CODE_ALL_TODOS:
                //do nothing
                updateCount = 0;
                break;
            case URI_MATCH_CODE_SINGLE_TODO:
                // selection has already been made
                updateCount = db.update(TodoDbHelper.TABLE_NAME, values, selection, selectionArgs);
                getContext().getContentResolver().notifyChange(uri, null);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        return updateCount;
    }

}