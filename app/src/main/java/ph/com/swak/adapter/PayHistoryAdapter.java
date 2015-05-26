package ph.com.swak.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ph.com.swak.R;
import ph.com.swak.model.PayHistory;
import ph.com.swak.utils.Util;

/**
 * Created by SWAK-THREE on 4/7/2015.
 */
public class PayHistoryAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<PayHistory> payHistories;
    private LayoutInflater inflater;
    private PayHistory model;

    public PayHistoryAdapter(Context context, ArrayList<PayHistory> payHistories) {
        this.context = context;
        this.payHistories = payHistories;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;

        if (convertView == null) {

            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.view_payhistory, parent, false);
            holder = new ViewHolder(convertView);

            holder.paramPeriod.setTypeface(Util.setTypeface(context, Util.FONT));
            holder.paramGross.setTypeface(Util.setTypeface(context, Util.FONT));
            holder.paramDeduc.setTypeface(Util.setTypeface(context, Util.FONT));
            holder.paramNet.setTypeface(Util.setTypeface(context, Util.FONT));

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        model = (PayHistory) getItem(position);

        if (position % 2 == 1) {
            holder.ll_row.setBackgroundColor(context.getResources().getColor(R.color.table_bg));
        } else {
            holder.ll_row.setBackgroundColor(Color.WHITE);
        }

        holder.paramPeriod.setText(model.getPeriod());
        holder.paramGross.setText(model.getGross());
        holder.paramDeduc.setText(model.getDeductions());
        holder.paramNet.setText(model.getNetpay());

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public PayHistory getItem(int position) {
        return payHistories.get(position);
    }

    @Override
    public int getCount() {
        return payHistories.size();
    }

    static class ViewHolder {

        @InjectView(R.id.row)
        LinearLayout ll_row;

        @InjectView(R.id.txtPeriod)
        TextView paramPeriod;

        @InjectView(R.id.txtGross)
        TextView paramGross;

        @InjectView(R.id.txtDeduc)
        TextView paramDeduc;

        @InjectView(R.id.txtNet)
        TextView paramNet;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }


    public void clear() {
        this.payHistories.clear();
        this.notifyDataSetChanged();
    }
}
