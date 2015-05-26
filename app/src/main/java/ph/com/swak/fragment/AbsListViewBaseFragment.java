package ph.com.swak.fragment;

import android.content.Intent;
import android.widget.AbsListView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import ph.com.swak.activity.SwakActivity;
import ph.com.swak.utils.Constants;

/**
 * Created by SWAK-THREE on 4/6/2015.
 */
public class AbsListViewBaseFragment extends BaseFragment {

    protected AbsListView listView;
    protected boolean pauseOnScroll = false;
    protected boolean pauseOnFling = true;

    @Override
    public void onResume() {
        super.onResume();
    }

    protected void startImagePagerActivity(int position, int index) {
        Intent intent = new Intent(getActivity(), SwakActivity.class);
        intent.putExtra(Constants.Extra.FRAGMENT_INDEX, BulletinActivity.INDEX);
        intent.putExtra(Constants.Extra.IMAGE_POSITION, position);
        startActivity(intent);
    }

    private void applyScrollListener() {
        listView.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), pauseOnScroll, pauseOnFling));
    }
}
