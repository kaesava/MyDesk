package kalyanaraman.kaesava.kshetrapalapuram.todo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kalyanaraman.kaesava.kshetrapalapuram.MyDeskApp;
import kalyanaraman.kaesava.kshetrapalapuram.MyDeskListActivity;
import kalyanaraman.kaesava.kshetrapalapuram.MyDeskObject;
import kalyanaraman.kaesava.kshetrapalapuram.MyDeskRESTClient;

/**
 * An activity representing a list of Todos. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link TodoDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class TodoListActivity extends MyDeskListActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, TodoEditActivity.class);
                intent.putExtra(TodoDetailFragment.ITEM_KEY, new Todo());
                context.startActivity(intent);
                startActivity(intent);
                return;
            }
        });

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);

        View recyclerView = findViewById(R.id.todo_list);
        assert recyclerView != null;

        setupRecyclerView((RecyclerView) recyclerView, progressBar);

        objectList = TodoList.getInstace();
        //TODO: Determine when to go to server, and when to use local db
        //MyDeskRESTClient.syncObjectList(objectList, MyDeskRESTClient.GET);
        TodoContentProviderHelper.getInstance().select_all((TodoList) objectList);
        ((RecyclerView) recyclerView).getAdapter().notifyDataSetChanged();

        if (findViewById(R.id.todo_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView, ProgressBar progressBar) {
        recyclerView.setAdapter(new TodoItemRecyclerViewAdapter(progressBar));
    }

    public class TodoItemRecyclerViewAdapter
            extends RecyclerView.Adapter<TodoItemRecyclerViewAdapter.ViewHolder> {

        private final ProgressBar progressBar;

        public TodoItemRecyclerViewAdapter(ProgressBar progressBar) {
            this.progressBar = progressBar;

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.todo_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            progressBar.setVisibility(View.VISIBLE);
            holder.mItem = (Todo) objectList.getObjectByPosition(position);
            holder.mTitleView.setText(holder.mItem.getTitle());
            holder.mCompletedCheckboxView.setChecked(holder.mItem.isCompleted());
            holder.mCompletedCheckboxView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    Map<String, String> nameValuePairs = new HashMap<String, String>();
                    nameValuePairs.put(Todo.COMPLETED, (isChecked ? "true" : "false"));
                    List<String> validation_errors = holder.mItem.updateFieldsAsStrings(nameValuePairs);
                    boolean send_to_server = false;
                    boolean save_to_db = true;
                    //TODO: set a LAST_WRTE_TO_DB_ERROR_FLAG and LAST_SEND_TO_SERVER_ERROR_FLAG; maybe also read-versions
                    if (validation_errors.isEmpty()) {
                        if (send_to_server) {
                            MyDeskRESTClient.syncObject(holder.mItem, MyDeskRESTClient.PUT);
                        }
                        if (save_to_db) {
                            if (!TodoContentProviderHelper.getInstance().update(holder.mItem)) {
                                validation_errors.add("Error saving");
                            }
                        }
                    }
                    if (validation_errors.isEmpty() && !holder.mItem.getLastWriteError().equals(MyDeskObject.ERROR_CODE)) {
                        Toast.makeText(getBaseContext(), "Saved", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getBaseContext(), validation_errors.get(0), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        TodoDetailFragment fragment = TodoDetailFragment.newInstance(holder.mItem);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.todo_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, TodoDetailActivity.class);
                        intent.putExtra(TodoDetailFragment.ITEM_KEY, holder.mItem);
                        context.startActivity(intent);
                    }
                }
            });
            progressBar.setVisibility(View.GONE);

        }

        @Override
        public int getItemCount() {
            return objectList.getObjectCount();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mTitleView;
            public final CheckBox mCompletedCheckboxView;
            public Todo mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mTitleView = (TextView) view.findViewById(R.id.title);
                mCompletedCheckboxView = (CheckBox) view.findViewById(R.id.completed);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mTitleView.getText() + "'";
            }
        }
    }
}
