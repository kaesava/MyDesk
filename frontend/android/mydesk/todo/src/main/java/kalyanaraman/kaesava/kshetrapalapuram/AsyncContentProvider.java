package kalyanaraman.kaesava.kshetrapalapuram;


import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import kalyanaraman.kaesava.kshetrapalapuram.todo.Todo;

/**
 * Created by kaesava on 3/12/15.
 */

public class AsyncContentProvider extends AsyncTask<String, Void, String> {

    private MyDeskObjectList objectList;

    public AsyncContentProvider(MyDeskObjectList objectList) {
        this.objectList = objectList;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        String read_line = "";
        String return_string = "";
        String mUrl = params[0];

        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(mUrl);

        try {
            HttpResponse response = client.execute(get);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();

            if (statusCode == 200) { // Ok
                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                while ((read_line = rd.readLine()) != null) {
                    return_string += read_line;
                }
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return return_string;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        objectList.updateOnRefresh(result);
    }

}