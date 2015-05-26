package ph.com.swak.model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by SWAK-THREE on 3/27/2015.
 */
public class Leave {

    @SerializedName("leavedescri")
    public String desc;

    @SerializedName("datefiled")
    public String date_filed;

    @SerializedName("numdays")
    public String no_of_days;

    @SerializedName("status")
    public String status;

    @SerializedName("result")
    public String result;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDate_filed() {
        return date_filed;
    }

    public void setDate_filed(String date_filed) {
        this.date_filed = date_filed;
    }

    public String getNo_of_days() {
        return no_of_days;
    }

    public void setNo_of_days(String no_of_days) {
        this.no_of_days = no_of_days;
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
            jsonObject.put("leavedescri", desc);
            jsonObject.put("datefiled", date_filed);
            jsonObject.put("numdays", no_of_days);
            jsonObject.put("result", result);

            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }
}
