package ph.com.swak.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.text.format.DateFormat;
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
import com.doomonafireball.betterpickers.radialtimepicker.RadialTimePickerDialog;

import org.joda.time.DateTime;

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
import ph.com.swak.utils.Util;
import ph.com.swak.view.FormText;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by SWAK-THREE on 4/7/2015.
 */
public class OTNDActivity extends ActionBarActivity implements CalendarDatePickerDialog.OnDateSetListener, RadialTimePickerDialog.OnTimeSetListener, DialogCallback {

    private CalendarDatePickerDialog date_from, date_to;
    private RadialTimePickerDialog hrs_from, hrs_to;

    private StringBuilder from, to;
    private int typeid;

    private static final String DATE_PICKER_FROM = "fragment_date_picker_from";
    private static final String DATE_PICKER_TO = "fragment_date_picker_to";

    private static final String HRS_PICKER_FROM = "fragment_hrs_picker_from";
    private static final String HRS_PICKER_TO = "fragment_hrs_picker_to";

    //@InjectView(R.id.progress)
    //SmoothProgressBar progressBar;

    @InjectView(R.id.rl_OTND)
    RelativeLayout rl_otnd;

    @InjectView(R.id.txtTitle)
    TextView txtTitle;

    @InjectView(R.id.txtCurrentDate)
    TextView txtCurrentDate;

    @InjectView(R.id.ll_type)
    public LinearLayout ll_type;

    @InjectView(R.id.btnClose)
    public Button btnClose;

    @InjectView(R.id.btnFile)
    public Button btnFile;

    @InjectView(R.id.ll_from)
    public LinearLayout ll_from;

    @InjectView(R.id.ll_to)
    public LinearLayout ll_to;

    @InjectView(R.id.lblReason)
    public TextView lblReason;

    @InjectView(R.id.lblNoOfHrs)
    public TextView lblNoOfHrs;

    @InjectView(R.id.txtNoOfHours)
    public FormText txtNoOfHours;

    @InjectView(R.id.txtReason)
    public FormText txtReason;

    @InjectView(R.id.txtFrom)
    public TextView txtFrom;

    @InjectView(R.id.txtTo)
    public TextView txtTo;

    @InjectView(R.id.txtType)
    public TextView txtType;

    @Override
    protected void onStart() {
        super.onStart();
        YoYo.with(Techniques.BounceInUp).duration(1200).playOn(rl_otnd);
        txtCurrentDate.setText(Util.getCurrentDate("MMM dd, yyyy"));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otnd);
        ButterKnife.inject(this);
        setContent();
    }

    private void setContent() {

        txtTitle.setTypeface(Util.setTypeface(this, Util.FONT), Typeface.BOLD);
        txtCurrentDate.setTypeface(Util.setTypeface(this, Util.FONT), Typeface.BOLD);

        txtTo.setTypeface(Util.setTypeface(this, Util.FONT));
        txtFrom.setTypeface(Util.setTypeface(this, Util.FONT));
        lblReason.setTypeface(Util.setTypeface(this, Util.FONT), Typeface.BOLD);
        lblNoOfHrs.setTypeface(Util.setTypeface(this, Util.FONT), Typeface.BOLD);

        txtType.setTypeface(Util.setTypeface(this, Util.FONT), Typeface.BOLD);
        txtNoOfHours.setTypeface(Util.setTypeface(this, Util.FONT), Typeface.BOLD);
        txtReason.setTypeface(Util.setTypeface(this, Util.FONT), Typeface.BOLD);
    }

    @OnClick(R.id.btnFile)
    public void btnFile() {
        if (validate()) {

            btnFile.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.lada), null);

            Drawable[] drawables = btnFile.getCompoundDrawables();
            for (Drawable drawable : drawables) {
                if (drawable != null && drawable instanceof Animatable) {
                    ((Animatable) drawable).start();
                }
            }

            btnFile.setEnabled(false);
            file_otnd();
        }else{

        }
    }

    private void file_otnd() {

        RestClient.get().fileOTND(Employee.getEmployeeInfo().getEmpId(), String.valueOf(typeid), Util.getCurrentDate("yyyy-MM-dd"), txtFrom.getText().toString(), txtTo.getText().toString(), txtNoOfHours.getText().toString(), txtReason.getText().toString(), new RestCallback<Responses>() {
            @Override
            public void failure(RestError error) {
                Log.i("File Leave Error", error.toJSON());
                Util.showDialogErrorMessage(OTNDActivity.this, OTNDActivity.this);
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
                Util.showDialogErrorMessage(OTNDActivity.this, OTNDActivity.this);
            }
        });

    }

    @OnClick(R.id.btnClose)
    public void btnClose() {
        finish();
    }

    @OnClick(R.id.ll_type)
    public void type() {

        final CharSequence leaveTypes[] = new CharSequence[]{"OVERTIME", "NIGHT DIFFERENTIAL"};

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Leave Type");
        builder.setItems(leaveTypes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                txtType.setText(leaveTypes[which]);
                typeid = which;
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
                .newInstance(OTNDActivity.this, now.getYear(), now.getMonthOfYear() - 1,
                        now.getDayOfMonth());
        date_from.setYearRange(2010, Util.getCurrentYear());
        date_from.show(fm, DATE_PICKER_FROM);
    }

    @OnClick(R.id.ll_to)
    public void to_date() {
        FragmentManager fm = this.getSupportFragmentManager();
        DateTime now = DateTime.now();
        date_to = CalendarDatePickerDialog.newInstance(OTNDActivity.this, now.getYear(), now.getMonthOfYear() - 1, now.getDayOfMonth());
        date_to.setYearRange(2010, Util.getCurrentYear());
        date_to.show(fm, DATE_PICKER_TO);
    }

    private boolean validate() {

        if (txtNoOfHours.getText().toString().isEmpty()) {
            txtNoOfHours.requestFocus();
            txtNoOfHours.setError(null, Util.addIcon(this, R.drawable.ic_action_info_outline));
            YoYo.with(Techniques.Shake).duration(700).playOn(txtNoOfHours);
            return false;
        } else {
            txtNoOfHours.setError(null);
        }

        if (txtReason.getText().toString().isEmpty()) {
            txtReason.requestFocus();
            txtReason.setError(null, Util.addIcon(this, R.drawable.ic_action_info_outline));
            YoYo.with(Techniques.Shake).duration(700).playOn(txtReason);

            return false;
        } else {
            txtReason.setError(null);
        }

        if (txtType.getText().toString().contains("/")) {
            type();
            return false;
        }else{
            Toast.makeText(this, "....", Toast.LENGTH_SHORT).show();
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
            if (Util.hoursBetween(Util.convertStringtoDatetime(dte_from), Util.convertStringtoDatetime(dte_to)) <= 0) {
                if (Util.minuteBetween(Util.convertStringtoDatetime(dte_from), Util.convertStringtoDatetime(dte_to)) <= 0) {
                    to_date();
                }
                return false;
            }
        }

        return true;
    }


    @Override
    public void onDateSet(CalendarDatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
        switch (dialog.getTag()) {
            case DATE_PICKER_FROM:
                from = new StringBuilder();
                from.append(Util.convertDate(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year));

                DateTime now = DateTime.now();
                hrs_from = RadialTimePickerDialog
                        .newInstance(OTNDActivity.this, now.getHourOfDay(), now.getMinuteOfHour(),
                                DateFormat.is24HourFormat(OTNDActivity.this));

                hrs_from.show(getSupportFragmentManager(), HRS_PICKER_FROM);

                break;
            case DATE_PICKER_TO:
                to = new StringBuilder();
                to.append(Util.convertDate(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year));

                now = DateTime.now();
                hrs_to = RadialTimePickerDialog
                        .newInstance(OTNDActivity.this, now.getHourOfDay(), now.getMinuteOfHour(),
                                DateFormat.is24HourFormat(OTNDActivity.this));
                hrs_to.show(getSupportFragmentManager(), HRS_PICKER_TO);

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

        hrs_from = (RadialTimePickerDialog) getSupportFragmentManager().findFragmentByTag(HRS_PICKER_FROM);
        hrs_to = (RadialTimePickerDialog) getSupportFragmentManager().findFragmentByTag(HRS_PICKER_TO);
        if (hrs_from != null && hrs_to != null) {
            hrs_from.setOnTimeSetListener(this);
        }
    }

    @Override
    public void onTimeSet(RadialTimePickerDialog dialog, int hourOfDay, int minute) {
        switch (dialog.getTag()) {
            default:
                break;

            case HRS_PICKER_FROM:
                from.append("  " + Util.convertTime(hourOfDay + ":" + minute));
                txtFrom.setText(from.toString());
                break;
            case HRS_PICKER_TO:
                to.append("  " + Util.convertTime(hourOfDay + ":" + minute));
                txtTo.setText(to.toString());
                break;
        }
    }

    @Override
    public void positiveSelectionCallback(View v) {

    }

    @Override
    public void negativeSelectionCallback(View v) {

    }

    @Override
    public void neutralSelectionCallback(View v) {

    }
}
