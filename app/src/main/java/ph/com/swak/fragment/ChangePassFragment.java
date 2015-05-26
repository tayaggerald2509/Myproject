package ph.com.swak.fragment;

import android.graphics.Typeface;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import ph.com.swak.R;
import ph.com.swak.auth.Responses.Responses;
import ph.com.swak.auth.RestCallback;
import ph.com.swak.auth.RestClient;
import ph.com.swak.auth.RestError;
import ph.com.swak.model.Employee;
import ph.com.swak.utils.Util;
import ph.com.swak.view.FormText;
import retrofit.RetrofitError;

/**
 * Created by SWAK-THREE on 4/8/2015.
 */
public class ChangePassFragment extends BaseFragment {

    @InjectView(R.id.rl_change)
    RelativeLayout rl_change;

    @InjectView(R.id.txtTitle)
    TextView txtTitle;

    @InjectView(R.id.txtOldPass)
    FormText txtOldPass;

    @InjectView(R.id.txtNewPass)
    FormText txtNewPass;

    @InjectView(R.id.txtConfirmNewPass)
    FormText txtConfirmPass;

    @InjectView(R.id.lblOldPass)
    TextView lblOldPass;

    @InjectView(R.id.lblNewPass)
    TextView lblNewPass;

    @InjectView(R.id.lblConfirmNewPass)
    TextView lblConfirm;

    @InjectView(R.id.btnChange)
    Button change;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View pass = inflater.inflate(R.layout.fragment_change, container, false);
        ButterKnife.inject(this, pass);
        setContent();
        return pass;
    }

    private void setContent() {
        txtTitle.setTypeface(Util.setTypeface(getActivity(), Util.FONT), Typeface.BOLD);

        lblOldPass.setTypeface(Util.setTypeface(getActivity(), Util.FONT), Typeface.BOLD);
        lblNewPass.setTypeface(Util.setTypeface(getActivity(), Util.FONT), Typeface.BOLD);
        lblConfirm.setTypeface(Util.setTypeface(getActivity(), Util.FONT), Typeface.BOLD);

        txtOldPass.setTypeface(Util.setTypeface(getActivity(), Util.FONT), Typeface.BOLD);
        txtNewPass.setTypeface(Util.setTypeface(getActivity(), Util.FONT), Typeface.BOLD);
        txtConfirmPass.setTypeface(Util.setTypeface(getActivity(), Util.FONT), Typeface.BOLD);

    }

    @Override
    public void onStart() {
        super.onStart();
        YoYo.with(Techniques.BounceInUp).duration(1200).playOn(rl_change);
    }


    @OnClick(R.id.btnChange)
    public void changepassword() {

        if (validate()) {

            change.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.lada), null);

            Drawable[] drawables = change.getCompoundDrawables();
            for (Drawable drawable : drawables) {
                if (drawable != null && drawable instanceof Animatable) {
                    ((Animatable) drawable).start();
                }
            }

            change.setEnabled(false);
            changepass();
        } else {

        }
    }

    private void changepass() {
        RestClient.get().changepassword(Employee.getEmployeeInfo().getEmpId(), Employee.getEmployeeInfo().getPwd(), txtNewPass.getText().toString(), new RestCallback<Responses>() {

            @Override
            public void failure(RestError error) {

            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
            }

            @Override
            public void success(Responses responses, retrofit.client.Response response) {

                Toast.makeText(getActivity(), responses.getResult(), Toast.LENGTH_LONG).show();
                if(responses.getResult().contains("success")){
                    clear();
                    Employee.changepassword(txtNewPass.getText().toString());
                }
            }

        });
    }

    private boolean validate() {
        if (txtOldPass.getText().toString().compareTo(Employee.getEmployeeInfo().getPwd()) == 0 && !txtOldPass.getText().toString().isEmpty()) {
            if (txtConfirmPass.getText().toString().compareTo(txtNewPass.getText().toString()) == 0 && !txtNewPass.getText().toString().isEmpty()) {
                return true;
            } else {
                YoYo.with(Techniques.Shake).duration(1200).playOn(txtConfirmPass);
                txtConfirmPass.setError(null, Util.addIcon(getActivity(), R.drawable.ic_action_info_outline));
            }
        } else {
            YoYo.with(Techniques.Shake).duration(1200).playOn(txtOldPass);
            txtOldPass.setError(null, Util.addIcon(getActivity(), R.drawable.ic_action_info_outline));
        }
        return false;
    }

    private void clear() {
        txtOldPass.setText(null);
        txtNewPass.setText(null);
        txtConfirmPass.setText(null);

        change.setEnabled(true);
        change.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_action_accept), null, null, null);
    }
}
