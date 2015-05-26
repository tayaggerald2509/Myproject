package ph.com.swak.model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by SWAK-THREE on 4/7/2015.
 */
public class PayHistory {

    @SerializedName("period")
    public String period;

    @SerializedName("gross")
    public String gross;

    @SerializedName("deductions")
    public String deductions;

    @SerializedName("netpay")
    public String netpay;

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getGross() {
        return gross;
    }

    public void setGross(String gross) {
        this.gross = gross;
    }

    public String getDeductions() {
        return deductions;
    }

    public void setDeductions(String deductions) {
        this.deductions = deductions;
    }

    public String getNetpay() {
        return netpay;
    }

    public void setNetpay(String netpay) {
        this.netpay = netpay;
    }

    public String toJSON() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("period", period);
            jsonObject.put("gross", gross);
            jsonObject.put("deductions", deductions);
            jsonObject.put("netpay", netpay);

            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

}
