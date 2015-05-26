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
import ph.com.swak.activity.OTNDActivity;
import ph.com.swak.adapter.OTNDAdapter;
import ph.com.swak.auth.RestCallback;
import ph.com.swak.auth.RestClient;
import ph.com.swak.auth.RestError;
import ph.com.swak.callback.NetworkCallback;
import ph.com.swak.model.Employee;
import ph.com.swak.model.OTND;
import ph.com.swak.receiver.NetworkReceiver;
import ph.com.swak.utils.Network;
import ph.com.swak.utils.Util;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by SWAK-THREE on 4/7/2015.
 */
public class OTNDFragment extends BaseFragment implements NumberPickerDialogFragment.NumberPickerDialogHandler, NetworkCallback {

    private Context context;
    private FragmentActivity ctx;

    private ArrayList<OTND> otndList;
    private OTNDAdapter adapter;

    private NetworkReceiver networkStateReceiver;

    @InjectView(R.id.otList)
    public ListView otList;

    @InjectView(R.id.txtCoveringYear)
    public TextView txtCoveringYear;

    @InjectView(R.id.ll_coveringYear)
    public LinearLayout ll_coveringYear;

    @InjectView(R.id.btnOT)
    public Button btnOT;

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

        View ot = inflater.inflate(R.layout.fragment_ot, container, false);
        ButterKnife.inject(this, ot);

        btnOT.setTypeface(Util.setTypeface(context, Util.FONT));

        txtCoveringYear.setText(Util.getCurrentYear() + "");
        txtCoveringYear.setTypeface(Util.setTypeface(this.getActivity(), Util.FONT), Typeface.BOLD);

        otndList = new ArrayList<OTND>();

        addHeader(inflater);

        return ot;
    }

    private void initListAdapter() {
        adapter = new OTNDAdapter(getActivity(), otndList);
        otList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void setOTND(String covyear) {
        showMessage(0);
        RestClient.get().getOTNDHistory(Employee.getEmployeeInfo().getEmpId(), covyear, new RestCallback<ArrayList<OTND>>() {

            @Override
            public void success(ArrayList<OTND> otnds, Response response) {

                otndList.clear();

                for (OTND otnd : otnds) {
                    if (!otnd.getDate_filed().isEmpty()) {
                        otndList.add(otnd);
                        Log.i("Leave Info", otnd.toJSON());
                    }
                }

                if (otndList.size() <= 0) {
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
            setOTND(Util.getCurrentYear() + "");
        } else {
            initListAdapter();
        }
    }

    private void addHeader(LayoutInflater inflater) {
        View header = inflater.inflate(R.layout.view_otnd_header, null);

        ((TextView) header.findViewById(R.id.lblDateFiled)).setTypeface(Util.setTypeface(getActivity(), Util.FONT), Typeface.BOLD);
        ((TextView) header.findViewById(R.id.lblType)).setTypeface(Util.setTypeface(getActivity(), Util.FONT), Typeface.BOLD);
        ((TextView) header.findViewById(R.id.lblHours)).setTypeface(Util.setTypeface(getActivity(), Util.FONT), Typeface.BOLD);
        ((TextView) header.findViewById(R.id.lblStatus)).setTypeface(Util.setTypeface(getActivity(), Util.FONT), Typeface.BOLD);

        otList.addHeaderView(header);
    }

    private void showMessage(int i) {
        viewFlipper.setVisibility(View.VISIBLE);
        viewFlipper.setDisplayedChild(i);
    }

    private void hideMessage() {
        viewFlipper.setVisibility(View.GONE);
    }

    @OnClick(R.id.btnOT)
    public void OT() {
        Util.startActivity(getActivity(), OTNDActivity.class, false);
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
    public void onDialogNumberSet(int reference, int number, double decimal, boolean isNegative, double fullNumber) {
        txtCoveringYear.setText(number + "");
        setOTND(number + "");
    }

    @Override
    public void networkAvailable() {
        setOTND(txtCoveringYear.getText().toString());
    }

    @Override
    public void networkUnavailable() {
        showMessage(2);
        otndList = new ArrayList<OTND>();
        initListAdapter();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        context.unregisterReceiver(networkStateReceiver);
    }
}
