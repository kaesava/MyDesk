package kalyanaraman.kaesava.kshetrapalapuram;

import android.os.Parcelable;

import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kaesava on 5/12/15.
 */
public abstract class MyDeskObject implements Parcelable {
    public static final String ERROR_CODE = "ERROR";
    protected int id = 0;
    protected String lastWriteError = "";

    public static final String ID = "id";

    public MyDeskObject() {
    }

    protected void updateUsingJSONObject(JSONObject jsonObject) {
        setId(jsonObject.optInt("id"));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Map<String, String> toHashMap() {
        Map<String, String> map = new HashMap<String, String>();
        Method[] methods = this.getClass().getMethods();
        for (Method m : methods) {
            if (!m.getName().equals("getClass") && (m.getName().startsWith("get") || m.getName().startsWith("is"))) {
                String value = null;
                try {
                    Object oValue = m.invoke(this);
                    if (oValue.getClass() == Date.class) {
                        value = (new SimpleDateFormat("yyyy-MM-dd")).format((Date) oValue);
                    } else {
                        value = oValue.toString();
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                if (m.getName().startsWith("get")) {
                    map.put(m.getName().substring(3).toLowerCase(), value);
                } else {
                    map.put(m.getName().substring(2).toLowerCase(), value);
                }
            }
        }
        map.remove("lastwriteerror");
        return map;
    }

    protected static List<String> validateFieldsAsStrings(Map<String, String> nameValuePairs) {
        List<String> validation_errors = new ArrayList<String>();
        if (nameValuePairs == null || nameValuePairs.isEmpty()) { // nothing to validate
            return validation_errors;
        }
        if (nameValuePairs.containsKey(MyDeskObject.ID)) {
            if ((nameValuePairs.get(MyDeskObject.ID) == null || nameValuePairs.get(MyDeskObject.ID) == "")) {
                validation_errors.add("ID is a required field");
                return validation_errors;
            }
            int id = 0; // used for new objects
            try {
                id = Integer.parseInt(nameValuePairs.get(MyDeskObject.ID));
            } catch (NumberFormatException e) {
                e.printStackTrace();
                validation_errors.add("ID is not formatted correctly; expecting positive integer");
                return validation_errors;
            }
            if (id < 0) {
                validation_errors.add("ID is not valid; expecting positive integer");
                return validation_errors;
            }

        }
        return validation_errors;
    }

    public String getLastWriteError() {
        return lastWriteError;
    }

    public void clearLastWriteError() {
        lastWriteError = "";
    }

    public void setLastWriteError(String lastWriteError) {
        this.lastWriteError = lastWriteError;
    }


}

