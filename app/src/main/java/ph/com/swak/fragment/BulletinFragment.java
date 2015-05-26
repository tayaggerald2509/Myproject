package ph.com.swak.fragment;

import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.etsy.android.grid.StaggeredGridView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ph.com.swak.R;
import ph.com.swak.adapter.BulletinAdapter;
import ph.com.swak.auth.RestCallback;
import ph.com.swak.auth.RestClient;
import ph.com.swak.auth.RestError;
import ph.com.swak.callback.NetworkCallback;
import ph.com.swak.model.Bulletin;
import ph.com.swak.receiver.NetworkReceiver;
import ph.com.swak.utils.Constants;
import ph.com.swak.utils.Util;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by SWAK-THREE on 3/26/2015.
 */
public class BulletinFragment extends AbsListViewBaseFragment implements NetworkCallback {

    public static final int INDEX = 1;

    @InjectView(R.id.bulletinList)
    public StaggeredGridView gridView;

    private BulletinAdapter adapter;
    private NetworkReceiver networkStateReceiver;
    private ArrayList<Bulletin> bulletinsList;

    static final String[] numbers = new String[]{
            "A", "B", "C", "D", "E",
            "F", "G", "H", "I", "J",
            "K", "L", "M", "N", "O",
            "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z"};


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        networkStateReceiver = new NetworkReceiver();
        networkStateReceiver.addListener(this);
        getActivity().registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View bulletin = inflater.inflate(R.layout.fragment_bulletin, container, false);
        ButterKnife.inject(this, bulletin);

        bulletinsList = new ArrayList<Bulletin>();

        return bulletin;
    }

    private void initListAdapter() {
        adapter = new BulletinAdapter(getActivity(), bulletinsList);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startImagePagerActivity(position, INDEX);
            }
        });

    }

    private void setBulletins() {

        RestClient.get().getBulletin(new RestCallback<ArrayList<Bulletin>>() {

            @Override
            public void success(ArrayList<Bulletin> bulletins, Response response) {
                bulletinsList.addAll(bulletins);

                for (Bulletin bulletin : bulletins) {
                    Log.i("Bulletin", bulletin.toJSON());
                }

                initListAdapter();
            }

            @Override
            public void failure(RestError error) {
                Log.e("Bulletin Error", error.toJSON());
            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
            }
        });
    }

    public class ImageAdapter extends BaseAdapter {

        private LayoutInflater inflater;
        private ArrayList<Bulletin> bulletins;
        private Bulletin bulletin;

        public ImageAdapter(ArrayList<Bulletin> bulletins) {
            this.bulletins = bulletins;
            inflater = LayoutInflater.from(getActivity());
        }

        @Override
        public int getCount() {
            return bulletins.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            View view = convertView;
            if (view == null) {
                view = inflater.inflate(R.layout.view_bulletin, parent, false);
                holder = new ViewHolder();
                assert view != null;
                holder.imageView = (ImageView) view.findViewById(R.id.imgBulletin);
                holder.progressBar = (ProgressBar) view.findViewById(R.id.progress);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            bulletin = (Bulletin) getItem(position);

            ImageLoader.getInstance()
                    .displayImage(Constants.URL + bulletin.getImage_url(), holder.imageView, Util.options(), new SimpleImageLoadingListener() {
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
                    }, new ImageLoadingProgressListener() {
                        @Override
                        public void onProgressUpdate(String imageUri, View view, int current, int total) {
                            holder.progressBar.setProgress(Math.round(100.0f * current / total));
                        }
                    });

            return view;
        }
    }

    static class ViewHolder {
        ImageView imageView;
        ProgressBar progressBar;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void networkAvailable() {
        setBulletins();
    }

    @Override
    public void networkUnavailable() {
        
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(networkStateReceiver);
    }
}
