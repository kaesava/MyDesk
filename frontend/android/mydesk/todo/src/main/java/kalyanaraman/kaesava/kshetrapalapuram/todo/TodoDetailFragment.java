package kalyanaraman.kaesava.kshetrapalapuram.todo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;

/**
 * A fragment representing a single Todo detail screen.
 * This fragment is either contained in a {@link TodoListActivity}
 * in two-pane mode (on tablets) or a {@link TodoDetailActivity}
 * on handsets.
 */
public class TodoDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ITEM_KEY = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private Todo todo;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TodoDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ITEM_KEY)) {
            todo = (Todo) getArguments().getParcelable(TodoDetailFragment.ITEM_KEY);
            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle("(ID " + todo.getId() + "): " + todo.getTitle());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.todo_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (todo != null) {
            ((TextView) rootView.findViewById(R.id.todo_detail)).setText(todo.getDetails());
            ((TextView) rootView.findViewById(R.id.todo_userid)).setText(String.valueOf(todo.getUserid()));
            ((TextView) rootView.findViewById(R.id.todo_completed)).setText(String.valueOf(todo.isCompleted()));
            ((TextView) rootView.findViewById(R.id.todo_duedate)).setText(new SimpleDateFormat("yyyy-MM-dd").format(todo.getDuedate()));
        }
        Button editButton = (Button) rootView.findViewById(R.id.edit_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TodoEditActivity.class);
                intent.putExtra(ITEM_KEY, todo);
                getActivity().startActivity(intent);
                return;
            }
        });
        return rootView;
    }
}
