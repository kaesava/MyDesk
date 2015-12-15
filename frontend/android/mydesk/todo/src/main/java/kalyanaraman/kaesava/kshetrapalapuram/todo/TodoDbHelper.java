package kalyanaraman.kaesava.kshetrapalapuram.todo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.text.SimpleDateFormat;

public class TodoDbHelper extends SQLiteOpenHelper {

    private static TodoDbHelper instance = null;
    private Context context;

    private TodoDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public static TodoDbHelper getInstance(Context context) {
        /**
         * use the application context.
         * this will ensure that you dont accidentally leak an Activity's
         * context (see this article for more information:
         * http://developer.android.com/resources/articles/avoiding-memory-leaks.html)
         */
        if (instance == null) {
            instance = new TodoDbHelper(context.getApplicationContext());
        }
        return instance;
    }

    private static final String BOOLEAN_TYPE = " BOOLEAN";
    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "todo.db";
    public static final String TABLE_NAME = "todos";

    private static final String SQL_CREATE_TODOS =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    BaseColumns._ID + " " + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
                    Todo.ID + " " + INTEGER_TYPE + " UNIQUE" + COMMA_SEP +
                    Todo.TITLE + " " + TEXT_TYPE + COMMA_SEP +
                    Todo.DETAILS + " " + TEXT_TYPE + COMMA_SEP +
                    Todo.DUE_DATE + " " + TEXT_TYPE + COMMA_SEP +
                    Todo.COMPLETED + " " + BOOLEAN_TYPE + COMMA_SEP +
                    Todo.USER_ID + " " + INTEGER_TYPE +
                    " )";

    private static final String SQL_DELETE_TODOS =
            "DROP TABLE IF EXISTS " + TABLE_NAME;


    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TODOS);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_TODOS);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


}