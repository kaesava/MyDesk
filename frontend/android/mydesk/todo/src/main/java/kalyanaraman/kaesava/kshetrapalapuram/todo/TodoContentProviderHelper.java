package kalyanaraman.kaesava.kshetrapalapuram.todo;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
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
    private static TodoContentProviderHelper instance = null;

    public static TodoContentProviderHelper getInstance() {
        if (instance == null) {
            instance = new TodoContentProviderHelper();
            todoContentProvider = new TodoContentProvider();
        }
        return instance;
    }

    private TodoContentProviderHelper() {
    }

    public int select_all(TodoList todoList) {
        Uri uri = Uri.parse(TodoContentProvider.CONTENT_URI_STRING);

        String[] columns = {Todo.ID, Todo.TITLE, Todo.DETAILS, Todo.DUE_DATE, Todo.COMPLETED, Todo.USER_ID};
        String selection = "";
        String[] selectionArgs = new String[0];
        Cursor c = todoContentProvider.query(uri, columns, selection, selectionArgs, "");
        todoList.clear();
        if (c.moveToFirst()) {

            while (!c.isAfterLast()) {
                todoList.addItem(new Todo(c));
                c.moveToNext();
            }
        }
        return c.getCount();
    }

    public Todo select(int id) {
        Uri uri = Uri.parse(TodoContentProvider.CONTENT_URI_STRING + "/" + String.valueOf(id));
        Todo todo = null;
        String[] columns = {Todo.TITLE, Todo.DETAILS, Todo.DUE_DATE, Todo.COMPLETED, Todo.USER_ID};
        String selection = Todo.ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor c = todoContentProvider.query(uri, columns, selection, selectionArgs, "");
        if (c.moveToFirst()) {
            todo = new Todo(id, c);
            return todo;
        }
        return null;
    }

    public boolean insert(Todo todo) {
        Uri uri = Uri.parse(TodoContentProvider.CONTENT_URI_STRING);

        // create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(Todo.ID, todo.getId());
        values.put(Todo.TITLE, todo.getTitle());
        values.put(Todo.DETAILS, todo.getDetails());
        values.put(Todo.USER_ID, todo.getUserid());
        values.put(Todo.DUE_DATE, (new SimpleDateFormat("yyyy-MM-dd")).format(todo.getDuedate()));
        values.put(Todo.COMPLETED, todo.isCompleted());

        // insert the new row, returning the primary key value of the new row
        return todoContentProvider.insert(uri, values) != null;
    }

    public boolean update(Todo todo) {
        int id = todo.getId();
        Uri uri = Uri.parse(TodoContentProvider.CONTENT_URI_STRING + "/" + String.valueOf(id));

        // create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(Todo.TITLE, todo.getTitle());
        values.put(Todo.DETAILS, todo.getDetails());
        values.put(Todo.USER_ID, todo.getUserid());
        values.put(Todo.DUE_DATE, (new SimpleDateFormat("yyyy-MM-dd")).format(todo.getDuedate()));
        values.put(Todo.COMPLETED, todo.isCompleted());

        String whereClause = Todo.ID + " = ?";
        String whereArgs[] = {String.valueOf(todo.getId())};
        // insert the new row, returning the primary key value of the new row
        return todoContentProvider.update(uri, values, whereClause, whereArgs) == 1;
    }

    public boolean delete(int id) {
        Uri uri = Uri.parse(TodoContentProvider.CONTENT_URI_STRING + "/" + String.valueOf(id));

        String whereClause = Todo.ID + " = ?";
        String whereArgs[] = {String.valueOf(id)};
        // insert the new row, returning the primary key value of the new row
        return todoContentProvider.delete(uri, whereClause, whereArgs) == 1;
    }
}