package kalyanaraman.kaesava.kshetrapalapuram;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import kalyanaraman.kaesava.kshetrapalapuram.todo.Todo;
import kalyanaraman.kaesava.kshetrapalapuram.todo.TodoList;

/**
 * Created by kaesava on 12/11/15.
 */

public class MyDeskRESTClient {
    private static final String BASE_URL = "http://10.0.2.2:3000/";
    public static final String GET = "GET";
    public static final String PUT = "PUT";
    public static final String POST = "POST";
    public static final String DELETE = "DELETE";

    public static final String TODO_SUB_URL = "todo/todos";
    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void syncObject(MyDeskObject myDeskObject, String http_method) {
        String subURL = "";
        if (myDeskObject instanceof Todo) {
            subURL = TODO_SUB_URL;
        }
        RequestParams params = null;
        String url = BASE_URL + subURL + "/" + String.valueOf(myDeskObject.getId()) + ".json";
        JsonHttpResponseHandler handler = null;

        switch (http_method) {
            case DELETE:
                params = new RequestParams();
                params.add(MyDeskObject.ID, String.valueOf(myDeskObject.getId()));
                handler = new PutObjectJSONHandler(myDeskObject);
                client.delete(url, params, handler);
                break;
            case POST:
                params = new RequestParams(myDeskObject.toHashMap());
                params.remove(MyDeskObject.ID);
                url = BASE_URL + subURL + ".json";
                handler = new PostObjectJSONHandler(myDeskObject);
                client.post(url, params, handler);
                break;
            case PUT:
                params = new RequestParams(myDeskObject.toHashMap());
                handler = new PutObjectJSONHandler(myDeskObject);
                client.put(url, params, handler);
                break;
            case GET:
                params = new RequestParams();
                params.add(MyDeskObject.ID, String.valueOf(myDeskObject.getId()));
                handler = new GetObjectJSONHandler(myDeskObject);
                client.get(url, params, handler);
                break;
            default:

                break;
        }
    }

    public static void syncObjectList(MyDeskObjectList myDeskObjectList, String http_method) {
        String subURL = "";
        if (myDeskObjectList instanceof TodoList) {
            subURL = TODO_SUB_URL;
        }
        String url = BASE_URL + subURL + ".json";
        JsonHttpResponseHandler handler = null;
        switch (http_method) {
            case GET:
                handler = new GetObjectListJSONHandler(myDeskObjectList);
                client.get(url, null, handler);
                break;
        }
    }

    private static class GetObjectListJSONHandler extends JsonHttpResponseHandler {

        private MyDeskObjectList objectList = null;

        public GetObjectListJSONHandler(MyDeskObjectList objectList) {
            this.objectList = objectList;
        }

        @Override
        public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONArray responses) {
            JSONObject jsonObject = null;
            objectList.clear();
            MyDeskObject object = null;
            try {
                for (int i = 0; i < responses.length(); i++) {
                    jsonObject = (JSONObject) responses.get(i);
                    if (objectList instanceof TodoList) {
                        object = new Todo();
                        object.updateUsingJSONObject(jsonObject);
                    }
                    if (object != null) {
                        objectList.addItem(object);
                        objectList.refreshUpdateDatetime();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private static class GetObjectJSONHandler extends JsonHttpResponseHandler {

        private MyDeskObject object = null;

        public GetObjectJSONHandler(MyDeskObject object) {
            this.object = object;
        }

        @Override
        public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
            JSONObject jsonObject = null;

            if (object instanceof Todo) {
                object = new Todo();
                object.updateUsingJSONObject(jsonObject);
            }
        }

        @Override
        public void onFailure(int statusCode, cz.
                msebera.android.httpclient.Header[] headers, String jsonResponse, Throwable throwable) {
            object.clearLastWriteError();
            if (statusCode != 200) {
                object.setLastWriteError(MyDeskObject.ERROR_CODE);
            }
        }
    }

    private static class PutObjectJSONHandler extends JsonHttpResponseHandler {

        private MyDeskObject object;

        private PutObjectJSONHandler(MyDeskObject object) {
            this.object = object;
        }

        @Override
        public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
            object.clearLastWriteError();
            if (statusCode != 200) {
                object.setLastWriteError(MyDeskObject.ERROR_CODE);
            }
        }

        @Override
        public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String jsonResponse, Throwable throwable) {
            object.clearLastWriteError();
            if (statusCode != 200) {
                object.setLastWriteError(MyDeskObject.ERROR_CODE);
            }
        }
    }

    private static class PostObjectJSONHandler extends JsonHttpResponseHandler {

        private MyDeskObject object;

        private PostObjectJSONHandler(MyDeskObject object) {
            this.object = object;
        }

        @Override
        public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
            object.clearLastWriteError();
            object.setId(response.optInt(MyDeskObject.ID));
        }

        @Override
        public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String jsonResponse, Throwable throwable) {
            object.clearLastWriteError();
            if (statusCode != 200) {
                object.setLastWriteError(MyDeskObject.ERROR_CODE);
            }
        }
    }

}


