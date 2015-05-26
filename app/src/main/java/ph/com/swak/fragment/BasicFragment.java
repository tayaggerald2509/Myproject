package ph.com.swak.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ph.com.swak.R;
import ph.com.swak.model.Employee;
import ph.com.swak.utils.Constants;
import ph.com.swak.utils.Util;

/**
 * Created by SWAK-THREE on 3/25/2015.
 */
public class BasicFragment extends BaseFragment {

    private String imgURI;

    @InjectView(R.id.rl_info)
    public RelativeLayout rl_info;

    @InjectView(R.id.imgAvatar)
    public ImageView imagAvatar;

    @InjectView(R.id.lblBasic)
    public TextView lblTitle;

    @InjectView(R.id.lblBirthday)
    public TextView lblBday;

    @InjectView(R.id.lblAge)
    public TextView lblAge;

    @InjectView(R.id.lblStatus)
    public TextView lblStatus;

    @InjectView(R.id.lblGender)
    public TextView lblGender;

    @InjectView(R.id.lblCompany)
    public TextView lblCompany;

    @InjectView(R.id.lblDepartment)
    public TextView lblDepartment;

    @InjectView(R.id.lblDesignation)
    public TextView lblDesignation;

    @InjectView(R.id.lblSSS)
    public TextView lblSSS;

    @InjectView(R.id.lblTIN)
    public TextView lblTIN;

    @InjectView(R.id.lblPhil)
    public TextView lblPhil;

    @InjectView(R.id.lblPagibig)
    public TextView lblPagibig;

    @InjectView(R.id.txtName)
    public TextView txtName;

    @InjectView(R.id.txtBirthday)
    public TextView txtBday;

    @InjectView(R.id.txtAge)
    public TextView txtAge;

    @InjectView(R.id.txtStatus)
    public TextView txtStatus;

    @InjectView(R.id.txtGender)
    public TextView txtGender;

    @InjectView(R.id.txtDesignation)
    public TextView txtDesignation;

    @InjectView(R.id.txtDepartment)
    public TextView txtDepartment;

    @InjectView(R.id.txtCompany)
    public TextView txtCompany;

    @InjectView(R.id.txtSSS)
    public TextView txtSSS;

    @InjectView(R.id.txtTIN)
    public TextView txtTIN;

    @InjectView(R.id.txtPhil)
    public TextView txtPhil;

    @InjectView(R.id.txtPagibig)
    public TextView txtPagibig;

    private Employee employee;

    private Typeface tf;


    @Override
    public void onStart() {
        super.onStart();
        YoYo.with(Techniques.BounceInUp).duration(1200).playOn(rl_info);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View basic = inflater.inflate(R.layout.fragment_basic_info, container, false);
       ButterKnife.inject(this, basic);
       setContentView(basic);
       setEmployeeInfo();
        return basic;
    }

    private void setContentView(View v) {

        txtAge.setTypeface(Util.setTypeface(this.getActivity(), Util.FONT));
        txtSSS.setTypeface(Util.setTypeface(this.getActivity(), Util.FONT));
        txtTIN.setTypeface(Util.setTypeface(this.getActivity(), Util.FONT));
        txtName.setTypeface(Util.setTypeface(this.getActivity(), Util.FONT));
        txtBday.setTypeface(Util.setTypeface(this.getActivity(), Util.FONT));
        txtPhil.setTypeface(Util.setTypeface(this.getActivity(), Util.FONT));
        txtStatus.setTypeface(Util.setTypeface(this.getActivity(), Util.FONT));
        txtGender.setTypeface(Util.setTypeface(this.getActivity(), Util.FONT));
        txtCompany.setTypeface(Util.setTypeface(this.getActivity(), Util.FONT));
        txtPagibig.setTypeface(Util.setTypeface(this.getActivity(), Util.FONT));
        txtDepartment.setTypeface(Util.setTypeface(this.getActivity(), Util.FONT));
        txtDesignation.setTypeface(Util.setTypeface(this.getActivity(), Util.FONT));


        lblAge.setTypeface(Util.setTypeface(this.getActivity(), Util.FONT), Typeface.BOLD);
        lblSSS.setTypeface(Util.setTypeface(this.getActivity(), Util.FONT), Typeface.BOLD);
        lblTIN.setTypeface(Util.setTypeface(this.getActivity(), Util.FONT), Typeface.BOLD);
        lblBday.setTypeface(Util.setTypeface(this.getActivity(), Util.FONT), Typeface.BOLD);
        lblPhil.setTypeface(Util.setTypeface(this.getActivity(), Util.FONT), Typeface.BOLD);
        lblTitle.setTypeface(Util.setTypeface(this.getActivity(), Util.FONT), Typeface.BOLD);
        lblStatus.setTypeface(Util.setTypeface(this.getActivity(), Util.FONT), Typeface.BOLD);
        lblGender.setTypeface(Util.setTypeface(this.getActivity(), Util.FONT), Typeface.BOLD);
        lblCompany.setTypeface(Util.setTypeface(this.getActivity(), Util.FONT), Typeface.BOLD);
        lblDesignation.setTypeface(Util.setTypeface(this.getActivity(), Util.FONT), Typeface.BOLD);
        lblDepartment.setTypeface(Util.setTypeface(this.getActivity(), Util.FONT), Typeface.BOLD);
        lblPagibig.setTypeface(Util.setTypeface(this.getActivity(), Util.FONT), Typeface.BOLD);

    }

    private void setEmployeeInfo() {
        employee = Employee.getEmployeeInfo();
        imgURI = Constants.URL + employee.getProf_pic();
        Util.getInstance().displayImage(imgURI, imagAvatar, Util.imageLoadingListener);
        txtName.setText(employee.getSuffix() + " " + employee.getLastname() + ", " + employee.getFirstname() + " " + employee.getMiddlename());
        txtDepartment.setText(employee.getDepartment());
        txtDesignation.setText(employee.getDesignation());
        txtBday.setText(employee.getBirthdate());
        txtAge.setText(employee.getAge());
        txtStatus.setText(employee.getCivil());
        txtGender.setText(employee.getGender());
        txtCompany.setText(employee.getCompany());
        txtSSS.setText(employee.getSSS());
        txtTIN.setText(employee.getTIN());
        txtPhil.setText(employee.getPhilHealth());
        txtPagibig.setText(employee.getPAGIBIG());
    }

}
