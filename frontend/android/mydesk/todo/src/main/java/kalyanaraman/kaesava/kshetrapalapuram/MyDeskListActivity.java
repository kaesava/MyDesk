package kalyanaraman.kaesava.kshetrapalapuram;

import android.support.v7.app.AppCompatActivity;


public abstract class MyDeskListActivity extends AppCompatActivity {

    protected String rawContent;

    protected String getRawContent() {
        return rawContent;
    }

    protected void setRawContent(String rawContent) {
        this.rawContent = rawContent;
    }
}
