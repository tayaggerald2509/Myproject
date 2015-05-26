package ph.com.swak.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;

import ph.com.swak.R;
import ph.com.swak.adapter.BulletinAdapter;
import ph.com.swak.auth.RestCallback;
import ph.com.swak.auth.RestClient;
import ph.com.swak.auth.RestError;
import ph.com.swak.model.Bulletin;
import ph.com.swak.utils.Constants;
import ph.com.swak.utils.Network;
import ph.com.swak.utils.Util;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by SWAK-THREE on 4/6/2015.
 */
public class BulletinActivity extends BaseFragment {

    public static final int INDEX = 2;

    private BulletinAdapter adapter;
    private ArrayList<String> bulletinsList;
    private ViewPager pager;

    public BulletinActivity() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.view_pager, container, false);
        pager = (ViewPager) rootView.findViewById(R.id.pager);

        if(Network.isConnected(getActivity()))
            setBulletins();

        return rootView;
    }

    private class ImageAdapter extends PagerAdapter {

        private ArrayList<String> bulletins;
        private Context context;
        private Bulletin bulletin;
        private LayoutInflater inflater;

        public ImageAdapter(Context context, ArrayList<String> bulletins) {
            this.bulletins = bulletins;
            this.context = context;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return bulletins.size();
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View imageLayout = inflater.inflate(R.layout.item_pager_image, view, false);
            assert imageLayout != null;
            ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image);
            final ProgressBar spinner = (ProgressBar) imageLayout.findViewById(R.id.loading);

            Util.getInstance().displayImage(Constants.URL + bulletins.get(position), imageView, Util.options(), new SimpleImageLoadingListener() {

                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    spinner.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    spinner.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    spinner.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {
                    super.onLoadingCancelled(imageUri, view);
                }
            });


            view.addView(imageLayout, 0);
            return imageLayout;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }
    }

    private void setBulletins() {

        RestClient.get().getBulletin(new RestCallback<ArrayList<Bulletin>>() {

            @Override
            public void success(ArrayList<Bulletin> bulletins, Response response) {
                //bulletinsList.addAll(bulletins);

                bulletinsList = new ArrayList<String>();

                int i = 1;
                for (Bulletin bulletin : bulletins) {
                    Log.i("Bulletin", bulletin.toJSON());
                    Log.i("Bulletin", bulletin.getImage_url() + " | " + i);
                    bulletinsList.add(bulletin.getImage_url());
                    i++;
                }

                pager.setAdapter(new ImageAdapter(getActivity(), bulletinsList));
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


}
