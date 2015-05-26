package ph.com.swak.model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by SWAK-THREE on 4/7/2015.
 */
public class OTND {

    @SerializedName("typedescri")
    public String typedesc;

    @SerializedName("datefiled")
    public String date_filed;

    @SerializedName("num_hours")
    public String no_of_hours;

    @SerializedName("status")
    public String status;

    @SerializedName("result")
    public String result;

    public String getTypedesc() {
        return typedesc;
    }

    public void setTypedesc(String typedesc) {
        this.typedesc = typedesc;
    }

    public String getDate_filed() {
        return date_filed;
    }

    public void setDate_filed(String date_filed) {
        this.date_filed = date_filed;
    }

    public String getNo_of_hours() {
        return no_of_hours;
    }

    public void setNo_of_hours(String no_of_hours) {
        this.no_of_hours = no_of_hours;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
            jsonObject.put("typedescri", typedesc);
            jsonObject.put("datefiled", date_filed);
            jsonObject.put("numdays", no_of_hours);
            jsonObject.put("result", result);

            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

}
