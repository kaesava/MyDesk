package kalyanaraman.kaesava.kshetrapalapuram;

import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by kaesava on 5/12/15.
 */
public abstract class MyDeskObject implements Parcelable {
    protected int id;
    private final String ID = "id";

    public MyDeskObject(int id) {
        this.id = id;
    }

    public MyDeskObject() {
        id = -1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    protected boolean sendtoServer(int id, String field_name, String value) {
        return true;
    }
}
