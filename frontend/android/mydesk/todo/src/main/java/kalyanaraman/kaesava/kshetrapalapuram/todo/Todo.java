package kalyanaraman.kaesava.kshetrapalapuram.todo;

import java.util.Date;

import kalyanaraman.kaesava.kshetrapalapuram.ContentProvider;
import kalyanaraman.kaesava.kshetrapalapuram.MyDeskObject;

/**
 * Created by kaesava on 11/29/15.
 */
public class Todo extends MyDeskObject {
    private int id;
    private int userid;
    private String title;
    private String details;
    private Date duedate;
    private boolean completed;

    public Todo(int id, int userid, String title, String details, Date duedate, boolean completed) {
        super(id);
        this.id = id;
        this.userid = userid;
        this.title = title;
        this.details = details;
        this.duedate = duedate;
        this.completed = completed;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Date getDuedate() {
        return duedate;
    }

    public void setDuedate(Date duedate) {
        this.duedate = duedate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

}

