package ph.com.swak.auth;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by SWAK-THREE on 3/25/2015.
 */
public class RestError {

    public String result;

    public RestError() {
    }

    public RestError(String message) {
        this.result = message;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String toJSON() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("result", result);

            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }
}
