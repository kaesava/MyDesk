package kalyanaraman.kaesava.kshetrapalapuram;

import android.app.Application;
import android.content.Context;

import kalyanaraman.kaesava.kshetrapalapuram.todo.TodoContentProvider;
import kalyanaraman.kaesava.kshetrapalapuram.todo.TodoContentProviderHelper;

/**
 * Created by kaesava on 12/15/15.
 */
public class MyDeskApp extends Application {

    public static Context appContext;

    public MyDeskApp() {
        appContext = (Context) this;
    }
}