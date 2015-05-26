package ph.com.swak.activity;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.dd.processbutton.iml.ActionProcessButton;

import java.io.BufferedReader;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import ph.com.swak.R;
import ph.com.swak.auth.RestCallback;
import ph.com.swak.auth.RestClient;
import ph.com.swak.auth.RestError;
import ph.com.swak.callback.DialogCallback;
import ph.com.swak.model.Employee;
import ph.com.swak.utils.Network;
import ph.com.swak.utils.Util;
import ph.com.swak.view.FormText;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by SWAK-THREE on 3/23/2015.
 */

public class LoginActivity extends ActionBarActivity implements DialogCallback {

    private Typeface tf;
    private Context context;

    @InjectView(R.id.rl_login)
    RelativeLayout rl_login;

    @InjectView(R.id.txtMessage)
    TextView txtMessage;

    @InjectView(R.id.txtEmpId)
    FormText txtEmpId;

    @InjectView(R.id.txtPass)
    FormText txtPass;

    private Employee emp;

    @InjectView(R.id.btnSignIn)
    ActionProcessButton btnSignIn;

    private Drawable errorDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

        txtEmpId.setTypeface(Util.setTypeface(this, Util.FONT));
        txtPass.setTypeface(Util.setTypeface(this, Util.FONT));
        txtMessage.setTypeface(Util.setTypeface(this, Util.FONT));


        YoYo.with(Techniques.BounceInUp).duration(1200).playOn(rl_login);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Employee.getEmployeeInfo() != null)
            Util.startActivity(this, MainActivity.class, true);
    }

    @OnClick(R.id.btnSignIn)
    public void Login() {


        Log.i("Password", txtPass.getText().toString());

        if (txtEmpId.getText().toString().isEmpty() || txtPass.getText().toString().isEmpty()) {
            txtMessage.setText("Invalid Username and/or Password...");
            txtMessage.setVisibility(View.VISIBLE);
            YoYo.with(Techniques.Shake).duration(700).playOn(txtEmpId);
            YoYo.with(Techniques.Shake).duration(700).playOn(txtPass);
        } else {
            if (Network.isConnected(this)) {
                btnSignIn.setProgress(10);
                btnSignIn.setEnabled(false);
                checkEmail();
            }
        }
    }

    public void checkEmail() {

        RestClient.get().getemployeeinfo(txtEmpId.getText().toString(), txtPass.getText().toString(), new RestCallback<Employee>() {

            @Override
            public void success(Employee employee, Response response) {
                BufferedReader reader = null;

                if (employee.getResult().contains("success")) {
                    emp = employee;
                    emp.save();
                    Util.startActivity(LoginActivity.this, MainActivity.class, true);
                } else {
                    txtMessage.setVisibility(View.VISIBLE);
                    txtMessage.setText("Invalid Username and/or Password...");
                    btnSignIn.setEnabled(true);
                }
                btnSignIn.setProgress(0);
            }

            @Override
            public void failure(RestError error) {
                Log.e("There are some problem", error.toJSON());
                Util.showDialogErrorMessage(context, LoginActivity.this);
            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
                Util.showDialogErrorMessage(context, LoginActivity.this);
            }
        });
    }

    @Override
    public void positiveSelectionCallback(View v) {
        checkEmail();
    }

    @Override
    public void negativeSelectionCallback(View v) {
    }

    @Override
    public void neutralSelectionCallback(View v) {
    }
}
