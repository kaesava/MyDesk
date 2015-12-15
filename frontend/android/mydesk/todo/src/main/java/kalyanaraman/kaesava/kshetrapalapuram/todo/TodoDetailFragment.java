package kalyanaraman.kaesava.kshetrapalapuram.todo;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import kalyanaraman.kaesava.kshetrapalapuram.MyDeskRESTClient;

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

    public static TodoDetailFragment newInstance(Todo todo) {
        TodoDetailFragment fragment = new TodoDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(TodoDetailFragment.ITEM_KEY, todo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            todo = (Todo) getArguments().getParcelable(TodoDetailFragment.ITEM_KEY);
            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(todo.getTitle());
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
        Button deleteButton = (Button) rootView.findViewById(R.id.delete_button);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog diaBox = getAskDialog();
                diaBox.show();
                return;
            }
        });
        return rootView;
    }

    private AlertDialog getAskDialog() {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(getActivity())
                //set message, title, and icon
                .setTitle(R.string.button_delete)
                .setMessage(R.string.delete_conf_msg)
                .setIcon(R.drawable.ic_delete_black_24dp)

                .setPositiveButton(R.string.button_delete, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        MyDeskRESTClient.syncObject(todo, MyDeskRESTClient.DELETE);
                        String errorMsg = todo.getLastWriteError();
                        boolean error_saving = !errorMsg.equals("");
                        if (error_saving) {
                            Toast.makeText(getActivity().getBaseContext(), errorMsg, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity().getBaseContext(), R.string.toastmsg_deleted, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(), TodoListActivity.class);
                            getActivity().startActivity(intent);
                        }
                        dialog.dismiss();
                    }
                })

                .setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        return myQuittingDialogBox;

    }
}
