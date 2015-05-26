package ph.com.swak.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.doomonafireball.betterpickers.calendardatepicker.CalendarDatePickerDialog;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import ph.com.swak.R;
import ph.com.swak.auth.Responses.Responses;
import ph.com.swak.auth.RestCallback;
import ph.com.swak.auth.RestClient;
import ph.com.swak.auth.RestError;
import ph.com.swak.callback.DialogCallback;
import ph.com.swak.model.Employee;
import ph.com.swak.model.Leave;
import ph.com.swak.model.LeaveType;
import ph.com.swak.utils.Util;
import ph.com.swak.view.FormText;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by SWAK-THREE on 3/27/2015.
 */
public class LeaveActivity extends ActionBarActivity implements CalendarDatePickerDialog.OnDateSetListener, DialogCallback {

    private static final String DATE_PICKER_FROM = "fragment_date_picker_from";
    private static final String DATE_PICKER_TO = "fragment_date_picker_to";

    private List<LeaveType> leavetype;
    private List<CharSequence> type;
    private CalendarDatePickerDialog date_from, date_to;
    private Leave leave;

    @InjectView(R.id.rl_leave)
    RelativeLayout rd_leave;

    @InjectView(R.id.ll_leavetype)
    LinearLayout ll_leavetype;

    @InjectView(R.id.ll_from)
    LinearLayout ll_from;

    @InjectView(R.id.ll_to)
    LinearLayout ll_to;

    @InjectView(R.id.txtTitle)
    TextView txtTitle;

    @InjectView(R.id.txtCurrentDate)
    TextView txtCurrentDate;

    @InjectView(R.id.txtLeaveType)
    TextView txtLeaveType;

    @InjectView(R.id.txtTo)
    TextView txtTo;

    @InjectView(R.id.txtFrom)
    TextView txtFrom;

    @InjectView(R.id.lblNoOfDays)
    TextView lblNoOfDays;

    @InjectView(R.id.lblReason)
    TextView lblReason;

    @InjectView(R.id.txtNoOfDays)
    FormText txtNoOfDays;

    @InjectView(R.id.txtReason)
    FormText txtReason;

    @InjectView(R.id.btnFile)
    Button btnFile;

    @InjectView(R.id.btnClose)
    Button btnClose;

    @Override
    protected void onStart() {
        super.onStart();
        YoYo.with(Techniques.BounceInUp).duration(1200).playOn(rd_leave);
        txtCurrentDate.setText(Util.getCurrentDate("MMM dd, yyyy"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave);
        ButterKnife.inject(this);

        setContent();
    }

    private void setContent() {

        txtTitle.setTypeface(Util.setTypeface(this, Util.FONT), Typeface.BOLD);
        txtCurrentDate.setTypeface(Util.setTypeface(this, Util.FONT), Typeface.BOLD);

        txtTo.setTypeface(Util.setTypeface(this, Util.FONT));
        txtFrom.setTypeface(Util.setTypeface(this, Util.FONT));
        lblReason.setTypeface(Util.setTypeface(this, Util.FONT), Typeface.BOLD);
        lblNoOfDays.setTypeface(Util.setTypeface(this, Util.FONT), Typeface.BOLD);

        txtLeaveType.setTypeface(Util.setTypeface(this, Util.FONT), Typeface.BOLD);
        txtNoOfDays.setTypeface(Util.setTypeface(this, Util.FONT), Typeface.BOLD);
        txtReason.setTypeface(Util.setTypeface(this, Util.FONT), Typeface.BOLD);
    }


    @OnClick(R.id.ll_leavetype)
    public void leave_type() {

        type = new ArrayList<>();
        for (LeaveType leaveType : LeaveType.getLeaveType()) {
            Log.i("LeaveType", leaveType.getType());
            type.add(leaveType.getType());
        }


        final CharSequence leaveTypes[] = type.toArray(new CharSequence[type.size()]);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Leave Type");
        builder.setItems(leaveTypes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                txtLeaveType.setText(leaveTypes[which]);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    @OnClick(R.id.ll_from)
    public void from_date() {
        FragmentManager fm = this.getSupportFragmentManager();
        DateTime now = DateTime.now();
        date_from = CalendarDatePickerDialog
                .newInstance(LeaveActivity.this, now.getYear(), now.getMonthOfYear() - 1,
                        now.getDayOfMonth());
        date_from.setYearRange(2010, Util.getCurrentYear());
        date_from.show(fm, DATE_PICKER_FROM);
    }

    @OnClick(R.id.ll_to)
    public void to_date() {
        FragmentManager fm = this.getSupportFragmentManager();
        DateTime now = DateTime.now();
        date_to = CalendarDatePickerDialog.newInstance(LeaveActivity.this, now.getYear(), now.getMonthOfYear() - 1, now.getDayOfMonth());
        date_to.setYearRange(2010, Util.getCurrentYear());
        date_to.show(fm, DATE_PICKER_TO);
    }

    @OnClick(R.id.btnClose)
    public void close(){
        finish();
    }

    @OnClick(R.id.btnFile)
    public void fileLeave() {
        if (validate()) {
            //progressBar.setVisibility(View.VISIBLE);

            btnFile.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.lada), null);

            Drawable[] drawables = btnFile.getCompoundDrawables();
            for (Drawable drawable : drawables) {
                if (drawable != null && drawable instanceof Animatable) {
                    ((Animatable) drawable).start();
                }
            }

            btnFile.setEnabled(false);
            file_Leave();
        }
    }

    @Override
    public void onDateSet(CalendarDatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
        switch (dialog.getTag()) {
            case DATE_PICKER_FROM:
                txtFrom.setText(Util.convertDate(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year));
                break;
            case DATE_PICKER_TO:
                txtTo.setText(Util.convertDate(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year));
                break;
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        // Example of reattaching to the fragment
        super.onResume();

        date_from = (CalendarDatePickerDialog) getSupportFragmentManager().findFragmentByTag(DATE_PICKER_FROM);
        date_to = (CalendarDatePickerDialog) getSupportFragmentManager().findFragmentByTag(DATE_PICKER_TO);
        if (date_from != null && date_to != null) {
            date_from.setOnDateSetListener(this);
        }

    }

    @Override
    public void onBackPressed() {
        return;
    }

    @Override
    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
    }

    private boolean validate() {

        if (txtNoOfDays.getText().toString().isEmpty()) {
            txtNoOfDays.requestFocus();
            txtNoOfDays.setError(null, Util.addIcon(this, R.drawable.ic_action_info_outline));
            YoYo.with(Techniques.Shake).duration(700).playOn(txtNoOfDays);
            return false;
        } else {
            txtNoOfDays.setError(null);
        }

        if (txtReason.getText().toString().isEmpty()) {
            txtReason.requestFocus();
            txtReason.setError(null, Util.addIcon(this, R.drawable.ic_action_info_outline));
            YoYo.with(Techniques.Shake).duration(700).playOn(txtReason);

            return false;
        } else {
            txtReason.setError(null);
        }

        if (txtLeaveType.getText().toString().contains("Leave Type")) {
            leave_type();
            return false;
        }

        if (txtFrom.getText().toString().contains("-")) {
            from_date();
            return false;
        } else if (txtTo.getText().toString().contains("-")) {
            to_date();
            return false;
        } else {
            String dte_from = txtFrom.getText().toString();
            String dte_to = txtTo.getText().toString();
            long days = Util.daysBetween(Util.convertStringtoDate(dte_from), Util.convertStringtoDate(dte_to));

            if (days < 0) {
                to_date();
                return false;
            }
        }

        return true;
    }

    private void file_Leave() {

        RestClient.get().fileleave(Employee.getEmployeeInfo().getEmpId(), LeaveType.getLeavetypeId(txtLeaveType.getText().toString()), Util.getCurrentDate("yyyy-MM-dd"), txtFrom.getText().toString(), txtTo.getText().toString(), txtNoOfDays.getText().toString(), txtReason.getText().toString(), new RestCallback<Responses>() {
            @Override
            public void failure(RestError error) {
                Log.i("File Leave Error", error.toJSON());
                Util.showDialogErrorMessage(LeaveActivity.this, LeaveActivity.this);
            }

            @Override
            public void success(Responses responses, Response response) {
                Log.i("File Leave", responses.getResult());
                if (responses.getResult().contains("success")) {
                    Log.i("File Leave", responses.toJSON());
                    finish();
                    Toast.makeText(getApplicationContext(), responses.getResult(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.i("File Leave Errors", error.getUrl());
                super.failure(error);
                Util.showDialogErrorMessage(LeaveActivity.this, LeaveActivity.this);
            }
        });

    }

    private void clear() {
        txtLeaveType.setText("Leave Type");
        txtFrom.setText("--/--/----");
        txtTo.setText("--/--/----");
        txtNoOfDays.setText("");
        txtReason.setText("");
    }

    @Override
    public void positiveSelectionCallback(View v) {
        file_Leave();
    }

    @Override
    public void negativeSelectionCallback(View v) {

    }

    @Override
    public void neutralSelectionCallback(View v) {

    }
}
