package kalyanaraman.kaesava.kshetrapalapuram.todo;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import kalyanaraman.kaesava.kshetrapalapuram.MyDeskObject;
import kalyanaraman.kaesava.kshetrapalapuram.MyDeskRESTClient;

/**
 * Created by kaesava on 11/29/15.
 */
public class Todo extends MyDeskObject {
    private int userid = 0;
    private String title = "";
    private String details = "";
    private Date duedate = Calendar.getInstance().getTime();
    private boolean completed = false;


    public static final String USER_ID = "userid";
    public static final String DUE_DATE = "duedate";
    public static final String COMPLETED = "completed";
    public static final String TITLE = "title";
    public static final String DETAILS = "details";

    public Todo() {
        super();
    }

    public Todo(JSONObject jsonTodo) {
        super(jsonTodo);
        setUserid(jsonTodo.optInt("userid"));
        setTitle(jsonTodo.optString("title"));
        setDetails(jsonTodo.optString("details"));
        setCompleted(jsonTodo.optBoolean("completed"));
        try {
            setDuedate((new SimpleDateFormat("yyyy-MM-dd")).parse(jsonTodo.optString("duedate")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Todo(Parcel in) {
        super();
        String[] data = new String[6];

        in.readStringArray(data);
        setId(Integer.parseInt(data[0]));
        setUserid(Integer.parseInt(data[1]));
        setTitle(data[2]);
        setDetails(data[3]);
        try {
            setDuedate((new SimpleDateFormat("yyyy-MM-dd")).parse(data[4]));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.completed = Boolean.parseBoolean(data[5]);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{String.valueOf(this.id), String.valueOf(this.userid), this.title, this.details, new SimpleDateFormat("yyyy-MM-dd").format(duedate), (this.completed ? "true" : "false")});
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


    public static List<String> validateFieldsAsStrings(Map<String, String> nameValuePairs) {
        List<String> validation_errors = MyDeskObject.validateFieldsAsStrings(nameValuePairs);
        if (nameValuePairs == null || nameValuePairs.isEmpty()) { // nothing to validate
            return validation_errors;
        }
        if (nameValuePairs.containsKey(Todo.TITLE) && (nameValuePairs.get(Todo.TITLE) == null || nameValuePairs.get(Todo.TITLE).trim().equals(""))) {
            validation_errors.add("Title is a required field");
            return validation_errors;
        }
        return validation_errors;
        //TODO: Validate other fields
    }

    public List<String> updateFieldsAsStrings(Map<String, String> nameValuePairs, boolean send_to_server) {
        List<String> validation_errors = Todo.validateFieldsAsStrings(nameValuePairs);
        if (!validation_errors.isEmpty()) {
            return validation_errors;
        }
        for (int i = 0; i < nameValuePairs.keySet().size(); i++) {
            String field_name = (String) nameValuePairs.keySet().toArray()[i];
            String value = nameValuePairs.get(field_name);

            if (field_name.equals(DUE_DATE)) try {
                setDuedate(new SimpleDateFormat("yyyy-MM-dd").parse(value));
            } catch (ParseException e) {
                e.printStackTrace();
                validation_errors.add("Due data badly formatted");
                return validation_errors;
            }
            if (field_name.equals(COMPLETED)) setCompleted(Boolean.parseBoolean(value));
            if (field_name.equals(TITLE)) setTitle(value);
            if (field_name.equals(DETAILS)) setDetails(value);
        }
        if (!send_to_server) {
            return validation_errors;
        }
        if (getId() == 0) {
            MyDeskRESTClient.syncObject(this, MyDeskRESTClient.POST);
        } else {
            MyDeskRESTClient.syncObject(this, MyDeskRESTClient.PUT);
        }
        if (getLastWriteError().equals(MyDeskObject.ERROR_CODE)) {
            validation_errors.add("Error saving");
            return validation_errors;
        }
        return validation_errors;
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

    private void setUserid(int userid) {
        this.userid = userid;
    }

    private void setTitle(String title) {
        this.title = title;
    }

    private void setDetails(String details) {
        this.details = details;
    }

    private void setCompleted(boolean completed) {
        this.completed = completed;
    }

    private void setDuedate(Date duedate) {
        this.duedate = duedate;
    }
}

