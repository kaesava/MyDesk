package kalyanaraman.kaesava.kshetrapalapuram.todo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        String url = "http://10.0.2.2:3000/todo/todos.json";
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

        int id, userid = -1;
        String title, details = null;
        Date duedate = null;
        boolean completed;

        try {
            JSONArray jsonTodos = new JSONArray(jsonString);

            for (int i = 0; i < jsonTodos.length(); i++) {
                JSONObject jsonTodo = jsonTodos.optJSONObject(i);
                id = jsonTodo.optInt("id");
                userid = jsonTodo.optInt("userid");
                title = jsonTodo.optString("title");
                details = jsonTodo.optString("details");
                completed = jsonTodo.optBoolean("completed");
                duedate = (new SimpleDateFormat("yyyy-MM-dd")).parse(jsonTodo.optString("duedate"));
                super.addItem(new Todo(id, userid, title, details, duedate, completed));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


}
