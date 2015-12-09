package kalyanaraman.kaesava.kshetrapalapuram;

import android.os.Parcelable;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import kalyanaraman.kaesava.kshetrapalapuram.todo.Todo;

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

    protected boolean saveObject() {
        String url = "http://10.0.2.2:3000/" + getObjectURLNamespace() + "/" + String.valueOf(id) + ".json";

        JSONObject post = null;
        try {
            post = new JSONObject(this.toJSONString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        DefaultHttpClient client = new DefaultHttpClient();
        //client.getCredentialsProvider().setCredentials(new AuthScope(null,-1), new UsernamePasswordCredentials(email,password));
        HttpPost httpPost = new HttpPost(url);
        JSONObject holder = new JSONObject();


        try {
            holder.put("post", post);

            StringEntity se = new StringEntity(holder.toString());
            httpPost.setEntity(se);
            httpPost.setHeader("Content-Type","application/json");


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException js) {
            js.printStackTrace();
        }

        HttpResponse response = null;

        try {
            response = client.execute(httpPost);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpEntity entity = response.getEntity();

        if (entity != null) {
            try {
                entity.consumeContent();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    protected abstract String toJSONString();

    private String getObjectURLNamespace() {
       if(this.getClass() == Todo.class) {
            return "todo/todos";
        }
        return "";
    }
}

