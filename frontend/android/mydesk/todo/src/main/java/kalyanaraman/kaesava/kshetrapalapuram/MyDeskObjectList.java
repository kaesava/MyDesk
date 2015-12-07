package kalyanaraman.kaesava.kshetrapalapuram;

import android.text.format.Time;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kalyanaraman.kaesava.kshetrapalapuram.todo.Todo;

/**
 * Created by kaesava on 5/12/15.
 */
public class MyDeskObjectList {


    protected Date dateTime;
    /**
     * An array of sample (dummy) items.
     */
    private final List<MyDeskObject> ITEMS = new ArrayList<MyDeskObject>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    private final Map<Integer, MyDeskObject> ITEM_MAP = new HashMap<Integer, MyDeskObject>();


    public MyDeskObject getObjectById(int id) {
        return ITEM_MAP.get(id);
    }

    public List<MyDeskObject> getObjects() {
        return ITEMS;
    }

    protected void refresh() {
        dateTime = Calendar.getInstance().getTime();
    }

    protected void clear() {
        ITEM_MAP.clear();
        ITEMS.clear();
    }

    protected void addItem(MyDeskObject item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getId(), item);
    }

    public int getObjectCount() {
        return ITEMS.size();
    }
}
