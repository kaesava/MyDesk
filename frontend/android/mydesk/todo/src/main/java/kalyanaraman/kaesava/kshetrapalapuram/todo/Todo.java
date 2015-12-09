package kalyanaraman.kaesava.kshetrapalapuram.todo;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import kalyanaraman.kaesava.kshetrapalapuram.MyDeskObject;

/**
 * Created by kaesava on 11/29/15.
 */
public class Todo extends MyDeskObject {
    private int userid;
    private String title;
    private String details;
    private Date duedate;
    private boolean completed;


    private final String USER_ID = "userid";
    private final String DUE_DATE = "duedate";
    private final String COMPLETED = "completed";
    private final String TITLE = "title";
    private final String DETAILS = "details";

    public Todo(Parcel in) {
        super();
        String[] data = new String[6];

        in.readStringArray(data);
        this.id = Integer.parseInt(data[0]);
        this.userid = Integer.parseInt(data[1]);
        this.title = data[2];
        this.details = data[3];
        try {
            this.duedate = (new SimpleDateFormat("yyyy-MM-dd")).parse(data[4]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.completed = Boolean.parseBoolean(data[5]);
    }

    public Todo(int id, int userid, String title, String details, Date duedate, boolean completed) {
        super(id);
        this.id = id;
        this.userid = userid;
        this.title = title;
        this.details = details;
        this.duedate = duedate;
        this.completed = completed;
    }

    public boolean setAndSendField(String field_name, String value) {
        if (field_name == USER_ID) this.userid = Integer.parseInt(value);
        if (field_name == DUE_DATE) try {
            this.duedate = new SimpleDateFormat("yyyy-MM-dd").parse(value);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (field_name == COMPLETED) this.completed = Boolean.parseBoolean(value);
        if (field_name == TITLE) this.title = value;
        if (field_name == DETAILS) this.details = value;

        return sendtoServer(this.id, field_name, value);
    }

    public int getUserid() {
        return userid;
    }

    public String getTitle() {
        return title;
    }

    public String getDetails() {
        return details;
    }

    public Date getDuedate() {
        return duedate;
    }

    public boolean isCompleted() {
        return completed;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{String.valueOf(this.id), String.valueOf(this.userid), this.title, this.details, new SimpleDateFormat("yyyy-MM-dd").format(duedate), String.valueOf(this.completed)});
    }

    public static final Parcelable.Creator<Todo> CREATOR = new Parcelable.Creator<Todo>() {

        @Override
        public Todo createFromParcel(Parcel source) {
            return new Todo(source);
        }

        @Override
        public Todo[] newArray(int size) {
            return new Todo[size];
        }
    };

    @Override
    protected String toJSONString() {
        return "{'id':"+String.valueOf(id)+",'userid':"+String.valueOf(userid)+",'title':'"+title+"','details':'"+details+"','duedate':'"+ new SimpleDateFormat("yyyy-MM-dd").format(duedate)+"','completed':" + (completed?"true":"false") + "}";
    }
}

