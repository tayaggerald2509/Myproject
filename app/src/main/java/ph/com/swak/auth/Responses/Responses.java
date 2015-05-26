package ph.com.swak.auth.Responses;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by SWAK-THREE on 3/25/2015.
 */
public class Responses {

    public String result;

    // default constructor, getters and setters
    public Responses() {

    }

    public Responses(String result) {
        this.result = result;
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
