package kalyanaraman.kaesava.kshetrapalapuram;


import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by kaesava on 3/12/15.
 */

public class ContentProvider extends AsyncTask<String, Void, String> {

    private MyDeskListActivity activity;

    public ContentProvider(MyDeskListActivity activity) {
        this.activity = activity;
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
    protected void onPostExecute(String content) {
        super.onPostExecute(content);
        activity.setRawContent(content);

    }

}
/*
public class ContentProvider {

    public static String getContent(String url) {
        HttpClient httpclient = new DefaultHttpClient();
        String content = null;
        HttpGet httpget = new HttpGet(url);
        HttpResponse response = null;
        InputStream instream = null;

        try {
            response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                instream = entity.getContent();
                content = convertStreamToString(instream);
            }

        } catch (Exception e) {
            // manage exceptions
            e.getStackTrace();
        } finally {
            if (instream != null) {
                try {
                    instream.close();
                } catch (Exception exc) {

                }
            }
        }

        return content;
    }

    private static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;

        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
        } finally {
            try {
                is.close();
            } catch (IOException e) {
            }
        }

        return sb.toString();
    }



}
*/