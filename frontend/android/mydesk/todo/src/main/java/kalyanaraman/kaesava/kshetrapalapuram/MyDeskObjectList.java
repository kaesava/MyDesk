package kalyanaraman.kaesava.kshetrapalapuram;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

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


    protected Date refreshDateTime = null;

    private final List<MyDeskObject> ITEMS = new ArrayList<MyDeskObject>();
    private final Map<Integer, MyDeskObject> ITEM_MAP = new HashMap<Integer, MyDeskObject>();
    private final List<RecyclerView> RECYCLERVIEW_LISTENERS = new ArrayList<RecyclerView>();

    public MyDeskObjectList() {
        reloadData();
    }

    public void reloadData() {
        String url = "http://10.0.2.2:3000/" + getObjectListURLNamespace() + ".json";
        refreshDateTime = Calendar.getInstance().getTime();
        (new AsyncContentProvider(this)).execute(url);
        return;
    }

    protected void clear() {
        ITEM_MAP.clear();
        ITEMS.clear();
        triggerRecyclerViewRefresh();
    }

    protected void addItem(MyDeskObject item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getId(), item);
        triggerRecyclerViewRefresh();
    }

    public int getObjectCount() {
        return ITEMS.size();
    }

    public void addListener(RecyclerView recyclerViewListener) {
        if (!RECYCLERVIEW_LISTENERS.contains(recyclerViewListener)) {
            RECYCLERVIEW_LISTENERS.add(recyclerViewListener);
        }
    }

    private void triggerRecyclerViewRefresh() {
        for (int i = 0; i < RECYCLERVIEW_LISTENERS.size(); i++) {
            RECYCLERVIEW_LISTENERS.get(i).getAdapter().notifyDataSetChanged();
        }

    }

    protected abstract void updateOnRefresh(String jsonString);

    public MyDeskObject getObjectByPosition(int position) {
        if (position < 0 || position >= ITEMS.size()) {
            return null;
        }
        return ITEMS.get(position);
    }

    private String getObjectListURLNamespace() {
        if(this.getClass() == TodoList.class) {
            return "todo/todos";
        }
        return "";
    }
}
