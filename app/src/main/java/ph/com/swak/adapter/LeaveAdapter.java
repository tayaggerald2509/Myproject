package ph.com.swak.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
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
import ph.com.swak.model.Leave;
import ph.com.swak.utils.Util;

/**
 * Created by SWAK-THREE on 3/27/2015.
 */
public class LeaveAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Leave> leave;
    private LayoutInflater inflater;
    private Leave model;

    public LeaveAdapter(Context context, ArrayList<Leave> leave) {
        this.context = context;
        this.leave = leave;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;

        if (convertView == null) {

            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.view_leave, parent, false);

            holder = new ViewHolder(convertView);

            holder.paramStatus = (TextView) convertView.findViewById(R.id.txtStatus);
            holder.paramNoOfDays = (TextView) convertView.findViewById(R.id.txtDays);
            holder.paramDesc = (TextView) convertView.findViewById(R.id.txtDesc);
            holder.paramDateFiled = (TextView) convertView.findViewById(R.id.txtDateFiled);

            holder.paramStatus.setTypeface(Util.setTypeface(context, Util.FONT), Typeface.BOLD);
            holder.paramNoOfDays.setTypeface(Util.setTypeface(context, Util.FONT));
            holder.paramDesc.setTypeface(Util.setTypeface(context, Util.FONT));
            holder.paramDateFiled.setTypeface(Util.setTypeface(context, Util.FONT));

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        model = (Leave) getItem(position);

        if (position % 2 == 1) {
            holder.ll_row.setBackgroundColor(context.getResources().getColor(R.color.table_bg));
        } else {
            holder.ll_row.setBackgroundColor(Color.WHITE);
        }

        holder.paramNoOfDays.setText(model.getNo_of_days());
        holder.paramDesc.setText(model.getDesc());
        holder.paramDateFiled.setText(model.getDate_filed());
        holder.paramStatus.setText(model.getStatus());

        if (model.getStatus().contentEquals("Disapproved")) {
            holder.paramStatus.setTextColor(context.getResources().getColor(R.color.danger));
        } else if (model.getStatus().contentEquals("For approval")) {
            holder.paramStatus.setTextColor(context.getResources().getColor(R.color.warning));
        } else {
            holder.paramStatus.setTextColor(context.getResources().getColor(R.color.success));
        }

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Leave getItem(int position) {
        return leave.get(position);
    }

    @Override
    public int getCount() {
        return leave.size();
    }

    static class ViewHolder {

        @InjectView(R.id.row)
        LinearLayout ll_row;

        @InjectView(R.id.txtDesc)
        TextView paramDesc;

        @InjectView(R.id.txtDateFiled)
        TextView paramDateFiled;

        @InjectView(R.id.txtDays)
        TextView paramNoOfDays;

        @InjectView(R.id.txtStatus)
        TextView paramStatus;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }


    public void clear() {
        this.leave.clear();
        this.notifyDataSetChanged();
    }

}
