package kalyanaraman.kaesava.kshetrapalapuram.todo;

import kalyanaraman.kaesava.kshetrapalapuram.MyDeskObjectList;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 */
public class TodoList extends MyDeskObjectList {

    private static final TodoList instance = new TodoList();

    private TodoList() {
        super();
    }

    public static TodoList getInstace() {
        return instance;
    }
}
