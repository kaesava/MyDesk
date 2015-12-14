package kalyanaraman.kaesava.kshetrapalapuram.todo;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.text.SimpleDateFormat;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TodoEditFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TodoEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TodoEditFragment extends Fragment {
    private Todo todo;
    private OnFragmentInteractionListener mListener;

    public TodoEditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param todo Todo.
     * @return A new instance of fragment TodoEditFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TodoEditFragment newInstance(Todo todo) {
        TodoEditFragment fragment = new TodoEditFragment();
        Bundle args = new Bundle();
        args.putParcelable(TodoDetailFragment.ITEM_KEY, todo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            todo = getArguments().getParcelable(TodoDetailFragment.ITEM_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.todo_edit_content, container, false);

        final EditText editTitle = (EditText) rootView.findViewById(R.id.edit_title);
        editTitle.setText(todo.getTitle());

        final EditText editDetails = (EditText) rootView.findViewById(R.id.edit_details);
        editDetails.setText(todo.getDetails());

        final EditText editDuedate = (EditText) rootView.findViewById(R.id.edit_duedate);
        editDuedate.setText((new SimpleDateFormat("yyyy-MM-dd")).format(todo.getDuedate()));

        final CheckBox editCompleted = (CheckBox) rootView.findViewById(R.id.edit_completed);
        editCompleted.setChecked(todo.isCompleted());

        Button saveButton = (Button) rootView.findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.saveTodo(editTitle.getText().toString(), editDetails.getText().toString(), (editCompleted.isChecked() ? "true" : "false"), editDuedate.getText().toString());
                }
            }
        });
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void saveTodo(String title, String details, String completed, String duedate);
    }
}
