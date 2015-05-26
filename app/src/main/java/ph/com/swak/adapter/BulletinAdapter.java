package ph.com.swak.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ph.com.swak.R;
import ph.com.swak.model.Bulletin;
import ph.com.swak.utils.Constants;
import ph.com.swak.utils.Util;

/**
 * Created by SWAK-THREE on 3/27/2015.
 */
public class BulletinAdapter extends BaseAdapter {

    private ArrayList<Bulletin> bulletins;
    private Context context;
    private Bulletin bulletin;
    private LayoutInflater inflater;

    public BulletinAdapter(Context context, ArrayList<Bulletin> bulletins) {
        this.bulletins = bulletins;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.view_bulletin, parent, false);
            holder = new ViewHolder(convertView);

            convertView.setTag(holder);
        }

        bulletin = (Bulletin) getItem(position);

        holder.txtDesc.setText(bulletin.getDesc());
        holder.txtDesc.setTypeface(Util.setTypeface(context, Util.FONT));

        Util.getInstance().displayImage(Constants.URL + bulletin.getImage_url(), holder.imageView, Util.options(), new SimpleImageLoadingListener() {

            @Override
            public void onLoadingStarted(String imageUri, View view) {
                holder.progressBar.setProgress(0);
                holder.progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                holder.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                holder.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                super.onLoadingCancelled(imageUri, view);
            }
        }, new ImageLoadingProgressListener() {
            @Override
            public void onProgressUpdate(String s, View view, int current, int total) {
                holder.progressBar.setProgress(Math.round(100.0f * current / total));
            }
        });

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Bulletin getItem(int position) {
        return bulletins.get(position);
    }

    @Override
    public int getCount() {
        return bulletins.size();
    }

    static class ViewHolder {

        @InjectView(R.id.imgBulletin)
        ImageView imageView;

        @InjectView(R.id.progress)
        ProgressBar progressBar;

        @InjectView(R.id.txtDesc)
        TextView txtDesc;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

}
