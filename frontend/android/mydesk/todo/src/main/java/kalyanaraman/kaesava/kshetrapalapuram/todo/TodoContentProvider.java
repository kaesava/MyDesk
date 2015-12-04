package kalyanaraman.kaesava.kshetrapalapuram.todo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        pullContent();
        return ITEM_MAP.get(id);
    }

    public static List<Todo> getTodos() {
        pullContent();
        return ITEMS;
    }

    private static boolean pullContent() {

        ITEM_MAP.clear();
        ITEMS.clear();

        addItem(new Todo(1, userid, "title11", "details1", new Date(2015, 1, 1), true));
        addItem(new Todo(2, userid, "title22", "details2", new Date(2015, 1, 2), false));
        return true;
    }

}
