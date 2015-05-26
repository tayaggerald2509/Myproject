package ph.com.swak.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by SWAK-THREE on 3/31/2015.
 */

@Table(name = "LeaveType")
public class LeaveType extends Model {

    private static LeaveType leaveType;

    @Column(name = "autoid")
    @SerializedName("id")
    public String autoId;

    @Column(name = "type")
    @SerializedName("type")
    public String type;

    @SerializedName("result")
    public String result;

    public LeaveType(){
        super();
    }

    public String getAutoId() {
        return autoId;
    }

    public void setAutoId(String autoId) {
        this.autoId = autoId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }


    public static String getLeavetypeId(String type){
        leaveType = new Select().from(LeaveType.class).where("type = ?", type).executeSingle();
        return leaveType.getAutoId();
    }

    public static List<LeaveType> getLeaveType(){
        return new Select().from(LeaveType.class).orderBy("type ASC").execute();
    }

    public static int getCount(){
        return new Select().from(LeaveType.class).count();
    }

    public static void deleteAll(){
        new Delete().from(LeaveType.class).execute();
    }


}
