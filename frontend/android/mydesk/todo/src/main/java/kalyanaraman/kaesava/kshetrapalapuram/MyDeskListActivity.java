package kalyanaraman.kaesava.kshetrapalapuram;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import kalyanaraman.kaesava.kshetrapalapuram.todo.R;
import kalyanaraman.kaesava.kshetrapalapuram.todo.Todo;
import kalyanaraman.kaesava.kshetrapalapuram.todo.TodoDetailActivity;
import kalyanaraman.kaesava.kshetrapalapuram.todo.TodoDetailFragment;

public abstract class MyDeskListActivity extends AppCompatActivity {

    private boolean mTwoPane;
    protected String rawContent;
    protected MyDeskObjectList listObject = new MyDeskObjectList();

    protected String getRawContent() {
        return rawContent;
    }

    protected void setRawContent(String rawContent) {
        this.rawContent = rawContent;
    }
}
