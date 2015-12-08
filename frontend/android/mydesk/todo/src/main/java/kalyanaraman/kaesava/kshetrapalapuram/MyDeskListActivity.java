package kalyanaraman.kaesava.kshetrapalapuram;

import android.support.v7.app.AppCompatActivity;

import kalyanaraman.kaesava.kshetrapalapuram.todo.TodoList;


public abstract class MyDeskListActivity extends AppCompatActivity {
    protected MyDeskObjectList objectList;


    public MyDeskObjectList getObjectList() {
        return objectList;
    }

}
