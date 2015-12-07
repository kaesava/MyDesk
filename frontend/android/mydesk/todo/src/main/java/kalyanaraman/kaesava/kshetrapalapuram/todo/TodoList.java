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

    public TodoList() {
        super();
        reload();
    }

    protected void reload() {
        super.reload();
        String url = "http://10.0.2.2:3000/todo/todos/1.json";
        (new ContentProvider(this)).execute(url);
        return;
    }

    /**
     * Method called by Asych Task when data is downloaded.
     * @param jsonString
     */
    @Override
    protected void updateOnRefresh(String jsonString) {
        super.clear();
        String details = jsonString;
        Todo todo = new Todo(1, 1, details, details, new Date(2015, 1, 1), true);
        super.addItem(todo);
        todo = new Todo(1, 1, "title22", "details2", new Date(2015, 2, 2), false);
        super.addItem(todo);
    }

}
