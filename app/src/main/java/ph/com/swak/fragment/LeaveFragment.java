package ph.com.swak.fragment;

import android.content.Context;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.doomonafireball.betterpickers.numberpicker.NumberPickerBuilder;
import com.doomonafireball.betterpickers.numberpicker.NumberPickerDialogFragment;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import ph.com.swak.R;
import ph.com.swak.activity.LeaveActivity;
import ph.com.swak.adapter.LeaveAdapter;
import ph.com.swak.auth.RestCallback;
import ph.com.swak.auth.RestClient;
import ph.com.swak.auth.RestError;
import ph.com.swak.callback.NetworkCallback;
import ph.com.swak.model.Employee;
import ph.com.swak.model.Leave;
import ph.com.swak.receiver.NetworkReceiver;
import ph.com.swak.utils.Network;
import ph.com.swak.utils.Util;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by SWAK-THREE on 3/26/2015.
 */
public class LeaveFragment extends BaseFragment implements NumberPickerDialogFragment.NumberPickerDialogHandler, NetworkCallback {

    private LayoutInflater inflater;

    private FragmentActivity ctx;
    private Context context;

    private ArrayList<Leave> leaveList;
    private Leave leaveModel;
    private LeaveAdapter adapter;

    private NetworkReceiver networkStateReceiver;

    @InjectView(R.id.btnLeave)
    public Button btnLeave;

    @InjectView(R.id.txtCoveringYear)
    public TextView txtCoveringYear;

    @InjectView(R.id.leaveList)
    public ListView listView;

    @InjectView(R.id.ll_coveringYear)
    public LinearLayout ll_CoveringYear;

    @InjectView(R.id.viewflipper)
    public ViewFlipper viewFlipper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        ctx = (FragmentActivity) getActivity();
        networkStateReceiver = new NetworkReceiver();
        networkStateReceiver.addListener(this);
        context.registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    public void onStart() {
        super.onStart();
        hideMessage();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View leave = inflater.inflate(R.layout.fragment_leave, container, false);
        ButterKnife.inject(this, leave);
        btnLeave.setTypeface(Util.setTypeface(context, Util.FONT));

        txtCoveringYear.setText(Util.getCurrentYear() + "");
        txtCoveringYear.setTypeface(Util.setTypeface(this.getActivity(), Util.FONT), Typeface.BOLD);

        leaveList = new ArrayList<Leave>();

        addHeader(inflater);

        return leave;
    }

    private void initListAdapter() {
        adapter = new LeaveAdapter(getActivity(), leaveList);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void setLeave(String covyear) {
        showMessage(0);

        Log.i("get leave history", Employee.getEmployeeInfo().getEmpId() + " | " + covyear);
        RestClient.get().getLeaveHistory(Employee.getEmployeeInfo().getEmpId(), covyear, new RestCallback<ArrayList<Leave>>() {

            @Override
            public void success(ArrayList<Leave> leaves, Response response) {

                leaveList.clear();

                for (Leave leave : leaves) {
                    if (leave.getResult().contains("success")) {
                        leaveList.add(leave);
                        Log.i("Leave Info", leave.toJSON());
                    }
                }

                if (leaveList.size() <= 0) {
                    showMessage(1);
                } else {
                    hideMessage();
                }

                initListAdapter();
            }

            @Override
            public void failure(RestError error) {
                Log.e("Leave Error", error.toJSON());
                showMessage(2);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("Leave Failure", error.getUrl());
                showMessage(2);
                super.failure(error);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Network.isConnected(ctx)) {
            setLeave(Util.getCurrentYear() + "");
        } else {
            initListAdapter();
        }
    }

    private void addHeader(LayoutInflater inflater) {
        View header = inflater.inflate(R.layout.view_leave_header, null);

        ((TextView) header.findViewById(R.id.lblDateFiled)).setTypeface(Util.setTypeface(getActivity(), Util.FONT), Typeface.BOLD);
        ((TextView) header.findViewById(R.id.lblDesc)).setTypeface(Util.setTypeface(getActivity(), Util.FONT), Typeface.BOLD);
        ((TextView) header.findViewById(R.id.lblDays)).setTypeface(Util.setTypeface(getActivity(), Util.FONT), Typeface.BOLD);
        ((TextView) header.findViewById(R.id.lblStatus)).setTypeface(Util.setTypeface(getActivity(), Util.FONT), Typeface.BOLD);

        listView.addHeaderView(header);
    }

    private void showMessage(int i) {
        viewFlipper.setVisibility(View.VISIBLE);
        viewFlipper.setDisplayedChild(i);
    }

    private void hideMessage() {
        listView.setVisibility(View.VISIBLE);
        viewFlipper.setVisibility(View.GONE);
    }

    @Override
    public void onDialogNumberSet(int reference, int number, double decimal, boolean isNegative, double fullNumber) {
        txtCoveringYear.setText(number + "");
        start(number);
    }


    private void start(int covyear) {
        if (Network.isConnected(context)) {
            setLeave(covyear + "");
        } else {
            showMessage(2);
            initListAdapter();
        }
    }

    @OnClick(R.id.btnLeave)
    public void addLeave() {
        Util.startActivity(getActivity(), LeaveActivity.class, false);
    }

    @OnClick(R.id.ll_coveringYear)
    public void setCoveringYear() {
        NumberPickerBuilder npb = new NumberPickerBuilder()
                .setFragmentManager(ctx.getSupportFragmentManager())
                .setStyleResId(R.style.BetterPickersDialogFragment_Light)
                .setMinNumber(2010)
                .setMaxNumber(Util.getCurrentYear())
                .setLabelText("Covering Year")
                .setDecimalVisibility(View.INVISIBLE)
                .setPlusMinusVisibility(View.INVISIBLE).addNumberPickerDialogHandler(this);
        npb.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        context.unregisterReceiver(networkStateReceiver);
    }

    @Override
    public void networkAvailable() {
        setLeave(Integer.valueOf(txtCoveringYear.getText().toString()) + "");
    }

    @Override
    public void networkUnavailable() {
        showMessage(2);
        leaveList = new ArrayList<Leave>();
        initListAdapter();
    }
}
