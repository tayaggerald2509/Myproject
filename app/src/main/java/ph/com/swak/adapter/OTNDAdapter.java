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
import ph.com.swak.model.OTND;
import ph.com.swak.utils.Util;

/**
 * Created by SWAK-THREE on 4/7/2015.
 */
public class OTNDAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<OTND> otnds;
    private LayoutInflater inflater;
    private OTND model;

    public OTNDAdapter(Context context, ArrayList<OTND> otnds) {
        this.context = context;
        this.otnds = otnds;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;

        if (convertView == null) {

            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.view_otnd, parent, false);
            holder = new ViewHolder(convertView);

            holder.paramStatus.setTypeface(Util.setTypeface(context, Util.FONT), Typeface.BOLD);
            holder.paramNoOfHours.setTypeface(Util.setTypeface(context, Util.FONT));
            holder.paramType.setTypeface(Util.setTypeface(context, Util.FONT));
            holder.paramDateFiled.setTypeface(Util.setTypeface(context, Util.FONT));

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        model = (OTND) getItem(position);

        if (position % 2 == 1) {
            holder.ll_row.setBackgroundColor(context.getResources().getColor(R.color.table_bg));
        } else {
            holder.ll_row.setBackgroundColor(Color.WHITE);
        }

        holder.paramNoOfHours.setText(model.getNo_of_hours());
        holder.paramType.setText(model.getTypedesc());
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
    public OTND getItem(int position) {
        return otnds.get(position);
    }

    @Override
    public int getCount() {
        return otnds.size();
    }

    static class ViewHolder {

        @InjectView(R.id.row)
        LinearLayout ll_row;

        @InjectView(R.id.txtType)
        TextView paramType;

        @InjectView(R.id.txtDateFiled)
        TextView paramDateFiled;

        @InjectView(R.id.txtHours)
        TextView paramNoOfHours;

        @InjectView(R.id.txtStatus)
        TextView paramStatus;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }


    public void clear() {
        this.otnds.clear();
        this.notifyDataSetChanged();
    }

}
