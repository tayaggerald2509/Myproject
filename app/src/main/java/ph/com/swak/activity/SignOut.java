package ph.com.swak.activity;

import android.app.Activity;
import android.os.Bundle;

import ph.com.swak.model.Employee;
import ph.com.swak.utils.Util;

/**
 * Created by SWAK-THREE on 4/10/2015.
 */
public class SignOut extends Activity {

    @Override
    protected void onStart() {
        super.onStart();
        Employee.signout();
        Util.startActivity(this, SplashActivity.class, true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
