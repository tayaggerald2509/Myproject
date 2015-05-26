package ph.com.swak.model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by SWAK-THREE on 3/26/2015.
 */
public class Bulletin {


    @SerializedName("filename")
    public String desc;

    @SerializedName("url")
    public String image_url;

    @SerializedName("result")
    public String result;

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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
            jsonObject.put("filename", desc);
            jsonObject.put("url", image_url);
            jsonObject.put("result", result);

            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }
}
