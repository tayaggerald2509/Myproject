package ph.com.swak;

import android.os.Handler;
import android.app.Application;
import android.content.Context;

import com.activeandroid.ActiveAndroid;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by SWAK-THREE on 3/26/2015.
 */
public class Swak extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);

        initImageLoader(getApplicationContext());
    }

    public static void initImageLoader(Context context) {

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisk(true).handler(new Handler()) .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context).defaultDisplayImageOptions(defaultOptions).build();
        ImageLoader.getInstance().init(config);
    }
}
