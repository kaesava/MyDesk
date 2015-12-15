package kalyanaraman.kaesava.kshetrapalapuram.todo;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import java.text.SimpleDateFormat;

/**
 * Created by kaesava on 15/12/15.
 */
public class TodoContentProviderHelper {

    private static TodoContentProvider todoContentProvider = null;

    public static TodoContentProvider getContentProviderInstance() {
        if (todoContentProvider == null) {
            todoContentProvider = new TodoContentProvider();
        }
        return todoContentProvider;
    }

    private TodoContentProviderHelper() {
    }

    public TodoList select_all() {
        // get the data repository in write mode
        Uri uri = Uri.parse(TodoContentProvider.AUTHORITY);

        TodoList todoList = null;
        String[] columns = {Todo.ID, Todo.TITLE, Todo.DETAILS, Todo.DUE_DATE, Todo.COMPLETED, Todo.USER_ID};
        String selection = "";
        String[] selectionArgs = {""};
        Cursor c = todoContentProvider.query(uri, columns, selection, selectionArgs,"");

        if (c.moveToFirst()) {
            todoList = new TodoList();
            while (!c.isAfterLast()) {
                todoList.addItem(new Todo(c));
                c.moveToNext();
            }
        }
        return todoList;
    }

    public Todo select(int id) {
        // get the data repository in write mode
        SQLiteDatabase db = getReadableDatabase();
        Todo todo = null;
        String[] columns = {Todo.TITLE, Todo.DETAILS, Todo.DUE_DATE, Todo.COMPLETED, Todo.USER_ID};
        String selection = Todo.ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor c = db.query(TABLE_NAME, columns, selection, selectionArgs, "", "", "");
        if (c.moveToFirst()) {
            todo = new Todo(id, c);
            return todo;
        }
        return null;
    }

    public long insert(Todo todo) {
        // get the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(Todo.ID, todo.getId());
        values.put(Todo.TITLE, todo.getTitle());
        values.put(Todo.DETAILS, todo.getDetails());
        values.put(Todo.USER_ID, todo.getUserid());
        values.put(Todo.DUE_DATE, (new SimpleDateFormat("yyyy-MM-dd")).format(todo.getDuedate()));
        values.put(Todo.COMPLETED, todo.isCompleted());

        // insert the new row, returning the primary key value of the new row
        return db.insert(TABLE_NAME, Todo.ID, values);
    }

    public boolean update(Todo todo) {
        // get the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(Todo.TITLE, todo.getTitle());
        values.put(Todo.DETAILS, todo.getDetails());
        values.put(Todo.USER_ID, todo.getUserid());
        values.put(Todo.DUE_DATE, (new SimpleDateFormat("yyyy-MM-dd")).format(todo.getDuedate()));
        values.put(Todo.COMPLETED, todo.isCompleted());

        String whereClause = "where " + Todo.ID + " = ?";
        String whereArgs[] = {String.valueOf(todo.getId())};
        // insert the new row, returning the primary key value of the new row
        return db.update(TABLE_NAME, values, whereClause, whereArgs) == 1;
    }

    public boolean delete(Todo todo) {
        // get the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        String whereClause = "where " + Todo.ID + " = ?";
        String whereArgs[] = {String.valueOf(todo.getId())};
        // insert the new row, returning the primary key value of the new row
        return db.delete(TABLE_NAME, whereClause, whereArgs) == 1;
    }
}