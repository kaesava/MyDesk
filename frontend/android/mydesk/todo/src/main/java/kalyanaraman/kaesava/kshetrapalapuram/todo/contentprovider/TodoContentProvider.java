package kalyanaraman.kaesava.kshetrapalapuram.todo.contentprovider;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kalyanaraman.kaesava.kshetrapalapuram.todo.Todo;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 */
public class TodoContentProvider {

    /**
     * An array of sample (dummy) items.
     */
    private static final List<Todo> ITEMS = new ArrayList<Todo>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    private static final Map<String, Todo> ITEM_MAP = new HashMap<String, Todo>();

    private static int userid;

    private static void addItem(Todo item) {
        ITEMS.add(item);
        ITEM_MAP.put(String.valueOf(item.getId()), item);
    }

    public static Todo getTodoById(String id) {
        pullContentTest();
        return ITEM_MAP.get(id);
    }

    public static List<Todo> getTodos() {
        pullContentTest();
        return ITEMS;
    }


    private static boolean pullContent() {

        //Resty r = new Resty();
        //String title =
        //        r.json("http://localhost:3000/todo/todos/4.json").
        //                addItem(new Todo(1, userid, "title1", "details1", true, new Date(2015, 1, 1)));
        return true;
    }

    private static boolean pullContentTest() {

        ITEM_MAP.clear();
        ITEMS.clear();

        addItem(new Todo(1, userid, "title1", "details1", true, new Date(2015, 1, 1)));
        addItem(new Todo(2, userid, "title2", "details2", false, new Date(2015, 1, 2)));
        return true;
    }

}
