package ph.com.swak.auth;

import java.util.ArrayList;

import ph.com.swak.auth.Responses.Responses;
import ph.com.swak.model.Bulletin;
import ph.com.swak.model.Employee;
import ph.com.swak.model.Leave;
import ph.com.swak.model.LeaveType;
import ph.com.swak.model.OTND;
import ph.com.swak.model.PayHistory;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by SWAK-THREE on 3/25/2015.
 */
public interface RestApi {

    static String filters = null;

    @POST("/webservice/getemployeeinfo/")
    public void getemployeeinfo(@Query("empid") String empid, @Query("pwd") String pass, RestCallback<Employee> callback);

    @GET("/webservice/getbulletininfo")
    public void getBulletin(RestCallback<ArrayList<Bulletin>> callback);

    @GET("/webservice/getleavehistory/")
    public void getLeaveHistory(@Query("empid") String empid, @Query("CoveringYear") String covyear, RestCallback<ArrayList<Leave>> callback);

    @GET("/webservice/getleavetype")
    public void getLeaveType(RestCallback<ArrayList<LeaveType>> callback);

    @POST("/webservice/FileLeave/")
    public void fileleave(@Query("empid") String empid, @Query("leaveid") String leavetype, @Query("datefiled") String date_filed, @Query("datefrom") String dte_from, @Query("dateto") String dte_to, @Query("numdays") String no_of_days, @Query("reason") String reason, RestCallback<Responses> callback);

    @GET("/webservice/GetOtHistory/")
    public void getOTNDHistory(@Query("empid") String empid, @Query("CoveringYear") String covyear, RestCallback<ArrayList<OTND>> callback);

    @GET("/webservice/GetPayHistoryInfo/")
    public void getpayHistory(@Query("empid") String empid, @Query("CoveringYear") String covyear, RestCallback<ArrayList<PayHistory>> callback);

    @POST("/webservice/FileOtNd/")
    public void fileOTND(@Query("empid") String empid, @Query("typeid") String type, @Query("datefiled") String date_filed, @Query("datefrom") String dte_from, @Query("dateto") String dte_to, @Query("num_hours") String num_hours, @Query("reason") String reason, RestCallback<Responses> callback);

    @POST("/webservice/changeEmpPassword/")
    public void changepassword(@Query("empid") String empid, @Query("currentpassword") String current, @Query("newpassword") String new_pass, RestCallback<Responses> callback);

}
