package kalyanaraman.kaesava.kshetrapalapuram;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kalyanaraman.kaesava.kshetrapalapuram.todo.Todo;
import kalyanaraman.kaesava.kshetrapalapuram.todo.TodoList;

/**
 * Created by kaesava on 5/12/15.
 */
public abstract class MyDeskObjectList {


    protected Date refreshDateTime = Calendar.getInstance().getTime();

    private final List<MyDeskObject> ITEMS = new ArrayList<MyDeskObject>();
    private final Map<Integer, MyDeskObject> ITEM_MAP = new HashMap<Integer, MyDeskObject>();

    public void clear() {
        ITEM_MAP.clear();
        ITEMS.clear();
    }

    public void addItem(MyDeskObject item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getId(), item);
    }

    public int getObjectCount() {
        return ITEMS.size();
    }

    public MyDeskObject getObjectByPosition(int position) {
        if (position < 0 || position >= ITEMS.size()) {
            return null;
        }
        return ITEMS.get(position);
    }

    public void refreshUpdateDatetime() {
        refreshDateTime = Calendar.getInstance().getTime();
    }
}
