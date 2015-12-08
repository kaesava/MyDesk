package kalyanaraman.kaesava.kshetrapalapuram.todo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;


import kalyanaraman.kaesava.kshetrapalapuram.MyDeskListActivity;

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
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        View recyclerView = findViewById(R.id.todo_list);
        assert recyclerView != null;

        setupRecyclerView((RecyclerView) recyclerView, progressBar);

        objectList = new TodoList();
        objectList.addListener((RecyclerView) recyclerView);

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
            holder.mIdView.setText(String.valueOf(holder.mItem.getId()));
            holder.mTitleView.setText(holder.mItem.getTitle());
            holder.mCompletedCheckboxView.setChecked(holder.mItem.isCompleted());
            holder.mCompletedCheckboxView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    holder.mItem.setAndSendField("completed", String.valueOf(isChecked));
                }
            });

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putParcelable(TodoDetailFragment.ITEM_KEY, holder.mItem);
                        TodoDetailFragment fragment = new TodoDetailFragment();
                        fragment.setArguments(arguments);
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
            public final TextView mIdView;
            public final TextView mTitleView;
            public final CheckBox mCompletedCheckboxView;
            public Todo mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.id);
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
