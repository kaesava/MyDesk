package kalyanaraman.kaesava.kshetrapalapuram.todo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kalyanaraman.kaesava.kshetrapalapuram.ContentProvider;
import kalyanaraman.kaesava.kshetrapalapuram.MyDeskObjectList;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 */
public class TodoList extends MyDeskObjectList {

    @Override
    protected void refresh() {
        super.refresh();
        super.clear();

        //String url = "http://10.0.2.2:3000/todo/todos/1.json";
        //ContentProvider c = new ContentProvider();
        //c.execute(url);
        String details = "details1";
        Todo todo = new Todo(1, 1, "title11", details, new Date(2015, 1, 1), true);
        addItem(todo);
        todo = new Todo(1, 1, "title22", "details2", new Date(2015, 2, 2), false);
        addItem(todo);
        return;
    }

    //public static void contentReady(ContentProvider c) {
    //   String details = c.getContent();
    //   addItem(new Todo(1, 1, "title11", details, new Date(2015, 1, 1), true));
    //   addItem(new Todo(2, 1, "title22", "details2", new Date(2015, 1, 2), false));
    //}

}
