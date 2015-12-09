package kalyanaraman.kaesava.kshetrapalapuram.todo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import kalyanaraman.kaesava.kshetrapalapuram.AsyncContentProvider;
import kalyanaraman.kaesava.kshetrapalapuram.MyDeskObjectList;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 */
public class TodoList extends MyDeskObjectList {

    public TodoList() {
        super();
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
