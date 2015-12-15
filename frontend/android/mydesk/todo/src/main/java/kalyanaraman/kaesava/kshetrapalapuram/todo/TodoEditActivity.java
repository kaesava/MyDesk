package kalyanaraman.kaesava.kshetrapalapuram.todo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kalyanaraman.kaesava.kshetrapalapuram.MyDeskObject;

/**
 * An activity representing a single Todo edit screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link TodoListActivity}.
 */
public class TodoEditActivity extends AppCompatActivity implements TodoEditFragment.OnFragmentInteractionListener {

    private Todo todo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.edit_toolbar);
        setSupportActionBar(toolbar);
        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        todo = getIntent().getParcelableExtra(TodoDetailFragment.ITEM_KEY);
        TodoEditFragment fragment = TodoEditFragment.newInstance(todo);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.todo_edit_subcontainer, fragment)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back

            if (todo.getId() == 0) { // abandon create new
                Intent intent = new Intent(this, TodoListActivity.class);
                startActivity(intent);

            } else { // abandon edit existing
                Intent intent = new Intent(this, TodoDetailActivity.class);
                intent.putExtra(TodoDetailFragment.ITEM_KEY, todo);
                NavUtils.navigateUpTo(this, intent);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void saveTodo(String title, String details, String completed, String duedate) {
        Map<String, String> nameValuePairs = new HashMap<String, String>();
        nameValuePairs.put(Todo.TITLE, title);
        nameValuePairs.put(Todo.DETAILS, details);
        nameValuePairs.put(Todo.COMPLETED, completed);
        nameValuePairs.put(Todo.DUE_DATE, duedate);

        boolean send_to_server = true;
        List<String> validation_errors = todo.updateFieldsAsStrings(nameValuePairs, send_to_server);
        if (validation_errors.isEmpty()) {
            Toast.makeText(getBaseContext(), R.string.toastmsg_saved, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this.getApplicationContext(), TodoListActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(getBaseContext(), validation_errors.get(0), Toast.LENGTH_SHORT).show();
        }
    }
}
