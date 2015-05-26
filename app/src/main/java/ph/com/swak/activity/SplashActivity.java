package ph.com.swak.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;

import com.activeandroid.query.Select;

import java.util.ArrayList;

import ph.com.swak.R;
import ph.com.swak.auth.RestCallback;
import ph.com.swak.auth.RestClient;
import ph.com.swak.auth.RestError;
import ph.com.swak.callback.DialogCallback;
import ph.com.swak.model.Employee;
import ph.com.swak.model.LeaveType;
import ph.com.swak.utils.Network;
import ph.com.swak.utils.Util;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SplashActivity extends ActionBarActivity implements DialogCallback {

    private Employee emp;
    private LeaveType leave;
    private Thread timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Network.isConnected(this)) {
            Initialize();
        } else {
            ifEmployeeExist();
        }
    }

    private void Initialize() {
        RestClient.get().getLeaveType(new RestCallback<ArrayList<LeaveType>>() {

            @Override
            public void success(ArrayList<LeaveType> leaveTypes, Response response) {

                LeaveType.deleteAll();

                for (LeaveType leaveType : leaveTypes) {
                    Log.i("Leave Type", leaveType.getType());
                    leave = leaveType;
                    leave.save();
                }

                ifEmployeeExist();
            }

            @Override
            public void failure(RestError error) {
                Log.e("Error LeaveType", error.toJSON());
                Util.showDialogErrorMessage(SplashActivity.this, SplashActivity.this);
            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
                Util.showDialogErrorMessage(SplashActivity.this, SplashActivity.this);
            }
        });
    }

    private void InitializeEmployee() {

        RestClient.get().getemployeeinfo(Employee.getEmployeeInfo().getEmpId(), Employee.getEmployeeInfo().getPwd(), new RestCallback<Employee>() {
            @Override
            public void failure(RestError error) {
                Log.i("SplashInitializeEmp", error.toJSON());
                Util.showDialogErrorMessage(SplashActivity.this, SplashActivity.this);
            }

            @Override
            public void success(Employee employee, Response response) {
                emp = new Select().from(Employee.class).where("empId=?", Employee.getEmployeeInfo().getEmpId()).executeSingle();
                emp = employee;
                emp.save();

                startNewScreen(MainActivity.class);
            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
                Util.showDialogErrorMessage(SplashActivity.this, SplashActivity.this);
            }
        });
    }

    private void startNewScreen(final Class<?> intent) {
        timer = new Thread() {
            public void run() {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Util.startActivity(SplashActivity.this, intent, true);
                }
            }
        };
        timer.start();
    }

    private void ifEmployeeExist() {
        if (Employee.getEmployeeInfo() != null) {
            if (Network.isConnected(this)) {
                InitializeEmployee();
            } else {
                startNewScreen(MainActivity.class);
            }
        } else {
            startNewScreen(LoginActivity.class);
        }
    }

    @Override
    public void positiveSelectionCallback(View v) {
        Initialize();
    }

    @Override
    public void negativeSelectionCallback(View v) {

    }

    @Override
    public void neutralSelectionCallback(View v) {

    }
}
